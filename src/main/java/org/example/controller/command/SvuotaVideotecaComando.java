package org.example.controller.command;

import org.example.model.Videoteca;

public class SvuotaVideotecaComando implements Comando{
    private final Videoteca videoteca;

    public SvuotaVideotecaComando(Videoteca videoteca){
        this.videoteca = videoteca;
    }

    @Override
    public void esegui(){
        videoteca.svuotaVideoteca();
    }
}
