package br.edu.utfpr.web2;

import java.util.ArrayList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAO {
	public Retorno save(Usuario model) throws IOException{
		try{
			Connection con = Conexao.getConnection();

			String id = model.getIdUsuario() > 0 ? Integer.toString(model.getIdUsuario()):"null";
			PreparedStatement ps = con.prepareStatement("INSERT OR REPLACE INTO usuario(idusuario,nome,senha,email) VALUES("+id+",?,?,?)");
			
			ps.setString(1, model.getNome());
			ps.setString(2, model.getSenha());
			ps.setString(3, model.getEmail());
	
			ps.execute();
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return new Retorno(-1, se.getMessage());
		}
		return new Retorno(1, "Gravado com sucesso!");
	}
	
	public Retorno delete(Usuario model) throws IOException, SQLException{
		Connection con = null;
		try{
			con = Conexao.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM Usuario WHERE idUsuario = ?");
			ps.setInt(1, model.getIdUsuario());
			ps.execute();
		}catch(SQLException se){
			return new Retorno(-1, se.getMessage());
		}finally{
			con.close();
		}
		return new Retorno(1, "Excluido com sucesso!");
	}
	
	public ArrayList<Usuario> find(String query) throws IOException, SQLException{
		
		ArrayList<Usuario> ret = new ArrayList<Usuario>();
		Connection con = null;
		try{
			con = Conexao.getConnection();
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Usuario WHERE ''='"+query+"' or idUsuario=cast('"+query+"' as integer) or upper(nome) like '%"+query+"%' order by (idUsuario=cast('"+query+"' as integer))desc, nome asc ");
			while (rs.next()) {
				Usuario p = new Usuario();
				p.setIdUsuario(rs.getInt("idusuario"));
				p.setEmail(rs.getString("email"));
				p.setSenha(rs.getString("senha"));
				p.setNome(rs.getString("nome"));
		
				ret.add(p);
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
			con.close();
		}
		
		return ret;
	}
	
	public Usuario getUsuario(String email, String senha) throws IOException, SQLException{
		Connection con = null;
		try{
			con = Conexao.getConnection();
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Usuario WHERE email = '"+email+"' and senha = '"+senha+"'");
			while (rs.next()) {
				Usuario p = new Usuario();
				p.setIdUsuario(rs.getInt("idusuario"));
				p.setEmail(rs.getString("email"));
				p.setSenha(rs.getString("senha"));
				p.setNome(rs.getString("nome"));
		
				return p;
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
			con.close();
		}
		return null;
	}
}
