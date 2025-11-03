package org.example.controller;

import org.example.controller.command.Comando;
import org.example.model.Videoteca;
import org.example.model.VideotecaMemento;

import java.util.Stack;

public class GestoreComandi {
    private final Stack<VideotecaMemento> cronologia = new Stack<>();
    private final Videoteca videoteca = Videoteca.getInstance();

    public void eseguiComando(Comando comando) {
        cronologia.push(new VideotecaMemento(videoteca.getTuttiFilms()));
        comando.esegui();
    }

    public void undo(){
        if(!cronologia.isEmpty()){
            VideotecaMemento memento= cronologia.pop();
            videoteca.sostituisciFilms(memento.getStato());
        }
    }
}
