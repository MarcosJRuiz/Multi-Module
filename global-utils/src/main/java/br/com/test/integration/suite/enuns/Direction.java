package br.com.test.integration.suite.enuns;

/**
 * Classe para definir o dispositivo que executara o teste.
 * 
 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
 */
public enum Direction {

	UP(900,200),
	DOWN(200,900);

	private final int start;
	private final int end;

	Direction(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int start() {
		return start;
	}

	public int end() {
		return end;
	}

}
