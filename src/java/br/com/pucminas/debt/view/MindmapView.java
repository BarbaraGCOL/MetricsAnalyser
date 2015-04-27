/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.view;

import br.com.pucminas.debt.dao.ProjetoDAO;
import br.com.pucminas.debt.dao.ProjetoDAOImpl;
import br.com.pucminas.debt.model.Metrica;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.model.ValorMetrica;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;
 
@ManagedBean
public class MindmapView implements Serializable {
     
    private MindmapNode root;
     
    private MindmapNode selectedNode;
    
    private Projeto projeto;
     
    public MindmapView() {
    }
    
    public MindmapView(Projeto projeto) {
 
    }
 
    public MindmapNode mapProjeto(Projeto projeto) {
        System.out.println(projeto);
        if(this.projeto == null || projeto.getId() != this.projeto.getId() || root == null){

            this.projeto = projeto;

            root = new DefaultMindmapNode("Projeto " + projeto.getNome(), "Projeto", "FFCC00", false);

            ProjetoDAO daoProj = new ProjetoDAOImpl();
            List<String>pacotesProjeto = daoProj.pacotesProjeto(projeto);
            MindmapNode pacote = null;
            MindmapNode metrica = null;

            MindmapNode pacotes = new DefaultMindmapNode("Packages", "Pacotes Projeto", "6e9ebf", true);

            for(String pack: pacotesProjeto){
                if(pack!=null){
                    pacote = new DefaultMindmapNode(pack, pack, "82c542", true);
                    pacotes.addNode(pacote);
                }
            }

            MindmapNode metricasGerais = new DefaultMindmapNode("Métricas", "Métricas do Projeto", "6e9ebf", true);

            List<Metrica>metricasProjeto = daoProj.metricasProjeto(daoProj.ultimaAtualizacao(projeto));

            for(Metrica m: metricasProjeto){
                if(!m.getValores().isEmpty()){
                    metrica = new DefaultMindmapNode(m.getTipo().name(), m.getValores().get(0).getValor(), "82c542", true);
                    metricasGerais.addNode(metrica);
                }
            }

            root.addNode(pacotes);
            root.addNode(metricasGerais);
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
        MindmapNode node = (MindmapNode) event.getObject();
        
        ProjetoDAO daoProj = new ProjetoDAOImpl();
        List<ValorMetrica>valores = daoProj.valoresProjeto(projeto);
        Set<String>pacotes = new TreeSet<>();
        
        if(node.getChildren().isEmpty()) {
            Object label = node.getLabel();
 
            if(label.equals("Packages")) {
                for(int i = 0; i < 25; i++) {
//                for(ValorMetrica val: valores){
//                    node.addNode(new DefaultMindmapNode("", "","82c542", false));
                    node.addNode(new DefaultMindmapNode("ns" + i + ".google.com", "Namespace " + i + " of Google", "82c542", false));
                }
            }
            else if(label.equals("Métricas")) {
                for(int i = 0; i < 18; i++) {
                    node.addNode(new DefaultMindmapNode("1.1.1."  + i, "IP Number: 1.1.1." + i, "fce24f", false));
                } 
            }
        }   
    }
     
    public void onNodeDblselect(SelectEvent event) {
        this.selectedNode = (MindmapNode) event.getObject();  
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, selectedNode.getLabel(), selectedNode.getData().toString()));
    }
}