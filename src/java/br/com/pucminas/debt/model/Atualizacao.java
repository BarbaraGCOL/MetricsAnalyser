/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.model;

import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_ATUALIZACAO;
import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_PROJETO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author barbara.lopes
 */
@Entity
@Table(name = "Atualizacao")
public class Atualizacao implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "Id_"+SUFIXO_ATUALIZACAO, nullable = false, unique = true)
    private int id;
    
    @Column(name = "Dat_" + SUFIXO_ATUALIZACAO, nullable = false)
    @Temporal(TemporalType.TIMESTAMP) 
    private Date data;
    
    @ManyToOne(cascade = PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_"+SUFIXO_PROJETO, nullable = false)
    private Projeto projeto;

    @OneToMany( cascade = PERSIST, mappedBy="atualizacao")
    private List<Metrica> metricas;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Metrica> getMetricas() {
        return metricas;
    }

    public void setMetricas(List<Metrica> metricas) {
        this.metricas = metricas;
    }
    
    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
}
