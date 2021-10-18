package br.com.test.integration.suite.driver;

import br.com.test.integration.suite.enuns.DEVICE;
import br.com.test.integration.suite.enuns.URL;
import br.com.test.integration.suite.evidence.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

/**
 * Classe de inicialização de instancia para dispositivo movel ou Browser.
 * 
 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
 */
public class DriverFactory {

	final static Logger logger = Logger.getLogger(DriverFactory.class);
	private static WebDriver driverWeb;

	private static AndroidDriver<MobileElement> driver;
	private static IOSDriver<IOSElement> driverIOS;
	private static DesiredCapabilities caps;

	private static final String ANDROID_LIGHT_OI = "ANDROID_LIGHT_OI";
	private static final String UDID_LIGHT_OI = Utils.getProp("device_id_android_light_oi");
	private static final String ANDROID_LIGHT_VIVO= "ANDROID_LIGHT_VIVO";
	private static final String UDID_LIGHT_VIVO = Utils.getProp("device_id_android_light_vivo");
	private static final String ANDROID_VAREJO_CLARO = "ANDROID_VAREJO_CLARO";
	private static final String UDID_VAREJO_CLARO = Utils.getProp("device_id_android_varejo_claro");
	private static final String ANDROID_VAREJO_VIVO = "ANDROID_VAREJO_VIVO";
	private static final String UDID_VAREJO_VIVO = Utils.getProp("device_id_android_varejo_vivo");
	private static final String ANDROID_EMPRESA_CLARO = "ANDROID_EMPRESA_CLARO";
	private static final String UDID_EMPRESA_CLARO = Utils.getProp("device_id_android_empresa_claro");
	private static final String ANDROID_EMPRESA_TIM = "ANDROID_EMPRESA_TIM";
	private static final String UDID_EMPRESA_TIM = Utils.getProp("device_id_android_empresa_tim");
	private static final String ANDROID_ION = "ANDROID_ION";
	private static final String UDID_ION = Utils.getProp("device_id_android_ion");

	private static final String IOS_PF = "IOS_PF";
	private static final String IOS_UDID_PF = Utils.getProp("device_id_ios_varejo");

	private static final String IOS_PJ = "IOS_PJ";
	private static final String IOS_UDID_PJ = Utils.getProp("device_id_ios_empresa");

	private static final String IOS_ION = "IOS_ION";
	private static final String IOS_UDID_ION = Utils.getProp("device_id_ios_ion");

	private static final int TIME_IMPLICIT = 5;
	private static final String DRIVER_CHROME = Utils.getProp("ch_driver");

	private static final String UDID_DEV = Utils.getProp("device_id_android_dev");
	private static final String ANDROID_DEV_LIGHT = "DEV_LIGHT";
	private static final String ANDROID_DEV_VAREJO = "DEV_VAREJO";
	private static final String ANDROID_DEV_EMPRESA = "DEV_PJ";
	private static final String ANDROID_DEV_ION = "DEV_ION";
	
	private static final String ITOKEN_VAREJO = "ITOKEN_VAREJO";
	private static final String UDID_ITOKEN_VAREJO = Utils.getProp("device_id_android_web_fisico");

	private static final String ITOKEN_EMPRESA = "ITOKEN_EMPRESA";
	private static final String UDID_ITOKEN_EMPRESA = Utils.getProp("device_id_android_web_empresa");

	private static final String IOS_UDID_DEV = Utils.getProp("device_id_ios_dev");
	private static final String IOS_DEV_VAREJO = "iDEV_VAREJO";
	private static final String IOS_DEV_EMPRESA = "iDEV_EMPRESA";
	private static final String IOS_DEV_ION = "iDEV_ION";

