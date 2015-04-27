/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.controller;

import br.com.pucminas.debt.dao.AtualizacaoDAO;
import br.com.pucminas.debt.dao.AtualizacaoDAOImpl;
import br.com.pucminas.debt.dao.MetricaDAO;
import br.com.pucminas.debt.dao.MetricaDAOImpl;
import br.com.pucminas.debt.dao.ProjetoDAO;
import br.com.pucminas.debt.dao.ProjetoDAOImpl;
import br.com.pucminas.debt.dao.UsuarioDAO;
import br.com.pucminas.debt.dao.UsuarioDAOImpl;
import br.com.pucminas.debt.dao.ValorMetricaDAO;
import br.com.pucminas.debt.dao.ValorMetricaDAOImpl;
import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Document;
import br.com.pucminas.debt.model.Metrica;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.model.Usuario;
import br.com.pucminas.debt.model.ValorMetrica;
import br.com.pucminas.debt.parser.ParserMetrics;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.TreeNode;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author barbara.lopes
 */
@ManagedBean
@SessionScoped
public class ProjetoController implements Serializable{
    
    /*Projeto*/
    private Projeto projeto;
    private DataModel listaProjetos;
    private Atualizacao ultimaAtualizacaoProjeto;
    private TreeNode arvoreProjeto;
    private Document selectedDocument;
    private Projeto projetoSelecionado;
    
    /*DAOs*/
    private ProjetoDAO dao = new ProjetoDAOImpl();
    private UsuarioDAO daoUsu = new UsuarioDAOImpl();
    private AtualizacaoDAO daoAtualiz = new AtualizacaoDAOImpl();
    private ValorMetricaDAO daoVal = new ValorMetricaDAOImpl();
    private MetricaDAO daoMet = new MetricaDAOImpl();
    
    /*Usuário*/
    private String loginUsuario = ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getName();
    private Usuario usuarioLogado = daoUsu.buscaLogin(loginUsuario);

    
    public ProjetoController() {

    }

    @PostConstruct
    public void init() {
       
    }

