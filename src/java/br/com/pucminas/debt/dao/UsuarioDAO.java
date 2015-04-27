package br.com.pucminas.debt.dao;

import br.com.pucminas.debt.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    
    void salvar (Usuario usuario);
    void alterar (Usuario usuario);
    void excluir (Usuario usuario);
    public List<Usuario> listar();
    public Usuario buscaLogin(String login);
}
