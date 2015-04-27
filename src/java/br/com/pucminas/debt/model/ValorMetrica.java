/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.model;

import br.com.pucminas.debt.pattern.DesignPatterns;
import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_VALOR_METRICA;
import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author BárbaraGabrielle
 */
@Entity
@Table(name = "Valor_Metrica")
public class ValorMetrica implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_"+SUFIXO_VALOR_METRICA, nullable = false,unique = true)
    private int id;

    @Column(name = "Name_"+SUFIXO_VALOR_METRICA)
    private String name;
    
    @Column(name = "Source_"+SUFIXO_VALOR_METRICA)
    private String source;
    
    @Column(name = "Pack_"+SUFIXO_VALOR_METRICA)
    private String pack;
    
    @Column(name = "Value_"+SUFIXO_VALOR_METRICA)
    private Float valor;
    
    @ManyToOne(cascade = PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_" + DesignPatterns.SUFIXO_METRICA, nullable = false)
    private Metrica metrica;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Metrica getMetrica() {
        return metrica;
    }

    public void setMetrica(Metrica metrica) {
        this.metrica = metrica;
    }
}