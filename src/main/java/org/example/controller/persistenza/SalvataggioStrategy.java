package org.example.controller.persistenza;

import org.example.model.Videoteca;

import java.io.IOException;

public interface SalvataggioStrategy {
    void salva(Videoteca videoteca, String percorsoFile)throws IOException;
    void carica(String percorsoFile)throws IOException;
}
