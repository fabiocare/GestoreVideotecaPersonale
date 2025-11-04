package org.example.controller.query;

import org.example.model.Film;
import org.example.model.Videoteca;

import java.util.List;

public class FiltraPerAnnoUscitaQuery implements Query {
    private final Videoteca videoteca;
    private final int annoUscita;

    public FiltraPerAnnoUscitaQuery(Videoteca videoteca, int annoUscita) {
        this.videoteca = videoteca;
        this.annoUscita = annoUscita;
    }

    @Override
    public List<Film> esegui() {
        return videoteca.filtraPerAnnoUscita(annoUscita);
    }

}
