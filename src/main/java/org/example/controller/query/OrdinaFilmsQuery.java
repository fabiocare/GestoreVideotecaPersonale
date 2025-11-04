package org.example.controller.query;

import org.example.model.Film;
import org.example.model.Videoteca;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrdinaFilmsQuery implements Query {
    private final Videoteca videoteca;
    private final Comparator<Film> comparatore;

    public OrdinaFilmsQuery(Videoteca videoteca, Comparator<Film> comparatore) {
        this.videoteca = videoteca;
        this.comparatore = comparatore;
    }

    @Override
    public List<Film> esegui() {
        return videoteca.getTuttiFilms()
                .stream()
                .sorted(comparatore)
                .collect(Collectors.toList());
    }
}
