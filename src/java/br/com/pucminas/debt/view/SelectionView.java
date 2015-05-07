/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.view;

/**
 *
 * @author BárbaraGabrielle
 */
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.service.DocumentService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.TreeNode;

@ManagedBean(name="treeSelectionView")
@ViewScoped
public class SelectionView implements Serializable {
     
    private TreeNode selectedNode;
     
    @ManagedProperty("#{documentService}")
    private DocumentService service;
     
    @PostConstruct
    public void init() {
    }
 
    public TreeNode arvore(Projeto projeto){
        return service.arvore(projeto);
    }
    
    public TreeNode getSelectedNode() {
        return selectedNode;
    }
 
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
 
    public void setService(DocumentService service) {
        this.service = service;
    }
}
