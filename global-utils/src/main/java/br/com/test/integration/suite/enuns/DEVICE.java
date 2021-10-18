package br.com.test.integration.suite.enuns;

/**
 * Classe para definir o dispositivo que executara o teste.
 * 
 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
 */
public enum DEVICE {
	
	ANDROID("ANDROID"),
	IOS ("IOS"),
	BROWSER ("BROWSER"); 
	
	private final String nome;
	
	DEVICE(String nome) {
	this.nome = nome;
	}
	
	public String nome() {	
		return nome;
	}

}