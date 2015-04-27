/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author barbara.lopes
 */
public enum TipoMetrica {
    
    PAR("Number of Parameters", "Qt Parâmetros"),
    NSF("Number of Static Attributes", "Qt Atributos Estáticos"),
    CE("Efferent Coupling", "Acoplamento"),
    SIX("Specialization Index", "Índice de Especialização"),
    NOC("Number of Classes", "Qt Classes"),
    NOF("Number of Attributes", "Qt Atributos"),
    RMA("Abstractness", "Abstração"),
    RMD("Normalized Distance", "Distância Normalizada"),
    NSM("Number of Static Methods", "Qt Métodos Estáticos"),
    NOI("Number of Interfaces", "Qt Interfaces"),
    TLOC("Total Lines of Code", "Qt Linhas Código"),
    WMC("Weighted methods per Class", "Métodos Pesados/Classe"),
    NOM("Number of Methods", "Qt Métodos"),
    DIT("Depth of Inheritance Tree", "Profundidade Árvore de Herança"),
    NOP("Number of Packages", "Qt Pacotes"),
    RMI("Instability", "Instabilidade"),
    VG("McCabe Cyclomatic Complexity", "Complexidade Ciclomática McCabe"),
    NBD("Nested Block Depth", "Blodo de Profundidade"),
    LCOM("Lack of Cohesion of Methods", "Falta de Coesão dos Métodos"),
    MLOC("Method Lines of Code", "Linhas de Código do Método"),
    NORM("Number of Overridden Methods", "Qt Métodos Sobrescritos"),
    CA("Afferent Coupling", "Acoplamento Aferente"),
    NSC("Number of Children", "Qt Filhos");
    
    private final String descricao;
    private final String descricaoPort;
    private static final Map<String, TipoMetrica> relations;  
    private static final Map<String, TipoMetrica> relations2;  
    
    TipoMetrica(String descricao, String descricaoPort){
        this.descricao = descricao;
        this.descricaoPort = descricaoPort;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public String getDescricaoPort() {
        return descricaoPort;
    }
    
    public static TipoMetrica getTipoPorDescr(String descr) {  
        return relations.get(descr);  
    }  
    
    public static TipoMetrica getTipoPorName(String name) {  
        return relations2.get(name);  
    }
    
    static {  
        relations = new HashMap<String, TipoMetrica>();  
        for(TipoMetrica m : values()) relations.put(m.getDescricao(), m);      
        
        relations2 = new HashMap<String, TipoMetrica>();  
        for(TipoMetrica m : values()) relations2.put(m.toString(), m);     
    }
}
