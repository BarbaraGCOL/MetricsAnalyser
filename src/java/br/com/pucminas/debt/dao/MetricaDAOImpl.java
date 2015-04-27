/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Metrica;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.util.HibernateUtil;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author barbara.lopes
 */
public class MetricaDAOImpl implements MetricaDAO, Serializable{
    
    @Override
    public void salvar (Metrica metrica){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.save(metrica);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    @Override
    public void excluir (Metrica metrica){
        
    }
}
