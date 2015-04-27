/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Atualizacao;
import br.com.pucminas.debt.model.Projeto;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 * @author barbara.lopes
 */
public interface AtualizacaoDAO {
    void salvar (Atualizacao atualizacao);
    void excluir (Atualizacao atualizacao);
    boolean atualizacaoExiste(Date data, List<Atualizacao>atualizacoes);
    public List<Atualizacao> listar(Projeto projeto);
    public List<String> listarDiretoriosAtualizacao(File file);
}
