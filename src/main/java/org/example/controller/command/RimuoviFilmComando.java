package org.example.controller.command;

import org.example.model.Videoteca;

public class RimuoviFilmComando implements Comando {
    private final Videoteca videoteca;
    private final String titolo;

    public RimuoviFilmComando(Videoteca videoteca, String titolo){
        this.videoteca=videoteca;
        this.titolo=titolo;
    }

    @Override
    public void esegui(){
        videoteca.rimuoviFilm(titolo);
    }
}
