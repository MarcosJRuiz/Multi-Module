package br.com.test.integration.suite.views.devices;

import br.com.test.integration.suite.dsl.Android;
import br.com.test.integration.suite.evidence.Utils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Notificacao {

	private static Android android = new Android();
	final static Logger logger = Logger.getLogger(Notificacao.class);
	//private static By NAO_QUERO = By.xpath("//android.widget.Button[@text='não quero']");
	//private static By AGORA_NAO = By.xpath("//android.widget.Button[@text='agora não']");
	private static By NAO = By.xpath("//android.widget.Button[contains(@text, 'não')]");
	private static By OK_ENTENDI = By.xpath("//android.widget.Button[@text='ok, entendi']");
	private static By FECHAR = By.xpath("//*[contains(@content-desc,'echar')]");
	private static By RECUSAR = By.xpath("//android.widget.Button[@text='Recusar']");
	private static By ACESSAR_MINHA_CONTA = By.xpath("//android.widget.Button[@content-desc='acessar minha conta']");
	private static By MAIS_TARDE = By.xpath("//android.widget.Button[@text='mais tarde']");
	
	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void clicarNao(TestInfo testInfo, WebDriver driver) {
		if (android.elementoExiste(NAO)) {
			Utils.evidence(testInfo, driver);
			android.clicar(NAO);
			logger.debug("clicou no botão que contem a palavra 'não' da notificação");
		}else {
			logger.debug("Não encontrou popup de notificação que contem a palavra 'Não'");
		}
	}
	
	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void clicarOkEntendi(TestInfo testInfo, WebDriver driver) {
		if (android.elementoExiste(OK_ENTENDI)) {
			Utils.evidence(testInfo, driver);
			android.clicar(OK_ENTENDI);
			logger.debug("clicou no botão 'Ok, entendi' da notificação");
		}else {
			logger.debug("Não encontrou popup de notificação 'Ok, entendi'");
		}
	}
	
	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void fechar(TestInfo testInfo, WebDriver driver) {
		if (android.elementoExiste(FECHAR)) {
			Utils.evidence(testInfo, driver);
			android.clicar(FECHAR);
			logger.debug("clicou no botão 'Fechar' da notificação");
		}else {
			logger.debug("Não encontrou popup de notificação 'Fechar'");
		}
	}
	
	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void recusar(TestInfo testInfo, WebDriver driver) {
		if (android.elementoExiste(RECUSAR)) {
			Utils.evidence(testInfo, driver);
			android.clicar(RECUSAR);
			logger.debug("clicou no botão 'Recusar' da notificação");
		}else {
			logger.debug("Não encontrou popup de notificação 'Recusar'");
		}
	}
	
	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void acessarMinhaConta(TestInfo testInfo, WebDriver driver) {
		if (android.elementoExiste(ACESSAR_MINHA_CONTA)) {
			Utils.evidence(testInfo, driver);
			android.clicar(ACESSAR_MINHA_CONTA);
			logger.debug("clicou no botão 'Acessar Minha Conta' da notificação");
		}else {
			logger.debug("Não encontrou popup de notificação 'Acessar Minha Conta'");
		}
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void maisTarde(TestInfo testInfo, WebDriver driver) {
		if (android.elementoExiste(MAIS_TARDE)) {
			Utils.evidence(testInfo, driver);
			android.clicar(MAIS_TARDE);
			logger.debug("clicou no botão 'mais tarde' da notificação");
		}else {
			logger.debug("Não encontrou popup de notificação 'mais tarde'");
		}
	}
	
//	/**
//	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
//	 */
//	public static void clicarAgoraNao() {
//		if (dsl.elementoExiste(AGORA_NAO)) {
//			dsl.clicar(AGORA_NAO);
//			logger.debug("clicou no botão agora não da notificação");
//		}else {
//			logger.debug("Não encontrou popup de notificação 'Agora não'");
//		}
//	}

}
