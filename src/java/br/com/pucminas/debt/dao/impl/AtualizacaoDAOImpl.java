/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.dao.impl;

import br.com.pucminas.debt.dao.AtualizacaoDAO;
import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.util.HibernateUtil;
import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author barbara.lopes
 */
public class AtualizacaoDAOImpl implements AtualizacaoDAO, Serializable{
    
    @Override
    public void salvar (Atualizacao atualizacao){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            atualizacao.setId(0);
            session.save(atualizacao);
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
    public void excluir (Atualizacao atualizacao){
        
    }
    
    @Override
    public List<Atualizacao> listar(Projeto projeto){
        return null;
    }
    
    @Override
    public boolean atualizacaoExiste(Date data, List<Atualizacao>atualizacoes){
        String date1, date2;
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date1 = DATE_FORMAT.format(data);
        
        for(Atualizacao a: atualizacoes){
            date2 = DATE_FORMAT.format(a.getData());
            if(date1.equals(date2)){
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public List<String> listarDiretoriosAtualizacao(File file){
       return null; 
    }
}
