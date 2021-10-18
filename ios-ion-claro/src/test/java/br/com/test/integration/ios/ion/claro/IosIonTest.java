package br.com.test.integration.ios.ion.claro;

import br.com.test.integration.suite.driver.DriverFactory;
import br.com.test.integration.suite.dsl.Ios;
import br.com.test.integration.suite.enuns.DEVICE;
import br.com.test.integration.suite.enuns.URL;
import br.com.test.integration.suite.evidence.EmailLogic;
import br.com.test.integration.suite.evidence.Utils;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IosIonTest {
	private static IOSDriver<IOSElement> driver;
	private TestInfo testInfo;
	final static Logger logger = Logger.getLogger("009");
	private static String AGENCIA;
	private static String CONTA;
	private static String SENHA_VIRTUAL;

	private String nomeTeste;
	private static Ios dsl;
	private String idTeste;
	private String statusLog;
	private String operadora;
	private String idException;

	@BeforeAll
	public static void inicializar() throws IOException {
		Utils.init();
		driver = DriverFactory.getDriverIOS(URL.IOS_ION);
		SENHA_VIRTUAL = Utils.getPropAndResolve("senha_virtual_ios_ion");
		AGENCIA = Utils.getPropAndResolve("agencia_ios_ion");
		CONTA = Utils.getPropAndResolve("conta_ios_ion");
		dsl = new Ios();
	}

	@BeforeEach
	public void init(TestInfo testInfo) {
		this.testInfo = testInfo;
		this.nomeTeste = Utils.getTestName(testInfo);
		this.operadora = Utils.getOperadora(testInfo);
		this.idTeste = Utils.getIdTest(testInfo).concat(";").concat(operadora).concat(";");
		this.statusLog = idTeste + "Log;";
		this.idException = "009" + Utils.getIdTest(testInfo);
		//Gravar.video();
		logger.info(idTeste.concat("Start;Inicio do cenário " + nomeTeste + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"));
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
		DriverFactory.killDriver(DEVICE.IOS);
	}

	@Test
	@Order(1)
	public void login001() {
		try {
			try {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[contains(@name,'Olá')]"));
				Utils.evidence(testInfo, driver);
				dsl.clicar(By.xpath("//XCUIElementTypeButton[contains(@name,'Sair')]"));
				logger.debug(statusLog.concat("Botão sair Clicado"));
			} catch (Exception e) {
				Utils.evidence(testInfo, driver);
				logger.debug(statusLog.concat("Login não estava salvo"));
			}

			// Clicando no botao entrar
			dsl.clicar(By.xpath("//XCUIElementTypeStaticText[@name='Entrar']"));
			logger.debug(statusLog.concat("Botão entrar Clicado"));

			Utils.evidence(testInfo, driver);

			dsl.clicar(By.xpath("//XCUIElementTypeTextField[@name='Agência']"));
			char[] chars2 = AGENCIA.toCharArray();
			for (char ch : chars2) {
				dsl.clicar(By.xpath("//XCUIElementTypeKey[@name='" + ch + "']"));
			}
			logger.debug(statusLog.concat("Digitado no teclado virtual a agencia: " + AGENCIA));
			Utils.evidence(testInfo, driver);

			//conta-corrente
			dsl.clicar(By.xpath("//XCUIElementTypeTextField[@name='Conta corrente']"));
			char[] chars3 = CONTA.toCharArray();
			for (char ch : chars3) {
				dsl.clicar(By.xpath("//XCUIElementTypeKey[@name='" + ch + "']"));
			}
			logger.debug(statusLog.concat("Digitado no teclado virtual a conta: " + CONTA));
			Utils.evidence(testInfo, driver);

			// Senha
			dsl.clicar(By.xpath("//XCUIElementTypeSecureTextField[@name='Senha eletrônica']"));
			char[] chars = SENHA_VIRTUAL.toCharArray();
			for (char ch : chars) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[contains(@name,'" + ch + "')]"));
			}
			Utils.evidence(testInfo, driver);
			logger.debug(statusLog.concat("Digitado no teclado virtual a senha: " + SENHA_VIRTUAL));

			//Clicando em entrar para poder prosseguir com o login
			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeButton[@name=\"Entrar\"]"));
			Utils.evidence(testInfo, driver);
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"Entrar\"]"));
			logger.debug(statusLog.concat("clicando no botão de Entrar"));
			dsl.aguardar(5);
			Utils.evidence(testInfo, driver);

			if(dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='Fechar']"))) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='Fechar']"));
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
				dsl.aguardar();
			}

			dsl.swipe("left");
			logger.debug(statusLog.concat("Swipe para esquerda"));
			dsl.aguardar();
			dsl.swipe("right");
			logger.debug(statusLog.concat("Swipe para direita"));
			dsl.aguardar();

			dsl.validaElementoNaTela(By.xpath("(//XCUIElementTypeStaticText[@name=\"messageLabel\"])[1]"));

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

