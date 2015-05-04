/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.model.ValorMetrica;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.model.TreeNode;
import org.primefaces.model.mindmap.MindmapNode;

/**
 *
 * @author Bárbara
 */
public interface ProjetoDAO {
    void salvar (Projeto projeto);
    void alterar (Projeto projeto);
    void excluir (Projeto projeto);
    public List<Projeto> listar();
    public Atualizacao ultimaAtualizacao(Projeto projeto);
    public TreeNode arvoreProjeto(Projeto projeto);
    public List<ValorMetrica> valoresProjeto(Projeto projeto);
    public MindmapNode mapaProjeto(Map<String, Set<String>>pacotesProj, Map<String, Set<String>>classesProj, Projeto projeto);
    public void estruturaProjeto(Map<String, Set<String>>pacotesProj, Map<String, Set<String>>classesProj, Projeto projeto);
    public List<ValorMetrica> metricasFile(Projeto projeto, String file);
    public List<ValorMetrica> valoresMetrica(Projeto projeto, String metrica, String file);
}
