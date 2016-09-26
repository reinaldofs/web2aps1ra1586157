package br.edu.utfpr.web2;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

public class Sessao  {
	private HttpSession session;
	
	public Sessao(HttpSession session){
		this.setSession(session);
		// inicializa a sessão
		if (session.isNew()){
			session.setAttribute("idusuario", 0);
			session.setAttribute("msg", "Nao autenticado!");
		}
	}
	
	public boolean getLogado(){
		return  ((int) session.getAttribute("idusuario"))>0 ;
	}
	
	public boolean validarSessao() throws ErroValidacao{
		if (!getLogado()){
			throw new ErroValidacao("NOT_LOGGED");
		}
		System.out.println("Sessão OK");
		return true;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
	
	public void logout(){
		session.setAttribute("idusuario", 0);
		session.setAttribute("msg", "Usuario desconectado!");
	}
	
	public boolean logar(String email, String senha) throws IOException, SQLException{
		UsuarioDAO dao = new UsuarioDAO();
		Usuario u = dao.getUsuario(email, senha);
		
		if (u==null){
			return false;
		}else{
			session.setAttribute("idusuario", u.getIdUsuario());
			session.setAttribute("nome", u.getNome());
			
			session.setAttribute("msg", "Bem vindo "+u.getNome()+"!");
			return true;
		}
		
	}
}
