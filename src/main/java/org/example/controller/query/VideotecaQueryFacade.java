package org.example.controller.query;

import org.example.model.Genere;
import org.example.model.StatoVisione;
import org.example.model.Videoteca;
import org.example.model.Film;

import java.util.Comparator;
import java.util.List;

public class VideotecaQueryFacade {
    private final Videoteca videoteca;

    public VideotecaQueryFacade(){
        this.videoteca = Videoteca.getInstance();
    }

    //Op
    public List<Film> getTuttiFilms(){ return new GetTuttiFilmsQuery(videoteca).esegui(); }

    public List<Film> cercaPerTitolo(String titolo){
        return new CercaPerTitoloQuery(videoteca, titolo).esegui();
    }

    public List<Film> cercaPerRegista(String regista){
        return new CercaPerRegistaQuery(videoteca, regista).esegui();
    }

    public List<Film> filtraPerAnnoUscita(int annoUscita){
        return new FiltraPerAnnoUscitaQuery(videoteca, annoUscita).esegui();
    }

    public List<Film> filtraPerGenere(Genere genere){
        return new FiltraPerGenereQuery(videoteca, genere).esegui();
    }

    public List<Film> filtraPerStatoVisione(StatoVisione statoVisione){
        return new FiltraPerStatoVisioneQuery(videoteca, statoVisione).esegui();
    }

    //ordinamento strategy
    public List<Film> ordinaPerTitolo(){
        return new OrdinaFilmsQuery(videoteca, Comparator.comparing(Film::getTitolo, String.CASE_INSENSITIVE_ORDER)).esegui();
    }

    public List<Film> ordinaPerTitoloDecrescente(){
        return new OrdinaFilmsQuery(videoteca, Comparator.comparing(Film::getTitolo, String.CASE_INSENSITIVE_ORDER).reversed()).esegui();
    }

    public List<Film> ordinaPerValutazione(){
        return new OrdinaFilmsQuery(videoteca, Comparator.comparing(Film::getValutazione).reversed()).esegui();
    }

    public List<Film> ordinaPerAnnoUscita(){
        return new OrdinaFilmsQuery(videoteca, Comparator.comparing(Film::getAnnoUscita).reversed()).esegui();
    }
}
