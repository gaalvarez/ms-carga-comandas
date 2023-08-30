package com.solera.audamedic.operativa.mscargacomandas.messaging;

import com.solera.audamedic.operativa.mscargacomandas.exception.SendEventException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CargaFinalizadaPubImpl implements CargaFinalizadaPub {

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    public CargaFinalizadaPubImpl(RabbitTemplate rabbitTemplate, @Qualifier("exchange") FanoutExchange fanoutExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.fanoutExchange = fanoutExchange;
    }

    @Override
    public void sendCargaFinalizadaEvent(Long idCargaFinalizadaLog, String tipoCarga) throws SendEventException {
        final CargaFinalizadaEvent event = new CargaFinalizadaEvent(idCargaFinalizadaLog, tipoCarga);
        try {
            rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", event, new MessagePostProcessor() {
                @Override
                public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                    message.getMessageProperties().setMessageId(UUID.randomUUID().toString());
                    return message;
                }
            });
        } catch (AmqpException ex) {
            throw new SendEventException(ex);
        }
    }
}
