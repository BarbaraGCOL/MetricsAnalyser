package br.com.pucminas.debt.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_USUARIO;
import static br.com.pucminas.debt.pattern.DesignPatterns.SUFIXO_AUTORIZACAO;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author Bárbara
 */
@Entity
@Table(name="Usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID =  1L;
    
    @Id
    @Column(name = "Log"+SUFIXO_USUARIO, nullable=false,unique=true)
    private String login;
    @Column(name = "Nom"+SUFIXO_USUARIO, nullable=false)
    private String nome;
    @Column(name = "Email"+SUFIXO_USUARIO, nullable=false)
    private String email;
    @Column(name = "Sen"+SUFIXO_USUARIO, nullable=false)
    private String senha;
    @Column(name = "Hab"+SUFIXO_USUARIO)
    private boolean habilitado=true;
    
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "UsuarioAutorizacao", 
               joinColumns = { @JoinColumn(name = "Log"+SUFIXO_USUARIO) }, 
               inverseJoinColumns = { @JoinColumn(name = "Nom"+SUFIXO_AUTORIZACAO) })
    private List<Autorizacao> autorizacoes;

    public Usuario() {
        
    }

    public Usuario(String login, String senha, String nome,String email, boolean isHabilitado, List<Autorizacao> autorizacoes) {
        this.login = login;
        this.senha = senha;
        this.habilitado = isHabilitado;
        this.autorizacoes = autorizacoes;
        this.nome=nome;
        this.email=email;
    }

    public List<Autorizacao> getAutorizacoes() {
        return autorizacoes;
    }

    public void setAutorizacoes(List<Autorizacao> autorizacoes) {
        this.autorizacoes = autorizacoes;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

