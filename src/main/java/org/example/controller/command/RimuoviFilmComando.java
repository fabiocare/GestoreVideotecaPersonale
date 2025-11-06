package org.example.controller.command;

import org.example.model.Videoteca;

public class RimuoviFilmComando implements Comando {
    private final Videoteca videoteca;
    private final String titolo;
    private final String regista;

    public RimuoviFilmComando(Videoteca videoteca, String titolo, String regista){
        this.videoteca=videoteca;
        this.titolo=titolo;
        this.regista=regista;
    }

    @Override
    public void esegui(){
        videoteca.rimuoviFilm(titolo, regista);
    }
}
