/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Metrica;
import br.com.pucminas.debt.model.Projeto;
import br.com.pucminas.debt.model.ValorMetrica;
import java.util.List;
import org.primefaces.model.TreeNode;

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
    List<Metrica> metricasProjeto(Atualizacao atualizacao);
    public TreeNode arvoreProjeto(Projeto projeto);
    public List<ValorMetrica> valoresProjeto(Projeto projeto);
    public List<String> pacotesProjeto(Projeto projeto);
}
