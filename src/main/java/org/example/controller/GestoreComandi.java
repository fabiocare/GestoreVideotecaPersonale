package org.example.controller;

import org.example.controller.command.Comando;

public class GestoreComandi {
    public void eseguiComando(Comando comando) {
        comando.esegui();
    }
}
