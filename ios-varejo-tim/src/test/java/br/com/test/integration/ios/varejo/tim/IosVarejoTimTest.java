package br.com.test.integration.ios.varejo.tim;

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
public class IosVarejoTimTest {
	private static IOSDriver<IOSElement> driver;
	private TestInfo testInfo;
	final static Logger logger = Logger.getLogger("003");
	private static String SENHA_VIRTUAL;
	private static String TRANSFERENCIA_DEST_NOME;
	private static String TRANSFERENCIA_DEST_VALOR;
	private static String SENHA_CARTAO;
	private String nomeTeste;
	private static Ios dsl;
	private String idTeste;
	private String statusLog;
	private String operadora;
	private String idException;
	
	@BeforeAll
	public static void inicializar() throws IOException {
		Utils.init();
		driver = DriverFactory.getDriverIOS(URL.IOS_PF);
		SENHA_VIRTUAL = Utils.getPropAndResolve("senha_virtual_ios_varejo_tim");
		SENHA_CARTAO = Utils.getPropAndResolve("senha_cartao_ios_varejo_tim");
		TRANSFERENCIA_DEST_NOME = Utils.getProp("favorecido_ios_varejo_tim");
		TRANSFERENCIA_DEST_VALOR = Utils.getPropAndResolve("valor_ios_varejo_tim");
		dsl = new Ios();
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
	public void login001() {
		try {
			Utils.evidence(testInfo, driver);
			logger.debug(statusLog.concat("Abrindo o teclado virtual"));
			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeButton[@name=\"button_electronic_password_access\"]"));

			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"button_electronic_password_access\"]"));

			logger.debug(statusLog.concat("Digitando a senha: " + SENHA_VIRTUAL));
			char[] chars = SENHA_VIRTUAL.toCharArray();
			for (char ch : chars) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[contains(@label, '" + ch + "')]"));
			}
			logger.debug(statusLog.concat("Digitado no teclado virtual a senha: " + SENHA_VIRTUAL));
   
			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeButton[@name='button_electronic_password_access']"));
			Utils.evidence(testInfo, driver);
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"button_electronic_password_access\"]"));
   
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeButton[@name='expandir']"));
   
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
			logger.debug(statusLog.concat("Aguardando expandir"));
			//Thread.sleep(5000);
			//XCUIElementTypeButton[@name="esconder valores saldo em conta"]
			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name=\"exibir valores saldo em conta\"]"))) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"exibir valores saldo em conta\"]"));
				logger.debug(statusLog.concat("Botão expandir Clicado"));
			}
//			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='expandir']"))) {
//				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='expandir']"));
//				logger.debug(statusLog.concat("Botão expandir Clicado");
//			}
//
			logger.debug(statusLog.concat("Validando consulta saldo"));
			//XCUIElementTypeButton[@name="esconder valores saldo em conta"]
			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeButton[@name=\"esconder valores saldo em conta\"]"));
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeButton[@name=\"esconder valores saldo em conta\"]"));
//			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeStaticText[@name=\"text_view_balance_value\"]"));
//			String texto = driver
//					.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"text_view_balance_value\"]"))
//					.getAttribute("name");
//			Assert.assertEquals("text_view_balance_value", texto);

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

	//@Disabled("desabilitado dia 06/10/2021 por conta do erro do itau PFM desligado")
	@Test
	@Order(3)
	public void consultaExtrato003() {
		try {
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@label='extrato']"));
			logger.debug(statusLog.concat("Botão Extrato Clicado"));

			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeCell[@name=\"filtros\"]/XCUIElementTypeOther/XCUIElementTypeButton"));
			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));
			dsl.aguardar(5);

			Utils.evidence(testInfo, driver);
			dsl.swipe("up");
			dsl.swipe("down");


			dsl.clicar(By.xpath("//XCUIElementTypeCell[@name=\"filtros\"]/XCUIElementTypeOther/XCUIElementTypeButton"));
			logger.debug(statusLog.concat("Botão filtros Clicado"));

			//dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeCell[@name='todos os lançamentos']"));
			//dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.swipe("up");
			logger.debug(statusLog.concat("Swipe para cima"));
			dsl.aguardar(5);

			Utils.evidence(testInfo, driver);
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='60 dias']"));
//			dsl.rolarTela(By.xpath("//XCUIElementTypeButton[@name='60 dias']"), By.xpath("//XCUIElementTypeButton[@name='7 dias']"));
//			dsl.swipe("right");
			dsl.swipeElementToDirection(By.xpath("//XCUIElementTypeButton[@name='60 dias']"), "left");
			logger.debug(statusLog.concat("Swipe para direita nos botões de seleção dos dias"));

			//dsl.aguardar();
			Utils.evidence(testInfo, driver);
			dsl.swipe("down");
			//dsl.aguardar();
			dsl.swipe("up");
			//dsl.aguardar();
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='90 dias']"));
			logger.debug(statusLog.concat("Botão 90 dias Clicado"));

			//dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='filtrar lançamentos']"));
			logger.debug(statusLog.concat("Botão Filtrar Lançamentos Clicado"));

			dsl.aguardar(5);
			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeButton[@name=\"serviços\"]"));
