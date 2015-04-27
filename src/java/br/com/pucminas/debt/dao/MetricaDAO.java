/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Metrica;

/**
 *
 * @author barbara.lopes
 */
public interface MetricaDAO {
    void salvar (Metrica metrica);
    void excluir (Metrica metrica);
}
