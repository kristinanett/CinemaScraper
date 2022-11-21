package oop;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class DataFilm extends Film implements Comparable<DataFilm> {

    private final String filmName;

    public DataFilm(String cinemaName, String filmDate, String filmGenre, String filmName) {
        super(cinemaName, filmDate, filmGenre);
        this.filmName = filmName;
    }

    public static DataFilm loadFilm(DataInputStream dis) throws Exception {
        String cinemaName = dis.readUTF();
        String filmName = dis.readUTF();
        String filmDate = dis.readUTF();
        String filmGenre = dis.readUTF();
        return new DataFilm(cinemaName, filmDate, filmGenre, filmName);
    }

    private String getFilmName() {
        return filmName;
    }

    public void saveFilm(DataOutputStream dos) throws Exception {
        dos.writeUTF(getCinemaName());
        dos.writeUTF(filmName);
        dos.writeUTF(getFilmDate());
        dos.writeUTF(getFilmGenre());
    }

    public int compareTo(DataFilm comparable) {
        return stringToDate(getFilmDate()).compareTo(stringToDate(comparable.getFilmDate()));
    }

    @Override
    public String toString() {
        return "Filmi nimi: " + getFilmName() + ". Žanr: " + getFilmGenre() + ". Kinodes alates: " + getFilmDate() + ". Kinos: " + getCinemaName() + ".";
    }
}
