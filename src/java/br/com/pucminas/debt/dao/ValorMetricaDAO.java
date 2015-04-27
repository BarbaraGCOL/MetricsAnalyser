/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Metrica;
import br.com.pucminas.debt.model.ValorMetrica;
import java.util.List;

/**
 *
 * @author barbara.lopes
 */
public interface ValorMetricaDAO {
    void salvar (List<ValorMetrica> valoresMetrica);
    void excluir (ValorMetrica valorMetrica);
}
