package com.solera.audamedic.operativa.mscargacomandas.cargacomanda;

import com.solera.audamedic.operativa.mscargacomandas.entity.CargaComandaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CargaComandaLogRepository extends JpaRepository<CargaComandaLog, Long> {
}