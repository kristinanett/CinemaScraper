package oop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ForumCinemasScrape extends Scrape {
    private static final String FORUM_CINEMA_URL = "https://www.forumcinemas.ee";
    private static final String SOON_BUTTON_XPATH = "//*[@id=\"new-body\"]/div[3]/div[2]/div/div/div/div/div/div[3]/div/ul/li[3]/a";
    private static final String COOKIE_BUTTON_XPATH = "//*[@id=\"Cookie-Popup\"]/div/button";
    private static final String FIRST_FILM_XPATH = "//*[@id=\"UpdateTarget-UserControlEventBlockList_2\"]/div[2]/div[2]/div/div[1]/div[4]/div[2]/div[1]/h2/a";
    private static final String GENRE_XPATH = "//*[@id=\"new-body\"]/div[3]/div/div/p/span[2]";
    private static final String DATE_XPATH = "//*[@id=\"layout-event\"]/div[1]/div[2]/div/div[2]/p[1]/span[2]";

    public static void forumCinemasScrape() throws Exception {
        WebDriver driver = initializeDriver();
        WebDriverWait wait = initializeDriverWait(driver);

        driver.get(FORUM_CINEMA_URL);
        buttonClick(driver, wait, SOON_BUTTON_XPATH);
        buttonClick(driver, wait, COOKIE_BUTTON_XPATH);

        List<DataFilm> forumCinemasFilms = getFilmsAsList(driver, wait);
        closeBrowser(driver);
        writeToFile("forumCinemasFilms.dat", forumCinemasFilms);
    }

    private static List<DataFilm> getFilmsAsList(WebDriver driver, WebDriverWait wait) {
        List<DataFilm> forumCinemasFilms = new ArrayList<>();
        for (int i = 1; i < 21; i++) {

            String nameXpath = FIRST_FILM_XPATH.substring(0, 74) + i + FIRST_FILM_XPATH.substring(75);
            String filmName = driver.findElement(By.xpath(nameXpath)).getText();
            buttonClick(driver, wait, nameXpath);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DATE_XPATH)));
            String filmGenre = driver.findElement(By.xpath(GENRE_XPATH)).getText().toLowerCase();
            String filmDateString = driver.findElement(By.xpath(DATE_XPATH)).getText();

            forumCinemasFilms.add(new DataFilm("Forum Cinemas", filmDateString, filmGenre, filmName));
            driver.navigate().back();
        }
        return forumCinemasFilms;
    }
}

