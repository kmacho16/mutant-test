package co.kmacho.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class DatosMutante {
    @ColumnName("cadena_hash")
    private String cadenaHash;

    @ColumnName("cadena_dna")
    private String cadenaDna;

    @ColumnName("cadena_mutante")
    private String cadenaMutante;

    @ColumnName("is_mutant")
    private boolean is_mutant;

    public String getCadenaHash() {
        return cadenaHash;
    }

    public void setCadenaHash(String cadenaHash) {
        this.cadenaHash = cadenaHash;
    }

    public String getCadenaDna() {
        return cadenaDna;
    }

    public void setCadenaDna(String cadenaDna) {
        this.cadenaDna = cadenaDna;
    }

    public String getCadenaMutante() {
        return cadenaMutante;
    }

    public void setCadenaMutante(String cadenaMutante) {
        this.cadenaMutante = cadenaMutante;
    }

    public boolean isIs_mutant() {
        return is_mutant;
    }

    public void setIs_mutant(boolean is_mutant) {
        this.is_mutant = is_mutant;
    }
}