//	@Disabled("Elemento botão Stories não tem funcionalidade")
	@Test
	@Order(2)
	public void stories002() {
		try {
			try {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='Fechar']"));
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
				dsl.aguardar();
		} catch (Exception e) {
				logger.debug(statusLog.concat("Saiu dos stories sem clicar no Botão sair do stories"));
				dsl.aguardar();
			}

			dsl.swipe("left");
			logger.debug(statusLog.concat("Swipe para esquerda"));
			dsl.aguardar();
			dsl.swipe("right");
			logger.debug(statusLog.concat("Swipe para direita"));
			dsl.aguardar();

			dsl.aguardarElementoExistir((By.xpath("//XCUIElementTypeButton[contains(@name,'Stories')]")));
			dsl.clicar(By.xpath("//XCUIElementTypeButton[contains(@name,'Stories')]"));
			dsl.aguardar();
			Utils.evidence(testInfo, driver);
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeButton[@name='Fechar']"));

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
	public void home003() {
		try {
			if(dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='Fechar']"))) {
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='Fechar']"));
			}

			dsl.aguardar();
			dsl.swipe("left");
			dsl.aguardar();
			dsl.swipe("right");
			dsl.aguardar();

			Utils.evidence(testInfo, driver);
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeStaticText[@name=\"walletTotalValueLabel\"]"));

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

	@Disabled("DEV - aguardando a rentabilidade aparecer")
	@Test
	@Order(4)
	public void rentabilidade004() {
		try {
			dsl.aguardar();
			if(dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='Fechar']"))) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='Fechar']"));
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
				dsl.aguardar();
			}
			dsl.swipe("left");
			logger.debug(statusLog.concat("Swipe para esquerda"));
			dsl.aguardar();
			Utils.evidence(testInfo, driver);
			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeOther[@name=\"investimentProfileView\"]"));
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeOther[@name=\"investimentProfileView\"]"));

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
	@Order(5)
	public void carteira005() {
		try {
			if(dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='Fechar']"))) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='Fechar']"));
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
			}

			Utils.evidence(testInfo, driver);
			logger.debug(statusLog.concat("Validando valor Carteira Clicado"));

			dsl.aguardar(1);
			dsl.swipe("left");
			dsl.aguardar();

			dsl.swipe("up");
			dsl.aguardar(1);
			if(dsl.elementoExiste(By.xpath(("(//XCUIElementTypeButton[@name=\"IACProductWalletRows\"])[1]")))){
				dsl.clicar(By.xpath("(//XCUIElementTypeButton[@name=\"IACProductWalletRows\"])[1]"));
				logger.debug(statusLog.concat("Botão exibir valores investido Clicado"));
				Utils.evidence(testInfo, driver);
			}

			//dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeButton[@name=\"IACProductWalletRows\"])[1]"));
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeStaticText[contains(@value,'Seu investimento foi realizado.')]"));

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

			//valida se stories ainda esta aberto e fecha
			if(dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[contains(@name,'Fechar')]"))) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[contains(@name,'Fechar')]"));
				logger.debug(statusLog.concat("Botão fechar stories Clicado"));
			}

			dsl.swipe("up");
			Utils.evidence(testInfo, driver);
			dsl.aguardar();

			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"profileTab\"]"));
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.xpath("//XCUIElementTypeButton[contains(@name,'Olá')]"));
			logger.debug(statusLog.concat("Botão Olá Clicado"));
			dsl.aguardar();

			dsl.clicar(By.xpath("//XCUIElementTypeButton[contains(@name,'Sair')]"));
			logger.debug(statusLog.concat("Botão sair Clicado"));
			dsl.aguardar();

			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"Não quero salvar\"]"));
			dsl.aguardar();

			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeStaticText[@name='Entrar']"));
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