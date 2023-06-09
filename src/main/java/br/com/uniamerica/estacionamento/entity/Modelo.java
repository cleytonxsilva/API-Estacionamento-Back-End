package br.com.uniamerica.estacionamento.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Audited
@Table(name = "modelo", schema = "public")
@AuditTable(value = "modelos_audit", schema = "audit")
public class Modelo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "modelo",nullable = false, unique = true, length = 50)
    private String modelo;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "marca", nullable = false)
    private Marca marca;



}
