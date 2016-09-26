package br.edu.utfpr.web2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class RelatorioTorcidaServlet
 */
@WebServlet("/RelatorioTorcidaServlet")
public class RelatorioTorcidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelatorioTorcidaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<RelatorioTorcida> ret = new ArrayList<RelatorioTorcida>();
		try{
			Connection con = Conexao.getConnection();
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM (SELECT t.nome time,t.sigla,(select count(1) from pessoa p where p.idtime=t.idtime) torcedores FROM time t) tmp where tmp.torcedores>0 ");
			while (rs.next()) {
				RelatorioTorcida p = new RelatorioTorcida();
				p.setTorcedores(rs.getInt("torcedores"));
				p.setTime(rs.getString("time"));
				p.setSigla(rs.getString("sigla"));
				ret.add(p);
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		String json = new Gson().toJson(ret);
		response.getWriter().print(json);
		response.getWriter().close();
	}



}
