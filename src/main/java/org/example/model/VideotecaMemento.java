package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class VideotecaMemento {
    private final List<Film> stato;

    public VideotecaMemento(List<Film> statoCorrente){
        this.stato = new ArrayList<>(statoCorrente);
    }

    public List<Film> getStato(){
        return new ArrayList<>(stato);
    }
}
