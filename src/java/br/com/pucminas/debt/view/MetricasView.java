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
import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.model.TipoMetrica;
import br.com.pucminas.debt.model.ValorMetrica;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.mindmap.MindmapNode;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;
 
@ManagedBean
public class MetricasView {
     
    private TagCloudModel model;
    private LineChartModel lineChart;
    private String metrica;

    private ProjetoDAO dao;
    private List<Atualizacao> atualizacoesProjeto;
    private Map<String, Map<String, Float>> mapAtualizacoes;
    private Projeto projeto;
    
    @PostConstruct
    public void init() {
        dao = new ProjetoDAOImpl();
        mapAtualizacoes = new HashMap<>();
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
          
    public TagCloudModel getModel() {
        return model;
    }
     
    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Métrica", TipoMetrica.getTipoPorName(item.getLabel()).getDescricaoPort());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        String m = TipoMetrica.getTipoPorName(item.getLabel()).getDescricaoPort();
        this.metrica = m;
    }
    
    public String getMetrica() {
        return metrica;
    }

    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }
}
