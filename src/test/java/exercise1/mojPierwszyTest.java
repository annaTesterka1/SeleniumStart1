package exercise1;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class mojPierwszyTest {
    WebDriver driver;


    @BeforeMethod //metoda uruchamiana przed Pierwsza metodą typu test
    @Parameters({ "seleniumHost", "seleniumPort", "browser", "url" })
    public void setUp(String seleniumHost, int seleniumPort, String browser, String url)
            throws MalformedURLException {
//    @BeforeTest
//    public void setUp() throws MalformedURLException {
//        System.setProperty("webdriver.chrome.driver", "c:\\pliki\\chromedriver.exe");
//        System.setProperty("webdriver.gecko.driver", "c:\\pliki\\geckodriver.exe");
//        System.setProperty("webdriver.ie.driver", "c:\\pliki\\IEDriverServer.exe");
//        System.setProperty("webdriver.edge.driver", "c:\\pliki\\MicrosoftWebDriver.exe");
//        driver = new ChromeDriver();
//        DesiredCapabilities chromeCaps = DesiredCapabilities.chrome();
//        chromeCaps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
//        chromeCaps.setCapability(CapabilityType.VERSION, "59.w10");
//        chromeCaps.setCapability(CapabilityType.PLATFORM, "WIN10");
//
//
//        driver = new RemoteWebDriver(new URL("http://212.106.131.201:4444/wd/hub"), chromeCaps);
//
            if (browser.equals("firefox")){
                System.setProperty("webdriver.gecko.driver", "c:\\pliki\\geckodriver.exe");
                DesiredCapabilities ffCaps = DesiredCapabilities.firefox();
                ffCaps.setCapability(CapabilityType.BROWSER_NAME, browser);
                driver = new RemoteWebDriver
                        (new URL("http://"+seleniumHost+":"+seleniumPort+"/wd/hub"),ffCaps);
            } else if (browser.equals("chrome")){
                System.setProperty("webdriver.chrome.driver", "c:\\pliki\\chromedriver.exe");
                DesiredCapabilities chromeCaps = DesiredCapabilities.chrome();
                chromeCaps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
                driver = new RemoteWebDriver
                        (new URL("http://"+seleniumHost+":"+seleniumPort+"/wd/hub"),chromeCaps);
            } else if (browser.equals("edge")) {
                System.setProperty("webdriver.edge.driver", "c:\\pliki\\MicrosoftWebDriver.exe");
                DesiredCapabilities edgeCaps = DesiredCapabilities.edge();
                edgeCaps.setCapability(CapabilityType.BROWSER_NAME, "MicrosoftEdge");
                driver = new RemoteWebDriver(
                        new URL("http://"+seleniumHost+":"+seleniumPort+"/wd/hub"),edgeCaps);
            }
            driver.navigate().to(url);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);



//        driver = new FirefoxDriver();
//        driver = new InternetExplorerDriver();
//        driver = new EdgeDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.get("http://helion.pl");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void startWebdriver() {
        Assert.assertFalse((driver.getTitle().contains("Helionjhh")), "tytul jest niewlasciwy");
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
