package br.com.test.integration.android.empresa.tim;

import br.com.test.integration.suite.driver.DriverFactory;
import br.com.test.integration.suite.dsl.Android;
import br.com.test.integration.suite.enuns.DEVICE;
import br.com.test.integration.suite.enuns.URL;
import br.com.test.integration.suite.evidence.EmailLogic;
import br.com.test.integration.suite.evidence.Utils;
import br.com.test.integration.suite.views.devices.DadosBancarios;
import br.com.test.integration.suite.views.devices.Notificacao;
import br.com.test.integration.suite.views.devices.Senha;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AndroidEmpresaTimTest {

	private static AndroidDriver<MobileElement> driver = null;
	public TestInfo testInfo;
	final static Logger logger = Logger.getLogger("004");
	private String nomeTeste;
	private static String VALOR_TRANSFERENCIA;
	private static String FAVORECIDO;
	private String idTeste;
	private String operadora;
	private String statusLog;
	private String idException;

	@BeforeAll
	public static void init() throws IOException {
		Utils.init();
		driver = DriverFactory.getDriver(URL.ANDROID_EMPRESA_TIM);
		FAVORECIDO = Utils.getPropAndResolve("favorecido_android_empresa_tim");
		VALOR_TRANSFERENCIA = Utils.getProp("valor");
	}

	@BeforeEach
	public void init(TestInfo testInfo) {
		this.testInfo = testInfo;
		this.nomeTeste = Utils.getTestName(testInfo);
		this.operadora = Utils.getOperadora(testInfo);
		this.idTeste = Utils.getIdTest(testInfo).concat(";").concat(operadora).concat(";");
		this.statusLog = idTeste + "Log;";
		this.idException = "004" + Utils.getIdTest(testInfo);
		//Gravar.video();
		logger.info("Start;Inicio do cenário " + nomeTeste + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//logger.info(idTeste.concat("Start;"));
	}

	@AfterEach
	public void fim()  {
		// Gravar.salvarVideo(this.nomeTeste);
		logger.debug(statusLog.concat("Fim do Cenário " + nomeTeste + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"));
		System.out.println();
	}

	@AfterAll
	public static void finalizar() {
		DriverFactory.killDriver(DEVICE.ANDROID);
	}

	@Test
	@Order(1)
	public void login001()  {
		try {

			DadosBancarios.androidEmpresa(idTeste, testInfo, driver);

			Senha.senhaVirtualAndroidEmpresa(idTeste,testInfo, driver);

			Notificacao.clicarNao(testInfo, driver);
			Notificacao.clicarOkEntendi(testInfo, driver);

			Utils.evidence(testInfo, driver);

			Android.validaElementoNaTela(By.id("com.itau.empresas:id/tabbar_home"));
			logger.info(idTeste.concat("Sucesso;"));

			EmailLogic.addSuccess(idException,Utils.dataHoraFormatada(),operadora);
		} catch (Exception e) {
			EmailLogic.addError(idException,Utils.dataHoraFormatada(),operadora,e.getMessage());

			logger.error(idTeste.concat("Erro;Erro ao executar o cenário: ").concat(nomeTeste).concat(" - ").concat(e.getMessage()));
			throw e;
		} finally {
			Utils.evidence(testInfo, driver);
		}
	}

	//@Disabled("DEV")
	@Test
	@Order(2)
	public void consultaSaldo002() {
		try {
			if (Android.elementoExiste(By.id("com.itau.empresas:id/tabbar_home"))) {
				Android.clicar(By.id("com.itau.empresas:id/tabbar_home"));
			}
			logger.debug(statusLog.concat("Tela Home " + nomeTeste));

			Android.validaElementoNaTela(By.id("com.itau.empresas:id/tabbar_home"));
			logger.info(idTeste.concat("Sucesso;"));

			EmailLogic.addSuccess(idException,Utils.dataHoraFormatada(),operadora);
		} catch (Exception e) {
			EmailLogic.addError(idException,Utils.dataHoraFormatada(),operadora,e.getMessage());

			logger.error(idTeste.concat("Erro;Erro ao executar o cenário: ").concat(nomeTeste).concat(" - ").concat(e.getMessage()));
			throw e;
		} finally {
			Utils.evidence(testInfo, driver);
		}
	}

	@Disabled("DEV")
	@Test
	@Order(3)
	public void consultaExtrato003()  {
		try {
			if (Android.elementoExiste(By.id("com.itau.empresas:id/tabbar_home"))) {
				Android.clicar(By.id("com.itau.empresas:id/tabbar_home"));
			}
			logger.debug(statusLog.concat("Tela Home " + nomeTeste));

			Android.clicar(By.xpath("//android.widget.Button[@text='ver extrato']"));
			logger.debug(statusLog.concat("Botão ver extrato Clicado"));

//			int i = 1;
//			while (i < 5 && !Android.elementoExiste(By.xpath("//android.widget.TextView[@text='filtrar lançamentos']"))) {
//				if (Android.elementoExiste(By.xpath("//android.widget.Button[@content-desc='exibir mais opções']"))) {
//					Android.clicar(By.xpath("//android.widget.Button[@content-desc='exibir mais opções']"));
//					logger.debug(statusLog.concat("Botão exibir mais opções Clicado"));
//				} else {
//					logger.debug(statusLog.concat("Botão exibir mais opções não encontrado - " + i));
//				}
//				i = i + 1;
//				Android.aguardar();
//			}

			Android.aguardar(28);
			Utils.evidence(testInfo, driver);

			Android.aguardarElementoExistir(By.xpath("//android.widget.Button[@content-desc=\"exibir mais opções\"]"));
			Android.clicar(By.xpath("//android.widget.Button[@content-desc=\"exibir mais opções\"]"));
			Utils.evidence(testInfo, driver);


			Android.aguardarElementoExistir(By.xpath("//android.widget.TextView[@text=\"filtrar lançamentos\"]"));

			Android.clicar(By.xpath("//android.widget.TextView[@text='filtrar lançamentos']"));
			logger.debug(statusLog.concat("Botão filtrar lançamentos Clicado"));

			Android.clicar(By.xpath("//android.widget.RadioButton[@text='90 dias']"));
			logger.debug(statusLog.concat("Botão 90 dias Clicado"));

			Utils.evidence(testInfo, driver);

			Android.clicar(By.xpath("//android.widget.Button[@content-desc='fechar']"));
			logger.debug(statusLog.concat("Botão fechar filtro Clicado"));

			Android.aguardarElementoExistir(By.xpath("//android.widget.TextView[contains(@text,'R$')]"));

			Android.validaElementoNaTela(By.xpath("//android.widget.Button[@content-desc='exibir mais opções']"));
			logger.info(idTeste.concat("Sucesso;"));

			EmailLogic.addSuccess(idException,Utils.dataHoraFormatada(),operadora);
		} catch (Exception e) {
			EmailLogic.addError(idException,Utils.dataHoraFormatada(),operadora,e.getMessage());

			logger.error(idTeste.concat("Erro;Erro ao executar o cenário: ").concat(nomeTeste).concat(" - ").concat(e.getMessage()));
			throw e;
		} finally {
			Utils.evidence(testInfo, driver);
		}
	}

	//@Disabled("DEV")
	@Test
	@Order(4)
	public void consultaLimiteCredito004()  {
		try {
			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='voltar']"))) {
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='voltar']"));
			}
			if (Android.elementoExiste(By.id("com.itau.empresas:id/tabbar_operacoes"))) {
				Android.clicar(By.id("com.itau.empresas:id/tabbar_operacoes"));
			}

			logger.debug(statusLog.concat("Tela Operações " + nomeTeste));

			Android.aguardarElementoExistir(By.xpath("//android.widget.Button[@content-desc='crédito']"));

			Android.clicar(By.xpath("//android.widget.Button[@content-desc='crédito']"));
			logger.debug(statusLog.concat("Botão crédito Clicado"));

			Android.aguardarElementoExistir(By.xpath("//android.widget.Button[@text='portabilidade de crédito']"));

			Android.validaElementoNaTela(By.xpath("//android.widget.TextView[@text='acompanhe seus créditos']"));


			logger.info(idTeste.concat("Sucesso;"));
			EmailLogic.addSuccess(idException,Utils.dataHoraFormatada(),operadora);
		} catch (Exception e) {
			EmailLogic.addError(idException,Utils.dataHoraFormatada(),operadora,e.getMessage());

			logger.error(idTeste.concat("Erro;Erro ao executar o cenário: ").concat(nomeTeste).concat(" - ").concat(e.getMessage()));
			throw e;
		} finally {
			Utils.evidence(testInfo, driver);
		}
	}

	//@Disabled("Cenário nao Autorizado pelo Itau")
	@Test
	@Order(4)
	public void transferencia005()  {
		try {
			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='voltar']"))) {
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='voltar']"));
			}
			if (Android.elementoExiste(By.id("com.itau.empresas:id/tabbar_operacoes"))) {
				Android.clicar(By.id("com.itau.empresas:id/tabbar_operacoes"));
			}

			logger.debug(statusLog.concat("Tela Operações " + nomeTeste));

			Android.aguardarElementoExistir(By.xpath("//android.widget.Button[@content-desc='transferências']"));

			Utils.evidence(testInfo, driver);

			Android.clicar(By.xpath("//android.widget.Button[@content-desc='transferências']"));
			logger.debug(statusLog.concat("Botão transferências Clicado"));

			Android.aguardarElementoExistir(By.xpath(
					"//*[@resource-id='com.itau.empresas:id/rv_menu_pex']//android.widget.Button[@text='contas itaú']"));

			Utils.evidence(testInfo, driver);

			Android.clicar(By.xpath(
					"//*[@resource-id='com.itau.empresas:id/rv_menu_pex']//android.widget.Button[@text='contas itaú']"));
			logger.debug(statusLog.concat("Botão contas itaú Clicado"));

			Android.aguardarElementoExistir(By.xpath("//android.view.View[@text='conta a ser creditada']"));

			// Utils.evidence(testInfo, driver);

			// dsl.rolarTela(By.xpath("//android.view.View[@text='conta a ser creditada']"),
			// By.xpath("//android.view.View[@text='conta a ser debitada']"));
			// logger.debug(statusLog.concat("Deslizado a tela para cima ");

			Android.clicar(By.xpath("//android.view.View[@content-desc='escolher favorecido']"));
			logger.debug(statusLog.concat("Botão escolher favorecido Clicado"));

			Utils.evidence(testInfo, driver);

			Android.aguardarElementoExistir(By.xpath("//android.view.View[contains(@text,'"+FAVORECIDO+"')]"));

			Utils.evidence(testInfo, driver);

			Android.clicar(By.xpath("//android.view.View[contains(@text,'"+FAVORECIDO+"')]"));
			logger.debug(statusLog.concat("Botão "+FAVORECIDO+" Clicado"));
			Android.aguardar(5);
			Android.aguardarElementoExistir(By.xpath("//android.widget.EditText[@resource-id='ctl00_DefaultContent_txtValor']"));
			
			Android.digitar(By.xpath("//android.widget.EditText[@resource-id='ctl00_DefaultContent_txtValor']"), VALOR_TRANSFERENCIA);
			logger.debug(statusLog.concat("Digitado o valor: " + VALOR_TRANSFERENCIA + " no elemento valor da transferência"));

			Utils.evidence(testInfo, driver);

			Android.clicar(By.xpath("//android.widget.Button[@text='transferir agora']"));
			logger.debug(statusLog.concat("Botão transferir agora Clicado"));
			
			if (Android.elementoExiste(By.xpath("//android.widget.TextView[@text='confirmar']"))) {
				Utils.evidence(testInfo, driver);
				Android.clicar(By.xpath("//android.widget.TextView[@text='confirmar']"));
				logger.debug(statusLog.concat("Botão confirmar no popup de duplicidade Clicado"));
			}
			
			Android.aguardarElementoExistir(By.xpath("//android.widget.Button[@text='confirmar']"));
			
			Utils.evidence(testInfo, driver);
			
			Android.clicar(By.xpath("//android.widget.Button[@text='confirmar']"));
			logger.debug(statusLog.concat("Botão confirmar Clicado"));

			Android.validaElementoNaTela(By.xpath("//android.view.View[@text='transação concluida com sucesso']"));

			logger.info(idTeste.concat("Sucesso;"));
			EmailLogic.addSuccess(idException,Utils.dataHoraFormatada(),operadora);
		} catch (Exception e) {
			EmailLogic.addError(idException,Utils.dataHoraFormatada(),operadora,e.getMessage());

			logger.error(idTeste.concat("Erro;Erro ao executar o cenário: ").concat(nomeTeste).concat(" - ").concat(e.getMessage()));
			throw e;
		} finally {
			Utils.evidence(testInfo, driver);
		}
	}

}
