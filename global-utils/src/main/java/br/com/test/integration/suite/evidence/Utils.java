package br.com.test.integration.suite.evidence;

import br.com.test.integration.suite.enuns.URL;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.xmlpull.v1.XmlPullParserException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public final class Utils {

	//private static Jedis jedis;
	public final static String LOCAL = "local";
	private static final String FILE_PARAMETERS = "target/sysmap-prod.properties";
	private static final String FILE_PARAMETERS_DEFAULT = "target/itau.properties";
	public static String MSG_ERRO_EXEC_CENARIO = "Erro ao executar o cenário de testes";
	public static String MSG_INIT_CENARIO = "Iniciando cenário de teste";
	public static final String REMOTE = "remote";
	private static Properties prop;
	private static final String ITOKEN_VAREJO = "ITOKEN_VAREJO";

	final static Logger logger = Logger.getLogger(Utils.class);

	public static void main(String[] args) throws Exception {

//		jedis = new Jedis(Utils.getProp("host_jedis"));
//		logger.info(jedis.ping()); // executar();

		System.setProperty("target", "local");
		init();
		logger.info(Utils.getProp("pf_ch_ag"));
	}

	public static void init() throws IOException {

		// if (isTargetLocal()) {
		// FILE_PARAMETERS = "target/sysmap-local.properties";
		// }

		//logger.debug("Log;Log;Log;Usando parametros definidos no arquivo: " + FILE_PARAMETERS);

		Properties propTarget = new Properties();
		FileInputStream file = new FileInputStream(FILE_PARAMETERS);

		propTarget.load(file);

		Properties propDefault = new Properties();
		FileInputStream fileDefault = new FileInputStream(FILE_PARAMETERS_DEFAULT);

		propDefault.load(fileDefault);

		prop = mergeProperties(propTarget, propDefault);

		//connectRedis();
	}

//	private static void connectRedis() {
//		try {
//			jedis = new Jedis(Utils.getProp("host_jedis"));
//		} catch (Exception e) {
//			Log.error("Erro ao conectar no jedis, o controle de sessão não será usado: ".concat(getMessageError(e)));
//		}
//	}

	private static Properties mergeProperties(Properties... properties) {
		return Stream.of(properties).collect(Properties::new, Map::putAll, Map::putAll);
	}

	/**
	 * Recorta a Imagem contendo somente o numero do Token
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	private static void loadImage(final String nomeArquivo, final URL url) {
		try {
			BufferedImage imagem = ImageIO.read(new File("target\\" + nomeArquivo));
			if (ITOKEN_VAREJO == url.nome()) {
				ImageIO.write(imagem.getSubimage(330, 1190, 450, 150), "PNG", new File("target\\token.png"));
			} else {
				ImageIO.write(imagem.getSubimage(225, 830, 270, 90), "PNG", new File("target\\token.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Converte a imagem em String.
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static String convertImageToString(final String nomeArquivo, final URL url) throws Exception {
		String result = null;
		loadImage(nomeArquivo, url);
		File imageFile = new File("target/token.png");
		Tesseract instance = new Tesseract();
		instance.setDatapath("../src/main/resources/tessdata");
		// instance.setLanguage("por");
		try {
			for (int i = 1; i < 3; i++) {
				result = instance.doOCR(imageFile);
				result = onlyNumbers(result);
				logger.debug("Tentativa : " + i + " do OCR : " + result);
				if (result.length() == 6) {
					break;
				}
			}
		} catch (TesseractException e) {
			logger.error("Erro ao converter em String o print da tela do device" + e);
			throw e;
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	public static void artefato(final String local, final String nomeArquivo, final byte[] bytes)
			throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException {

		// String arquivo = nomeArquivo + ".png";

		// logger.debug("Criando inputstream");

		try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {

			final String host = getProp("minio_host");
			final String usuario = getProp("minio_usuario");
			final String senha = getProp("minio_senha");

			// logger.debug("Conecatando no Minio Host: ".concat(host).concat("; Usuario:
			// ").concat(usuario));

			try {
				MinioClient minioClient = new MinioClient(host, usuario, senha);

				if (!minioClient.bucketExists(local)) {
					minioClient.makeBucket(local);
				}

				// logger.debug("Fazendo o upload");

				minioClient.putObject(local, nomeArquivo, bais, bais.available(), "application/octet-stream");

				// logger.debug("Upload realizado com sucesso, Fechando o inputstream");

				String url = minioClient.presignedGetObject(local, nomeArquivo);

				logger.debug("Evidencia;Log;Log;Evidencia ".concat(nomeArquivo).concat(" gerada com sucesso: ").concat(url));

			} catch (MinioException e) {
				logger.error("Evidencia;Log;Log;Erro ao salvar evidencia - ".concat(e.getMessage()));
			}
		} catch (Exception e) {
			logger.error("Evidencia;Log;Log;Erro ao gerar a evidencia - ".concat(e.getMessage()));
		}
	}

	public static void arquivoLog(final TestInfo testName, final String result) {
		String pathFile = getProp("monitoring_log");
		try {
			File arquivo = new File(pathFile);

			if (!arquivo.exists()) {
				arquivo.createNewFile();
			}

			FileWriter fw = new FileWriter(arquivo, true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(dataHoraFormatada() + " | " + testName.getTestMethod().toString() + " | " + result);
			bw.newLine();
			bw.close();
			fw.close();

		} catch (IOException ex) {
			logger.error("Erro ao salvar o log [".concat(pathFile).concat("]: ").concat(Utils.getMessageError(ex)));
		}
	}

	public static String dataHora() {
		Date dataAtual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmsss");
		return dateFormat.format(dataAtual);
	}

	public static String getHora() {
		Date dataAtual = new Date();
		dataAtual.setHours(dataAtual.getHours() + 1);
		DateFormat dateFormat = new SimpleDateFormat("HH");
		return dateFormat.format(dataAtual);
	}

	public static String dataHoraFormatada() {

		Date dataAtual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dataFormatada = dateFormat.format(dataAtual);

		return dataFormatada;

	}

	public static String dataFormatada() {
		Date dataAtual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		return dateFormat.format(dataAtual);
	}

	public static void eraseImage(final String name) {
		File pugar = new File("C:\\app\\" + name);
		if (pugar.exists()) {
			pugar.delete();
		}
	}

	public static String onlyNumbers(final String str) {
		if (str != null) {
			return str.replaceAll("[^0123456789]", "");
		} else {
			return "";
		}
	}

	public static boolean isTargetLocal() {
		String target = System.getProperty("target");

		logger.debug("Target:" + target);

		return target != null && LOCAL.equals(target);
	}

	public static String getProp(final String key) {
		try {
			return (prop.getProperty(key).replaceAll("\"", "").replaceAll("'", ""));
		} catch (Exception e) {
			logger.error("Parameter;Log;Erro;A propriedade [".concat(key).concat("] não foi declada no seu arquivo de configuração."));
			throw e;
		}
	}

	public static String getPropAndResolve(String string) {
		return getProp(getProp(string));
	}

	/** @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a> */
	public static String getTokenImage(final TestInfo testInfo, final WebDriver driver) {
		return evidenceDefault(testInfo, driver, true);
	}

	/** @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a> */
	public static void evidence(final TestInfo testInfo, final WebDriver driver) {
		evidenceDefault(testInfo, driver, false);
	}

	/** @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a> */
	private static String evidenceDefault(final TestInfo testInfo, final WebDriver driver, final Boolean token) {
		String nomeArquivo = null;
		try {
			if (System.getProperty("evidence") == null || System.getProperty("evidence") == "true") {

				//logger.debug("Gerando evidencias do teste : ".concat(testInfo.getTestMethod().get().getName()));

				String buildNumber = "0";

				if (System.getProperty("buildInfo") != null) {
					buildNumber = System.getProperty("jenkinsBuildNumber");
					logger.debug("Build info: ".concat(System.getProperty("buildInfo")));
				}

				// logger.debug("Recuperando imagem do Browser");
				byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

				// logger.debug("Definindo nome do arquivo");
				nomeArquivo = dasherize(testInfo.getTestClass().get().getSimpleName()).replace("-test", "").concat("-")
						.concat(dasherize(testInfo.getTestMethod().get().getName())).concat("-")
						.concat(Utils.dataHora()).concat(".png");

				if (token) {
					saveLocalFile("target/".concat(nomeArquivo), bytes);
				} else {
					String localArquivo = getProp("evidences").concat(nomeArquivo);
					evidenceSave("build-".concat(buildNumber), localArquivo, nomeArquivo, bytes);
					// saveLocalFile("target/".concat(nomeArquivo), bytes);
				}

			} else {
				logger.warn("Não será gerado evidencia " + testInfo.getTestMethod().toString() + ".");
			}
		} catch (Exception e) {
			logger.debug("Erro ao gerar evidencia, os testes não serão interrompidos.".concat(getMessageError(e)));
		}
		return nomeArquivo;
	}

	private static void evidenceSave(final String folder, String localArquivo, final String nomeArquivo,
									 final byte[] bytes) throws IOException {
		try {
			// saveLocalFile(localArquivo, bytes);
			Utils.artefato(folder, nomeArquivo, bytes);
		} catch (Exception e) {
			logger.error("Erro ao salvar a imagem no Minio, salvando evidencia local");
			e.printStackTrace();
			saveLocalFile(localArquivo, bytes);
		}

	}

	public static String dasherize(String str) {
		if (str == null)
			return null;

		return str.replaceAll("([A-Z]+)([A-Z][a-z])", "$1-$2").replaceAll("([a-z])([A-Z])", "$1-$2")
				.replaceAll("_", "-").toLowerCase();
	}

	private static void saveLocalFile(final String scrFile, final byte[] bytes) throws IOException {

		try {
			File file = new File(scrFile);
			FileOutputStream os = new FileOutputStream(file);
			os.write(bytes);
			os.close();

//			File file = new File(scrFile);
//
//			OutputStream os = new FileOutputStream(scrFile);
//			System.out.println("Successfully byte inserted");
//			os.close();
//
//			FileUtils.copyFile(file, new File(scrFile));
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//			private void writeScreenshotToFile(WebDriver driver, File screenshot) {
//			    try {
//			        FileOutputStream screenshotStream = new FileOutputStream(screenshot);
//			        screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
//			        screenshotStream.close();
//			    } catch (IOException unableToWriteScreenshot) {
//			        System.err.println("Unable to write " + screenshot.getAbsolutePath());
//			        unableToWriteScreenshot.printStackTrace();
//			    }
//			}

			logger.debug("Evidencia gerada: " + scrFile);
		} catch (Exception e) {
			logger.error("Erro ao salvar evidencia local", e);

		}

	}

	public static String getMessageError(Exception e) {
		try {
			String cause = null != e.getCause() ? e.getCause().toString() : "Causa generica";
			String message = null != e.getMessage() ? e.getMessage()
					: e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "Erro generico";

			return cause.concat(" :: ").concat(message);
		} catch (Exception e2) {
			logger.error("Erro ao recuperar cause e messagae do erro: ", e);
			return "Erro generico";
		}
	}

	public static String getTestName(TestInfo testInfo) {
		return testInfo.getDisplayName().replace("(", "").replace(")", "");
	}

	public static String getIdTest(TestInfo testInfo) {
		return Utils.getTestName(testInfo).replaceAll("[^0-9]", "");
	}

	public static String getOperadora(TestInfo testInfo) {
		String[] op = String.valueOf(testInfo.getTestClass()).replace(".",";").split(";");
		return op[op.length-2];
	}

	//	public static String getRedisKey(String key) {
//		String value = null;
//		if (null != jedis)
//			value = jedis.get(key);
//		return value;
//	}
//
//	public static void setRedisKey(String key, String value) {
//		if (null != jedis)
//			jedis.set(key, value);
//	}
//
//	public static void delRedisKey(String key) {
//		if (null != jedis)
//			jedis.del(key);
//	}

}