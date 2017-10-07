package core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Chrome {
    static WebDriver driver;
    public static void main(String[] args) throws InterruptedException {
        Logger.getLogger("").setLevel(Level.OFF);

        String driverPath = "";

        String url = "https://www.facebook.com/";
        String email_add = "konstrevan@gmail.com";
        String password = "";

        if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
            driverPath = "./resources/webdriver/mac/chromedriver";}
        else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            driverPath = "./resources/webdriver/pc/chromedriver.exe";}
        else { throw new IllegalArgumentException("Unknows OS");}

        System.setProperty("webdriver.chrome.driver", driverPath);
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeOptions option = new ChromeOptions();
        option.addArguments("disaple-infobars");
        option.addArguments("--disbale-notifications");
        option.addArguments("--disable-annotations");
        if (System.getProperty("os.name").toUpperCase().contains("MAC"))
            option.addArguments("-start-fullscreen");
        else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
            option.addArguments("--start-maximized");
        else throw new IllegalArgumentException("Unknown OS");
        driver = new ChromeDriver(option);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait delay = new WebDriverWait(driver, 15);
        //Wait<WebDriver> longdelay = new FluentWait<WebDriver>(driver)
        //        .withTimeout(15, TimeUnit.SECONDS)
        //        .pollingEvery(2, TimeUnit.SECONDS)
        //        .ignoring(NoSuchElementException.class);
        final long teststart = (System.currentTimeMillis());
        driver.get(url);

        String title = driver.getTitle();
        String copyright = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='mvl copyright']//child::span"))).getText();
        System.out.println("Your browser is: Chrome" + "\nTitle of the page is:" + title + "\nCopyright: " + copyright);

        Dimension field_email = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']"))).getSize(),
                field_pass = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='pass']"))).getSize(),
                field_login = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Log In']"))).getSize();
        Point locate_email = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']"))).getLocation() ,
                locate_pass = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='pass']"))).getLocation(),
                locate_login = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Log In']"))).getLocation();

        WebElement email = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']")));email.clear(); email.sendKeys(email_add);
        WebElement pass = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='pass']")));pass.clear(); pass.sendKeys(password);
        delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Log In']"))).click();

        //Совершенно не пойму как запустить FluentWait
        //WebElement profile = longdelay.until(new Function<WebDriver, WebElement>() {
        //    public WebElement apply(WebDriver driver) {
        //        return driver.findElement(By.xpath("//a[@title='Profile']"));}}); profile.click();

        WebElement profile = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title='Profile']")));profile.click();
        String freindscount = delay.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-tab-key='friends']//child::span[1]"))).getText();
        WebElement menu = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@id='pageLoginAnchor']"))); menu.click();
        WebElement logout = delay.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@class='_w0d']//ancestor::span[2]"))); logout.click();
        driver.quit();
        final long benchmark = (((System.currentTimeMillis()) - teststart) / 1000);

        System.out.println("You have " + freindscount + " friends.");
        System.out.println("\nInformation about element 'Email field' is: " + "\n\t\tSize is: " + field_email +
                "\n\t\tLocated at: " + locate_email);
        System.out.println("Information about of element 'Password field' is: " + "\n\t\tSize is: " + field_pass +
                "\n\t\tLocated at: " + locate_pass);
        System.out.println("Information about of element 'Log In button' is: " + "\n\t\tSize is: " + field_login +
                "\n\t\tLocated at: " + locate_login);
        System.out.println("Total time consumed: " + benchmark + " seconds.");
    }
}
