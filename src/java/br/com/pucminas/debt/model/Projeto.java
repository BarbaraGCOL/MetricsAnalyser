/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.model;

import br.com.pucminas.debt.model.Usuario;
import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_PROJETO;
import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_USUARIO;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author barbara.lopes
 */
@Entity
@Table(name = "Projeto")
public class Projeto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_"+SUFIXO_PROJETO, nullable = false,unique = true)
    private int id;
    
    @Column(name = "Nom_" + SUFIXO_PROJETO, nullable = false)
    private String nome;
   
    @Column(name = "Desc_" + SUFIXO_PROJETO, nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Gerente", nullable = false)
    private Usuario gerente;
    
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "Usuario_Projeto", 
    joinColumns = { @JoinColumn(name = "Id" + SUFIXO_PROJETO) }, 
    inverseJoinColumns = { @JoinColumn(name = "Id" + SUFIXO_USUARIO) })
    private List<Usuario> equipe;
    
    @OneToMany(cascade=ALL, mappedBy="projeto", fetch = FetchType.EAGER)
    List<Atualizacao> atualizacoes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
    }

    public List<Usuario> getEquipe() {
        return equipe;
    }

    public void setEquipe(List<Usuario> equipe) {
        this.equipe = equipe;
    }

    public List<Atualizacao> getAtualizacoes() {
        return atualizacoes;
    }

    public void setAtualizacoes(List<Atualizacao> atualizacoes) {
        this.atualizacoes = atualizacoes;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Projeto other = (Projeto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
 
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.id;
        return hash;
    }
    
    @Override
    public String toString() {
        return "Projeto{" + "id=" + id + ", descricao=" + descricao + '}';
    }
}