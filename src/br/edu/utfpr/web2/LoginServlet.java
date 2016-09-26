package br.edu.utfpr.web2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Sessao sessao = new Sessao(request.getSession());
		Retorno ret;
		if (sessao.logar(request.getParameter("email"), request.getParameter("senha"))){
			ret = new Retorno(1, "Logado com sucesso!");
		}else{
			ret = new Retorno(1, "Email ou senha incorretos!");
		}
		String json = new Gson().toJson(ret);
		
		response.getWriter().write(json);
		response.getWriter().close();
	}

}
