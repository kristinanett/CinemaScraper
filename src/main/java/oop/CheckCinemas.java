package oop;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class CheckCinemas {
    public static List<DataFilm> getFilms(InputFilm inputFilm) throws Exception {
        List<DataFilm> dataFilms = new ArrayList<>();
        String chosenCinemas = inputFilm.getCinemaName();
        if (chosenCinemas.contains("Apollo Kino")) {
            dataFilms.addAll(readFilms("apolloFilms.dat"));
        }
        if (chosenCinemas.contains("Cinamon")) {
            dataFilms.addAll(readFilms("cinamonFilms.dat"));
        }
        if (chosenCinemas.contains("Forum Cinemas")) {
            dataFilms.addAll(readFilms("forumCinemasFilms.dat"));
        }
        return dataFilms;
    }

    public static List<DataFilm> readFilms(String filename) throws Exception {
        List<DataFilm> dataFilms = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            int numberOfFilms = dis.readInt();
            while (numberOfFilms != 0) {
                dataFilms.add(DataFilm.loadFilm(dis));
                numberOfFilms -= 1;
            }
        }
        return dataFilms;
    }
}
