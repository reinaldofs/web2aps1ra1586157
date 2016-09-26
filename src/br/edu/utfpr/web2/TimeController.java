package br.edu.utfpr.web2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TimeController {
	private Time model;
	protected TimeDAO dao;
	
	public TimeController(Time model){
		this.model = model;
		dao = new TimeDAO();
	}
	public TimeController(){
		dao = new TimeDAO();
	}
	
	public Time getModel() {
		return model;
	}

	public void setModel(Time model) {
		this.model = model;
	}
	
	public Retorno delete() throws IOException, SQLException{
		return this.dao.delete(this.model);
	}
	
	public Retorno save() throws ErroValidacao, IOException, SQLException{
		if (this.model.getNome().trim().equals("")){
			throw new ErroValidacao("Informe o nome do time");
		}
		if (this.model.getSigla().trim().equals("")){
			throw new ErroValidacao("Informe a sigla do time");
		}
		return this.dao.save(this.model);
	}
	
	public ArrayList<Time> find(String query) throws IOException, SQLException{
		return this.dao.find(query);
	}
}
