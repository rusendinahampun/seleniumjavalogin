import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Login {
    @Test
    public void open_browser() throws IOException {

        WebDriver driver;
        String baseUrl = "https://portal.bisacpns.com/auth/login";

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);

        // Ambil screenshot setelah membuka URL
        captureScreenshot(driver, "01-website");

        // Get Form
        WebElement username = driver.findElement(By.id("email"));
        username.sendKeys("sendinahampun18@gmail.com");
        captureScreenshot(driver, "02-username");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("habeahan123");
        captureScreenshot(driver, "03-password");

        WebElement buttonMasuk = driver.findElement(By.className("btn-primary"));
        buttonMasuk.click();
        captureScreenshot(driver, "04-login button");

        // Tunggu halaman selesai dimuat
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Ambil screenshot setelah login
        captureScreenshot(driver, "05-post login");

        // Scroll ke bawah secara bertahap
        scrollAndCapture(driver);

        // Tutup browser
        driver.quit();
    }

    // Fungsi untuk mengambil screenshot dengan nama file sesuai step
    private void captureScreenshot(WebDriver driver, String stepName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Format waktu untuk nama file
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

            // Simpan screenshot ke folder tertentu
            String screenshotFolder = "D:\\Data Sendi\\Automation\\Selenium\\Capture\\";
            File destFile = new File(screenshotFolder + stepName + "_" + timestamp + ".png");
            FileUtils.copyFile(screenshot, destFile);

            System.out.println("Screenshot diambil untuk step: " + stepName);
        } catch (IOException e) {
            System.err.println("Gagal mengambil screenshot untuk step: " + stepName);
            e.printStackTrace();
        }
    }

    // Fungsi untuk scroll dan mengambil screenshot hingga ke bagian bawah halaman
    private void scrollAndCapture(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
        int step = 1;

        while (true) {
            // Scroll ke bawah
            js.executeScript("window.scrollBy(0, 1000);");
            try {
                Thread.sleep(1000); // Tunggu agar halaman selesai di-render
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Ambil screenshot per scroll
            captureScreenshot(driver, "scroll_step_" + step);
            step++;

            // Cek apakah sudah mencapai bagian bawah halaman
            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }

        System.out.println("Scrolling selesai.");
    }
}
