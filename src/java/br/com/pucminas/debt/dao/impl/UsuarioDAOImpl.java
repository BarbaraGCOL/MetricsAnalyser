package br.com.pucminas.debt.dao.impl;

import br.com.pucminas.debt.dao.UsuarioDAO;
import br.com.pucminas.debt.model.Autorizacao;
import br.com.pucminas.debt.model.Usuario;
import br.com.pucminas.debt.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void salvar(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        List<Autorizacao>autorizacoes=new ArrayList<Autorizacao>();
        try
        {
            t=session.beginTransaction();
            Autorizacao aut = (Autorizacao)session.get(Autorizacao.class, "ROLE_USER");
            autorizacoes.add(aut);
            if(usuario.getLogin().equals("admin"))
            {
                Autorizacao aut2 = (Autorizacao)session.get(Autorizacao.class, "ROLE_ADMIN");
                autorizacoes.add(aut2);
            }
            usuario.setAutorizacoes(autorizacoes);
            session.save(usuario);
            
            session.getTransaction().commit();
            
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage("Usuário "+usuario.getNome(), "Cadastrado com sucesso!")); 
//            
//            EmailController email=new EmailController();
//            email.enviarEmail(usuario);
        }
        catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!", "Não foi possível cadastrar o usuário: "+e));  
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.flush();
            session.close();

        }
    }

    @Override
    public void excluir(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        try
        {
            t=session.beginTransaction();
            session.delete(usuario);
            session.getTransaction().commit();
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage("Usuário "+usuario.getNome(), "Excluído com sucesso!")); 
        }
        catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!", "Não foi possível excluir o usuário: "+e));  
        } finally {
            session.close();
        }
    }

    @Override
    public void alterar(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        try
        {
            t=session.beginTransaction();
            session.update(usuario);
            session.getTransaction().commit();
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage("Usuário "+usuario.getNome(), "Alterado com sucesso!")); 
        }
        catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!", "Não foi possível alterar o usuário: "+e));  
        } finally {
            session.flush();
            session.close();
        }
    }

    @Override
    public List<Usuario> listar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        List<Usuario> lista=new ArrayList<Usuario>();
        
        try
        {
            t=session.beginTransaction();
            lista = (ArrayList<Usuario>) session.createQuery("from Usuario").list();  
            session.getTransaction().commit();
        }
        catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.flush();
            session.close();
        }
        return lista;
    }
    
    @Override
    public Usuario buscaLogin(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        Usuario usuario;
        Query query;

        try {
            t = session.beginTransaction();
            query = session.createQuery("select u from Usuario u where u.login=?1");
            query.setParameter("1", login);
            usuario = (Usuario) query.list().get(0);
            
            return usuario;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.flush();
            session.close();
        }
        return null;
    }
}