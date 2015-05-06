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
    private ProjetoDAO dao = new ProjetoDAOImpl();
    private TreeNode arvoreProjeto;
    
    public TreeNode createDocuments(Projeto projeto){
        Map<String, Set<String>>pacotesProj = new HashMap<>();
        Map<String, Set<String>>classesProj = new HashMap<>();
        
        dao.estruturaProjeto(pacotesProj, classesProj, projeto);
        
        
        Map<String, TreeNode> rootNodes = new HashMap<String, TreeNode>();

        //Retrieve the list of root Nodes. eg Tables in the database.
        for(Map.Entry<String, Set<String>> pack : pacotesProj.entrySet()){

           //table.getTableName() will be the name of the node, it could be something like "music" or "documents"
//           rootNodes.add(pack.getKey()table.getTableName(), new DefaultTreeNode(table.getTableName(), new Document......, root);
            TreeNode pacotes = new DefaultTreeNode(new Document(pack.getKey(), "Pacote"), arvoreProjeto);
            for(String c: pacotesProj.get(pack.getKey())){
                TreeNode classes = new DefaultTreeNode(new Document(c, "Classe"), pacotes); 
                for(String m: classesProj.get(c)){
                    TreeNode metodos = new DefaultTreeNode(new Document(m, "Método"), classes); 
                }
            }
        }
        
        
        
        
        arvoreProjeto = new DefaultTreeNode(new Document("Files", "Pacote"), null);
        for(Map.Entry<String, Set<String>> pack : pacotesProj.entrySet()){
            TreeNode pacotes = new DefaultTreeNode(new Document(pack.getKey(), "Pacote"), arvoreProjeto);
            for(String c: pacotesProj.get(pack.getKey())){
                TreeNode classes = new DefaultTreeNode(new Document(c, "Classe"), pacotes); 
                for(String m: classesProj.get(c)){
                    TreeNode metodos = new DefaultTreeNode(new Document(m, "Método"), classes); 
                }
            }
        }
        return arvoreProjeto;
    }
    
    public TreeNode createDocuments() {
        TreeNode root = new DefaultTreeNode(new Document("Files", "Folder"), null);
         
        TreeNode documents = new DefaultTreeNode(new Document("Documents",  "Folder"), root);
        TreeNode pictures = new DefaultTreeNode(new Document("Pictures",  "Folder"), root);
        TreeNode movies = new DefaultTreeNode(new Document("Movies",  "Folder"), root);
         
        TreeNode work = new DefaultTreeNode(new Document("Work",  "Folder"), documents);
        TreeNode primefaces = new DefaultTreeNode(new Document("PrimeFaces",  "Folder"), documents);
         
        //Documents
        TreeNode expenses = new DefaultTreeNode("document", new Document("Expenses.doc", "Word Document"), work);
        TreeNode resume = new DefaultTreeNode("document", new Document("Resume.doc", "Word Document"), work);
        TreeNode refdoc = new DefaultTreeNode("document", new Document("RefDoc.pages", "Pages Document"), primefaces);
         
        //Pictures
        TreeNode barca = new DefaultTreeNode("picture", new Document("barcelona.jpg", "JPEG Image"), pictures);
        TreeNode primelogo = new DefaultTreeNode("picture", new Document("logo.jpg",  "JPEG Image"), pictures);
        TreeNode optimus = new DefaultTreeNode("picture", new Document("optimusprime.png", "PNG Image"), pictures);
         
        //Movies
        TreeNode pacino = new DefaultTreeNode(new Document("Al Pacino",  "Folder"), movies);
        TreeNode deniro = new DefaultTreeNode(new Document("Robert De Niro",  "Folder"), movies);
         
        TreeNode scarface = new DefaultTreeNode("mp3", new Document("Scarface",  "Movie File"), pacino);
        TreeNode carlitosWay = new DefaultTreeNode("mp3", new Document("Carlitos' Way", "Movie File"), pacino);
         
        TreeNode goodfellas = new DefaultTreeNode("mp3", new Document("Goodfellas", "Movie File"), deniro);
        TreeNode untouchables = new DefaultTreeNode("mp3", new Document("Untouchables", "Movie File"), deniro);
         
        return root;
    }
    
}
