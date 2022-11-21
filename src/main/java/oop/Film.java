package oop;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Film {


    private final String cinemaName;
    private final String filmDate;
    private String filmGenre;

    public Film(String cinemaName, String filmDate, String filmGenre) {
        this.cinemaName = cinemaName;
        this.filmDate = filmDate;
        this.filmGenre = filmGenre;
    }

    public static Date stringToDate(String filmDateString) {
        SimpleDateFormat myFormatStringToDate = new SimpleDateFormat("dd.MM.yyyy");
        Date filmDate;
        try {
            filmDate = myFormatStringToDate.parse(filmDateString);
        } catch (ParseException e) {
            throw new DataException();
        }
        return filmDate;
    }

    public String getFilmDate() {
        return filmDate;
    }

    public String getFilmGenre() {
        return filmGenre;
    }

    public void setFilmGenre(String filmGenre) {
        this.filmGenre = filmGenre;
    }

    public String getCinemaName() {
        return cinemaName;
    }
}

