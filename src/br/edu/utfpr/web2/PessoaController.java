package br.edu.utfpr.web2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PessoaController {
	private Pessoa model;
	protected PessoaDAO dao;
	
	public PessoaController(Pessoa model){
		this.model = model;
		dao = new PessoaDAO();
	}
	public PessoaController(){
		dao = new PessoaDAO();
	}
	
	public Pessoa getModel() {
		return model;
	}

	public void setModel(Pessoa model) {
		this.model = model;
	}
	
	public Retorno delete() throws IOException, SQLException{
		return this.dao.delete(this.model);
	}
	
	public Retorno save() throws ErroValidacao, IOException, SQLException{
		if (this.model.getNome().trim().equals("")){
			throw new ErroValidacao("Informe o nome do torcedor");
		}
		
		if (this.model.getTime()==null || this.model.getTime().getIdTime()==0){
			throw new ErroValidacao("Selecione o time");
		}
		
		return this.dao.save(this.model);
	}
	
	public ArrayList<Pessoa> find(String query) throws IOException, SQLException{
		return this.dao.find(query);
	}
}
