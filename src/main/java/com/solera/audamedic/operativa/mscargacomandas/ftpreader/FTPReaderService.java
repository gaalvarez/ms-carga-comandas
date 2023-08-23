package com.solera.audamedic.operativa.mscargacomandas.ftpreader;

import com.solera.audamedic.operativa.mscargacomandas.cargacomanda.CargaComandaService;
import com.solera.audamedic.operativa.mscargacomandas.messaging.CargaFinalizadaPub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

record DatosArchivo (Long idProveedor, String mips) {}


@Component
@Slf4j
@RequiredArgsConstructor
public class FTPReaderService implements ApplicationRunner {

    private final CargaComandaService cargaComandaService;
    private List<DatosArchivo> datosArchivos = Arrays.asList(new DatosArchivo(1L, "A, B, C"), new DatosArchivo(2L, "D, E , F"),new DatosArchivo(2L, "G, H, I"));

    @Override
    public void run(ApplicationArguments args) throws Exception {
    //Aqui iría toda la lógica de leer los archivos y enviar uno a uno el evento o como se plantee,
        // lo que he puesto aquí es solo para el demo
        for (DatosArchivo datosArchivo :
                datosArchivos) {
            log.info("Guardando y enviando cargacomandalog");
            cargaComandaService.generateCargaComandaLog(datosArchivo.idProveedor(), datosArchivo.mips());
        }
    }

}
