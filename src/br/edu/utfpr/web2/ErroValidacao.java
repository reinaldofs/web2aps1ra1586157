package br.edu.utfpr.web2;

public class ErroValidacao extends Exception {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErroValidacao(String message) {
        super(message);
    }
}
