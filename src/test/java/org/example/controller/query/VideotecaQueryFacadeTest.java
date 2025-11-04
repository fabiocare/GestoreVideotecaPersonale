package org.example.controller.query;

import org.example.model.Film;
import org.example.model.Genere;
import org.example.model.StatoVisione;
import org.example.model.Videoteca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class VideotecaQueryFacadeTest {
    private VideotecaQueryFacade facade;

    @BeforeEach
    void setUp(){
        Videoteca videoteca = Videoteca.getInstance();
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

        facade = new VideotecaQueryFacade();
    }


    //ricerca e filtra
    @ParameterizedTest
    @CsvSource({
            "Star Wars, George Lucas",
            "fight club, David Fincher", //test case insensitive
            "Spirited Away, Hayao Miyazaki"
    })
    void testCercaPerTitolo(String titolo, String registaAtteso){
        List<Film> ris = facade.cercaPerTitolo(titolo);
        assertEquals(1, ris.size());
        assertEquals(registaAtteso, ris.getFirst().getRegista());
    }

    @ParameterizedTest
    @CsvSource({
            "George Lucas, Star Wars",
            "David Fincher, Fight Club",
            "Hayao Miyazaki, Spirited Away"
    })
    void testCercaPerRegista(String regista, String titoloAtteso){
        List<Film> ris = facade.cercaPerRegista(regista);
        assertEquals(1, ris.size());
        assertEquals(titoloAtteso, ris.getFirst().getTitolo());
    }

    @ParameterizedTest
    @EnumSource(Genere.class)
    void testFiltraPerGenere(Genere genere){
        List<Film> ris = facade.filtraPerGenere(genere);
        for(Film f : ris){
            assertEquals(genere, f.getGenere());
        }
    }

    @ParameterizedTest
    @EnumSource(StatoVisione.class)
    void testFiltraPerStatoVisione(StatoVisione statoVisione){
        List<Film> ris = facade.filtraPerStatoVisione(statoVisione);
        for(Film f : ris){
            assertEquals(statoVisione, f.getStatoVisione());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "2001, Spirited Away",
            "1999, Fight Club",
            "1977, Star Wars"
    })
    void testFiltraPerAnnoUscita(int annoUscita, String titoloAtteso){
        List<Film> ris = facade.filtraPerAnnoUscita(annoUscita);
        assertEquals(1, ris.size());
        assertEquals(titoloAtteso, ris.getFirst().getTitolo());
    }

    //ordinamento
    @Test
    void testOrdinaPerTitolo(){
        List<Film> ris = facade.ordinaPerTitolo();
        assertEquals("Fight Club", ris.get(0).getTitolo());
        assertEquals("Spirited Away", ris.get(1).getTitolo());
        assertEquals("Star Wars", ris.get(2).getTitolo());
    }

    @Test
    void testOrdinaPerTitoloDecrescente(){
        List<Film> ris = facade.ordinaPerTitoloDecrescente();
        assertEquals("Star Wars", ris.get(0).getTitolo());
        assertEquals("Spirited Away", ris.get(1).getTitolo());
        assertEquals("Fight Club", ris.get(2).getTitolo());
    }

    @Test
    void testOrdinaPerValutazione(){
        List<Film> ris = facade.ordinaPerValutazione();
        assertEquals(5, ris.getFirst().getValutazione());
    }

    @Test
    void testOrdinaPerAnnoUscita(){
        List<Film> ris = facade.ordinaPerAnnoUscita();
        assertEquals(2001, ris.get(0).getAnnoUscita());
        assertEquals(1999, ris.get(1).getAnnoUscita());
        assertEquals(1977, ris.get(2).getAnnoUscita());
    }
}
