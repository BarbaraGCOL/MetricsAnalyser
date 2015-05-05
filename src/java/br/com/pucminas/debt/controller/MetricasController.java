/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.controller;

import br.com.pucminas.debt.dao.ProjetoDAO;
import br.com.pucminas.debt.dao.impl.ProjetoDAOImpl;
import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Document;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.model.TipoMetrica;
import br.com.pucminas.debt.model.ValorMetrica;
import br.com.pucminas.debt.view.MetricasView;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.mindmap.MindmapNode;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;
 
@ManagedBean
@SessionScoped
public class MetricasController implements Serializable {
     
    private MindmapNode root;
    private MindmapNode selectedNode;
    private Projeto projeto;
    private Map<String, Set<String>>pacotesProj = new HashMap<>();
    private Map<String, Set<String>>classesProj = new HashMap<>();
    ProjetoDAO dao = new ProjetoDAOImpl();

    private Map<String, String> metricas = new HashMap<>();
    private Map<String, Float> valoresMetricas = new HashMap<>();
    private TagCloudModel model;
    private List<ValorMetrica>valoresSelection;
    private Set<TipoMetrica> metricasFile;
    private String file;
    private String metrica;
    private List<ValorMetrica>valores;

    private TreeNode arvoreProjeto;
    private Document selectedDocument;
    
    public MetricasController() {
    }
    
    public MetricasController(Projeto projeto) {
 
    }
 
    public MindmapNode mapProjeto(Projeto projeto) {
        if(this.projeto == null || projeto.getId() != this.projeto.getId() || root == null){
            this.projeto = projeto;
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
            Logger.getLogger(MetricasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.file = this.selectedNode.getLabel();
        getModelFile();
    }
    
    public TagCloudModel getModelFile() {
        valoresSelection = dao.metricasFile(this.projeto, file);
        metricasFile = new HashSet<>();
        metricas = new HashMap<>();
        valoresMetricas = new HashMap<>();
        this.metrica = null;
        
        for(ValorMetrica v: valoresSelection){
            metricasFile.add(v.getMetrica().getTipo());
            metricas.put(v.getMetrica().getTipo().toString(), v.getMetrica().getTipo().getDescricaoPort());
            valoresMetricas.put(v.getMetrica().getTipo().toString(),v.getValor());
        }
        
        model = modelFile(metricasFile);
         
        return model;
    }
    
    public TagCloudModel modelFile(Set<TipoMetrica>tipos){
        model = new DefaultTagCloudModel();
        int count = 0;
        
        int []sizes = new int []{1,3,2,5,4,2,5,3,4,1,1,3,2,5,4,2,5,3,4,1,3,4,1};  
        
        for(TipoMetrica t: tipos){
            if(count % 2 == 0){
                model.addTag(new DefaultTagCloudItem(t.name(), sizes[count]));
            }
            else{
                model.addTag(new DefaultTagCloudItem(t.name(), "#", sizes[count]));
            }
            count ++;
        }
        
        return model;
    }
    
    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Métrica", TipoMetrica.getTipoPorName(item.getLabel()).getDescricaoPort());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        this.metrica = item.getLabel();
        this.valores = dao.valoresMetrica(this.projeto, this.metrica, file);
    }
    
    public String onSelectTable(){
        this.file = selectedDocument.getName();
        getModelFile();
        return redirecionaMetricasProjeto();
    }
    
    public LineChartModel initLineChart() {
        LineChartModel model = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel(getMetrica());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sfData = new SimpleDateFormat("yyyy-MM-dd");
        String data = null;
        Date datetime;
        
        if(this.metrica != null || !this.metrica.equals("")){
            for(ValorMetrica val: valores){
                try {
                    datetime = sf.parse(val.getMetrica().getAtualizacao().getData()+"");
                    data = sfData.format(datetime); 
                } catch (ParseException ex) {
                    Logger.getLogger(MetricasView.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                series1.set(data, val.getValor());
            }
            
            model.addSeries(series1);

            String nomeMetrica = TipoMetrica.getTipoPorName(getMetrica()).getDescricaoPort();
            model.setTitle("Evolução (" + nomeMetrica + ")");
            model.setZoom(true);
            model.setAnimate(true);
            model.setLegendPosition("se");
            model.setSeriesColors("FF6600");
            
            model.getAxis(AxisType.Y).setLabel("Valor");
            DateAxis axis = new DateAxis("Data");
            axis.setTickAngle(-50);
            axis.setMax(sfData.format(new Date()));
            axis.setTickFormat("%b %#d, %y");
            
            model.getAxes().put(AxisType.X, axis);
        }
        return model;
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
    
    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Map<String, String> getMetricas() {
        return metricas;
    }

    public void setMetricas(Map<String, String> metricas) {
        this.metricas = metricas;
    }

    public Map<String, Float> getValoresMetricas() {
        return valoresMetricas;
    }

    public void setValoresMetricas(Map<String, Float> valoresMetricas) {
        this.valoresMetricas = valoresMetricas;
    }

    public TagCloudModel getModel() {
        return model;
    }

    public void setModel(TagCloudModel model) {
        this.model = model;
    }

    public List<ValorMetrica> getValoresSelection() {
        return valoresSelection;
    }

    public void setValoresSelection(List<ValorMetrica> valoresSelection) {
        this.valoresSelection = valoresSelection;
    }

    public Set<TipoMetrica> getMetricasFile() {
        return metricasFile;
    }

    public void setMetricasFile(Set<TipoMetrica> metricasFile) {
        this.metricasFile = metricasFile;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    public String getMetrica() {
        return metrica;
    }

    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }
    public List<ValorMetrica> getValores() {
        return valores;
    }

    public void setValores(List<ValorMetrica> valores) {
        this.valores = valores;
    }

    public TreeNode getArvoreProjeto() {
        if(arvoreProjeto == null){
            setArvoreProjeto(dao.arvoreProjeto(this.projeto));
        }
        return arvoreProjeto;
    }

    public void setArvoreProjeto(TreeNode arvoreProjeto) {
        this.arvoreProjeto = arvoreProjeto;
    }
    
    public void viewDocumento(Document doc) { 
        this.selectedDocument = doc;
    }
    
    public Document getSelectedDocument() {
        return selectedDocument;
    }
 
    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
}