package com.solera.audamedic.operativa.mscargacomandas.messaging;

import com.solera.audamedic.operativa.mscargacomandas.exception.SendEventException;

import java.util.List;

public interface CargaFinalizadaPub {
    void sendCargaFinalizadaEvent(Long idCargaFinalizadaLog, String tipoCarga) throws SendEventException;
}
