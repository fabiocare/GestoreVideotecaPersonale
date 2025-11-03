package org.example.controller.command;

import org.example.model.Film;
import org.example.model.Videoteca;

public class AggiungiFilmComando implements Comando {
    private final Videoteca videoteca;
    private final Film film;

    public AggiungiFilmComando(Videoteca videoteca, Film film) {
        this.videoteca = videoteca;
        this.film = film;
    }

    @Override
    public void esegui() {
        videoteca.aggiungiFilm(film);
    }
}
