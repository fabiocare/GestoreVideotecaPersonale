package org.example.controller.query;

import org.example.model.Film;
import org.example.model.StatoVisione;
import org.example.model.Videoteca;

import java.util.List;

public class FiltraPerStatoVisioneQuery implements Query {
    private final Videoteca videoteca;
    private final StatoVisione statoVisione;

    public FiltraPerStatoVisioneQuery(Videoteca videoteca, StatoVisione statoVisione) {
        this.videoteca = videoteca;
        this.statoVisione = statoVisione;
    }

    @Override
    public List<Film> esegui() {
        return videoteca.filtraPerStatoVisione(statoVisione);
    }
}
