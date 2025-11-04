package org.example.controller.persistenza;

import org.example.model.Film;
import org.example.model.Genere;
import org.example.model.StatoVisione;
import org.example.model.Videoteca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SalvataggioCSV implements SalvataggioStrategy{

    @Override
    public void salva(Videoteca videoteca, String percorsoFile) throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(percorsoFile))){
            for(Film f : videoteca.getTuttiFilms()){
                String riga = String.format("%s;%s;%d;%s;%d;%s",
                        f.getTitolo(),
                        f.getRegista(),
                        f.getAnnoUscita(),
                        f.getGenere(),
                        f.getValutazione(),
                        f.getStatoVisione());
                writer.write(riga);
                writer.newLine();
            }
        }
    }

    @Override
    public void carica(String percorsoFile) throws IOException{
        List<Film> films = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))){
            String linea;
            while((linea = reader.readLine()) != null){
                String[] campi = linea.split(";");

                Film.Builder builder = new Film.Builder(campi[0], campi[1], Integer.parseInt(campi[2]));

                if(campi.length > 3 && !campi[3].trim().isEmpty()){
                    builder.genere(Enum.valueOf(Genere.class, campi[3]));
                }

                if(campi.length > 4 && !campi[4].trim().isEmpty()){
                    builder.valutazione(Integer.parseInt(campi[4]));
                }

                if(campi.length > 5 && !campi[5].trim().isEmpty()){
                    builder.statoVisione(Enum.valueOf(StatoVisione.class, campi[5]));
                }

                Film film = builder.build();
                films.add(film);
            }
        }
        Videoteca videoteca = Videoteca.getInstance();
        videoteca.svuotaVideoteca();
        for (Film f: films)
            videoteca.aggiungiFilm(f);
    }
}
