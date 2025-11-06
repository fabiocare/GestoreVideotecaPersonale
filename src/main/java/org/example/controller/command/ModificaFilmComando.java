package org.example.controller.command;

import org.example.model.StatoVisione;
import org.example.model.Videoteca;

public class ModificaFilmComando implements Comando{
    private final Videoteca videoteca;
    private final String titolo;
    private final String regista;
    private final StatoVisione nuovoStato;
    private final int nuovaValutazione;

    public ModificaFilmComando(Videoteca videoteca, String titolo, String regista, StatoVisione nuovoStato, int nuovaValutazione){
        this.videoteca = videoteca;
        this.titolo = titolo;
        this.regista = regista;
        this.nuovoStato = nuovoStato;
        this.nuovaValutazione = nuovaValutazione;
    }

    @Override
    public void esegui(){
        videoteca.modificaFilm(titolo, regista, nuovoStato, nuovaValutazione);
    }
}
