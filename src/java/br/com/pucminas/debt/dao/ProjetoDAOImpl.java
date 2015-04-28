/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Document;
import br.com.pucminas.debt.util.HibernateUtil;
import br.com.pucminas.debt.model.Projeto;
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
    public TreeNode arvoreProjeto(Projeto projeto){
        Map<String, Set<String>>pacotesProj = new HashMap<>();
        Map<String, Set<String>>classesProj = new HashMap<>();
        
        estruturaProjeto(pacotesProj, classesProj, projeto);
        
        TreeNode root = new DefaultTreeNode(new Document("Files", "Pacote"), null);
        for(Map.Entry<String, Set<String>> pack : pacotesProj.entrySet()){
            TreeNode pacotes = new DefaultTreeNode(new Document(pack.getKey(), "Pacote"), root);
            for(String c: pacotesProj.get(pack.getKey())){
                TreeNode classes = new DefaultTreeNode(new Document(c, "Classe"), pacotes); 
                for(String m: classesProj.get(c)){
                    TreeNode metodos = new DefaultTreeNode(new Document(m, "Método"), classes); 
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
        
        
        return lista;
    }
    
    @Override
    public MindmapNode mapaProjeto(Map<String, Set<String>>pacotesProj, Map<String, Set<String>>classesProj, Projeto projeto){        
        MindmapNode root = new DefaultMindmapNode("Projeto " + projeto.getNome(), "Projeto", "FFCC00", false);

        MindmapNode pacote, classe, metodo;
        
        for(Map.Entry<String, Set<String>> pack : pacotesProj.entrySet()){
            if(pack.getKey() != null){
                pacote = new DefaultMindmapNode(pack.getKey(), pack.getKey(), "6e9ebf", true);
                 
                for(String c: pack.getValue()){
                    classe = new DefaultMindmapNode(c, c, "82c542", true);
                    for(String m: classesProj.get(c)){
                        metodo = new DefaultMindmapNode(m, m, "BC8F8F", true);
                        classe.addNode(metodo);
                    }
                    pacote.addNode(classe);
                }
                root.addNode(pacote);
            }
        }
        
        return root;
    }
    
    @Override
    public void estruturaProjeto(Map<String, Set<String>>pacotesProj, Map<String, Set<String>>classesProj, Projeto projeto){
        List<ValorMetrica> valoresProj = valoresProjeto(projeto);
        
        for(ValorMetrica val: valoresProj){
            if(val != null && val.getSource() != null){
                String []nameClass = val.getSource().split("\\.");
                if(val.getName().equals(val.getPack())){
                    if(pacotesProj.get(val.getPack()) == null){
                        pacotesProj.put(val.getPack(), new HashSet<>());
                    }
                }
                else
                    if(nameClass.length == 2 && nameClass[0].equals(val.getName())){
                        if(classesProj.get(val.getSource()) == null){
                            classesProj.put(val.getSource(), new HashSet<>());
                            if(pacotesProj.get(val.getPack()) == null){
                                Set<String>c = new HashSet<>();
                                pacotesProj.put(val.getPack(), c);
                            }
                            pacotesProj.get(val.getPack()).add(val.getSource());
                        }
                    }
                    else
                        if(!val.getName().equals(val.getSource())){
                            if(classesProj.get(val.getSource()) == null){
                                classesProj.put(val.getSource(), new HashSet<>());
                            }
                            classesProj.get(val.getSource()).add(val.getName());
                            if(pacotesProj.get(val.getPack()) == null){
                                Set<String>c = new HashSet<>();
                                pacotesProj.put(val.getPack(), c);
                            }
                            pacotesProj.get(val.getPack()).add(val.getSource());
                        }
            }
        }
    }
    
    @Override
    public List<ValorMetrica> metricasFile(Projeto projeto, String file){
        
        List<ValorMetrica> lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
                
            Query query = session.createQuery("select v from ValorMetrica v "
                    + "inner join v.metrica m "
                    + "inner join m.atualizacao a "
                    + "where a.projeto.id = " + projeto.getId() 
                    + " and v.name = "+file);
            
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
}
