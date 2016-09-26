package br.edu.utfpr.web2;

import java.util.ArrayList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TimeDAO {
	public Retorno save(Time model) throws IOException, SQLException{
		Connection con = null;
		try{
			con = Conexao.getConnection();
			String id = model.getIdTime() > 0 ? Integer.toString(model.getIdTime()):"null";
			PreparedStatement ps = con.prepareStatement("INSERT OR REPLACE INTO time(idtime,nome,sigla) VALUES("+id+",?,?)");
			
			ps.setString(1, model.getNome());
			ps.setString(2, model.getSigla());

			ps.execute();
		}catch(SQLException se){
			return new Retorno(-1, se.getMessage());
		}finally{
			con.close();
		}
		return new Retorno(1, "Gravado com sucesso!");
	}
	
	public Retorno delete(Time model) throws IOException, SQLException{
		Connection con = null;
		try{
			con = Conexao.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM time WHERE idtime = ?");
			ps.setInt(1, model.getIdTime());
			ps.execute();
		}catch(SQLException se){
			return new Retorno(-1, se.getMessage());
		}finally{
			con.close();
		}
		return new Retorno(1, "Excluido com sucesso!");
	}
	
	public ArrayList<Time> find(String query) throws IOException, SQLException{
		
		ArrayList<Time> ret = new ArrayList<Time>();
		Connection con = null;
		try{
			con = Conexao.getConnection();
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM time WHERE ''='"+query+"' or idtime=cast('"+query+"' as integer) or upper(nome) like '%"+query+"%' or upper(sigla) like '%"+query+"%' order by nome asc");
			while (rs.next()) {
				Time p = new Time();
				p.setIdTime(rs.getInt("idtime"));
				p.setNome(rs.getString("nome"));
				p.setSigla(rs.getString("sigla"));

				ret.add(p);
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
			con.close();
		}
		return ret;
	}
	
}
