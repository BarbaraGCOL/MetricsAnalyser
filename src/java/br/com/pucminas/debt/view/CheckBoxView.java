/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
/**
 *
 * @author barbara.lopes
 */
@ManagedBean
@ViewScoped
public class CheckBoxView {
    
    private boolean value1 = false;  
    private boolean value2 = true;
 
    public boolean isValue1() {
        return value1;
    }
 
    public void setValue1(boolean value1) {
        this.value1 = value1;
    }
 
    public boolean isValue2() {
        return value2;
    }
 
    public void setValue2(boolean value2) {
        this.value2 = value2;
    }
     
    public void addMessage() {
        String summary = value2 ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
    
    boolean renderedTree = false;
    boolean renderedEstr = false;
    
    public boolean isRenderedEstr() {
        return renderedEstr;
    }

    public void setRenderedEstr(boolean rendered) {
        this.renderedEstr = rendered;
    }
    
    public boolean isRenderedTree() {
        return renderedTree;
    }

    public void setRenderedTree(boolean rendered) {
        this.renderedTree = rendered;
    }
    
    public void changeMarkTree(ValueChangeEvent vcEvent){

        renderedTree = Boolean.valueOf(vcEvent.getNewValue().toString()).booleanValue();
        System.out.println();

    }
    
    public void changeMarkEstr(ValueChangeEvent vcEvent){

        renderedEstr = Boolean.valueOf(vcEvent.getNewValue().toString()).booleanValue();
        System.out.println();

    }
}