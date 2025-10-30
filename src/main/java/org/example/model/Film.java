package org.example.model;

import java.util.Objects;

public  class Film {
    private final String titolo;
    private final String regista;
    private final int annoUscita;
    private final Genere genere;
    private int valutazione;
    private StatoVisione statoVisione;


    private Film(Builder builder) {
        this.titolo = builder.titolo;
        this.regista = builder.regista;
        this.annoUscita = builder.annoUscita;
        this.genere = builder.genere;
        this.valutazione = builder.valutazione;
        this.statoVisione = builder.statoVisione;
    }

    // Getter
    public String getTitolo() {
        return titolo;
    }

    public String getRegista() {
        return regista;
    }

    public int getAnnoUscita() {
        return annoUscita;
    }

    public Genere getGenere() {
        return genere;
    }

    public int getValutazione() {
        return valutazione;
    }

    public StatoVisione getStatoVisione() {
        return statoVisione;
    }

    // Builder static inner class
    public static class Builder {
        private final String titolo;
        private final String regista;
        private final int annoUscita;
        private Genere genere = Genere.ALTRO;
        private int valutazione = 0;
        private StatoVisione statoVisione = StatoVisione.DA_VEDERE;

        public Builder(String titolo, String regista, int annoUscita) {
            this.titolo = Objects.requireNonNull(titolo);
            this.regista = Objects.requireNonNull(regista);
            this.annoUscita = annoUscita;
            if (annoUscita < 1888) {
                throw new IllegalArgumentException("L'anno di uscita non può essere precedente al 1888");
            }
        }

        public Builder genere(Genere genere) {
            this.genere = genere;
            return this;
        }

        public Builder valutazione(int valutazione) {
            if (valutazione < 1 || valutazione > 5) {
                throw new IllegalArgumentException("La valutazione deve essere tra 1 e 5");
            }
            this.valutazione = valutazione;
            return this;
        }

        public Builder statoVisione(StatoVisione statoVisione) {
            this.statoVisione = statoVisione;
            return this;
        }

        public Film build() {
            return new Film(this);
        }
    }

    @Override
    public String toString() {
        return String.format("%-25s %-20s [%4d] %-15s %d★ %-12s",
            titolo, regista, annoUscita, genere, valutazione, statoVisione);
    }
}

