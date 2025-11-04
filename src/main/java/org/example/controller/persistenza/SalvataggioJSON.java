package org.example.controller.persistenza;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Film;
import org.example.model.Videoteca;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class SalvataggioJSON implements SalvataggioStrategy{
    private final Gson gson = new Gson();

    @Override
    public void salva(Videoteca videoteca, String percorsoFile) throws IOException{
        try(Writer writer = new FileWriter(percorsoFile)){
            gson.toJson(videoteca.getTuttiFilms(), writer);
        }
    }

    @Override
    public void carica(String percorsoFile) throws IOException{
        try(Reader reader = new FileReader(percorsoFile)){
            Type listType = new TypeToken<List<Film>>(){}.getType();
            List<Film> films = gson.fromJson(reader, listType);
            if (films == null){
                throw new IOException("Formato JSON non valido");
            }
            Videoteca videoteca = Videoteca.getInstance();
            videoteca.svuotaVideoteca();
            for (Film f: films)
                videoteca.aggiungiFilm(f);
        }
    }
}
