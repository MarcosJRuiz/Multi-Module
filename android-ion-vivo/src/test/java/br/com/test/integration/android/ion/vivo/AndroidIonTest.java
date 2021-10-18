package br.com.test.integration.android.ion.vivo;

import br.com.test.integration.suite.driver.DriverFactory;
import br.com.test.integration.suite.dsl.Android;
import br.com.test.integration.suite.enuns.DEVICE;
import br.com.test.integration.suite.enuns.URL;
import br.com.test.integration.suite.evidence.EmailLogic;
import br.com.test.integration.suite.evidence.Utils;
import br.com.test.integration.suite.views.devices.DadosBancarios;
import br.com.test.integration.suite.views.devices.Senha;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AndroidIonTest {

	private static AndroidDriver<MobileElement> driver = null;
	private TestInfo testInfo;
	private final static Logger logger = Logger.getLogger("008");
	private String nomeTeste;
	private String idTeste;
	private String statusLog;
	private String operadora;
	private String idException;

	@BeforeAll
	public static void inicializar() throws IOException {
		Utils.init();
		driver = DriverFactory.getDriver(URL.ANDROID_ION);
	}

	@BeforeEach
	public void init(TestInfo testInfo) {
		this.testInfo = testInfo;
		this.nomeTeste = Utils.getTestName(testInfo);
		this.operadora = Utils.getOperadora(testInfo);
		this.idTeste = Utils.getIdTest(testInfo).concat(";").concat(operadora).concat(";");
		this.statusLog = idTeste + "Log;";
		this.idException = "008" + Utils.getIdTest(testInfo);
		//Gravar.video();
		logger.info(idTeste.concat("Start;Inicio do cenário " + nomeTeste + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"));
		//logger.info(idTeste.concat("Start;"));
	}

	@AfterEach
	public void fim() {
		//Gravar.salvarVideo(this.nomeTeste);
		logger.debug(statusLog.concat("Fim do Cenário " + nomeTeste + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"));
		System.out.println();
	}

	@AfterAll
	public static void finalizar() {
		DriverFactory.killDriver(DEVICE.ANDROID);
	}

	@Test
	@Order(1)
	public void Login001(){
		try{
		Android.aguardar();

		if (Android.elementoExiste(By.id("com.itau.investimentos:id/btnUserName"))) {
			Utils.evidence(testInfo, driver);
			Android.clicar(By.id("com.itau.investimentos:id/btnUserName"));
			logger.debug(statusLog.concat("Botão Olá,\"cliente\" Clicado"));
		}
		if (Android.elementoExiste(By.id("com.itau.investimentos:id/btnLeave"))) {
			Utils.evidence(testInfo, driver);
			Android.clicar(By.id("com.itau.investimentos:id/btnLeave"));
			logger.debug(statusLog.concat("Botão sair do ion Clicado"));
		}
		logger.debug(statusLog.concat(" Tela Home " + nomeTeste));
		Android.aguardar();

		DadosBancarios.androidIon(testInfo, driver);
		Senha.senhaVirtualAndroidIon(testInfo, driver);

		Android.aguardarElementoSairDaTela(By.xpath("//android.widget.TextView[@text='Acesso à conta']"));
		Android.aguardar();

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

	//@Disabled("Dev")
	@Test
	@Order(2)
	public void stories002() {
		try {

			try {
				if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc= 'Fechar']"))) {
					Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc= 'Fechar']"));
					logger.debug(statusLog.concat("Botão sair do stories Clicado"));
				}
			} catch (Exception e){
				logger.debug(statusLog.concat("Saiu dos stories sem clicar no Botão sair do stories"));
			}

			if(Android.elementoExiste(By.id("com.itau.investimentos:id/popup_card"))) {
				Android.clicar(By.id("com.itau.investimentos:id/popup_card"));
				logger.debug(statusLog.concat("Pagina inicial Clicada"));
			}

			Android.aguardarElementoExistir(By.id("com.itau.investimentos:id/ibStoriesButton"));
			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));

			Utils.evidence(testInfo, driver);
			Android.clicar(By.id("com.itau.investimentos:id/ibStoriesButton"));
			logger.debug(statusLog.concat("Botão stories do ion Clicado"));

			Android.aguardarElementoExistir(By.id("com.itau.investimentos:id/ionProgressStories"));  //com.itau.investimentos:id/ionProgressStories
			logger.debug(statusLog.concat("Validar barra de stories"));
			Android.validaElementoNaTela(By.id("com.itau.investimentos:id/ionProgressStories"));

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
	@Order(3)
	public void home003()  {
		try {
			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Fechar']"))) {  //com.itau.investimentos:id/ibClose
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Fechar']"));
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
			}

			if(Android.elementoExiste(By.id("com.itau.investimentos:id/popup_card"))) {
				Android.clicar(By.id("com.itau.investimentos:id/popup_card"));
				logger.debug(statusLog.concat("Pagina inicial Clicada"));
			}

			if(Android.elementoExiste(By.xpath("(//android.widget.TextView[@content-desc='Valor oculto'])[1]"))){
				Android.clicar(By.xpath("//android.widget.Button[@content-desc='mostrar valores']"));
				logger.debug(statusLog.concat("Botão exibir valores investido Clicado"));
			}

			Android.aguardar();
			Utils.evidence(testInfo, driver);

			Android.aguardar();
			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));
			Utils.evidence(testInfo, driver);

			Android.aguardarElementoExistir(By.xpath("(//android.widget.TextView[contains(@text, 'R$')])[1]"));
			logger.debug(statusLog.concat("Saldo investido Validado"));

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
	public void rentabilidade004() {
		try {

			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Fechar']"))) {  //com.itau.investimentos:id/ibClose
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Fechar']"));
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
				Utils.evidence(testInfo, driver);
			}

			if(Android.elementoExiste(By.id("com.itau.investimentos:id/popup_card"))) {
				Android.clicar(By.id("com.itau.investimentos:id/popup_card"));
				logger.debug(statusLog.concat("Pagina inicial Clicada"));
			}

			if(Android.elementoExiste(By.xpath("(//android.widget.TextView[@content-desc='Valor oculto'])[1]"))){
				Android.clicar(By.xpath("//android.widget.Button[@content-desc='mostrar valores']"));
				logger.debug(statusLog.concat("Botão exibir valores investido Clicado"));
				Utils.evidence(testInfo, driver);
			}

			Android.aguardar();
			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));
			Utils.evidence(testInfo, driver);

			Android.aguardarElementoExistir(By.xpath("(//android.widget.TextView[contains(@text, 'R$')])[1]"));
			logger.debug(statusLog.concat("Saldo investido Validado"));

			Android.rolarTela(By.id("com.itau.investimentos:id/ib_clickable_indicator"), By.id("com.itau.investimentos:id/tv_portfolio_title"));
			logger.debug(statusLog.concat("Swipe para direita"));

			Android.aguardarElementoExistir(By.xpath("//android.widget.Button[@text='Rentabilidade']"));
			logger.debug(statusLog.concat("Rentabilidade Validada"));
			Android.aguardar();

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
	@Order(5)
	public void carteira005() {
		try {

			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Fechar']"))) {
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Fechar']"));
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
			}

			if(Android.elementoExiste(By.id("com.itau.investimentos:id/popup_card"))) {
				Android.clicar(By.id("com.itau.investimentos:id/popup_card"));
				logger.debug(statusLog.concat("Pagina inicial Clicada"));
			}

			Utils.evidence(testInfo, driver);

			Android.rolarTela(By.id("com.itau.investimentos:id/ibStoriesButton"), By.id("com.itau.investimentos:id/notification"));
			logger.debug(statusLog.concat("Swipe para esquerda"));
			Utils.evidence(testInfo, driver);

			if(Android.elementoExiste(By.xpath("(//android.widget.TextView[@content-desc='Valor oculto'])[1]"))){
				Android.clicar(By.xpath("//android.widget.Button[@content-desc='mostrar valores']"));
				logger.debug(statusLog.concat("Botão exibir valores investido Clicado"));
				Utils.evidence(testInfo, driver);
			}

			Android.aguardar();
			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));
			Utils.evidence(testInfo, driver);

			Android.aguardarElementoExistir(By.xpath("(//android.widget.TextView[contains(@text, 'R$')])[1]"));
			logger.debug(statusLog.concat("Saldo investido Validado"));

			Android.rolarTela(By.xpath("//android.widget.TextView[@text='Carteira']"), By.xpath("//android.widget.TextView[@text='Investimentos']"));
			logger.debug(statusLog.concat("Swipe para baixo"));
			Android.aguardar();
			Utils.evidence(testInfo, driver);

			Android.clicar(By.id("com.itau.investimentos:id/ib_clickable_indicator"));
			logger.debug(statusLog.concat("Todos os produtos Clicado"));

			Android.aguardarElementoExistir(By.id("com.itau.investimentos:id/btProfitabilityTypeButton"));
			logger.debug(statusLog.concat("Rentabilidade Validada"));

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
	@Order(6)
	public void sair006() {
		try {

			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Fechar']"))) {  //com.itau.investimentos:id/ibClose
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Fechar']"));
				logger.debug(statusLog.concat("Botão fechar todos os produtos Clicado"));
			}

			if (Android.elementoExiste(By.id("//com.itau.investimentos:id/ibClose"))) {  //com.itau.investimentos:id/ibClose
				Android.clicar(By.id("//com.itau.investimentos:id/ibClose"));
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
			}

			if(Android.elementoExiste(By.id("com.itau.investimentos:id/popup_card"))) {
				Android.clicar(By.id("com.itau.investimentos:id/popup_card"));
			}

			if (Android.elementoExiste(By.id("com.itau.investimentos:id/profileTab"))) {
				Android.clicar(By.id("com.itau.investimentos:id/profileTab"));
				logger.debug(statusLog.concat("Botão perfil ion Clicado"));
				Utils.evidence(testInfo, driver);
			}

			if(Android.elementoExiste(By.id("com.itau.investimentos:id/popup_card"))) {
				Android.clicar(By.id("com.itau.investimentos:id/popup_card"));
			}

			Android.aguardar();
			Utils.evidence(testInfo, driver);

			Android.clicar(By.id("com.itau.investimentos:id/invest_welcome_name"));
			logger.debug(statusLog.concat("Expandir botão de sair Clicado"));
			Android.aguardar();
			Utils.evidence(testInfo, driver);

			Android.clicar(By.id("com.itau.investimentos:id/btLogout"));
			logger.debug(statusLog.concat("Botão sair do ion Clicado"));
			Android.aguardar();
			Utils.evidence(testInfo, driver);

			Android.clicar(By.id("com.itau.investimentos:id/btnLogoutAndDontRemember"));
			Android.aguardar();

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
