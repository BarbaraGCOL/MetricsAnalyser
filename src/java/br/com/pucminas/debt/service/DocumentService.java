/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.service;

import br.com.pucminas.debt.dao.ProjetoDAO;
import br.com.pucminas.debt.dao.impl.ProjetoDAOImpl;
import br.com.pucminas.debt.model.Document;
import br.com.pucminas.debt.model.Projeto;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author BárbaraGabrielle
 */
@ManagedBean(name = "documentService")
@ApplicationScoped
public class DocumentService {
    private final ProjetoDAO dao = new ProjetoDAOImpl();
    private Projeto projeto;
    private TreeNode arvore;
    
    public TreeNode arvore(Projeto projeto) {
        
        if(this.projeto == null || projeto.getId() != this.projeto.getId()){
            this.projeto = projeto;    
            Map<String, Set<String>>pacotesProj = new HashMap<>();
            Map<String, Set<String>>classesProj = new HashMap<>();

            dao.estruturaProjeto(pacotesProj, classesProj, projeto);

            arvore = new DefaultTreeNode(new Document("Files", "Folder"), null);

            for(Map.Entry<String, Set<String>> pack : pacotesProj.entrySet()){

                TreeNode pacotes = new DefaultTreeNode(new Document(pack.getKey(), "Folder"), arvore);
                for(String c: pacotesProj.get(pack.getKey())){
                    TreeNode classes = new DefaultTreeNode(new Document(c, "Folder"), pacotes); 
                    for(String m: classesProj.get(c)){
                        TreeNode metodos = new DefaultTreeNode(new Document(m, "Folder"), classes); 
                    }
                }
            }
        } 
        return arvore;
    }
    
}
