package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Audited
@Table(name = "veiculo", schema = "public")
@AuditTable(value = "veiculos_audit", schema = "audit")
public class Veiculo extends AbstractEntity{

    @Getter @Setter
    @Column(name = "placa_carro",nullable = false,unique = true, length = 10)
    private String placaCarro; //not null / unique /tamanho 10

    @Getter @Setter
    @Column(name = "ano_carro",nullable = false)
    private int anoCarro; //not null

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "modelo", nullable = false)
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", nullable = false)
    private Cor cor;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo_veiculo", nullable = false)
    private TipoVeiculo tipo;

}