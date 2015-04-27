package br.com.pucminas.debt.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_AUTORIZACAO;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 *
 * @author Bárbara
 */
@Entity
@Table(name="Autorizacao")
public class Autorizacao implements Serializable {

    private static final long serialVersionUID =  1L;
    
    @Id
    @Column(name = "Nom"+SUFIXO_AUTORIZACAO, nullable=false,unique=true)
    private String nome;
    
    @ManyToMany(mappedBy="autorizacoes")
    private List<Usuario> usuarios;

    public Autorizacao() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}

