package org.example.controller.query;

import org.example.model.Film;
import org.example.model.Videoteca;

import java.util.List;

public class GetTuttiFilmsQuery implements Query {
    private final Videoteca videoteca;

    public GetTuttiFilmsQuery(Videoteca videoteca) {
        this.videoteca = videoteca;
    }

    @Override
    public List<Film> esegui() {
        return videoteca.getTuttiFilms();
    }
}
