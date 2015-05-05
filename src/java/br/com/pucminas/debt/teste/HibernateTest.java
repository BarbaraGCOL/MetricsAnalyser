package br.com.pucminas.debt.teste;

import br.com.pucminas.debt.model.Autorizacao;
import br.com.pucminas.debt.dao.impl.AutorizacaoDAOImpl;
import br.com.pucminas.debt.model.Usuario;
import br.com.pucminas.debt.dao.impl.UsuarioDAOImpl;

import java.sql.SQLException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Bárbara
 */
public class HibernateTest {

    public static void main(String[] args) throws SQLException {

        Autorizacao autorizacaoUser=new Autorizacao();
        Autorizacao autorizacaoAdmin=new Autorizacao();
        
        autorizacaoUser.setNome("ROLE_USER");
        autorizacaoAdmin.setNome("ROLE_ADMIN");
        
        AutorizacaoDAOImpl daoAuto=new AutorizacaoDAOImpl();
        
        daoAuto.save(autorizacaoUser);
        daoAuto.save(autorizacaoAdmin);
        
        Usuario usuario=new Usuario();
        usuario.setNome("Administrador");
        usuario.setLogin("admin");
        usuario.setEmail("admin@gmail.com");
        usuario.setSenha("admin");
        UsuarioDAOImpl daoUsu=new UsuarioDAOImpl();
        daoUsu.salvar(usuario);
        System.out.println("Usuário: " + usuario.getLogin());
    
    }
}
