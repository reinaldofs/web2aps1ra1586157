package br.edu.utfpr.web2;

import java.util.ArrayList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PessoaDAO {
	public Retorno save(Pessoa model) throws IOException{
		try{
			Connection con = Conexao.getConnection();
			int idTime = 0;
			String id = model.getIdPessoa() > 0 ? Integer.toString(model.getIdPessoa()):"null";
			PreparedStatement ps = con.prepareStatement("INSERT OR REPLACE INTO pessoa(idpessoa,nome,cpf,email,telefone,endereco,cidade,cep,idtime,estado) VALUES("+id+",?,?,?,?,?,?,?,?,?)");
			
			ps.setString(1, model.getNome());
			ps.setString(2, model.getCpf());
			ps.setString(3, model.getEmail());
			ps.setString(4, model.getTelefone());
			ps.setString(5, model.getEndereco());
			ps.setString(6, model.getCidade());
			ps.setString(7, model.getCep());
			
			if (model.getTime()!=null){
				idTime = model.getTime().getIdTime();
			}
			
			ps.setInt(8, idTime);
			ps.setString(9, model.getEstado());
			ps.execute();
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return new Retorno(-1, se.getMessage());
		}
		return new Retorno(1, "Gravado com sucesso!");
	}
	
	public Retorno delete(Pessoa model) throws IOException{
		try{
			Connection con = Conexao.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM pessoa WHERE idpessoa = ?");
			ps.setInt(1, model.getIdPessoa());
			ps.execute();
		}catch(SQLException se){
			return new Retorno(-1, se.getMessage());
		}
		return new Retorno(1, "Excluido com sucesso!");
	}
	
	public ArrayList<Pessoa> find(String query) throws IOException{
		
		ArrayList<Pessoa> ret = new ArrayList<Pessoa>();
		try{
			Connection con = Conexao.getConnection();
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM pessoa WHERE ''='"+query+"' or idpessoa=cast('"+query+"' as integer) or upper(nome) like '%"+query+"%' order by (idpessoa=cast('"+query+"' as integer))desc, nome asc ");
			while (rs.next()) {
				Pessoa p = new Pessoa();
				p.setIdPessoa(rs.getInt("idpessoa"));
				p.setCep(rs.getString("cep"));
				p.setCidade(rs.getString("cidade"));
				p.setNome(rs.getString("nome"));
				p.setEmail(rs.getString("email"));
				p.setEndereco(rs.getString("endereco"));
				p.setCpf(rs.getString("cpf"));
				p.setTelefone(rs.getString("telefone"));
				p.setEstado(rs.getString("estado"));
		
				TimeController tc = new TimeController();
				ArrayList<Time> arr = tc.find(Integer.toString(rs.getInt("idtime")));
				if (arr.size()>0){
					p.setTime(arr.get(0));
				}
				
				ret.add(p);
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		return ret;
	}
	
}