    public Projeto getProjeto() {
        if (projeto == null) {
            projeto = new Projeto();
        }
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Projeto getProjetoSelecionado() {
        return projetoSelecionado;
    }

    public void setProjetoSelecionado(Projeto projetoSelecionado) {
        this.projetoSelecionado = projetoSelecionado;
    }

    public Atualizacao getUltimaAtualizacaoProjeto() {
        if(ultimaAtualizacaoProjeto == null && projetoSelecionado != null){
            setUltimaAtualizacaoProjeto(dao.ultimaAtualizacao(projetoSelecionado));
        }
        return ultimaAtualizacaoProjeto;
    }

    public void setUltimaAtualizacaoProjeto(Atualizacao ultimaAtualizacaoProjeto) {
        this.ultimaAtualizacaoProjeto = ultimaAtualizacaoProjeto;
    }

    public DataModel getListarProjetos() {
        ProjetoDAOImpl est = new ProjetoDAOImpl();
        List<Projeto> lista = est.listar();
        listaProjetos = new ListDataModel(lista);

        return listaProjetos;
    }

    public void prepararAdicionarProjeto(ActionEvent actionEvent) {
        projeto = new Projeto();
        projeto.setGerente(usuarioLogado);
    }

    public void prepararAlterarProjeto(ActionEvent actionEvent) {
        projeto = (Projeto) (listaProjetos.getRowData());
    }

    public void excluirProjeto(ActionEvent actionEvent) {
        Projeto projetoTemp = (Projeto) (listaProjetos.getRowData());
        dao.excluir(projetoTemp);
    }

    public void adicionarProjeto(ActionEvent actionEvent) {
        projeto.setGerente(usuarioLogado);
        dao.salvar(projeto);
    }

    public void alterarProjeto(ActionEvent actionEvent) {
        dao.alterar(projeto);
    }

    public void stateChangeListener(ValueChangeEvent event) {
        if (event.getNewValue() != projetoSelecionado) {
            projetoSelecionado = null;
        }
    }
    
    public String redirecionaAtualizacoesProjeto() {
        projetoSelecionado = (Projeto) (listaProjetos.getRowData());
        atualizaProjeto();
        return "atualizacoes_projeto?faces-redirect=true";
    }
    
    public String redirecionaMetricasProjeto() {
        return "metricas?faces-redirect=true";
    }
    
    public void atualizaProjeto(){
    
        List<Metrica>metricas;
        Atualizacao atualizacao = new Atualizacao();
        ParserMetrics parser = new ParserMetrics();
        
        String dir = "C:\\TCC\\MetricsAnalyser\\Arquivo\\";
        File diretorio = new File(dir);
        File[] fList = diretorio.listFiles();

        String caminho;
        long val;
        Date date;
        File arquivo;
        List<Atualizacao> ats = projetoSelecionado.getAtualizacoes();
         
        for ( int i = 0; i < fList.length; i++ ){
            if (fList[i].getName().endsWith(".xml")) {
                
                caminho = dir +"" + fList[i].getName();
                arquivo = new File(caminho);
                val = arquivo.lastModified();
                date = new Date(val);
                
                if(ats == null || !daoAtualiz.atualizacaoExiste(date, ats)){
                    
                    atualizacao.setData(date);
                    atualizacao.setId(0);
                    metricas = parser.fazerParsing(caminho);
                    atualizacao.setMetricas(metricas);
                    atualizacao.setProjeto(projetoSelecionado);
                    
                    if(projetoSelecionado.getAtualizacoes() == null){
                        List<Atualizacao>atualizacoes = new ArrayList<>();
                        atualizacoes.add(atualizacao);
                        projetoSelecionado.setAtualizacoes(atualizacoes);
                    }
                    else{
                        projetoSelecionado.getAtualizacoes().add(atualizacao);
                    }
                    
                    dao.salvar(projetoSelecionado);
                    daoAtualiz.salvar(atualizacao);
                    
                    for(Metrica m: metricas){
                        m.setAtualizacao(atualizacao);
                        daoMet.salvar(m);
                        for(ValorMetrica v: m.getValores()){
                            v.setMetrica(m);
                        }
                        daoVal.salvar(m.getValores());
                    }
                    atualizacao = new Atualizacao();
                }
            }
        }  
    }
    
    /*Getters e Setters*/
    public DataModel getListaProjetos() {
        return listaProjetos;
    }

    public void setListaProjetos(DataModel listaProjetos) {
        this.listaProjetos = listaProjetos;
    }

    public ProjetoDAO getDao() {
        return dao;
    }

    public void setDao(ProjetoDAO dao) {
        this.dao = dao;
    }

    public UsuarioDAO getDaoUsu() {
        return daoUsu;
    }

    public void setDaoUsu(UsuarioDAO daoUsu) {
        this.daoUsu = daoUsu;
    }

    public AtualizacaoDAO getDaoAtualiz() {
        return daoAtualiz;
    }

    public void setDaoAtualiz(AtualizacaoDAO daoAtualiz) {
        this.daoAtualiz = daoAtualiz;
    }

    public ValorMetricaDAO getDaoVal() {
        return daoVal;
    }

    public void setDaoVal(ValorMetricaDAO daoVal) {
        this.daoVal = daoVal;
    }

    public MetricaDAO getDaoMet() {
        return daoMet;
    }

    public void setDaoMet(MetricaDAO daoMet) {
        this.daoMet = daoMet;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    
    public TreeNode getArvoreProjeto() {
        if(arvoreProjeto == null){
            setArvoreProjeto(dao.arvoreProjeto(projetoSelecionado));
        }
        return arvoreProjeto;
    }

    public void setArvoreProjeto(TreeNode arvoreProjeto) {
        this.arvoreProjeto = arvoreProjeto;
    }
    
    public Document getSelectedDocument() {
        return selectedDocument;
    }
 
    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
}
