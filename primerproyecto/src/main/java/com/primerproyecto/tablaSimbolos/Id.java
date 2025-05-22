package com.primerproyecto.tablaSimbolos;

/**
 * Id
 */
public abstract class Id {

    private boolean inicializado;
    private boolean usado;
    private String tipoDato;
    private String tokenNombre;

    public Id(){
        
    }

    public Id(boolean inicializado, boolean usado, String tipoDato, String tokenNombre){
        this.inicializado = inicializado;
        this.usado = usado;
        this.tipoDato = tipoDato;
        this.tokenNombre = tokenNombre;
    }


    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }
    public boolean isInicializado() {
        return inicializado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
    public boolean isUsado() {
        return usado;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }
    public String getTipoDato() {
        return tipoDato;
    }
    
    public void setTokenNombre(String tokenNombre) {
        this.tokenNombre = tokenNombre;
    }
    public String getTokenNombre() {
        return tokenNombre;
    }

    @Override
    public String toString() {
        return String.valueOf(this.isInicializado());
    }

}