package org.example.controller.query;

import org.example.model.Film;
import org.example.model.Videoteca;

import java.util.List;

public class CercaPerTitoloQuery implements Query {
    private final Videoteca videoteca;
    private final String titolo;

    public CercaPerTitoloQuery(Videoteca videoteca, String titolo) {
        this.videoteca = videoteca;
        this.titolo = titolo;
    }

    @Override
    public List<Film> esegui() {
        return videoteca.cercaPerTitolo(titolo);
    }
}
