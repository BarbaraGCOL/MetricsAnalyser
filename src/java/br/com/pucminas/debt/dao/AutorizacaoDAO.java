package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Autorizacao;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public interface AutorizacaoDAO {
    
    void save (Autorizacao autorizacao);
    void update (Autorizacao autorizacao);
    void remove (Autorizacao autorizacao);
    void merge (Autorizacao autorizacao);
    Autorizacao getEntity(Serializable id);
    Autorizacao getEntityByDetachedCriteria(DetachedCriteria criteria);
    Autorizacao getEntityByHQLQuery(String stringQuery);
    List<Autorizacao> getEntities();
    List<Autorizacao> getListByDetachedCriteria(DetachedCriteria criteria);    
    
}
