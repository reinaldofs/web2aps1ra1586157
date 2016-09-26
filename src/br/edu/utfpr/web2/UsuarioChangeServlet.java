package br.edu.utfpr.web2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class UsuarioIncluirServlet
 */
@WebServlet("/UsuarioChangeServlet")
public class UsuarioChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario p = new Usuario();
		Retorno ret;
		try{
			p.setIdUsuario(Integer.parseInt(request.getParameter("idusuario")));
			// valida a sessão para alteração de usuário
			if (p.getIdUsuario()>0){
				(new Sessao(request.getSession())).validarSessao();
			}
			p.setNome((String) request.getParameter("nome"));
			p.setSenha((String) request.getParameter("senha"));
			p.setEmail((String) request.getParameter("email"));

			UsuarioController c = new UsuarioController(p);
			ret = c.save();
		}catch(Exception e){
			ret = new Retorno(-1, e.getMessage());
		}
		String json = new Gson().toJson(ret);
		response.getWriter().print(json);
		response.getWriter().close();
		
	}


}
