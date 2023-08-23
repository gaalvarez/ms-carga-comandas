package com.solera.audamedic.operativa.mscargacomandas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CargaComandaLog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargaComandaLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carga_comanda_id")
    private Long id;

    @JoinColumn(name = "proveedor_id")
    private Long proveedorId;

    @JoinColumn(name = "carga_comanda_estado")
    private String cargaComandaEstado;

    @JoinColumn(name = "mips_cargadas")
    private String mipsCargadas;
}
