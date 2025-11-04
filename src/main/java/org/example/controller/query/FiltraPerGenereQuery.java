package org.example.controller.query;

import org.example.model.Film;
import org.example.model.Genere;
import org.example.model.Videoteca;

import java.util.List;

public class FiltraPerGenereQuery implements Query {
    private final Videoteca videoteca;
    private final Genere genere;

    public FiltraPerGenereQuery(Videoteca videoteca, Genere genere) {
        this.videoteca = videoteca;
        this.genere = genere;
    }

    @Override
    public List<Film> esegui() {
        return videoteca.filtraPerGenere(genere);
    }
}
