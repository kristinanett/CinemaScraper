package oop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ApolloScrape extends Scrape {
    private static final String APOLLOKINO_URL = "https://apollokino.ee";
    private static final String POPUP_XPATH = "//*[@id=\"Area-Select-Modal\"]/div[2]/div/div[2]/form/div/div[6]/button";
    private static final String SOON_BUTTON_XPATH = "//*[@id=\"apollo-header-menu\"]/div/div/div/ul/li[2]/a";
    private static final String FIRST_FILM_XPATH = "//*[@id=\"UpdateTarget-UserControlEventBlockList_1\"]/div[2]/div/div[1]/div/div/div[2]/div/div[1]/h2/a";
    private static final String FIRST_GENRE_XPATH = "//*[@id=\"UpdateTarget-UserControlEventBlockList_1\"]/div[2]/div/div[1]/div/div/div[2]/div/div[1]/p[1]/b";
    private static final String OTHER_ELEMENTS_XPATH = "//*[@id=\"UpdateTarget-UserControlEventBlockList_1\"]/div[2]/div/div[1]/div/div/div[2]/div/div[1]/p";

    public static void apolloScrape() throws Exception {
        WebDriver driver = initializeDriver();
        WebDriverWait wait = initializeDriverWait(driver);

        driver.get(APOLLOKINO_URL);
        buttonClick(driver, wait, POPUP_XPATH);
        buttonClick(driver, wait, SOON_BUTTON_XPATH);

        List<DataFilm> apolloFilms = getFilmsAsList(driver, wait);
        closeBrowser(driver);
        writeToFile("apolloFilms.dat", genreCorrectionApollo(apolloFilms));
    }

    private static List<DataFilm> getFilmsAsList(WebDriver driver, WebDriverWait wait) {
        List<DataFilm> apolloFilms = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            String nameXpath = FIRST_FILM_XPATH.substring(0, 67) + i + FIRST_FILM_XPATH.substring(68);
            String genreXpath = FIRST_GENRE_XPATH.substring(0, 67) + i + FIRST_GENRE_XPATH.substring(68);
            String dateXpath = choosingCorrectDateXpath(driver, i);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(genreXpath)));
            String filmName = driver.findElement(By.xpath(nameXpath)).getText();
            String filmGenre = driver.findElement(By.xpath(genreXpath)).getText().toLowerCase();
            String filmDateString = driver.findElement(By.xpath(dateXpath)).getText();


            apolloFilms.add(new DataFilm("Apollo kino", filmDateString, filmGenre, filmName));
        }
        return apolloFilms;
    }

    private static String choosingCorrectDateXpath(WebDriver driver, int i) {
        String dateXpath;
        String otherXpath = OTHER_ELEMENTS_XPATH.substring(0, 67) + i + OTHER_ELEMENTS_XPATH.substring(68);
        List<WebElement> otherElements = driver.findElements(By.xpath(otherXpath));
        if (otherElements.size() == 2)
            dateXpath = otherXpath + "[2]/b";
        else
            dateXpath = otherXpath + "[3]/b";
        return dateXpath;
    }

    private static List<DataFilm> genreCorrectionApollo(List<DataFilm> apolloFilms) {
        for (DataFilm film : apolloFilms) {
            if (film.getFilmGenre().contains("põnevus")) {
                film.setFilmGenre(film.getFilmGenre().replace("põnevus", "põnevik"));
            }
            if (film.getFilmGenre().contains("action")) {
                film.setFilmGenre(film.getFilmGenre().replace("action", "märul"));
            }
        }
        return apolloFilms;
    }
}
