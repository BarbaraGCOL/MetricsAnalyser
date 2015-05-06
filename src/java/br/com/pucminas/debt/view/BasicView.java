/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.view;

import br.com.pucminas.debt.dao.ProjetoDAO;
import br.com.pucminas.debt.dao.impl.ProjetoDAOImpl;
import br.com.pucminas.debt.model.Document;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.service.DocumentService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.TreeNode;

/**
 *
 * @author BárbaraGabrielle
 */
@ManagedBean(name="ttBasicView")
@ViewScoped
public class BasicView implements Serializable {
     
    private TreeNode root;
    private ProjetoDAO dao = new ProjetoDAOImpl();
     
    private Document selectedDocument;
         
    @ManagedProperty("#{documentService}")
    private DocumentService service;
     
    @PostConstruct
    public void init() {
        root = service.createDocuments();
    }
 
    public TreeNode preencheEstrutura(Projeto projeto){
        return dao.arvoreProjeto(projeto);
    }
    
    public TreeNode getRoot() {
        return root;
    }
 
    public void setService(DocumentService service) {
        this.service = service;
    }
 
    public Document getSelectedDocument() {
        return selectedDocument;
    }
 
    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
}
