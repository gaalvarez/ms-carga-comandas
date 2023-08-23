package com.solera.audamedic.operativa.mscargacomandas.cargacomanda;

import com.solera.audamedic.operativa.mscargacomandas.entity.CargaComandaLog;

import java.util.Optional;

public interface CargaComandaService {
    CargaComandaLog generateCargaComandaLog(Long idProveedor, String mips);
}
