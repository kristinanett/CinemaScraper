package oop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class Scrape {

    protected static WebDriver initializeDriver() {
        File file = new File("chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=" + screenSize.width + "x" + screenSize.height);
        return new ChromeDriver();
    }

    protected static WebDriverWait initializeDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, 30);
    }

    protected static void closeBrowser(WebDriver driver) {
        driver.close();
        driver.quit();
    }

    protected static void buttonClick(WebDriver driver, WebDriverWait wait, String buttonXpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonXpath)));
        driver.findElement(By.xpath(buttonXpath)).click();
    }

    protected static void writeToFile(String fileName, List<DataFilm> films) throws Exception {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            dos.writeInt(films.size());
            for (DataFilm film : films) {
                film.saveFilm(dos);
            }
        }
    }
}
