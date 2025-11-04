package org.example.controller.query;

import org.example.model.Film;
import org.example.model.Videoteca;

import java.util.List;

public class CercaPerRegistaQuery implements Query {
    private final Videoteca videoteca;
    private final String regista;

    public CercaPerRegistaQuery(Videoteca videoteca, String regista) {
        this.videoteca = videoteca;
        this.regista = regista;
    }

    @Override
    public List<Film> esegui() {
        return videoteca.cercaPerRegista(regista);
    }
}
