package br.com.test.integration.suite.views.devices;

import br.com.test.integration.suite.driver.DriverFactory;
import br.com.test.integration.suite.dsl.Android;
import br.com.test.integration.suite.enuns.DEVICE;
import br.com.test.integration.suite.enuns.URL;
import br.com.test.integration.suite.evidence.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class Itoken {


	public static String getNumber(final TestInfo testInfo, final URL url) throws Exception {
		AppiumDriver<MobileElement> driver = DriverFactory.getDriver(url);
		WebElement botaoItoken;

		String ITOKEN_VAREJO = "ITOKEN_VAREJO";
		if (Objects.equals(ITOKEN_VAREJO, url.nome())) {
			botaoItoken = driver.findElement(By.xpath("//android.widget.Button[@content-desc='iToken']"));
		} else {
			botaoItoken = driver.findElement(By.xpath("//android.widget.Button[@text='iToken']"));
		}

		botaoItoken.click();

		for (int i = 0; i < 20 && driver.findElements(By.xpath("//android.widget.TextView[@text='iToken']")).size() == 0; i++) {
			Thread.sleep( 1000);
			System.out.printf("Aguardando existencia na tela do elemento %s - %d%n", "//android.widget.TextView[@text='iToken']", i);
		}

		String imageToken = Utils.getTokenImage(testInfo, driver);
		driver.quit();
		System.out.println();
		String token = Utils.convertImageToString(imageToken, url);
		System.out.println();
		return token;
	}
}
