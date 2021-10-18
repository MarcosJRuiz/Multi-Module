package br.com.test.integration.suite.dsl;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.test.integration.suite.driver.DriverFactory;
import br.com.test.integration.suite.enuns.Direction;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

/**
 * Classe que contem os metodos de interação com os devices
 * 
 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
 */

public class Ios  extends Browser{

	/**
	 * swipe from element to direction
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public void swipeElementToDirection(By seletor, String direction) {

	}
	/**
	 * swipe to direction
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public void swipe(String direction) {

	}
}
