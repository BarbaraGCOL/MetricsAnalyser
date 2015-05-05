package br.com.pucminas.debt.dao.impl;

import br.com.pucminas.debt.dao.AutorizacaoDAO;
import br.com.pucminas.debt.model.Autorizacao;
import br.com.pucminas.debt.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;

public class AutorizacaoDAOImpl implements AutorizacaoDAO, Serializable {

    private static final long serialVersionUID = 1L;
    
    private Class<Autorizacao> classe;
    private Session session;
    private Transaction t;
    /*
    public AutorizacaoDAOImpl(Class<Autorizacao> classe, Session session) {
        super();
        this.classe = classe;
        this.session = session;
    }*/
    
    
    @Override
    public void save(Autorizacao autorizacao) {
        session = HibernateUtil.getSessionFactory().openSession();
        t = session.beginTransaction();
        session.save(autorizacao);
        t.commit();
    }

    @Override
    public void update(Autorizacao autorizacao) {
        session = HibernateUtil.getSessionFactory().openSession();
        t = session.beginTransaction();
        session.update(autorizacao);
        t.commit();
    }

    @Override
    public void remove(Autorizacao autorizacao) {
        session = HibernateUtil.getSessionFactory().openSession();
        t = session.beginTransaction();
        session.delete(autorizacao);
        t.commit();
    }

    @Override
    public void merge(Autorizacao autorizacao) {
        session = HibernateUtil.getSessionFactory().openSession();
        t = session.beginTransaction();
        session.merge(autorizacao);
        t.commit();
    }

    @Override
    public Autorizacao getEntity(Serializable id) {
        Autorizacao entity = (Autorizacao)session.get(classe, id);
        return entity;
    }

    @Override
    public Autorizacao getEntityByDetachedCriteria(DetachedCriteria criteria) {
        Autorizacao entity = (Autorizacao)criteria.getExecutableCriteria(session).uniqueResult();
        return entity;
    }

        
    @Override
    public Autorizacao getEntityByHQLQuery(String stringQuery) {
        Query query = session.createQuery(stringQuery);        
        return (Autorizacao) query.uniqueResult();
    }

    @Override
    public List<Autorizacao> getListByDetachedCriteria(DetachedCriteria criteria) {
        return criteria.getExecutableCriteria(session).list();
    }
    
    @Override
    public List<Autorizacao> getEntities() {
        List<Autorizacao> enties = (List<Autorizacao>) session.createCriteria(classe).list();
        return enties;
    }    
    
}
