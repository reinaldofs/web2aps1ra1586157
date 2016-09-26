package br.edu.utfpr.web2;

import java.io.IOException;
import java.util.ArrayList;

public class UsuarioController {
	private Usuario model;
	protected UsuarioDAO dao;
	
	public UsuarioController(Usuario model){
		this.model = model;
		dao = new UsuarioDAO();
	}
	public UsuarioController(){
		dao = new UsuarioDAO();
	}
	
	public Usuario getModel() {
		return model;
	}

	public void setModel(Usuario model) {
		this.model = model;
	}
	
	public Retorno delete() throws IOException{
		return this.dao.delete(this.model);
	}
	
	public Retorno save() throws ErroValidacao, IOException{
		if (this.model.getNome().trim().equals("")){
			throw new ErroValidacao("Informe o nome do torcedor");
		}
		
		return this.dao.save(this.model);
	}
	
	public ArrayList<Usuario> find(String query) throws IOException{
		return this.dao.find(query);
	}
}