//			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeStaticText[@name='R$']"));

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
	public void minhasFinancas007() {
		try {
			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='voltar']"))){
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='voltar']"));
				logger.debug(statusLog.concat("Botão Voltar Clicado"));
			}
			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@label='extrato']"))){
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@label='extrato']"));
				logger.debug(statusLog.concat("Botão Voltar Clicado"));
			}

			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));
			Utils.evidence(testInfo, driver);
			dsl.aguardar(5);
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='minhas finanças']"));
			logger.debug(statusLog.concat("Botão Minhas Finanças Clicado"));

			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeStaticText[@name='resumo de conta corrente']"));

			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeStaticText[@name='resumo de conta corrente']"));

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
	public void consultaLimiteCredito005() {
		try {

			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name=\"voltar\"]"))){
				driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"voltar\"]")).click();
			}
			Utils.evidence(testInfo, driver);
			logger.debug(statusLog.concat("Clicando em serviços"));
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"serviços\"]"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);
			logger.debug(statusLog.concat("Clicando em empréstimos"));
			dsl.clicar(By.xpath("//XCUIElementTypeCell[@name=\"empréstimo\"]/XCUIElementTypeOther"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);
			//XCUIElementTypeStaticText[@name="meus limites de crédito"]
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeStaticText[@name=\"meus limites de crédito\"]"));
			//dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeNavigationBar[@name=\"crédito\"]"));

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
	public void consultaLimiteCartao004() {
		try {
			logger.debug(statusLog.concat("Clicando em serviços"));
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name=\"serviços\"]"));
			logger.debug(statusLog.concat("Clicando em cartões"));

			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeCell[@name=\"cartões\"]/XCUIElementTypeOther"))) {
				dsl.clicar(By.xpath("//XCUIElementTypeCell[@name=\"cartões\"]/XCUIElementTypeOther"));
			}
			dsl.aguardarElementoExistir(By.xpath("//XCUIElementTypeNavigationBar[@name=\"cartões\"]"));
   
			logger.debug(statusLog.concat("Validando limite cartões"));
			dsl.validaElementoNaTela(By.xpath("//XCUIElementTypeNavigationBar[@name=\"cartões\"]"));

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
 
	@Disabled("Cenário não Autorizado pelo Itau")
	@Test
	@Order(6)
	public void transferencia006() {
		try {
			if(dsl.elementoExiste(By.xpath("//XCUIElementTypeStaticText[@name=\"sim\"]"))){
				dsl.clicar(By.xpath("//XCUIElementTypeStaticText[@name=\"sim\"]"));
			}
			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='voltar']"))){
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='voltar']"));
				logger.debug(statusLog.concat("Botão voltar Clicado"));
			}
			if (dsl.elementoExiste(By.xpath("//XCUIElementTypeButton[@name='transações']"))) {
				dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='transações']"));
				logger.debug(statusLog.concat("Botão transações Clicado"));
			}
   
			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));
   
			Utils.evidence(testInfo, driver);
   
			dsl.clicar(By.xpath("//XCUIElementTypeButton[@name='fazer transferência']"));
			logger.debug(statusLog.concat("Botão fazer transferência Clicado"));

			dsl.aguardarElementoExistir(By.name("Pj1"));
			Utils.evidence(testInfo, driver);

			if(dsl.elementoExiste(By.xpath("//XCUIElementTypeStaticText[@name=\"continuar\"]"))){
				dsl.clicar(By.xpath("//XCUIElementTypeStaticText[@name=\"continuar\"]"));
			}

			if(dsl.elementoExiste(By.xpath("//XCUIElementTypeStaticText[@name=\"agora não\"]"))){
				dsl.clicar(By.xpath("//XCUIElementTypeStaticText[@name=\"agora não\"]"));
			}
   
			try {
				dsl.clicar(By.name(TRANSFERENCIA_DEST_NOME));
				logger.debug(statusLog.concat("Conta " + TRANSFERENCIA_DEST_NOME + " Clicado"));
			} catch (Exception e) {
				logger.error("Conta " + TRANSFERENCIA_DEST_NOME + " Não encontrado", e);
				throw e;
			}

			dsl.aguardar();

			// dsl.clicarPorCordenadas(360,330);
			dsl.digitar(By.xpath("//XCUIElementTypeTextField"), TRANSFERENCIA_DEST_VALOR);
			dsl.clicar(By.name("Limpar texto"));
			dsl.digitar(By.xpath("//XCUIElementTypeTextField"), TRANSFERENCIA_DEST_VALOR);
			logger.debug(statusLog.concat("Digitado o valor: " + TRANSFERENCIA_DEST_VALOR + " no elemento valor da transferência"));

			dsl.clicar(By.name("KEYBOARD_OK_BUTTON"));
			logger.debug(statusLog.concat("Fechando teclado alfanumerico do iPhone"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.name("continuar para data"));
			logger.debug(statusLog.concat("Botão continuar para data Clicado"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.name("continuar para senha e revisão"));
			logger.debug(statusLog.concat("Botão continuar para senha e revisão Clicado"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			try {
				dsl.digitar(By.className("PASSWORD_FIELD"), SENHA_CARTAO);
				logger.debug(statusLog.concat("Digitado a senha do cartão: " + SENHA_CARTAO));
			} catch (Exception e) {
				char[] chars = SENHA_CARTAO.toCharArray();
				for (char ch : chars) {
					dsl.clicar(By.xpath("//XCUIElementTypeKey[@label = '" + ch + "']"));
				}
				logger.debug(statusLog.concat("Digitado a senha do cartão: " + SENHA_CARTAO + " pelo teclado do aparelho"));
			}
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.name("CONTINUE_BUTTON"));
			logger.debug(statusLog.concat("Botão continuar Clicado"));

			dsl.aguardarElementoExistir(By.name("confirmar transferência"));
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.name("confirmar transferência"));
			logger.debug(statusLog.concat("Confirmar transferência Clicado"));

			// TODO: validar elemento final

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