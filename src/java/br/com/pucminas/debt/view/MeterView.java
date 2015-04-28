/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.view;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author barbara.lopes
 */
@ManagedBean
public class MeterView implements Serializable {
    private MeterGaugeChartModel meterGaugeModel2;
 
    @PostConstruct
    public void init() {
        createMeterGaugeModels();
    }
 
    public MeterGaugeChartModel getMeterGaugeModel2() {
        return meterGaugeModel2;
    }
 
    private MeterGaugeChartModel initMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>(){{
            add(2);
            add(5);
            add(10);
            add(20);
        }};
         
        return new MeterGaugeChartModel(15, intervals);
    }
 
    private void createMeterGaugeModels() {
        meterGaugeModel2 = initMeterGaugeModel();
        meterGaugeModel2.setTitle("Complexidade Ciclomática Método");
        meterGaugeModel2.setSeriesColors("66cc66,93b75f,E7E658,cc6666");
        meterGaugeModel2.setGaugeLabel("Complexidade Ciclomática");
        meterGaugeModel2.setGaugeLabelPosition("bottom");
        meterGaugeModel2.setShowTickLabels(false);
        meterGaugeModel2.setLabelHeightAdjust(110);
        meterGaugeModel2.setIntervalOuterRadius(100);
    }
 
}
