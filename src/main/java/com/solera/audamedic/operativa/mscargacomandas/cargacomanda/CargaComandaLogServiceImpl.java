package com.solera.audamedic.operativa.mscargacomandas.cargacomanda;

import com.solera.audamedic.operativa.mscargacomandas.entity.CargaComandaLog;
import com.solera.audamedic.operativa.mscargacomandas.messaging.CargaFinalizadaPub;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargaComandaLogServiceImpl implements CargaComandaService {

    private final CargaComandaLogRepository cargaComandaLogRepository;
    private final CargaFinalizadaPub cargaFinalizadaPub;

    @Override
    public CargaComandaLog generateCargaComandaLog(Long idProveedor, String mips) {
        CargaComandaLog cargaComandaLog = new CargaComandaLog();
        cargaComandaLog.setProveedorId(idProveedor);
        cargaComandaLog.setMipsCargadas(mips);
        cargaComandaLog.setCargaComandaEstado("Cargada");
        cargaComandaLog = cargaComandaLogRepository.save(cargaComandaLog);
        cargaFinalizadaPub.sendCargaFinalizadaEvent(cargaComandaLog.getId(), "FTP");
        return cargaComandaLog;
    }
}