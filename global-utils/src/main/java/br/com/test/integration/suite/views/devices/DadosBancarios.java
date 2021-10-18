package br.com.test.integration.suite.views.devices;

import br.com.test.integration.suite.dsl.Android;
import br.com.test.integration.suite.evidence.Utils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DadosBancarios {

	final static Logger logger = Logger.getLogger(Notificacao.class);
	private static final By CHECK_SIM = By.xpath("//android.widget.Switch[@text='Sim']");
	//private static final By CHECK_NAO = By.xpath("//android.widget.Switch[@text='Não']");
	private static final By CHECK = By.xpath("//android.widget.Switch");
	//private static final By CONFIRMA = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v7.widget.LinearLayoutCompat/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button[2]");

	//private static final By ACESSAR = By.xpath("//android.widget.TextView[@text='acessar']");
	private static final By ACESSAR = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.Button/android.widget.TextView");
	private static final By AGENCIA = By.xpath("//android.widget.EditText[@content-desc='agência']");
	private static final By CONTA = By.xpath("//android.widget.EditText[@content-desc='conta']");
	//private static final By LEMBRAR_CONTA = By.xpath("//android.widget.Switch[@content-desc='lembrar agência e conta?']");
	private static final By BTN_OK = By.xpath("//android.widget.Button[@content-desc='ok']");
	private static final String AGENCIA_LIGHT_OI = Utils.getPropAndResolve("agencia_android_light_oi");
	private static final String CONTA_LIGHT_OI = Utils.getPropAndResolve("conta_android_light_oi");
	private static final String AGENCIA_LIGHT_VIVO = Utils.getPropAndResolve("agencia_android_light_vivo");
	private static final String CONTA_LIGHT_VIVO = Utils.getPropAndResolve("conta_android_light_vivo");
	private static final String AGENCIA_ID = "agência";
	private static final String CONTA_ID = "conta";
	private static final String AGENCIA_VAREJO_CLARO = Utils.getPropAndResolve("agencia_android_varejo_claro");
	private static final String CONTA_VAREJO_CLARO = Utils.getPropAndResolve("conta_android_varejo_claro");
	private static final String AGENCIA_VAREJO_VIVO = Utils.getPropAndResolve("agencia_android_varejo_vivo");
	private static final String CONTA_VAREJO_VIVO = Utils.getPropAndResolve("conta_android_varejo_vivo");
	//private static final By ACESSA_MINHA_CONTA = By.xpath("//android.widget.Button[@content-desc=\"acessar minha conta\"]");
	private static final String OPERADOR_CLARO = Utils.getPropAndResolve("operador_android_empresa_claro");
	private static final String OPERADOR_TIM = Utils.getPropAndResolve("operador_android_empresa_tim");
	private static final By INPUT_OPERADOR = By.xpath("//android.widget.EditText[@text='insira código do operador ou agência e conta']");
	private static final By TEXT_OPERADOR = By.id("com.itau.empresas:id/login_texto_subtitulo1");
	private static final By BOTAO_ACESSAR = By.xpath("//android.widget.Button[@text='acessar']");

	private static final By BOTAO_ENTRAR_ION = By.xpath("//android.widget.TextView[@text='Entrar']");
	private static final By AGENCIA_ION = By.xpath("//android.view.View[android.view.View[@text='Agência']]/android.widget.EditText");
	//private static final By CONTA_ION = By.xpath("//android.view.View[android.view.View[@text='Conta corrente']]/android.widget.EditText");

	private static final String NUMERO_CONTA_ION = Utils.getPropAndResolve("conta_android_ion");

	private static final String NUMERO_AGENCIA_ION = Utils.getPropAndResolve("agencia_android_ion");



	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void androidLight(String idTeste, TestInfo testInfo, WebDriver driver) {
		if (idTeste.contains("oi")) {
			androidLightDefualt(AGENCIA_LIGHT_OI,CONTA_LIGHT_OI,testInfo, driver);
		}else if (idTeste.contains("vivo")){
			androidLightDefualt(AGENCIA_LIGHT_VIVO,CONTA_LIGHT_VIVO,testInfo, driver);
		}
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	private static void androidLightDefualt(String agencia, String conta, TestInfo testInfo, WebDriver driver) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		if (Android.elementoExiste(CONTA)) {
			lembrar(testInfo);
			Android.digitar(AGENCIA, agencia);
			logger.debug(statusLog.concat("Digitado a Agencia: " + agencia));
			Android.digitar(CONTA, conta);
			logger.debug(statusLog.concat("Digitado a Conta: " + conta));
		}else {
			logger.debug(statusLog.concat(String.format("Agenca: %s e Conta: %s cadastrado ", agencia, conta)));
		}
		Utils.evidence(testInfo, driver);
		Android.clicar(ACESSAR);
		logger.debug(statusLog.concat("Botão acessar pressionado"));
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	private static void lembrar(TestInfo testInfo) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		if (Android.elementoMarcado(CHECK)) {
			Android.clicar(CHECK);
			logger.debug(statusLog.concat("Botão salvar conta pressionado"));
		}
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	private static void naoLembrar(TestInfo testInfo) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		if (Android.elementoExiste(CHECK_SIM)) {
			Android.clicar(CHECK_SIM);
			logger.debug(statusLog.concat("Botão salvar conta pressionado"));
		}
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void androidVarejo(String idTeste, TestInfo testInfo, WebDriver driver) {
		if (idTeste.contains("claro")) {
			androidVarejoDefault(AGENCIA_VAREJO_CLARO, CONTA_VAREJO_CLARO, testInfo, driver);
		}else if (idTeste.contains("vivo")){
			androidVarejoDefault(AGENCIA_VAREJO_VIVO, CONTA_VAREJO_VIVO, testInfo, driver);
		}
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	private static void androidVarejoDefault(String agencia, String conta, TestInfo testInfo, WebDriver driver) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		if (Android.elementoExiste(CONTA)) {
			naoLembrar(testInfo);
			Android.digitarByAccessibilityId(AGENCIA_ID, agencia);
			logger.debug(statusLog.concat("Digitado a Agencia: " + agencia));
			Android.digitarByAccessibilityId(CONTA_ID, conta);
			logger.debug(statusLog.concat("Digitado a Conta: " + conta));
			Utils.evidence(testInfo, driver);
			Android.clicar(BTN_OK);
			logger.debug(statusLog.concat("Botão OK pressionado"));
		}
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void androidEmpresa(String idTeste, TestInfo testInfo,  AndroidDriver<MobileElement> driver) {
		String operador = null;
		if (idTeste.contains("tim")) {
			operador = OPERADOR_TIM;
		}else if (idTeste.contains("claro")){
			operador = OPERADOR_CLARO;
		}
		androidEmpresaDefault(operador, testInfo, driver);
	}

	/**
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	private static void androidEmpresaDefault(String operador, TestInfo testInfo,  AndroidDriver<MobileElement> driver) {
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		if (Android.elementoExiste(INPUT_OPERADOR)) {
			Utils.evidence(testInfo, driver);
			//naoLembrar();
			Android.clicar(INPUT_OPERADOR);

			Android.digitar(INPUT_OPERADOR, operador);
			logger.debug(statusLog.concat("Digitado o Operador: " + operador));

			driver.hideKeyboard();

			Utils.evidence(testInfo, driver);

			Android.clicar(BOTAO_ACESSAR);
			logger.debug(statusLog.concat("Botão acessar pressionado"));
		}else {
			logger.debug(statusLog.concat(Android.lerTexto(TEXT_OPERADOR).concat(" já cadastrado")));
		}
	}


	public static void androidIon(TestInfo testInfo,  AndroidDriver<MobileElement> driver){
		String statusLog = Utils.getIdTest(testInfo).concat(";").concat(Utils.getOperadora(testInfo)).concat(";Log;");
		Utils.evidence(testInfo, driver);
		Android.clicar(BOTAO_ENTRAR_ION);
		logger.debug(statusLog.concat("Botão entrar pressionado"));

		Android.aguardar(3);
		Utils.evidence(testInfo, driver);

		Android.clicar(AGENCIA_ION);
		tecladoAndroid(driver,NUMERO_AGENCIA_ION);
		logger.debug(statusLog.concat("Digitado a Agencia: " + NUMERO_AGENCIA_ION));

		Android.aguardar();
		tecladoAndroid(driver,NUMERO_CONTA_ION);
		logger.debug(statusLog.concat("Digitado a Conta: " + NUMERO_CONTA_ION));
	}

	private static void tecladoAndroid(AndroidDriver<MobileElement> driver,String senha) {
		char[] chars = senha.toCharArray();
		for (char ch : chars) {
			keyBoard(driver, ch);
			Android.aguardar(1);
		}
	}

	private static void keyBoard (AndroidDriver<MobileElement> driver, char numero){
		if(numero == '1') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
		} else 	if(numero == '2') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_2));
		} else 	if(numero == '3') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
		} else 	if(numero == '4') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_4));
		} else 	if(numero == '5') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_5));
		} else 	if(numero == '6') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_6));
		} else 	if(numero == '7') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_7));
		} else 	if(numero == '8') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_8));
		} else 	if(numero == '9') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_9));
		} else 	if(numero == '0') {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
		}

	}
}
