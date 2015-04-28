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
import br.com.pucminas.debt.model.TipoMetrica;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;
 
@ManagedBean
public class MetricasView {
     
    private TagCloudModel model;
     
    @PostConstruct
    public void init() {
//        model = new DefaultTagCloudModel();
//        int count = 0;
//        
//        int []sizes = new int []{1,3,2,5,4,2,5,3,4,1,1,3,2,5,4,2,5,3,4,1,3,4,1};  
//        
//        for(TipoMetrica t: TipoMetrica.values()){
//            if(count % 2 == 0){
//                model.addTag(new DefaultTagCloudItem(t.name(), sizes[count]));
//            }
//            else{
//                model.addTag(new DefaultTagCloudItem(t.name(), "#", sizes[count]));
//            }
//            count ++;
//        }
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
    }
}
