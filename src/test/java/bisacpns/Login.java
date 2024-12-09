package bisacpns;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Login {
    @Test
    public void succes_login_case() throws InterruptedException {
        //Membuka portal web dengan web driver
        WebDriver driver;
        String baseUrl = "https://portal.bisacpns.com/auth/login";

        //setup webdriver otomatis klik maximize window
        //Membuka halaman login
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);

        //Setup Melakukan Scroll
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Setup Memunculkan message title pada console
        String pagetitle = driver.getTitle();

        //Input email
        driver.findElement(By.id("email")).sendKeys("sendinahampun18@gmail.com");
        //Input password
        driver.findElement(By.id("password")).sendKeys("habeahan123");
        //Click login
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        //Panggil Melakukan Scroll (Setelah klik submit / Tergantung kebutuhan dimana)
        //Cara scroll dengan pixel
        //js.executeScript("window.scrollBy(0,500)");

        //Panggil Melakukan Scroll (Setelah klik submit / Tergantung kebutuhan dimana
        //Cara scroll dengan element yang dibutuhkan saja
        WebElement btnnextslide = driver.findElement(By.xpath("//*[@id=\"xp-container\"]/div[2]/div[2]/div[2]/div/div/div/button[1]"));
        js.executeScript("arguments[0].scrollIntoView();", btnnextslide);
        //Setting Delay sebelum klik button next slide
        Thread.sleep(6000);
        btnnextslide.click();

        //Setting Delay setelah klik button next delay
        Thread.sleep(6000);
        //Close browser
        driver.close();

        //Panggil Untuk Memunculkan Pesan Title Pada Console
        System.out.println(pagetitle);
    }
}
