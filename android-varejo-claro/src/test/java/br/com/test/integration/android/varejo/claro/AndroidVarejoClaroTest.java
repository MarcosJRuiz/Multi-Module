package br.com.test.integration.android.varejo.claro;

import br.com.test.integration.suite.driver.DriverFactory;
import br.com.test.integration.suite.dsl.Android;
import br.com.test.integration.suite.enuns.DEVICE;
import br.com.test.integration.suite.enuns.URL;
import br.com.test.integration.suite.evidence.EmailLogic;
import br.com.test.integration.suite.evidence.Utils;
import br.com.test.integration.suite.views.devices.DadosBancarios;
import br.com.test.integration.suite.views.devices.Notificacao;
import br.com.test.integration.suite.views.devices.Senha;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AndroidVarejoClaroTest {
	private static AppiumDriver<MobileElement> driver = null;
	public TestInfo testInfo;
	final static Logger logger = Logger.getLogger("002");
	private String nomeTeste;
	private static String TRANSFERENCIA_DEST_NOME;
	private static String TRANSFERENCIA_DEST_VALOR;
	private static String SENHA_CARTAO;
	private String idTeste;
	private String statusLog;
	private String operadora;
	private String idException;


	@BeforeAll
	public static void inicializar() throws IOException {
		Utils.init();
		driver = DriverFactory.getDriver(URL.ANDROID_VAREJO_CLARO);
		TRANSFERENCIA_DEST_NOME = Utils.getProp("favorecido_android_varejo_claro");
		TRANSFERENCIA_DEST_VALOR = Utils.getPropAndResolve("valor_android_varejo_claro");
		SENHA_CARTAO = Utils.getPropAndResolve("senha_cartao_android_varejo_claro");
	}

	@BeforeEach
	public void init(TestInfo testInfo) {
		this.testInfo = testInfo;
		this.nomeTeste = Utils.getTestName(testInfo);
		this.operadora = Utils.getOperadora(testInfo);
		this.idTeste = Utils.getIdTest(testInfo).concat(";").concat(operadora).concat(";");
		this.statusLog = idTeste + "Log;";
		this.idException = "002" + Utils.getIdTest(testInfo);
		//Gravar.video();
		logger.info("Start;Inicio do cenário " + nomeTeste + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
		DriverFactory.killDriver(DEVICE.ANDROID);
	}

	@Test
	@Order(1)
	public void login001() {
		try {
			Notificacao.fechar(testInfo, driver);
			Notificacao.recusar(testInfo, driver);
			Notificacao.acessarMinhaConta(testInfo, driver);

			DadosBancarios.androidVarejo(idTeste, testInfo, driver);
			Senha.senhaVirtualAndroidVarejo(idTeste,testInfo, driver);

			Android.aguardar();
			Notificacao.clicarNao(testInfo, driver);
			Notificacao.fechar(testInfo, driver);
			Notificacao.maisTarde(testInfo, driver);

			Android.validaElementoNaTela((By.xpath("//android.widget.TextView[contains(@text,'R$')]")));

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
			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"))) {
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"));
			}
			if (Android.elementoExiste(By.xpath("//android.view.View[@content-desc='início']"))) {
				Android.clicar(By.xpath("//android.view.View[@content-desc='início']"));
			}
			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));

			if (Android.elementoExiste(By.xpath("//android.widget.Button[@content-desc='exibir valores saldo em conta']"))) {
				Utils.evidence(testInfo, driver);
				Android.clicar(By.xpath("//android.widget.Button[@content-desc='exibir valores saldo em conta']"));
				logger.debug(statusLog.concat("Botão exibir valores saldo em conta clicado"));
				Android.aguardar();
			}

			Android.validaElementoNaTela((By.xpath("//android.widget.TextView[contains(@text, 'R$')]")));
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
	public void consultaLimiteCartao004() {
		try {
			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"))) {
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"));
			}
			if (Android.elementoExiste(By.xpath("//android.view.View[@content-desc='início']"))) {
				Android.clicar(By.xpath("//android.view.View[@content-desc='início']"));
			}

			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));

			Android.aguardarElementoExistir(By.xpath("//android.widget.Button[@text='ver fatura']"));

			Utils.evidence(testInfo, driver);
			Android.clicar(By.xpath("//android.widget.Button[@text='ver fatura']"));
			logger.debug(statusLog.concat("Botão ver mais clicado dentro do card de cartão "));

			Android.aguardar();

			Android.validaElementoNaTela(By.xpath("//android.widget.TextView[contains(@text,'detalhes da fatura')]"));
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
	public void consultaLimiteCredito005() {
		try {
			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"))) {
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"));
			}
			if (Android.elementoExiste(By.xpath("//android.view.View[@content-desc='início']"))) {
				Android.clicar(By.xpath("//android.view.View[@content-desc='início']"));
			}
			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));

			Android.aguardar();
			Utils.evidence(testInfo, driver);

			Android.rolarTela(By.xpath("//android.widget.TextView[@text='crédito']"), By.xpath("//android.widget.TextView[@text='Itau Uniclass Visa Platinum']"));
			logger.debug(statusLog.concat("Swipe para cima"));

			Android.aguardar();
			Utils.evidence(testInfo, driver);

			Android.clicar(By.xpath("//android.widget.Button[@text='ver mais']"));
			logger.debug(statusLog.concat("Botão ver mais clicado dentro do card do crédito "));

			Android.aguardarElementoExistir(By.xpath("//android.view.View[@text='meus limites de crédito']"));

			Android.validaElementoNaTela(By.xpath("//android.widget.TextView[@text='crédito']"));
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

	//@Disabled("desabilitado dia 05/07/2021 por conta do erro do itau PFM desligado")
	@Test
	@Order(6)
	public void consultaMinhasFinancas007() {
		try {
			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"))) {
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"));
			}
			if (Android.elementoExiste(By.xpath("//android.view.View[@content-desc='extrato']"))) {
				Android.clicar(By.xpath("//android.view.View[@content-desc='extrato']"));
			}

			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));

			Android.aguardar(5);

			Utils.evidence(testInfo, driver);
			Android.aguardarElementoExistir(By.xpath("//android.widget.LinearLayout[@content-desc='minhas finanças']/android.widget.TextView"));
			Android.clicar(By.xpath("//android.widget.LinearLayout[@content-desc='minhas finanças']/android.widget.TextView"));
			//Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='ver minhas finanças']"));
			logger.debug(statusLog.concat("Botão ver minhas finanças Clicado"));

			Android.validaElementoNaTela(By.xpath("//android.widget.TextView[@text='minhas finanças']"));
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
	@Order(5)
	public void consultaExtrato003() {
		try {
			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"))) {
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"));
				logger.debug(statusLog.concat("Botão Voltar Clicado"));
			}
			if (Android.elementoExiste(By.xpath("//android.view.View[@content-desc='extrato']"))) {
				Android.clicar(By.xpath("//android.view.View[@content-desc='extrato']"));
				logger.debug(statusLog.concat("Botão Extrato Clicado"));
			}

			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));

			Utils.evidence(testInfo, driver);
			Android.clicar(By.xpath("//android.widget.Button[@text='filtros']"));
			logger.debug(statusLog.concat("Botão filtros Clicado"));

			Android.aguardarElementoExistir(By.xpath("//android.widget.TextView[@text='tipo de lançamento']"));

			Utils.evidence(testInfo, driver);
			Android.rolarTela(By.xpath("//android.widget.TextView[@text='tipo de lançamento']"),
					By.xpath("//android.widget.TextView[@text='origem dos lançamentos']"));
			logger.debug(statusLog.concat("Swipe para cima"));

			Utils.evidence(testInfo, driver);
			Android.rolarTela(By.xpath("//android.widget.Button[@text='60 dias']"),
					By.xpath("//android.widget.Button[@text='7 dias']"));
			logger.debug(statusLog.concat("Swipe para direita nos botões de seleção dos dias"));

			Utils.evidence(testInfo, driver);
			Android.clicar(By.xpath("//android.widget.Button[@text='90 dias']"));
			logger.debug(statusLog.concat("Botão 90 dias Clicado"));

			Utils.evidence(testInfo, driver);
			Android.clicar(By.xpath("//android.widget.Button[@content-desc='voltar']"));
			logger.debug(statusLog.concat("Botão voltar Clicado"));

			Android.aguardarElementoExistir(By.xpath("//android.widget.TextView[@text='R$']"));

			Android.validaElementoNaTela(By.xpath("//android.widget.Button[@text='filtros']"));
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

	//@Disabled("Cenário não Autorizado pelo Itau")
	@Test
	@Order(7)
	public void transferencia006() {
		try {
			if (Android.elementoExiste(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"))) {
				Android.clicar(By.xpath("//android.widget.ImageButton[@content-desc='Voltar']"));
			}
			if (Android.elementoExiste(By.xpath("//android.view.View[@content-desc='transações']"))) {
				Android.clicar(By.xpath("//android.view.View[@content-desc='transações']"));
			}
			logger.debug(statusLog.concat(" Tela Home " + nomeTeste));
			Utils.evidence(testInfo, driver);

			Android.clicar(By.xpath("//android.widget.Button[@content-desc='fazer transferência']"));
			logger.debug(statusLog.concat("Botão fazer transferência Clicado"));
			Android.aguardar();
			try {
				Android.clicar(By.xpath("//android.widget.TextView[contains(@text,'" + TRANSFERENCIA_DEST_NOME + "')]"));
				logger.debug(statusLog.concat("Conta " + TRANSFERENCIA_DEST_NOME + " Clicado"));
		} catch (Exception e) {
				logger.error("Conta " + TRANSFERENCIA_DEST_NOME + " Não encontrado", e);
				throw e;
			}
			
			Android.aguardar();

			Android.digitar(By.xpath("//android.widget.EditText"), TRANSFERENCIA_DEST_VALOR);
			logger.debug(statusLog.concat("Digitado o valor: " + TRANSFERENCIA_DEST_VALOR + " no elemento valor da transferência"));
			
			Android.aguardar();
			Utils.evidence(testInfo, driver);

			Android.clicar(By.xpath("//android.widget.Button[@text='continuar para data']"));
			logger.debug(statusLog.concat("Botão continuar para data Clicado"));
			
			Android.aguardarElementoExistir(By.xpath("//android.widget.Button[@content-desc='continuar para senha e revisão']"));
			Utils.evidence(testInfo, driver);
						
			Android.clicar(By.xpath("//android.widget.Button[@content-desc='continuar para senha e revisão']"));
			logger.debug(statusLog.concat("Botão continuar para senha e revisão Clicado"));
			
			By senhaCartao = By.className("android.widget.EditText");

			Android.digitar(senhaCartao, SENHA_CARTAO);
			logger.debug(statusLog.concat("Digitado a senha do cartão: " + SENHA_CARTAO));
			
			Utils.evidence(testInfo, driver);
			
			int i = 1;
			while (i < 5 && !Android.elementoExiste(By.xpath("//android.widget.Button[@text='confirmar transferência']"))) {
				if (Android.elementoExiste(By.xpath("//android.widget.Button[@content-desc='continuar']"))) {
					Android.clicar(By.xpath("//android.widget.Button[@content-desc='continuar']"));
					logger.debug(statusLog.concat("Botão continuar Clicado"));
				}else {
					logger.debug(statusLog.concat("Botão continuar não encontrado - " + i));
				}
				i = i + 1;
				Android.aguardar();
			}
			
			Utils.evidence(testInfo, driver);
																		
			Android.clicar(By.xpath("//android.widget.Button[@text='confirmar transferência']"));
			logger.debug(statusLog.concat("Botão confirmar transferência Clicado"));
			
			Android.aguardarElementoExistir(By.xpath("//android.widget.TextView[@text='efetivação']"));
			
			Notificacao.fechar(testInfo, driver);
			
			assertTrue(Android.elementoExiste(By.xpath("//android.widget.TextView[@content-desc='concluída']")));
			
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
