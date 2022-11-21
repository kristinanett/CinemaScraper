package oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckDates {

    public static List<DataFilm> findFilmsWithSuitableDates(List<DataFilm> allScrapedFilms, InputFilm inputFilm) {
        List<DataFilm> filmsWithSuitableDates = new ArrayList<>();
        for (DataFilm data : allScrapedFilms) {
            if (Film.stringToDate(data.getFilmDate()).compareTo(Film.stringToDate(inputFilm.getFilmDate())) > 0) {
                filmsWithSuitableDates.add(data);
                Collections.sort(filmsWithSuitableDates);
            }
        }
        return filmsWithSuitableDates;
    }


}
