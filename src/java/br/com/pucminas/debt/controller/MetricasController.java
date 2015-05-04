/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.controller;

import br.com.pucminas.debt.dao.ProjetoDAO;
import br.com.pucminas.debt.dao.ProjetoDAOImpl;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.model.TipoMetrica;
import br.com.pucminas.debt.model.ValorMetrica;
import br.com.pucminas.debt.view.MetricasView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.mindmap.MindmapNode;
import org.primefaces.model.tagcloud.TagCloudModel;

/**
 *
 * @author barbara.lopes
 */
@ManagedBean
@SessionScoped
public class MetricasController implements Serializable{    
    private Map<String, String> metricas = new HashMap<>();
    private Map<String, Float> valoresMetricas = new HashMap<>();
    private TagCloudModel model;
    private List<ValorMetrica>valoresSelection;
    private Set<TipoMetrica> metricasFile;
    private LineChartModel lineChart;
    private boolean chartRendered;

    //  @ManagedProperty("#{param.projeto}")
    private Projeto projeto;

    /*DAOs*/
    private ProjetoDAO dao = new ProjetoDAOImpl();
    
    @PostConstruct
    public void init(){
       chartRendered = false;
    }
    
    public void attrListener(ActionEvent event){
 
	Projeto p = (Projeto)event.getComponent().getAttributes().get("projeto");
 
    }
    
    public TagCloudModel modelFile(MindmapNode selection, Projeto projetoSelecionado) {
        MetricasView view = new MetricasView();
        valoresSelection = dao.metricasFile(projetoSelecionado, selection.getLabel());
        metricasFile = new HashSet<>();
        metricas = new HashMap<>();
        valoresMetricas = new HashMap<>();
        for(ValorMetrica v: valoresSelection){
            metricasFile.add(v.getMetrica().getTipo());
            metricas.put(v.getMetrica().getTipo().toString(), v.getMetrica().getTipo().getDescricaoPort());
            valoresMetricas.put(v.getMetrica().getTipo().toString(),v.getValor());
        }
        model = view.modelFile(metricasFile);
        return model;
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
    
    public Set<TipoMetrica> getMetricasFile(){
        return metricasFile;
    }
    
    public List<ValorMetrica> getValoresSelection() {
        return valoresSelection;
    }

    public void setValoresSelection(ArrayList<ValorMetrica> valoresSelection) {
        this.valoresSelection = valoresSelection;
    }
    
    public void setModel(TagCloudModel model) {
        this.model = model;
    }
    
    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
    
    public ProjetoDAO getDao() {
        return dao;
    }

    public void setDao(ProjetoDAO dao) {
        this.dao = dao;
    }
    
    public LineChartModel getLineChart() {
        return lineChart;
    }

    public void setLineChart(LineChartModel lineChart) {
        this.lineChart = lineChart;
    }
    
    public boolean isChartRendered() {
        return chartRendered;
    }

    public void setChartRendered(boolean chartRendered) {
        this.chartRendered = chartRendered;
    }

}
