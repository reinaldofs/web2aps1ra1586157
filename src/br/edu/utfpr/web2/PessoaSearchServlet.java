package br.edu.utfpr.web2;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;



/**
 * Servlet implementation class PessoaSearchServlet
 */
@WebServlet("/PessoaSearchServlet")
public class PessoaSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PessoaSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String json;
		PessoaController c = new PessoaController();
		try{
			(new Sessao(request.getSession())).validarSessao();
			ArrayList<Pessoa> p = c.find((String)request.getParameter("query"));
			json = new Gson().toJson(p);
		}catch(Exception e){
			json = new Gson().toJson(new Retorno(-1, e.getMessage()));
		}
		
		response.getWriter().print(json);
		response.getWriter().close();
	
	}

	

}
