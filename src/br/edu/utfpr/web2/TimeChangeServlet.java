package br.edu.utfpr.web2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class PessoaIncluirServlet
 */
@WebServlet("/TimeChangeServlet")
public class TimeChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Time p = new Time();
		Retorno ret;
		try{
			(new Sessao(request.getSession())).validarSessao();
			p.setIdTime(Integer.parseInt(request.getParameter("idtime")));
			p.setNome((String) request.getParameter("nome"));
			p.setSigla((String) request.getParameter("sigla"));
			
			TimeController c = new TimeController(p);
			ret = c.save();
		}catch(Exception e){
			ret = new Retorno(-1, e.getMessage());
		}
		String json = new Gson().toJson(ret);
		response.getWriter().print(json);
		response.getWriter().close();
		
	}


}