	/**
	 * Método de criação do Driver com suas capacidades.
	 * 
	 * @param url
	 * @return Driver instanciado
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static AndroidDriver<MobileElement> getDriver(URL url) {
		String udid = null;
		if (driver == null && ANDROID_LIGHT_OI == url.nome()) {
			udid = UDID_LIGHT_OI;
		}else if (driver == null && ANDROID_LIGHT_VIVO == url.nome()) {
			udid = UDID_LIGHT_VIVO;
		} else if (driver == null && ANDROID_VAREJO_CLARO == url.nome()) {
			udid = UDID_VAREJO_CLARO;
		} else if (driver == null && ANDROID_VAREJO_VIVO == url.nome()) {
			udid = UDID_VAREJO_VIVO;
		} else if (driver == null && ANDROID_EMPRESA_CLARO == url.nome()) {
			udid = UDID_EMPRESA_CLARO;
		} else if (driver == null && ANDROID_EMPRESA_TIM == url.nome()) {
			udid = UDID_EMPRESA_TIM;
		} else if (ANDROID_ION == url.nome()) {
			udid = UDID_ION;
		} else if (ITOKEN_VAREJO == url.nome()) {
			udid = UDID_ITOKEN_VAREJO;
		} else if (ITOKEN_EMPRESA == url.nome()) {
			udid = UDID_ITOKEN_EMPRESA;
		} else if (ANDROID_DEV_LIGHT == url.nome() || ANDROID_DEV_VAREJO == url.nome() || ANDROID_DEV_EMPRESA == url.nome() || ANDROID_DEV_ION == url.nome()) {
			udid = UDID_DEV;
		}
		driverMobileDefault(udid, url);

		return driver;
	}

	public static AndroidDriver<MobileElement> getDriver() {
		return driver;
	}

	private static void driverMobileDefault(String udid, URL url) {
		caps = new DesiredCapabilities();
		caps.setCapability("platformName", "Android");
		caps.setCapability("deviceName", udid);
		caps.setCapability("udid", udid);
		caps.setCapability("automationName", "uiautomator2");
		caps.setCapability("appPackage", url.packge());
		caps.setCapability("appActivity", url.activity());
		caps.setCapability("noReset", true);
		caps.setCapability("fullReset", false);
		caps.setCapability("systemPort", url.port());
		String link = url.url();
		try {
			driver = new AndroidDriver<>(new java.net.URL(link), caps);
		} catch (MalformedURLException e) {
			logger.error("Driver Android [URL: " + link + " e Device: " + udid + "] com ERRO");
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(TIME_IMPLICIT, TimeUnit.SECONDS);
		logger.debug("Driver Android instanciado com URL: " + link + " e Device: " + udid);
		System.out.println();
	}

	/**
	 * Método para fechar o Driver.
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void killDriver(DEVICE tipoDevice) {
		switch (tipoDevice) {
		case ANDROID:
			if (driver != null) {
				driver.quit();
				driver = null;
			}
			break;
		case IOS:
			if (driverIOS != null) {
				driverIOS.quit();
				driverIOS = null;
			}
			break;
		default:
			if (driverWeb != null) {
				driverWeb.quit();
				driverWeb = null;
			}
			break;
		}

	}

	/**
	 * Método de criação do Driver com suas capacidades.
	 * 
	 * @param url
	 * @return Driver instanciado
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static IOSDriver<IOSElement> getDriverIOS(URL url) {
		if (driverIOS == null && IOS_PJ == url.nome()) {
			driverMobileDefaultIos(url, IOS_UDID_PJ);
		} else if (driverIOS == null && IOS_PF == url.nome()) {
			driverMobileDefaultIos(url, IOS_UDID_PF);
		}else if (driverIOS == null && IOS_ION == url.nome()) {
			driverMobileDefaultIos(url, IOS_UDID_ION);
		}else if (IOS_DEV_EMPRESA == url.nome() || IOS_DEV_VAREJO == url.nome() || IOS_DEV_ION == url.nome()) {
			driverMobileDefaultIos(url, IOS_UDID_DEV);
		}
		return driverIOS;
	}

	public IOSDriver<IOSElement> getDriverIOS() {
		return driverIOS;
	}

	private static void driverMobileDefaultIos(URL url, String udid) {

		caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone de SysMap");
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.6");
		caps.setCapability(MobileCapabilityType.NO_RESET, true);
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
		caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "600");
		caps.setCapability(MobileCapabilityType.UDID, udid);
		caps.setCapability("bundleId", url.bundleId());
		caps.setCapability("xcodeOrgId", url.xcodeOrgId());
		caps.setCapability("xcodeSigningId", "iPhone Developer");
		caps.setCapability("wdaLocalPort", url.port());
		String link = url.url();
		try {
			driverIOS = new IOSDriver<>(new java.net.URL(link), caps);
		} catch (Exception e) {
			logger.error("Driver iOS [URL: " + link + " e Device: " + udid + "] com ERRO");
			e.printStackTrace();
		}
		driverIOS.manage().timeouts().implicitlyWait(TIME_IMPLICIT, TimeUnit.SECONDS);
		// driverIOS.resetApp();
		logger.debug("Driver iOS instanciado com URL: " + link + " e Device: " + udid);
		System.out.println();
	}

	/**
	 * Método de criação do Driver com suas capacidades.
	 * 
	 * @param url
	 * @return Driver instanciado
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static WebDriver getDriverBrowser(URL url) {
		if (driverWeb == null) {
			driverWebPF(url.url());
		}
		return driverWeb;
	}

	public WebDriver getDriverBrowser() {
		return driverWeb;
	}

	private static void driverWebPF(String url) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("—-disable-gpu");
		options.addArguments("--window-size=1280,720");
		options.addArguments("--ignore-ssl-errors=yes");
		options.addArguments("--ignore-certificate-errors");
		//options.addArguments("--headless");
		System.setProperty("webdriver.chrome.driver", DRIVER_CHROME);
		driverWeb = new ChromeDriver(options);
		driverWeb.manage().timeouts().implicitlyWait(TIME_IMPLICIT, TimeUnit.SECONDS);
		driverWeb.get(url);
		driverWeb.manage().window().maximize();
		logger.debug("Driver Chrome instanciado com URL: " + url);
		System.out.println();
	}

}
