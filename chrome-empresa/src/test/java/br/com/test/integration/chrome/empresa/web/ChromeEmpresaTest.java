package br.com.test.integration.chrome.empresa.web;

import br.com.test.integration.suite.driver.DriverFactory;
import br.com.test.integration.suite.dsl.Browser;
import br.com.test.integration.suite.enuns.DEVICE;
import br.com.test.integration.suite.enuns.URL;
import br.com.test.integration.suite.evidence.EmailLogic;
import br.com.test.integration.suite.evidence.Utils;
import br.com.test.integration.suite.views.devices.Itoken;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChromeEmpresaTest {

	private static WebDriver driver = null;
	private static Browser dsl;
	private String nomeTeste;
	public TestInfo testInfo;
	final static Logger logger = Logger.getLogger("007");
	private static String OPERADOR;
	private static String SENHA_VIRTUAL;
	private static String TRANSF_VALOR;
	private static String FAVORECIDO;
	private String idTeste;

	private String statusLog;
	private String operadora;
	private String idException;

	@BeforeAll
	public static void inicializar() throws Exception {
		Utils.init();
		OPERADOR = Utils.getPropAndResolve("operador_chrome_empresa");
		SENHA_VIRTUAL = Utils.getPropAndResolve("senha_virtual_chrome_empresa");
		TRANSF_VALOR = Utils.getPropAndResolve("valor_chrome_empresa");
		FAVORECIDO = Utils.getPropAndResolve("favorecido_chrome_empresa");
		driver = DriverFactory.getDriverBrowser(URL.BROWSER);
		dsl = new Browser();
	}

	@BeforeEach
	public void inicio(TestInfo testInfo) {
		this.testInfo = testInfo;
		this.nomeTeste = Utils.getTestName(testInfo);
		this.operadora = Utils.getOperadora(testInfo);
		this.idTeste = Utils.getIdTest(testInfo).concat(";").concat(operadora).concat(";");
		this.statusLog = idTeste + "Log;";
		this.idException = "007" + Utils.getIdTest(testInfo);
		logger.info(idTeste.concat("Start;Inicio do cenário " + nomeTeste + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"));
	}

	@AfterEach
	public void fim() {
		logger.debug(statusLog.concat("Fim do Cenário " + nomeTeste + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"));
		System.out.println();
	}

	@AfterAll
	public static void finalizar() {
		DriverFactory.killDriver(DEVICE.BROWSER);
	}

	@Test
	@Order(1)
	public void login001() throws Exception {
		try {
			Utils.evidence(testInfo, driver);
			dsl.clicar(By.id("menuTypeAccess"));
			logger.debug(statusLog.concat("clicado no menu operador"));

			dsl.digitar(By.id("codOp"), OPERADOR);
			logger.debug(statusLog.concat("Agencia " + OPERADOR + " digitada"));
			Utils.evidence(testInfo, driver);

			dsl.aguardarElementoExistir(By.xpath("//*[contains(text(),'Teclado virtual')]"));

			dsl.selectText(By.id("seleciona-autenticacao"), "iToken no aplicativo");
			logger.debug(statusLog.concat("No campo dispositovo de segurança foi selecionado iToken no aplicativo"));

			dsl.precionar(By.tagName("body"), Keys.ESCAPE);

			dsl.validaElementoNaTela(By.id("lupa-busca"));

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
	
	@Test
	@Order(2)
	public void consultaSaldo002() {
		try {
			if (dsl.elementoExiste(By.xpath("//body[@id='ibba']//a[@class='logos']"))) {
				dsl.clicar(By.xpath("//body[@id='ibba']//a[@class='logos']"));
			} else {
				dsl.switchFrame("MENU");
				dsl.clicar(By.xpath("//a[@role='link']"));
			}
			dsl.aguardarLoading();

			logger.debug(statusLog.concat("Pagina HOME"));
			Utils.evidence(testInfo, driver);


			dsl.clicar(By.xpath("//a[@id='botao-buscar-ni']"));
			logger.debug(statusLog.concat("Clicado na lupa para expandir o campo de busca para digitar"));
			Utils.evidence(testInfo, driver);

			dsl.digitar(By.xpath("//input[@id='input-busca']"), "Saldo de Conta Corrente");
			logger.debug(statusLog.concat("Digitado: 'Consultar saldo da conta corrente' no campo de busca"));
			Utils.evidence(testInfo, driver);

			dsl.precionar(By.id("input-busca"), Keys.ENTER);
			logger.debug(statusLog.concat("iniciar busca"));

			dsl.switchFrame("CORPO");
			dsl.validaElementoNaTela(By.xpath("//h1[text()='Conta']"));

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


	@Disabled ("EM DESENVOLVIMENTO")
	@Test
	@Order(3)
	public void consultaLimiteCredito003() {
		try {


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


	@Test
	@Order(4)
	public void realizarTransferencia004() throws Exception {
		try {
			Utils.evidence(testInfo, driver);
			logger.debug(statusLog.concat("Pagina HOME"));

			dsl.clicarJs(By.xpath("//a[text()='pagamentos e transferências']"));
			logger.debug(statusLog.concat("Menu pagamentos e transferências clicado"));
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.xpath("//a[text()='pagamentos e transferências']/..//a[contains(text(),'incluir')]"));
			logger.debug(statusLog.concat("SubMenu incluir Clicado"));
			Utils.evidence(testInfo, driver);

			dsl.aguardar(5);

			dsl.switchFrame("CORPO");

			dsl.rolarAteElemento(By.xpath("//a[text()='Conta corrente para conta corrente']"));
			logger.debug(statusLog.concat("Scroll aplicado ate o texto Conta corrente para conta corrente aparecer"));

			Utils.evidence(testInfo, driver);

			dsl.clicarJs(By.xpath("//a[text()='Conta corrente para conta corrente']"));
			logger.debug(statusLog.concat("Link Conta corrente para conta corrente pressionado"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.clicarJs(By.xpath("//td[text()='LEANDRO PF4']/..//a[text()='selecionar']"));
			logger.debug(statusLog.concat("Link selecionar pressionado"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.digitar(By.id("ValorTef"), TRANSF_VALOR);
			logger.debug(statusLog.concat("Valor da Transferencia R$ " + TRANSF_VALOR + " digitada"));
			Utils.evidence(testInfo, driver);


			dsl.clicar(By.id("data1"));
			logger.debug(statusLog.concat("Radio com texto Hoje clicado"));
			Utils.evidence(testInfo, driver);


			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.clicar(By.xpath("//img[@alt='Este pagamento/transferência será efetivado.']/.."));
			logger.debug(statusLog.concat("Botão incluir e autorizar Clicado"));

			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			if (dsl.elementoExiste(By.xpath("//input[@alt='INCLUIR_E_AUTORIZAR']"))) {
				dsl.clicarJs(By.xpath("//input[@alt='INCLUIR_E_AUTORIZAR']"));
				logger.debug(statusLog.concat("Botão incluir e autorizar Clicado"));
				dsl.aguardar();
				Utils.evidence(testInfo, driver);
			}

			dsl.clicarJs(By.xpath("//input[@alt='AUTORIZAR']"));
			logger.debug(statusLog.concat("Botão autorizar Clicado"));
			Utils.evidence(testInfo, driver);

			logger.debug(statusLog.concat("iToken no Aplicativo Clicado"));
			dsl.aguardar();
			Utils.evidence(testInfo, driver);

			dsl.aguardar();
			dsl.digitar(By.xpath(".//*[@id='senhaDS02']"), Itoken.getNumber(testInfo, URL.ITOKEN_EMPRESA));
			logger.debug(statusLog.concat("Digitado o iToken no campo código de segurança"));

			Utils.evidence(testInfo, driver);

			dsl.clicarJs(By.xpath("//input[@alt='CONFIRMAR']"));
			logger.debug(statusLog.concat("Botão confirmar Clicado"));
			Utils.evidence(testInfo, driver);

			dsl.aguardarLoading();

			dsl.aguardarElementoExistir(By.xpath("//p[text()='Operação efetuada com sucesso.']"));

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