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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

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
        if(lista == null || lista.isEmpty()){
            return new Atualizacao();
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
                    if(s != null && !s.equals("")){
                        TreeNode work = new DefaultTreeNode(new Document(s, "Source"), documents); 
                    }
                }
            }
        }
        
        return root;
    }
    
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
    
    public MindmapNode estruturaProjeto(Projeto projeto){
        Map<String, List<String>> pacotes = new HashMap<String, List<String>>();
        Map<String, List<String>> classes = new HashMap<String, List<String>>();
        
//       Atualizacao ultimaAtualizacao = ultimaAtualizacao(projeto);
        
        List<ValorMetrica> lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
                
            Query query = session.createQuery("select distinct v from ValorMetrica v "
                    + "inner join v.metrica m "
                    + "inner join m.atualizacao a "
                    + "where a.id = (select max(id) "
                    + "from Atualizacao "
                    + "where projeto.id = " + projeto.getId() + ")");
            
            lista = (ArrayList<ValorMetrica>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.close();
        }
        
        for(ValorMetrica val: lista){
            System.out.println("Métrica: "+val.getName());
            String [] s = (val.getSource().split("."));
            if(val.getName() != null && val.getName().equals(val.getPack())){
                if(pacotes.get(val.getPack()) == null){
                    pacotes.put(val.getPack(), new ArrayList<>());
                }
            }
            else
                if(val.getName() != null && s.length == 2 && s[0].equals(val.getName())){
                    if(pacotes.get(val.getPack()) == null){
                        List<String>classesPack = new ArrayList<>();
                        classesPack.add(val.getSource());
                        pacotes.put(val.getPack(), classesPack);
                    }  
                    else{
                        pacotes.get(val.getPack()).add(val.getSource());
                    }
                }
                else{
                    if(val.getName() != null && classes.get(val.getSource()) == null){
                        List<String>metodosPack = new ArrayList<>();
                        metodosPack.add(val.getName());
                        classes.put(val.getSource(), metodosPack);
                    }
                    else{
                        classes.get(val.getSource()).add(val.getName());
                    }
                }
        }
        
        MindmapNode root;
        
        root = new DefaultMindmapNode("Projeto " + projeto.getNome(), "Projeto", "FFCC00", false);
        
        MindmapNode pacotesProj = new DefaultMindmapNode("Packages", "Pacotes Projeto", "6e9ebf", true);

        MindmapNode pacote = null;
        MindmapNode classe = null;
        MindmapNode metodo = null;
        
        for(Map.Entry<String, List<String>> pack : pacotes.entrySet()){
            pacote = new DefaultMindmapNode(pack.getKey(), pack.getKey(), "82c542", true);
            for(String c: pack.getValue()){
                classe = new DefaultMindmapNode(c, c, "fce24f", true);
                
                for(String m: classes.get(c)){
                    metodo = new DefaultMindmapNode(m, m, "FFCC00", true);
                    classe.addNode(metodo);
                }
                pacote.addNode(classe);
            }
            pacotesProj.addNode(pacote);
        }
            
        root.addNode(pacotesProj);
        
        return root;
    }
}
