package br.com.test.integration.ios.empresa.oi;

import br.com.test.integration.suite.driver.DriverFactory;
import br.com.test.integration.suite.dsl.Ios;
import br.com.test.integration.suite.enuns.DEVICE;
import br.com.test.integration.suite.enuns.URL;
import br.com.test.integration.suite.evidence.EmailLogic;
import br.com.test.integration.suite.evidence.Utils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IosEmpresaOiTest {

	public static IOSDriver<IOSElement> driver;
	public static WebDriverWait wait;
	public TestInfo testInfo;
	private String nomeTeste;
	final static Logger logger = Logger.getLogger("005");
	private static Ios dsl;
	private String idTeste;
	private static String VALOR_TRANSFERENCIA;
	private static String SENHA_VIRTUAL;
	private static String FAVORECIDO;
	private String statusLog;
	private String operadora;
	private String idException;

	@BeforeAll
	public static void inicializar() throws IOException {
		Utils.init();
		VALOR_TRANSFERENCIA = Utils.getPropAndResolve("valor_ios_empresa_oi");
		SENHA_VIRTUAL = Utils.getPropAndResolve("senha_virtual_ios_empresa_oi");
		FAVORECIDO = Utils.getProp("favorecido_ios_empresa_oi");
		driver = DriverFactory.getDriverIOS(URL.IOS_PJ);
		wait = new WebDriverWait(driver, 30);
		dsl = new Ios();
	}

	@BeforeEach
	public void init(TestInfo testInfo) {
		this.testInfo = testInfo;
		this.nomeTeste = Utils.getTestName(testInfo);
		this.operadora = Utils.getOperadora(testInfo);
		this.idTeste = Utils.getIdTest(testInfo).concat(";").concat(operadora).concat(";");
		this.statusLog = idTeste + "Log;";
		this.idException = "005" + Utils.getIdTest(testInfo);
		//Gravar.video();
		logger.info(idTeste.concat("Start;Inicio do cenário " + nomeTeste + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"));
		//logger.info(idTeste.concat("Start;"));
	}

	@AfterEach
	public void fim() {
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
	public void login001() throws InterruptedException {
		try {
			logger.debug(statusLog.concat("Clicado no input de senha"));
			MobileElement password = driver.findElementByAccessibilityId("PASSWORD_TEXTFIELD");
			password.click();
			Thread.sleep(1000);
			logger.debug(statusLog.concat("Digitando a senha"));
			for (int i = 0; i < SENHA_VIRTUAL.length(); i++) {
				char digitoSenha = SENHA_VIRTUAL.charAt(i);
				wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//XCUIElementTypeStaticText[contains(@name, " + digitoSenha + ")]"))).click();
			}
			MobileElement el4 = driver.findElementByAccessibilityId("ACCESS_BUTTON");
			el4.click();

			logger.debug(statusLog.concat("Aguardando carregar a página"));
			dsl.aguardar();

			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeButton[@name=\"extratoButton\"]"));

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
	public void consultaSaldo002() {
		try {

			if (!dsl.elementoExiste(By.xpath("//XCUIElementTypeStaticText[@name='atalhos']"))) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='início']"));
			}

			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));

			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='expandir']"))) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='expandir']"));
				logger.debug(statusLog.concat("Botão Expandir Saldo Clicado"));
			}

			assertTrue(dsl.elementoExiste(By.xpath("//XCUIElementTypeStaticText[contains(@value,'R$')]")));

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

	@Disabled("Dev")
	@Test
	@Order(3)
	public void consultaExtrato003() {
		try {

			Utils.evidence(testInfo, driver);
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"extratoButton\"]"));
			logger.debug(statusLog.concat("Botão extrato clicado"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"filtro\"]"));
			logger.debug(statusLog.concat("Botão filtro clicado"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"Filtrar lançamentos.\"]"));
			logger.debug(statusLog.concat("Botão filtrar lançamentos clicado"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);
			dsl.clicar(By.xpath("//XCUIElementTypeCell[@name=\"90 dias\"]"));
			logger.debug(statusLog.concat("Botão 90 dias clicado"));

			Utils.evidence(testInfo, driver);
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"aplicar\"]"));
			logger.debug(statusLog.concat("Botão aplicar clicado"));

			dsl.aguardar();
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeButton[@name=\"seu saldo em conta é de \"]"));

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
	@Order(4)
	public void consultaLimiteCredito004() {
		try {

			dsl.aguardar(1);
			dsl.swipe("down");
			dsl.aguardar(1);
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='início']"));
			dsl.aguardar(1);
			WebElement menuOperacoes = new WebDriverWait(driver, 1000).until(
					ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeButton[@name=\"operações\"]")));
			if (menuOperacoes.isEnabled()) {
				menuOperacoes.click();
			}
			dsl.aguardar();
			driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"crédito\"]")).click();

			dsl.aguardar(5);
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeNavigationBar[@name='acompanhe seus créditos']"));

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

	@Disabled("Erro na tela de Trnasferencia todos elementos não clicavel")
	@Test
	@Order(5)
	public void transferencia005() {
		try {
			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name=\"voltar\"]"))){
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"voltar\"]"));
			}

			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='operações']"))){
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='operações']"));
				logger.debug(statusLog.concat("Clicando em operações"));
			}

			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));
			Utils.evidence(testInfo, driver);

			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeStaticText[@name='pagamentos']"));
			dsl.clicar(By.xpath("//XCUIElementTypeApplication[@name=\"Empresas\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[2]/XCUIElementTypeOther"));
			logger.debug(statusLog.concat("Tranferencias Clicado"));
			dsl.aguardar();
			Utils.evidence(testInfo, driver);


			dsl.aguardarElementoExistir(By.xpath("(//XCUIElementTypeStaticText[@name=\"contas itaú\"])[2]/XCUIElementTypeOther"));
			dsl.clicar(By.xpath("(//XCUIElementTypeStaticText[@name=\"contas itaú\"])[2]/XCUIElementTypeOther"));
			logger.debug(statusLog.concat("Contas itau Clicado"));

			dsl.aguardarElementoExistir(By.xpath("(//XCUIElementTypeStaticText[@name=\"tipo de conta\"])[2]"));

			dsl.aguardar();
			dsl.swipe("up");
			logger.debug(statusLog.concat("swipe para baixo"));
			dsl.aguardar();

			Utils.evidence(testInfo, driver);


			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeLink[@name=\"escolher favorecido\"]"));
			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.xpath("//XCUIElementTypeLink[@name=\"escolher favorecido\"]"));
			logger.debug(statusLog.concat("Escolher favorecido Clicado"));
			dsl.aguardar(5);
			Utils.evidence(testInfo, driver);

			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeStaticText[contains(@name, '"+FAVORECIDO +"')]"));
			dsl.clicar(By.xpath("//XCUIElementTypeStaticText[contains(@name, '"+FAVORECIDO+"')]"));
			logger.debug(statusLog.concat("Selecionando destinatario"));
			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.xpath("//XCUIElementTypeTextField[@name=\"insira o valor\"]"));
			dsl.digitar(By.xpath("//XCUIElementTypeTextField[@name=\"insira o valor\"]" ), VALOR_TRANSFERENCIA);
			logger.debug(statusLog.concat("Valor de tranferencia Digitado"));
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"OK\"]"));
			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeButton[@name=\"transferir agora\"]"));
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"transferir agora\"]"));
			logger.debug(statusLog.concat("Botão transferir agora Clicado"));
			Utils.evidence(testInfo, driver);

			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeStaticText[@name=\"confirmar\"]"))){
				dsl.clicar(By.xpath("//XCUIElementTypeStaticText[@name=\"confirmar\"]"));
				logger.debug(statusLog.concat("Botão confirmar duplicidade Clicado"));
				Utils.evidence(testInfo, driver);
			}

			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeButton[@name=\"confirmar\"]"));
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"confirmar\"]"));
			logger.debug(statusLog.concat("Botão confirmar Clicado"));
			Utils.evidence(testInfo, driver);

			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeStaticText[@name=\"transação concluida com sucesso\"]"));
			logger.debug(statusLog.concat("Elemento transação concluida Encontrado"));
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeStaticText[@name=\"transação concluida com sucesso\"]"));

			logger.info(idTeste.concat("Sucesso;"));
			EmailLogic.addSuccess(idException,Utils.dataHoraFormatada(),operadora);
		} catch (Exception e) {
			EmailLogic.addError(idException,Utils.dataHoraFormatada(),operadora,e.getMessage());
			logger.error(idTeste.concat("Erro;Erro ao executar o cenário: ").concat(nomeTeste).concat(" - ").concat(e.getMessage()));
			throw e;
		} finally {
			Utils.evidence(testInfo, driver);
			logger.info("Cenário de testes finalizado com sucesso.");
		}
	}

}