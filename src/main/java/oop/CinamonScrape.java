package oop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

public class CinamonScrape extends Scrape {
    private static final String CINAMON_URL = "https://cinamonkino.com";
    private static final String CITY_BUTTON_XPATH = "//*[@id=\"__nuxt\"]/div[2]/main/div/div[2]/div/a[8]/span[2]";
    private static final String FILMS_BUTTON_XPATH = "//*[@id=\"__nuxt\"]/div[2]/div/header/div/div[2]/nav/ul/li[2]/a/span";
    private static final String SOON_BUTTON_XPATH = "//*[@id=\"__nuxt\"]/div[2]/div/main/div/section/div[1]/div/div[1]/ul/li[2]/span";
    private static final String FIRST_FILM_XPATH = "//*[@id=\"__nuxt\"]/div[2]/div/main/div/section/div[2]/section[2]/div/div[2]/div/div[2]/a/h3";
    private static final String FIRST_GENRE_XPATH = "//*[@id=\"__nuxt\"]/div[2]/div/main/div/section/div[2]/section[2]/div/div[2]/div/div[3]";
    private static final String FIRST_DATE_XPATH = "//*[@id=\"__nuxt\"]/div[2]/div/main/div/section/div[2]/section[2]/div/div[2]/div/div[4]/span[2]";

    public static void cinamonScrape() throws Exception {
        WebDriver driver = initializeDriver();
        WebDriverWait wait = initializeDriverWait(driver);

        driver.get(CINAMON_URL);
        buttonClick(driver, wait, CITY_BUTTON_XPATH);
        buttonClick(driver, wait, FILMS_BUTTON_XPATH);
        buttonClick(driver, wait, SOON_BUTTON_XPATH);

        List<DataFilm> cinamonFilms = getFilmsAsList(driver, wait);
        closeBrowser(driver);
        writeToFile("cinamonFilms.dat", genreCorrection(cinamonFilms));
    }

    private static List<DataFilm> getFilmsAsList(WebDriver driver, WebDriverWait wait) {
        List<DataFilm> cinamonFilms = new ArrayList<>();
        for (int i = 2; i < 21; i++) {
            String nameXpath = FIRST_FILM_XPATH.substring(0, 72) + i + FIRST_FILM_XPATH.substring(73);
            String genreXpath = FIRST_GENRE_XPATH.substring(0, 72) + i + FIRST_GENRE_XPATH.substring(73);
            String dateXpath = FIRST_DATE_XPATH.substring(0, 72) + i + FIRST_DATE_XPATH.substring(73);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dateXpath)));
            String filmName = driver.findElement(By.xpath(nameXpath)).getText();
            String filmGenre = driver.findElement(By.xpath(genreXpath)).getText().toLowerCase();
            String filmDateString = driver.findElement(By.xpath(dateXpath)).getText();

            cinamonFilms.add(new DataFilm("Cinamon", filmDateString, filmGenre, filmName));
        }
        return cinamonFilms;
    }

    private static List<DataFilm> genreCorrection(List<DataFilm> cinamonFilms) {
        for (DataFilm film : cinamonFilms) {
            if (film.getFilmGenre().contains("seiklusfilm")) {
                film.setFilmGenre(film.getFilmGenre().replace("seiklusfilm", "seiklus"));
            }
            if (film.getFilmGenre().contains("koguperefilm")) {
                film.setFilmGenre(film.getFilmGenre().replace("koguperefilm", "kogupere"));
            }
            if (film.getFilmGenre().contains("põnevus")) {
                film.setFilmGenre(film.getFilmGenre().replace("põnevus", "põnevik"));
            }
        }
        return cinamonFilms;
    }
}

