/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Document;
import br.com.pucminas.debt.model.Metrica;
import br.com.pucminas.debt.util.HibernateUtil;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.model.TipoMetrica;
import br.com.pucminas.debt.model.ValorMetrica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Bárbara
 */
public class ProjetoDAOImpl implements ProjetoDAO, Serializable {

    @Override
    public void salvar(Projeto projeto) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.saveOrUpdate(projeto);
            session.getTransaction().commit();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Projeto " + projeto.getNome(), "Cadastrado com sucesso!"));
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Não foi possível cadastrar o projeto: " + e));
        } finally {
            session.close();
        }
    }

    @Override
    public void excluir(Projeto projeto) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.delete(projeto);
            session.getTransaction().commit();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Projeto " + projeto.getNome(), "Excluído com sucesso!"));
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Não foi possível excluir o projeto: " + e));
        } finally {
            session.close();
        }
    }

    @Override
    public void alterar(Projeto projeto) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            Projeto e = (Projeto) session.get(Projeto.class, projeto.getId());  
            e.setDescricao(projeto.getDescricao());  
            session.merge(e);
            session.getTransaction().commit();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Projeto " + projeto.getNome(), "Alterado com sucesso!"));
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Não foi possível alterar a estória: " + e));
        } finally {
            session.close();
        }
    }

    @Override
    public List<Projeto> listar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        List<Projeto> lista = new ArrayList<Projeto>();

        try {
            t = session.beginTransaction();
            Query query = session.createQuery("from Projeto");
            lista = (ArrayList<Projeto>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.close();
        }
        return lista;
    }
    
    @Override
    public Atualizacao ultimaAtualizacao(Projeto projeto){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        List<Atualizacao> lista = new ArrayList<Atualizacao>();

        try {
            t = session.beginTransaction();
            //Query query = session.createQuery("from Atualizacao");
            
            Query query = session.createQuery("from Atualizacao a "
                    + "where a.id = (select max(id) "
                    + "from Atualizacao "
                    + "where projeto.id = " + projeto.getId() + ")");
            
            lista = (ArrayList<Atualizacao>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.close();
        }
        return lista.get(0);
    }
    
    @Override
    public List<Metrica> metricasProjeto(Atualizacao atualizacao){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        List<Metrica> lista = new ArrayList<Metrica>();

        try {
            t = session.beginTransaction();
            //Query query = session.createQuery("from Atualizacao");
            
            Query query = session.createQuery("from Metrica m "
                    + "where m.atualizacao.id = "+atualizacao.getId()
                    + " and m.tipo in ('" + TipoMetrica.CE+ "','"+
                    TipoMetrica.NOC + "', '" + TipoMetrica.RMA  + "', '" + 
                    TipoMetrica.RMD + "', '" + TipoMetrica.NOI  + "', '" + 
                    TipoMetrica.TLOC + "', '" + TipoMetrica.NOP + "', '" + 
                    TipoMetrica.RMI + "', '" + TipoMetrica.CA + "')");
            
            lista = (ArrayList<Metrica>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.close();
        }
        return lista;
    }
    
    @Override
    public TreeNode arvoreProjeto(Projeto projeto){
        
        TreeNode root = new DefaultTreeNode(new Document("Files", "Package"), null);
        List<ValorMetrica> lista = null;
        HashMap<String, HashSet<String>>pacotes = new HashMap<>();
        
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
                
            Query query = session.createQuery("select v from ValorMetrica v "
                    + "inner join v.metrica m "
                    + "inner join m.atualizacao a "
                    + "where a.projeto.id = "+projeto.getId());
            
           lista = (ArrayList<ValorMetrica>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.close();
        }
        
        String pack, source;
        HashSet<String>sources;
        for(ValorMetrica v: lista){
            pack = v.getPack();
        
            if(pacotes.containsKey(pack)){
                pacotes.get(pack).add(v.getSource());
            }
            else{
                sources = new HashSet<String>();
                sources.add(v.getSource());
                pacotes.put(v.getPack(), sources);
            }
        }

        for(Map.Entry<String, HashSet<String>> pacote : pacotes.entrySet()){
            if(pacote.getKey() != null){
                TreeNode documents = new DefaultTreeNode(new Document(pacote.getKey(), "Package"), root);
                for(String s: (pacotes.get(pacote.getKey()))){
                   if(s != null && !s.equals("")) 
                   {
                       TreeNode work = new DefaultTreeNode(new Document(s, "Source"), documents);
                   } 
                }
            }
        }
        
        return root;
    }
    
    @Override
    public List<ValorMetrica> valoresProjeto(Projeto projeto){
        
        List<ValorMetrica> lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
                
            Query query = session.createQuery("select v from ValorMetrica v "
                    + "inner join v.metrica m "
                    + "inner join m.atualizacao a "
                    + "where a.projeto.id = "+projeto.getId());
            
            lista = (ArrayList<ValorMetrica>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.close();
        }
        
        
        return lista;
    }
    
    @Override
    public List<String> pacotesProjeto(Projeto projeto){
        
        List<String> lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
                
            Query query = session.createQuery("select distinct v.pack from ValorMetrica v "
                    + "inner join v.metrica m "
                    + "inner join m.atualizacao a "
                    + "where a.projeto.id = "+projeto.getId());
            
            lista = (ArrayList<String>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.close();
        }
        
        
        return lista;
    }
}
