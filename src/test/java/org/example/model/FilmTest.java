package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    @Test
    void testCreaFilmConBuilder() {
        Film film = new Film.Builder("Goodfellas", "Martin Scorsese", 1990)
                .genere(Genere.DRAMMATICO)
                .valutazione(5)
                .statoVisione(StatoVisione.VISTO)
                .build();

        assertEquals("Goodfellas", film.getTitolo());
        assertEquals("Martin Scorsese", film.getRegista());
        assertEquals(1990, film.getAnnoUscita());
        assertEquals(Genere.DRAMMATICO, film.getGenere());
        assertEquals(5, film.getValutazione());
        assertEquals(StatoVisione.VISTO, film.getStatoVisione());
    }

    @Test
    void testAnnoUscitaFuoriRange() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Film.Builder("Antico Film", "Regista Storico", 1800) // anno non valido
                        .build()
        );
        assertEquals("L'anno di uscita non può essere precedente al 1888", exception.getMessage());
    }
}
