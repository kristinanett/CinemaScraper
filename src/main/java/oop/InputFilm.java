package oop;

public class InputFilm extends Film {

    public InputFilm(String cinemaName, String filmDate, String filmGenre) {
        super(cinemaName, filmDate, filmGenre);
    }

    @Override
    public String toString() {
        return "Soovid näha filmi kino(de)s: " + getCinemaName() + ". Alates kuupäevast " + getFilmDate() + ". Sobivad žanrid on: " + getFilmGenre() + ".";
    }
}
