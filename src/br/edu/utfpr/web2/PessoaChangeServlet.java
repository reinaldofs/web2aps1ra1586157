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
@WebServlet("/PessoaChangeServlet")
public class PessoaChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PessoaChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet -> Inicio");
		Pessoa p = new Pessoa();
		Time t = new Time();
		Retorno ret;
		try{
			System.out.println("Servlet -> Try");
			p.setIdPessoa(Integer.parseInt(request.getParameter("idpessoa")));
			p.setNome((String) request.getParameter("nome"));
			p.setCpf((String) request.getParameter("cpf"));
			p.setEmail((String) request.getParameter("email"));
			p.setTelefone((String) request.getParameter("telefone"));
			p.setEndereco((String) request.getParameter("endereco"));
			p.setCidade((String) request.getParameter("cidade"));
			p.setEstado((String) request.getParameter("estado"));
			p.setCep((String) request.getParameter("cep"));
			t.setIdTime(Integer.parseInt(request.getParameter("idtime")));
			p.setTime(t);
			PessoaController c = new PessoaController(p);
			ret = c.save();
		}catch(Exception e){
			System.out.println("Servlet -> "+e.getMessage());
			ret = new Retorno(-1, e.getMessage());
		}
		String json = new Gson().toJson(ret);
		response.getWriter().print(json);
		response.getWriter().close();
		
	}


}
