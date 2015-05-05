package br.com.pucminas.debt.controller;

import br.com.pucminas.debt.model.Usuario;
import br.com.pucminas.debt.dao.impl.UsuarioDAOImpl;
import br.com.pucminas.debt.dao.UsuarioDAO;
import br.com.pucminas.debt.model.Autorizacao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@ManagedBean
@RequestScoped
public class UsuarioController {

    private Usuario usuarioLogado=new Usuario();;
    private Usuario usuario=new Usuario();
    private DataModel listaUsuarios;
    Autorizacao autorizacaoUsuario=new Autorizacao();
    //MensagensController msg=new MensagensController();
    private boolean disabledAlterar=false;
    private boolean disabledIncluir=false;
    private Usuario usuarioSelecionado=new Usuario();

    public UsuarioController() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext){
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication){
                usuarioLogado.setLogin(((User)authentication.getPrincipal()).getUsername());
            }
        }
    }

    public boolean isDisabledAlterar() {
        return disabledAlterar;
    }

    public void setDisabledAlterar(boolean disabledAlterar) {
        this.disabledAlterar = disabledAlterar;
    }

    public boolean isDisabledIncluir() {
        return disabledIncluir;
    }

    public void setDisabledIncluir(boolean disabledIncluir) {
        this.disabledIncluir = disabledIncluir;
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    
    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }
    
    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public DataModel getListarUsuarios() {
        UsuarioDAOImpl usu=new UsuarioDAOImpl();
        List<Usuario> lista = usu.listar();
        listaUsuarios = new ListDataModel(lista);
        return listaUsuarios;
    }
    
    public void prepararAdicionarUsuario(ActionEvent actionEvent){
        usuario = new Usuario();
        disabledIncluir=false;
        disabledAlterar=true;
    }

    public void prepararAlterarUsuario(ActionEvent actionEvent){
        usuario = (Usuario)(listaUsuarios.getRowData());
        disabledIncluir=true;
        disabledAlterar=false;
    }

    public void excluirUsuario(ActionEvent actionEvent){  
        usuario = (Usuario)(listaUsuarios.getRowData());
        if(!usuario.getLogin().equals("admin"))
        {
            UsuarioDAO dao=new UsuarioDAOImpl();
            dao.excluir(usuario);
        }
    }

    public void adicionarUsuario(ActionEvent actionEvent){
        UsuarioDAOImpl dao=new UsuarioDAOImpl();
        autorizacaoUsuario.setUsuarios(null);
        dao.salvar(usuario);
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.addCallbackParam("sucesso", true); 
    }

    public void alterarUsuario(ActionEvent actionEvent){
        if(!usuario.getLogin().equals("admin"))
        {
            UsuarioDAO dao=new UsuarioDAOImpl();
            dao.alterar(usuario);
        }
    }
}