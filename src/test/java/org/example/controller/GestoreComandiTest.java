package org.example.controller;

import org.example.controller.command.AggiungiFilmComando;
import org.example.model.Film;
import org.example.model.Genere;
import org.example.model.StatoVisione;
import org.example.model.Videoteca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestoreComandiTest {
    private Videoteca videoteca;
    private GestoreComandi gestore;

    @BeforeEach
    void setUp(){
        videoteca = Videoteca.getInstance();
        videoteca.svuotaVideoteca();
        gestore = new GestoreComandi();
    }

    //test undo()
    @Test
    void testUndoComando(){
        Film film1 = new Film.Builder("Parasite", "Bong Joon-ho", 2019)
                .genere(Genere.THRILLER)
                .statoVisione(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        Film film2 = new Film.Builder("Alien", "Ridley Scott", 1979)
                .genere(Genere.HORROR)
                .statoVisione(StatoVisione.DA_VEDERE)
                .valutazione(4)
                .build();

        gestore.eseguiComando(new AggiungiFilmComando(videoteca, film1));
        gestore.eseguiComando(new AggiungiFilmComando(videoteca, film2));
        assertEquals(2, videoteca.getTuttiFilms().size());
        gestore.undo();
        assertEquals(1, videoteca.getTuttiFilms().size());
        assertTrue(videoteca.getTuttiFilms().contains(film1));
        assertFalse(videoteca.getTuttiFilms().contains(film2));
    }
}
