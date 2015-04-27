/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.model;

import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_ATUALIZACAO;
import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_METRICA;
import java.io.Serializable;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author barbara.lopes
 */
@Entity
@Table(name = "Metrica")
public class Metrica implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id_"+SUFIXO_METRICA, nullable = false,unique = true)
    private int id;
    
    @Enumerated(EnumType.STRING) 
    private TipoMetrica tipo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_" + SUFIXO_ATUALIZACAO, nullable = false)
    private Atualizacao atualizacao;
    
    @OneToMany(mappedBy="metrica", cascade = ALL, fetch = FetchType.EAGER)
    List<ValorMetrica>valores;
 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoMetrica getTipo() {
        return tipo;
    }

    public void setTipo(TipoMetrica tipo) {
        this.tipo = tipo;
    }

    public Atualizacao getAtualizacao() {
        return atualizacao;
    }

    public void setAtualizacao(Atualizacao atualizacao) {
        this.atualizacao = atualizacao;
    }
    
    public List<ValorMetrica> getValores() {
        return valores;
    }

    public void setValores(List<ValorMetrica> valores) {
        this.valores = valores;
    }
    
//    public List<Parametro> getParametros() {
//        return parametros;
//    }
//
//    public void setParametros(List<Parametro> parametros) {
//        this.parametros = parametros;
//    }
}
