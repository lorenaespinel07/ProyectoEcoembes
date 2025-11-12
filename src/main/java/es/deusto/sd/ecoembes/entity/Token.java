package es.deusto.sd.ecoembes.entity;

import java.sql.Timestamp;

public class Token {
	private String token; 
	private Personal personal;

	public Token(String token, Personal personal) {
		super();
		this.token = token;
		this.personal = personal;
	}
	public Token() {}

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
