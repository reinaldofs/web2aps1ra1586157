package br.edu.utfpr.web2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
	
	static Connection getConnection() throws SQLException, IOException{
		//String absolutePath = "C:\\Users\\Reinaldo\\Google Drive\\Disciplinas\\Programacao WEB 2\\workspace\\web2aps1ra1586157\\src\\database.db3";
		String absolutePath = "";
		try {
			Properties prop = new Properties();
	        InputStream inputStream = Conexao.class.getClassLoader().getResourceAsStream("/config.properties");
	        prop.load(inputStream);
	        
	        absolutePath = prop.getProperty("databaseFile");
	        System.out.println("Caminho banco -> "+absolutePath);
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return DriverManager.getConnection("jdbc:sqlite:" + absolutePath);
	}
}
