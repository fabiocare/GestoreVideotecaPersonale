package org.example.controller.command;

import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VideotecaComandoTest {
    private Videoteca videoteca;

    @BeforeEach
    void setUp(){
        videoteca = Videoteca.getInstance();
        videoteca.svuotaVideoteca();
    }

    @Test
    void testAggiuntaFilm(){
        Film film = new Film.Builder("Forrest Gump", "Robert Zemeckis", 1994)
                .genere(Genere.DRAMMATICO)
                .statoVisione(StatoVisione.DA_VEDERE)
                .valutazione(4)
                .build();

        Comando comando = new AggiungiFilmComando(videoteca, film);
        comando.esegui();

        assertTrue(videoteca.getTuttiFilms().contains(film));
    }

    @Test
    void testRimozioneFilm(){
        Film film = new Film.Builder("Tre uomini e una gamba", "Massimo Venier", 1997)
                .genere(Genere.COMMEDIA)
                .statoVisione(StatoVisione.VISTO)
                .valutazione(4)
                .build();
        videoteca.aggiungiFilm(film);

        Comando comando = new RimuoviFilmComando(videoteca, "Tre uomini e una gamba");
        comando.esegui();

        assertFalse(videoteca.getTuttiFilms().contains(film));
    }

    @Test
    void testModificaFilm(){
        Film filmIniziale = new Film.Builder("Dune", "Denis Villeneuve", 2021)
                .genere(Genere.FANTASCIENZA)
                .statoVisione(StatoVisione.DA_VEDERE)
                .valutazione(2)
                .build();
        videoteca.aggiungiFilm(filmIniziale);

        Comando comando = new ModificaFilmComando(videoteca, filmIniziale.getTitolo(), StatoVisione.VISTO, 4);
        comando.esegui();

        Film modificato = videoteca.cercaPerTitolo("Dune").getFirst();
        assertEquals(4, modificato.getValutazione());
        assertEquals(StatoVisione.VISTO, modificato.getStatoVisione());
    }
}
