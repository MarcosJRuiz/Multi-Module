package br.com.test.integration.suite.views.devices;

import br.com.test.integration.suite.dsl.Android;
import br.com.test.integration.suite.evidence.Utils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Senha {

	final static Logger logger = Logger.getLogger(Notificacao.class);
	private static final String SENHA_VIRTUAL_LIGHT_OI = Utils.getPropAndResolve("senha_virtual_android_light_oi");
	private static final String SENHA_VIRTUAL_LIGHT_VIVO = Utils.getPropAndResolve("senha_virtual_android_light_vivo");
	private static final By ACESSAR = By.xpath("//android.widget.Button[contains(@text, 'acessar')]");
	private static final String SENHA_VIRTUAL_VAREJO_CLARO = Utils.getPropAndResolve("senha_virtual_android_varejo_claro");
	private static final String SENHA_VIRTUAL_VAREJO_VIVO = Utils.getPropAndResolve("senha_virtual_android_varejo_vivo");
	private static final By TECLADO_VIRTUAL = By.xpath("//*[@content-desc='apagar']");
	private static final String SENHA_VIRTUAL_EMPRESA_CLARO = Utils.getPropAndResolve("senha_virtual_android_empresa_claro");
	private static final String SENHA_VIRTUAL_EMPRESA_TIM = Utils.getPropAndResolve("senha_virtual_android_empresa_tim");
	private static final String SENHA_VIRTUAL_ION = Utils.getPropAndResolve("senha_virtual_android_ion");
	private static final By ENTRAR = By.xpath("//android.widget.Button[@text='Entrar']");

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void senhaVirtualAndroidLight(String idTeste, TestInfo testInfo, WebDriver driver) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		if (!Android.elementoExiste(TECLADO_VIRTUAL)) Android.clicar(ACESSAR);
		Android.aguardar();
		Utils.evidence(testInfo, driver);
		if (idTeste.contains("oi")){
			tecladoVirtualAndroid(testInfo, SENHA_VIRTUAL_LIGHT_OI);
		}else if (idTeste.contains("vivo")){
			tecladoVirtualAndroid(testInfo, SENHA_VIRTUAL_LIGHT_VIVO);
		}
		Utils.evidence(testInfo, driver);
		Android.clicar(ACESSAR);
		logger.debug(statusLog.concat("Botão acessar Clicado"));
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void senhaVirtualAndroidVarejo(String idTeste, TestInfo testInfo, WebDriver driver) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		if (!Android.elementoExiste(TECLADO_VIRTUAL)) Android.clicar(ACESSAR);

		Utils.evidence(testInfo, driver);
		if (idTeste.contains("claro")) {
			tecladoVirtualAndroid(testInfo, SENHA_VIRTUAL_VAREJO_CLARO);
		}else if (idTeste.contains("vivo")) {
			tecladoVirtualAndroid(testInfo, SENHA_VIRTUAL_VAREJO_VIVO);
		}
		Utils.evidence(testInfo, driver);
		Android.clicar(ACESSAR);
		logger.debug(statusLog.concat("Botão acessar Clicado"));
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void senhaVirtualAndroidEmpresa(String idTeste, TestInfo testInfo, WebDriver driver) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		if (!Android.elementoExiste(TECLADO_VIRTUAL)) Android.clicar(ACESSAR);

		Utils.evidence(testInfo, driver);
		if (idTeste.contains("claro")) {
			tecladoVirtualAndroid(testInfo, SENHA_VIRTUAL_EMPRESA_CLARO);
		}else if (idTeste.contains("tim")) {
			tecladoVirtualAndroid(testInfo, SENHA_VIRTUAL_EMPRESA_TIM);
		}
		Utils.evidence(testInfo, driver);
		Android.clicar(ACESSAR);
		logger.debug(statusLog.concat("Botão acessar Clicado"));
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void senhaVirtualAndroidIon(TestInfo testInfo, AndroidDriver<MobileElement> driver) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		Android.aguardar();
		Utils.evidence(testInfo, driver);
		tecladoVirtualAndroid(testInfo, SENHA_VIRTUAL_ION);

		Android.aguardar();
		Utils.evidence(testInfo, driver);
		Android.clicar(ENTRAR);
		logger.debug(statusLog.concat("Botão entrar Clicado"));
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	private static void tecladoVirtualAndroid(TestInfo testInfo, String senha) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		char[] chars = senha.toCharArray();
		for (char ch : chars) {
			Android.clicar(By.xpath("//android.widget.Button[contains(@text, '" + ch + "')]"));
			Android.aguardar(1);
		}
		logger.debug(statusLog.concat("Digitado no teclado virtual a senha: " + senha));
	}
}
