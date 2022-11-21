package oop;

import java.util.*;

public class CheckGenres {

    public static List<DataFilm> findFilmsWithSuitableGenres(List<DataFilm> dataFilms, InputFilm inputFilm) {
        List<String> allGenres = new ArrayList<>(Arrays.asList("animatsioon", "komöödia", "draama", "seiklus", "põnevik", "märul", "kogupere", "fantaasia", "õudus", "sõda", "thriller", "ooper", "biograafia", "ballett", "krimi", "romantika", "muusika", "ulmefilm", "müsteerium", "teater"));


        if (inputFilm.getFilmGenre().contains("üllata mind")) {
            surpriseMe(allGenres, inputFilm);
        }

        List<DataFilm> filmsWithSuitableGenres = new ArrayList<>();


        Collection<String> inputGenres = Arrays.asList(inputFilm.getFilmGenre().split(", "));
        for (DataFilm data : dataFilms) {
            Collection<String> filmGenres = Arrays.asList(data.getFilmGenre().split(", "));
            if (!Collections.disjoint(filmGenres, inputGenres)) {
                filmsWithSuitableGenres.add(data);
            }
        }
        return filmsWithSuitableGenres;
    }

    private static void surpriseMe(List<String> allGenres, InputFilm inputFilm) {
        StringBuilder sb = new StringBuilder();
        List<String> availableGenres = new ArrayList<>(allGenres);
        String[] chosenGenres = inputFilm.getFilmGenre().split(", ");
        for (int i = 0; i < 3; i++) {
            if (chosenGenres[i].equals("üllata mind")) {
                String surpriseGenre = availableGenres.get((int) Math.round(Math.random() * 19));
                availableGenres.remove(surpriseGenre);
                sb.append(surpriseGenre);
            } else {
                sb.append(chosenGenres[i]);
            }
            if (i != 2) {
                sb.append(", ");
            }
        }
        inputFilm.setFilmGenre(sb.toString());
    }
}
