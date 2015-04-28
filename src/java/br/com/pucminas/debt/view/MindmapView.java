/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.view;

import br.com.pucminas.debt.dao.ProjetoDAO;
import br.com.pucminas.debt.dao.ProjetoDAOImpl;
import br.com.pucminas.debt.model.Projeto;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.MindmapNode;
 
@ManagedBean
@SessionScoped
public class MindmapView implements Serializable {
     
    private MindmapNode root;
    private MindmapNode selectedNode;
    private Projeto projeto;
    private Map<String, Set<String>>pacotesProj = new HashMap<>();
    private Map<String, Set<String>>classesProj = new HashMap<>();
    ProjetoDAO dao = new ProjetoDAOImpl();
    
    public MindmapView() {
    }
    
    public MindmapView(Projeto projeto) {
 
    }
 
    public MindmapNode mapProjeto(Projeto projeto) {
        if(this.projeto == null || projeto.getId() != this.projeto.getId() || root == null){
            if(pacotesProj.isEmpty()){
                dao.estruturaProjeto(pacotesProj, classesProj, projeto);
            }
            root = dao.mapaProjeto(pacotesProj, classesProj, projeto);
        }
        return root;
    }
    
    public MindmapNode getRoot() {
        return root;
    }
 
    public MindmapNode getSelectedNode() {
        return selectedNode;
    }
    public void setSelectedNode(MindmapNode selectedNode) {
        this.selectedNode = selectedNode;
    }
 
    @SuppressWarnings("empty-statement")
    public void onNodeSelect(SelectEvent event) {
        this.selectedNode = (MindmapNode) event.getObject();  
    }
     
    public String redirecionaMetricasProjeto() {
        return "metricas?faces-redirect=true";
    }
    
    public void onNodeDblselect(SelectEvent event) {
        this.selectedNode = (MindmapNode) event.getObject();  
        try { 
            FacesContext.getCurrentInstance().getExternalContext().redirect("metricas.jsf");
        } catch (IOException ex) {
            Logger.getLogger(MindmapView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Map<String, Set<String>> getPacotesProj() {
        return pacotesProj;
    }

    public void setPacotesProj(Map<String, Set<String>> pacotesProj) {
        this.pacotesProj = pacotesProj;
    }

    public Map<String, Set<String>> getClassesProj() {
        return classesProj;
    }

    public void setClassesProj(Map<String, Set<String>> classesProj) {
        this.classesProj = classesProj;
    }
}