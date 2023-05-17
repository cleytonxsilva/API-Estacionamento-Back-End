package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalTime;
@Entity
@Audited
@Table(name = "condutor", schema = "public")
@AuditTable(value = "condutores_audit", schema = "audit")
public class Condutor extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome_condutor", nullable = false, length = 50)
    private String nomeCondutor;

    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true, length = 20)
    private String cpf;

    @Getter @Setter
    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @Getter @Setter
    @Column(name = "tempo_pago", nullable = false)
    private LocalTime tempoPago;

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto;

}
