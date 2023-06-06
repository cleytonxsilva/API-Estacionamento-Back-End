package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Audited
@Table(name = "marca", schema = "public")
@AuditTable(value = "marcas_audit", schema = "audit")
public class Marca extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String marca;

    @Getter @Setter
    @OneToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<Modelo> modelo;
}
