package br.com.test.integration.suite.dsl;

import br.com.test.integration.suite.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Classe que contem os metodos de interação com o Navegador
 * 
 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
 */
public class Browser {
	private static final DriverFactory app = new DriverFactory();
	private static final WebDriver driver = app.getDriverBrowser();
	private static final WebDriverWait wait = new WebDriverWait(driver, 45);
	JavascriptExecutor js = (JavascriptExecutor) driver;
	private static final String LOAD = "//*[@aria-label='carregando' or @alt='Carregando']";

	/**
	 * Tempo de expera de 2 segundos
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void aguardar() {
		aguardar(2);
	}

	/**
	 * Inseir o tempo de expera em segundos.
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void aguardar(int segundos) {
		try {
			Thread.sleep(segundos * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Limpa o seletor.
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void limpar(By seletor) {
		wait.until(ExpectedConditions.elementToBeClickable(seletor)).clear();
	}

	/**
	 * Digita o valor informado no seletor indicado.
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void digitar(By seletor, String valor) {
		limpar(seletor);
		wait.until(ExpectedConditions.elementToBeClickable(seletor)).sendKeys(valor);
	}

	/**
	 * Clica no elemento indicado.
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void clicar(By seletor) {
		wait.until(ExpectedConditions.elementToBeClickable(seletor)).click();
	}

	/**
	 * Valida se o elemento checkbox ou swtich esta marcado.
	 *
	 * @return boolean True ou False
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static boolean elementoMarcado(By seletor) {
		return Boolean.parseBoolean(driver.findElement(seletor).getAttribute("checked"));
	}

	/**
	 * Aguardar por 30 segundos para conter o elemento na tela.
	 * 
	 * @return booleano
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static boolean aguardarElementoExistir(By seletor) {
		aguardar(1);
		boolean resp;
		for (int i = 0; i < 30; i++) {
			resp = elementoExiste(seletor);
			System.out.printf("Aguardando existencia na tela do elemento %s - %d%n", seletor, i);
			if (resp) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Aguardar por ate 30 segundos o elemento sair da tela.
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public void aguardarLoading() {
		boolean resp;
		for (int i = 0; i < 60; i++) {
			resp = driver.findElements(By.xpath(LOAD)).size() == 0;
			System.out.printf("Load na tela - %d%n", i);
			if (resp) {
				return;
			}
			aguardar(1);
		}
	}

	/**
	 * Valida se o Elemento existe.
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 *
	 * @return booleano
	 */
	public static boolean elementoExiste(By seletor) {
		return driver.findElements(seletor).size() != 0;
	}

	/**
	 * Clica na tecla do teclado informado. Necessário informa o Elemento
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public void precionar(By seletor, Keys key) {
		aguardarLoading();
		wait.until(ExpectedConditions.elementToBeClickable(seletor)).sendKeys(key);
	}

	/**
	 * Foca no frame informado
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public void switchFrame(String nomeFrame) {
		aguardarLoading();
		driver.switchTo().defaultContent();
		aguardar(1);
		driver.switchTo().frame(nomeFrame);
		aguardar(1);
	}

	/**
	 * Seleciona a opção no seletor por Texto
	 *@author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public void selectText(By seletor, String texto) {
		Select combo = new Select(driver.findElement(seletor));
		combo.selectByVisibleText(texto);
	}

	/**
	 * Retorna o texto contido no elemento informado
	 *
	 * @return texto em formato de String
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static String lerTexto(By seletor) {
		return driver.findElement(seletor).getAttribute("value");
	}

	/**
	 * Clica no Elemento pela função em JavaScript
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public void clicarJs(By seletor) {
		js.executeScript("arguments[0].click();", driver.findElement(seletor));
	}

	/**
	 * Rolar a tela até o elemento indicado
	 * 
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public void rolarAteElemento(By seletor) {
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(seletor));
	}

	/**
	 * Valida se elemento esta visivel na tela. Caso não elemento não conter na tela é lancada uma exeção
	 * 
	 *  @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	public static void validaElementoNaTela(By seletor) {
		wait.until(ExpectedConditions.presenceOfElementLocated(seletor));
	}

}
