package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculo", schema = "public")
public class Veiculo extends AbstractEntity{
   @Getter @Setter
    @Column(name = "placa_carro",nullable = false,unique = true, length = 10)
    private String placaCarro; //not null / unique /tamanho 10
    @Getter @Setter
    @Column(name = "ano_carro",nullable = false)
    private int anoCarro; //not null
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "modelo_carro", nullable = false)
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length = 20)
    private Cor cor;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo_veiculo", length = 6)
    private TipoVeiculo tipo;

    public Modelo getModelo() {
     return null;
    }

}
