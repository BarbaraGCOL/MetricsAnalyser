/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.view;

/**
 *
 * @author barbara.lopes
 */
import br.com.pucminas.debt.dao.ProjetoDAO;
import br.com.pucminas.debt.dao.ProjetoDAOImpl;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.model.ValorMetrica;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.mindmap.MindmapNode;
 
@ManagedBean
public class ChartView implements Serializable {
 
    private LineChartModel lineChart;
    private String metrica;
    private ProjetoDAO dao;
    private List<ValorMetrica>valores;
    
    @PostConstruct
    public void init() {
        dao = new ProjetoDAOImpl();
        valores = new ArrayList<>();
    }
 
    public LineChartModel getLineChart() {
        return lineChart;
    }
 
    public String getMetrica() {
        return metrica;
    }

    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }
    
    public LineChartModel initLinearModel(Projeto projeto, String metrica, MindmapNode file) {
        lineChart = new LineChartModel();
        if(metrica != null & !metrica.equals("")){
        valores = dao.valoresMetrica(projeto, metrica, file.getLabel());
        float maior = -200.0f, menor = -200.0f;
        Date maiorData = new Date(), menorData = null;
        int count = 0;
        
        
        LineChartModel model = new LineChartModel();
        
//        if(this.metrica.equals("") || !this.metrica.equals(metrica)){
            if(valores != null && !valores.isEmpty()){
//            this.metrica = metrica;
            LineChartSeries series1 = new LineChartSeries();
            series1.setLabel("Series 1");
            Float valor;
            Date data;
            
            for(ValorMetrica v: valores){
                data = v.getMetrica().getAtualizacao().getData();
                valor = v.getValor();
                series1.set(data, valor);
                if(valor > maior){
                    maior = valor;
                }
                if(v.getValor() < menor){
                    menor = v.getValor();
                } 
                if(count == 0){
                    menorData = data;
                }
                else{
                    if(data.before(menorData)){
                        menorData = data;
                    }
                }
                count ++;
            }
            

            model.addSeries(series1);
            lineChart = model;
            lineChart.setTitle("Gráfico de Evolução da Métrica " + metrica);
            lineChart.setAnimate(true);
            lineChart.setLegendPosition("se");
            Axis yAxis = lineChart.getAxis(AxisType.Y);
            yAxis.setMin(menor);
            yAxis.setMax(maior);

            yAxis.setMin(menorData);
            yAxis.setMax(maiorData);
            
            }
        }    
        return lineChart;
    }
    
}
