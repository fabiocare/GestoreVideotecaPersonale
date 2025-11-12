package org.example.view;

import org.example.controller.GestoreComandi;
import org.example.controller.command.AggiungiFilmComando;
import org.example.controller.command.ModificaFilmComando;
import org.example.controller.command.RimuoviFilmComando;
import org.example.controller.command.SvuotaVideotecaComando;
import org.example.controller.persistenza.SalvataggioCSV;
import org.example.controller.persistenza.SalvataggioJSON;
import org.example.controller.persistenza.SalvataggioStrategy;
import org.example.controller.query.GetTuttiFilmsQuery;
import org.example.controller.query.VideotecaQueryFacade;
import org.example.model.Film;
import org.example.model.Genere;
import org.example.model.StatoVisione;
import org.example.model.Videoteca;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class InterfacciaUtente {
    private final GestoreComandi gestoreComandi = new GestoreComandi();
    private final Scanner scanner = new Scanner(System.in);
    private SalvataggioStrategy salvataggioStrategy;
    private final Videoteca videoteca = Videoteca.getInstance();

    public void avvia(){
        while(true){
            mostraMenu();
            String scelta = scanner.nextLine();

            switch (scelta){
                case "1" -> aggiungiFilm();
                case "2" -> rimuoviFilm();
                case "3" -> modificaFilm();
                case "4" -> visualizzaFilm();
                case "5" -> cercaFilm();
                case "6" -> ordinaFilm();
                case "7" -> salvaVideoteca();
                case "8" -> caricaVideoteca();
                case "9" -> undo();
                case "10"-> svuotaVideoteca();
                case "0" ->{
                    if(videoteca.getTuttiFilms().isEmpty()) {
                        System.out.println("Chiusura programma...");
                    }else{
                        System.out.println("La videoteca contiene dei film. Vuoi salvare prima di uscire? (Si/No)");

                        String risposta = scanner.nextLine().trim().toUpperCase();

                        if(risposta.equals("SI")){
                            salvaVideoteca();
                            System.out.println("Videoteca salvata. Chiusura programma...");
                        }else{
                            System.out.println("Chiusura programma senza salvare...");
                        }
                    }
                    return;
                }
                default -> System.out.println("Scelta non valida.");
            }
        }
    }

    private void mostraMenu(){
        System.out.println("""
    ╔══════════════════════════════════════════════╗
    ║                MENU VIDEOTECA                ║    
    ╚══════════════════════════════════════════════╝
    ║    1. Aggiungi un Film                       ║ 
    ║    2. Rimuovi un Film                        ║ 
    ║    3. Modifica un Film                       ║ 
    ║    4. Visualizza Film                        ║ 
    ║    5. Cerca Film                             ║ 
    ║    6. Ordina Film                            ║   
    ║    7. Salva Videoteca                        ║ 
    ║    8. Carica Videoteca                       ║ 
    ║    9. Annulla Ultima Operazione              ║ 
    ║   10. Svuota Videoteca                       ║ 
    ║    0. Esci                                   ║ 
    ║                                              ║ 
    ╚══════════════════════════════════════════════╝
        Inserisci la tua scelta:
        """);
    }

    private void aggiungiFilm(){
        System.out.println("Inserisci titolo: ");
        String titolo = scanner.nextLine();
        System.out.println("Inserisci regista: ");
        String regista = scanner.nextLine();
        System.out.println("Inserisci anno di uscita: ");
        int annoUscita;
        try{
            annoUscita = Integer.parseInt(scanner.nextLine());
            if(annoUscita < 1888 || annoUscita > 2025){
                System.out.println("Anno di uscita non valido.");
                return;
            }
        } catch (NumberFormatException e){
            System.out.println("Anno di uscita non valido.");
            return;
        }
        System.out.println("Inserisci genere: ");
        for (Genere g: Genere.values()) {
            System.out.print(g.ordinal() + 1 + "." + g.name() + " ");
        }
        System.out.println();
        int indexGenere = Integer.parseInt(scanner.nextLine()) - 1;
        Genere genere;
        if(indexGenere < 0 || indexGenere >= Genere.values().length){
            System.out.println("Genere non valido.");
            return;
        }
        else{
            genere = Genere.values()[indexGenere];
        }

        System.out.println("Inserisci valutazione (1-5): ");
        int valutazione = Integer.parseInt(scanner.nextLine());
        if(valutazione <= 0 || valutazione > 5){
            System.out.println("Valutazione non valida, deve essere tra 1 e 5.");
            return;
        }
        System.out.println();

        System.out.println("Inserisci stato di visione: ");
        for(StatoVisione sv: StatoVisione.values()){
            System.out.print(sv.ordinal() + 1 + "." + sv.name() + " ");
        }
        System.out.println();

        StatoVisione statoVisione;
        int indexStatoVisione = Integer.parseInt(scanner.nextLine()) - 1;
        if(indexStatoVisione < 0 || indexStatoVisione >= StatoVisione.values().length) {
            System.out.println("Stato di visione non valido.");
            return;
        }else{
            statoVisione = StatoVisione.values()[indexStatoVisione];
        }

        Film nuovoFilm = new Film.Builder(titolo, regista, annoUscita)
                .genere(genere)
                .valutazione(valutazione)
                .statoVisione(statoVisione)
                .build();

        gestoreComandi.eseguiComando(new AggiungiFilmComando(videoteca, nuovoFilm));
        System.out.println("Film aggiunto con successo!");
    }

    private void rimuoviFilm() {
        System.out.println("Inserisci il titolo del film da rimuovere: ");
        String titolo = scanner.nextLine();

        List<Film> candidati = new VideotecaQueryFacade().cercaPerTitolo(titolo).stream().filter(f -> f.getTitolo().equalsIgnoreCase(titolo)).toList();

        if (candidati.isEmpty()) {
            System.out.println("Nessun film trovato con il titolo specificato.");
            return;
        }
        Film scelto;
        if (candidati.size() == 1) {
            scelto = candidati.getFirst();
            System.out.println("Rimuovendo il film: " + scelto.getTitolo() + " di " + scelto.getRegista());
        } else {
            System.out.println("Sono stati trovati più film con lo stesso titolo. Specifica il regista: ");
            for (int i = 0; i < candidati.size(); i++) {
                Film f = candidati.get(i);
                System.out.println((i + 1) + ". " + f.getTitolo() + " di " + f.getRegista());
            }
            int scelta = leggiInteroInRange("Seleziona [1-" + candidati.size() + "]: ", 1, candidati.size());
            scelto = candidati.get(scelta - 1);
            System.out.println("Rimuovendo il film: " + scelto.getTitolo() + " di " + scelto.getRegista());
        }
        gestoreComandi.eseguiComando(new RimuoviFilmComando(videoteca, scelto.getTitolo(), scelto.getRegista()));
        System.out.println("Film rimosso con successo!");
    }

    private int leggiInteroInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine();
            try {
                int v = Integer.parseInt(s);
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Riprova.");
            }
        }
    }

    private void modificaFilm(){
        System.out.println("Inserisci il titolo del film da modificare: ");
        String titolo = scanner.nextLine();
        if(titolo.isEmpty()){
            System.out.println("Titolo non valido.");
            return;
        }
        List<Film> candidati = new VideotecaQueryFacade().cercaPerTitolo(titolo).stream().filter(f -> f.getTitolo().equalsIgnoreCase(titolo)).toList();

        if(candidati.isEmpty()){
            System.out.println("Nessun film trovato con il titolo specificato.");
            return;
        }

        Film scelto;
        if(candidati.size() == 1){
            scelto = candidati.getFirst();
            System.out.println("Modificando il film: " + scelto.getTitolo() + " di " + scelto.getRegista());
        }else{
            System.out.println("Sono stati trovati più film con lo stesso titolo. Specifica il regista: ");
            for(int i = 0; i < candidati.size(); i++){
                Film f = candidati.get(i);
                System.out.println((i + 1) + ". " + f.getTitolo() + " di " + f.getRegista());
            }
            int scelta = leggiInteroInRange("Seleziona [1-" + candidati.size() + "]: ", 1, candidati.size());
            scelto = candidati.get(scelta - 1);
            System.out.println("Modificando il film: " + scelto.getTitolo() + " di " + scelto.getRegista());
        }
        System.out.println("Inserisci stato di visione: ");
        for(StatoVisione sv : StatoVisione.values()){
            System.out.print(sv.ordinal() + 1 + "." + sv.name() + " ");
        }
        System.out.println();

        StatoVisione statoVisione;
        int indexStatoVisione = Integer.parseInt(scanner.nextLine()) - 1;

        if(indexStatoVisione < 0 || indexStatoVisione >= StatoVisione.values().length) {
            System.out.println("Stato di visione non valido.");
            return;
        }else{
            statoVisione = StatoVisione.values()[indexStatoVisione];
        }

        System.out.print("Inserisci nuova valutazione (1-5): ");
        int valutazione = Integer.parseInt(scanner.nextLine());
        if(valutazione < 1 || valutazione > 5){
            System.out.println("Valutazione non valida, deve essere tra 1 e 5.");
            return;
        }

        gestoreComandi.eseguiComando(new ModificaFilmComando(videoteca, scelto.getTitolo(), scelto.getRegista(), statoVisione, valutazione));
        System.out.println("Film aggiornato con successo!");
    }

    private void visualizzaFilm(){
        List<Film> films = new GetTuttiFilmsQuery(videoteca).esegui();
        if(films.isEmpty()){
            System.out.println("Nessun film presente nella videoteca.");
        }else{
            vistaTabella(films);
        }
    }

    private void vistaTabella(List<Film> films){
        System.out.printf("%-30s %-20s %-15s %-15s %-15s %-10s%n",
                        "Titolo", "Regista", "Anno", "Genere", "StatoVisione", "Valutazione");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for(Film f : films){
            System.out.printf("%-30s %-20s %-15s %-15s %-15s %-10s%n",
                    f.getTitolo(),
                    f.getRegista(),
                    f.getAnnoUscita(),
                    f.getGenere(),
                    f.getStatoVisione(),
                    f.getValutazione());
        }
    }

    private void cercaFilm(){
        System.out.println("Cerca per: 1. Titolo  2. Regista  3. Genere  4. Stato Visione  5. Anno Uscita");
        String scelta = scanner.nextLine();

        switch(scelta){
            case "1" -> {
                System.out.println("Inserisci titolo: ");
                String titolo = scanner.nextLine();
                vistaTabella(new VideotecaQueryFacade().cercaPerTitolo(titolo));
            }
            case "2" -> {
                System.out.println("Inserisci regista: ");
                String regista = scanner.nextLine();
                vistaTabella(new VideotecaQueryFacade().cercaPerRegista(regista));
            }
            case "3" -> {
                System.out.println("Scegli il genere:");
                for (Genere g : Genere.values()) {
                    System.out.print((g.ordinal() + 1) + ". " + g.name() + " ");
                }
                System.out.println();
                int indexGenere;
                try {
                    indexGenere = Integer.parseInt(scanner.nextLine()) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Input non valido.");
                    return;
                }

                if (indexGenere < 0 || indexGenere >= Genere.values().length) {
                    System.out.println("Genere non valido.");
                    return;
                }
                Genere genere = Genere.values()[indexGenere];
                vistaTabella(new VideotecaQueryFacade().filtraPerGenere(genere));
                }

            case "4" -> {
                System.out.println("Scegli lo stato di visione:");
                for (StatoVisione s : StatoVisione.values()) {
                    System.out.print((s.ordinal() + 1) + ". " + s.name() + " ");
                }
                System.out.println();
                int indexStato;
                try {
                    indexStato = Integer.parseInt(scanner.nextLine()) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Input non valido.");
                    return;
                }

                if (indexStato < 0 || indexStato >= StatoVisione.values().length) {
                    System.out.println("Stato di visione non valido.");
                    return;
                }
                StatoVisione statoVisione = StatoVisione.values()[indexStato];
                vistaTabella(new VideotecaQueryFacade().filtraPerStatoVisione(statoVisione));
            }
            case "5" -> {
                System.out.println("Inserisci anno di uscita: ");
                int annoUscita = Integer.parseInt(scanner.nextLine());
                vistaTabella(new VideotecaQueryFacade().filtraPerAnnoUscita(annoUscita));
            }
            default -> System.out.println("Scelta non valida.");
        }
    }

    private void ordinaFilm(){
        System.out.println("Ordina per: 1. Titolo (A-Z)  2. Titolo (Z-A)  3. Valutazione  4. Anno di uscita");
        String scelta = scanner.nextLine();

        switch (scelta){
            case "1" -> vistaTabella(new VideotecaQueryFacade().ordinaPerTitolo());
            case "2" -> vistaTabella(new VideotecaQueryFacade().ordinaPerTitoloDecrescente());
            case "3" -> vistaTabella(new VideotecaQueryFacade().ordinaPerValutazione());
            case "4" -> vistaTabella(new VideotecaQueryFacade().ordinaPerAnnoUscita());
            default -> System.out.println("Scelta non valida.");
        }
    }

    private void salvaVideoteca(){
        System.out.println("Scegli il formato di salvataggio (CSV/JSON): ");
        String formato = scanner.nextLine().trim().toUpperCase();

        if(formato.equals("CSV")){
            salvataggioStrategy = new SalvataggioCSV();
        }else if (formato.equals("JSON")){
            salvataggioStrategy = new SalvataggioJSON();
        }else{
            System.out.println("Formato non valido.");
            return;
        }

        System.out.println("Inserisci il nome del file: ");
        String nomeFile = scanner.nextLine();

        try{
            salvataggioStrategy.salva(videoteca, nomeFile);
            System.out.println("Salvataggio completato.");
        }catch(IOException e){
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    private void caricaVideoteca() {
        System.out.println("Scegli il formato di caricamento (CSV/JSON): ");
        String formato = scanner.nextLine().trim().toUpperCase();

        if (formato.equals("CSV")) {
            salvataggioStrategy = new SalvataggioCSV();
        } else if (formato.equals("JSON")) {
            salvataggioStrategy = new SalvataggioJSON();
        } else {
            System.out.println("Formato non valido.");
            return;
        }

        System.out.println("Inserisci il nome del file: ");
        String nomeFile = scanner.nextLine();

        try {
            salvataggioStrategy.carica(nomeFile);
            System.out.println("Caricamento completato.");
        }catch(IOException e){
            System.out.println("Errore durante il caricamento: " + e.getMessage());
        }
    }

    private void undo(){
        gestoreComandi.undo();
        System.out.println("Ultima operazione annullata.");
    }

    private void svuotaVideoteca(){
        gestoreComandi.eseguiComando(new SvuotaVideotecaComando(videoteca));
        System.out.println("Videoteca svuotata.");
    }

    public static void main(String[] args){
        new InterfacciaUtente().avvia();
    }
}
