package br.edu.utfpr.web2;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;



/**
 * Servlet implementation class UsuarioSearchServlet
 */
@WebServlet("/UsuarioSearchServlet")
public class UsuarioSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json;
		UsuarioController c = new UsuarioController();
		
		try{
			(new Sessao(request.getSession())).validarSessao();
		
			ArrayList<Usuario> p = c.find((String)request.getParameter("query"));
			json = new Gson().toJson(p);
		}catch(Exception e){
			json = new Gson().toJson(new Retorno(-1, e.getMessage()));
		}
		
		response.getWriter().print(json);
		response.getWriter().close();
	
	}

	

}
