package org.example.model;

import java.util.*;

public class Videoteca {
    private static Videoteca instance;
    private final List<Film> films;

    private Videoteca() {
        this.films = new ArrayList<>();
    }

    public static Videoteca getInstance(){
        if(instance == null){
            instance = new Videoteca();
        }
        return instance;
    }

    //Operazioni principali
    public void aggiungiFilm(Film film){
        films.add(film);
    }

    public void rimuoviFilm(String titolo, String regista){
        films.removeIf(f -> f.getTitolo().equalsIgnoreCase(titolo) && f.getRegista().equalsIgnoreCase(regista));
    }

    public void modificaFilm(String titolo, String regista, StatoVisione nuovoStato, int nuovaValutazione){
        for (Film film : films) {
            if (film.getTitolo().equalsIgnoreCase(titolo) && film.getRegista().equalsIgnoreCase(regista)) {
                Film nuovoFilm = new Film.Builder(film.getTitolo(), film.getRegista(), film.getAnnoUscita())
                        .genere(film.getGenere())
                        .statoVisione(nuovoStato)
                        .valutazione(nuovaValutazione)
                        .build();
                films.set(films.indexOf(film), nuovoFilm);
                return;
            }
        }
    }

    //cerca e filtra
    public List<Film> cercaPerTitolo(String titolo){
        return films.stream().filter(f -> f.getTitolo().toLowerCase().contains(titolo.toLowerCase())).toList();
    }

    public List<Film> cercaPerRegista(String regista){
        return films.stream().filter(f -> f.getRegista().toLowerCase().contains(regista.toLowerCase())).toList();
    }

    public List<Film> filtraPerGenere(Genere g){
        return films.stream().filter(f -> f.getGenere() == g).toList();
    }

    public List<Film> filtraPerStatoVisione(StatoVisione sv){
        return films.stream().filter(f -> f.getStatoVisione() == sv).toList();
    }

    public List<Film> filtraPerAnnoUscita(int anno){
        return films.stream().filter(f -> f.getAnnoUscita() == anno).toList();
    }

    //altre operazioni
    public void sostituisciFilms(List<Film> nuoviFilms){
        films.clear();
        films.addAll(nuoviFilms);
    }

    public List<Film> getTuttiFilms() {
        return Collections.unmodifiableList(films);
    }

    public void svuotaVideoteca(){
        films.clear();
    }
}
