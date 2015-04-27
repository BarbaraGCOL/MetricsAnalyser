/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.view;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
/**
 *
 * @author barbara.lopes
 */
@ManagedBean
public class ComboboxView {
    
    private boolean selected = false;
    private Map<Boolean, String> opcoes;
    
    @PostConstruct
    public void init() {
        opcoes = new HashMap<>();
        opcoes.put(true, "Exibir Árvore do Projeto");
        opcoes.put(false, "Exibir Estrutura do Projeto");
    }
    
    public boolean isSelected() {
        System.out.println("Opção: "+selected);
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public Map<Boolean, String> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(Map<Boolean, String> opcoes) {
        this.opcoes = opcoes;
    }
    
    public String textoOpcao(boolean opcao){
        return opcoes.get(opcao);
    }
 }