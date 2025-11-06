package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class VideotecaTest {
    private Videoteca videoteca;

    @BeforeEach
    void setUp(){
        videoteca = Videoteca.getInstance();
        videoteca.svuotaVideoteca();

        Film filmBase = new Film.Builder("Oldboy", "Park Chan-wook", 2003)
                .genere(Genere.THRILLER)
                .valutazione(5)
                .statoVisione(StatoVisione.VISTO)
                .build();

        videoteca.aggiungiFilm(filmBase);
    }

    @Test
    void testAggiuntaFilm(){
        List<Film> films= videoteca.getTuttiFilms();
        assertEquals(1, films.size());
        assertEquals("Oldboy", films.getFirst().getTitolo());
    }

    @Test
    void testRimozioneFilm(){
        videoteca.rimuoviFilm("Oldboy", "Park Chan-wook");
        assertTrue(videoteca.getTuttiFilms().isEmpty());
    }

    @Test
    void testModificaFilm(){
        videoteca.modificaFilm("Oldboy", "Park Chan-wook", StatoVisione.DA_VEDERE, 4);
        Film aggiornato = videoteca.getTuttiFilms().getFirst();
        assertEquals(4,aggiornato.getValutazione());
        assertEquals(StatoVisione.DA_VEDERE, aggiornato.getStatoVisione());
    }

    @Test
    void TestFiltraPerGenere(){
        List<Film> trovati = videoteca.filtraPerGenere(Genere.THRILLER);
        assertEquals(1, trovati.size());
    }

    @Test
    void testCercaPerTitolo(){
        List<Film> trovati = videoteca.cercaPerTitolo("Oldboy");
        assertFalse(trovati.isEmpty());
        assertEquals("Park Chan-wook", trovati.getFirst().getRegista());
    }

    @Test
    void testFiltraPerAnnoUscita(){
        List<Film> trovati = videoteca.filtraPerAnnoUscita(2003);
        assertEquals(1, trovati.size());
    }
}
