package br.edu.utfpr.web2;

public class Retorno {
	private int status;
	private String msg;
	public Retorno(int status, String msg){
		this.setStatus(status);
		this.setMsg(msg);
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
