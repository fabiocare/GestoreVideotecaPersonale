package org.example.controller.persistenza;

import org.example.model.Film;
import org.example.model.Genere;
import org.example.model.StatoVisione;
import org.example.model.Videoteca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class SalvataggioStrategyTest {
    private Videoteca videoteca;

    @BeforeEach
    void setUp(){
        videoteca = Videoteca.getInstance();
        videoteca.svuotaVideoteca();

        videoteca.aggiungiFilm(new Film.Builder("Star Wars", "George Lucas", 1977)
                .genere(org.example.model.Genere.FANTASCIENZA)
                .valutazione(3)
                .statoVisione(StatoVisione.DA_VEDERE)
                .build());

        videoteca.aggiungiFilm(new Film.Builder("Fight Club", "David Fincher", 1999)
                .genere(Genere.DRAMMATICO)
                .valutazione(4)
                .statoVisione(StatoVisione.IN_VISIONE)
                .build());

        videoteca.aggiungiFilm(new Film.Builder("Spirited Away", "Hayao Miyazaki", 2001)
                .genere(Genere.ANIMAZIONE)
                .valutazione(5)
                .statoVisione(StatoVisione.VISTO)
                .build());
    }

    @Test
    public void testSalvataggioJSON() throws IOException{
        SalvataggioJSON salvataggio = new SalvataggioJSON();
        String nomeFile = "test.json";

        salvataggio.salva(videoteca, nomeFile);
        salvataggio.carica(nomeFile);

        assertEquals(3, videoteca.getTuttiFilms().size());

        Film filmCaricato = videoteca.getTuttiFilms().getFirst();
        assertEquals("Star Wars", filmCaricato.getTitolo());
        assertEquals("George Lucas", filmCaricato.getRegista());
        assertEquals(1977, filmCaricato.getAnnoUscita());
        assertEquals(Genere.FANTASCIENZA, filmCaricato.getGenere());
        assertEquals(StatoVisione.DA_VEDERE, filmCaricato.getStatoVisione());
        assertEquals(3, filmCaricato.getValutazione());
    }

    @Test
    void testSalvataggioCSV() throws IOException{
        SalvataggioCSV salvataggio = new SalvataggioCSV();
        String nomeFile = "test.csv";

        salvataggio.salva(videoteca, nomeFile);
        salvataggio.carica(nomeFile);

        assertEquals(3, videoteca.getTuttiFilms().size());

        Film filmCaricato = videoteca.getTuttiFilms().get(2);
        assertEquals("Spirited Away", filmCaricato.getTitolo());
        assertEquals("Hayao Miyazaki", filmCaricato.getRegista());
        assertEquals(2001, filmCaricato.getAnnoUscita());
        assertEquals(Genere.ANIMAZIONE, filmCaricato.getGenere());
        assertEquals(StatoVisione.VISTO, filmCaricato.getStatoVisione());
        assertEquals(5, filmCaricato.getValutazione());
    }

    @Test
    void testCaricaFileInesistenteJSON(){
        SalvataggioJSON salvataggio = new SalvataggioJSON();
        String fileInesistente = "file_inesistente.json";
        assertThrows(IOException.class, () -> salvataggio.carica(fileInesistente));
    }

    @Test
    void testCaricaFileInesistenteCSV(){
        SalvataggioCSV salvataggio = new SalvataggioCSV();
        String fileInesistente = "file_inesistente.csv";
        assertThrows(IOException.class, () -> salvataggio.carica(fileInesistente));
    }
}
