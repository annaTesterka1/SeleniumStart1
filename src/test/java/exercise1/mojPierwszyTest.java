package exercise1;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class mojPierwszyTest {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "c:\\pliki\\chromedriver.exe");
//        System.setProperty("webdriver.gecko.driver", "c:\\pliki\\geckodriver.exe");
//        System.setProperty("webdriver.ie.driver", "c:\\pliki\\IEDriverServer.exe");
//        System.setProperty("webdriver.edge.driver", "c:\\pliki\\MicrosoftWebDriver.exe");
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
//        driver = new InternetExplorerDriver();
//        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://helion.pl");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void startWebdriver() {
        Assert.assertFalse((driver.getTitle().contains("Helionfgfg")), "tytul jest niewlasciwy");
    }

    @Test
    public void startHelion() {
        Assert.assertTrue((driver.getTitle().contains("Helion")), "tytul jest niewlasciwy");

    }

    @Test
    public void logoHelion() {
        WebElement logo = driver.findElement(By.cssSelector("p.logo"));
        Assert.assertTrue(logo.isDisplayed(), "brak loga");
    }

    @Test
    public void seleniumWHelion() {
        WebElement search = driver.findElement(By.id("inputSearch"));
        search.sendKeys("selenium");
        WebElement searchButton = driver.findElement(By.cssSelector(".button"));
        searchButton.click();
        List<WebElement> wynikiSelenium = driver.findElements(By.partialLinkText("Selenium"));
        System.out.println("znaleziono " + wynikiSelenium.size() + " ksiazek o Selenium");
        Assert.assertTrue(wynikiSelenium.size() > 0, "brak ksiazki");

        List<WebElement> wynikiLazySelenium = driver.findElements(By.cssSelector(".cover"));
        System.out.println("znaleziono " + wynikiLazySelenium.size() + " ksiazek typu Selenium");
        Assert.assertTrue(wynikiLazySelenium.size() > 0, "brak ksiazki");
    }
    @Test
    public void seleniumWHelion1() throws IOException {
        WebElement search = driver.findElement(By.id("inputSearch"));
        search.sendKeys("selenium");
        WebElement searchButton = driver.findElement(By.cssSelector(".button"));
        searchButton.click();
        List<WebElement> wynikiSelenium = driver.findElements(By.partialLinkText("Selenium"));
        System.out.println("znaleziono " + wynikiSelenium.size() + " ksiazek o Selenium");
        Assert.assertTrue(wynikiSelenium.size() > 0, "brak ksiazki");

        List<WebElement> wynikiLazySelenium = driver.findElements(By.cssSelector(".book-info"));
        System.out.println("znaleziono " + wynikiLazySelenium.size() + " ksiazek typu Selenium");
        Assert.assertTrue(wynikiLazySelenium.size() > 0, "brak ksiazki");

        wynikiLazySelenium.get(1).click();
        List<WebElement> tytul = driver.findElements(By.cssSelector(".title-group"));
        String tytulKsiazki = tytul.get(0).getText();
        Assert.assertTrue(tytulKsiazki.contains("Selenium"), "brak info o selenium 1");
        System.out.println("znaleziony tytul: " + tytulKsiazki);
        try {
            Assert.assertTrue(tytulKsiazki.contains("Selenium"), "brak info o selenium 2");
            System.out.println("znaleziony tytul ok: " + tytulKsiazki);
        } catch (Throwable e) {
            System.out.println("UWAGA : COS POSZLO NIE TAK");
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("c:\\test\\okladka.png"));
        }

    }
    @DataProvider
    public Object[][] dataForSearchTest() {
        return new Object[][] {
                {"Selenium", 3}, {"Java", 21}, {"Kali", 4},
                {"Jenkins", 3}, {"JavaScript", 20}, {"Git", 10}
        };
    }
    @Test (dataProvider = "dataForSearchTest")
    public void helionKsiazkiData(String tytul, int ilosc) throws InterruptedException {
        System.out.println("-------------- Rozpoczynamy nowy test ------------------- ");
        System.out.println("test dla tytulu: " + tytul + " oraz zalozonej ilosci: " + ilosc);
        driver.get("http://helion.pl");
        WebElement search = driver.findElement(By.cssSelector("input#inputSearch"));
        search.sendKeys(tytul);
        WebElement searchButton = driver.findElement(By.cssSelector(".button"));
        searchButton.click();
        Thread.sleep(10000);
        List<WebElement> wyniki = driver.findElements(By.partialLinkText(tytul));
        System.out.println("ilosc ksiazek: " + wyniki.size());
        Assert.assertTrue(wyniki.size() == ilosc);
    }





}
