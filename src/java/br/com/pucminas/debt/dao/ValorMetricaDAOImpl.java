/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.ValorMetrica;
import br.com.pucminas.debt.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author barbara.lopes
 */
public class ValorMetricaDAOImpl implements ValorMetricaDAO, Serializable{
    
    @Override
    public void salvar (List<ValorMetrica> valoresMetrica){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            
            for(ValorMetrica val: valoresMetrica){
                session.save(val);
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    @Override
    public void excluir (ValorMetrica valorMetrica){
        
    }
}
