package com.solera.audamedic.operativa.mscargacomandas.messaging;

import com.solera.audamedic.operativa.mscargacomandas.exception.SendEventException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargaFinalizadaPubImpl implements CargaFinalizadaPub {

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;


    @Override
    public void sendCargaFinalizadaEvent(Long idCargaFinalizadaLog, String tipoCarga) throws SendEventException {
        final CargaFinalizadaEvent event = new CargaFinalizadaEvent(idCargaFinalizadaLog, tipoCarga);
        try {
            rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", event);
        } catch (AmqpException ex) {
            throw new SendEventException(ex);
        }
    }
}
