package com.primerproyecto.tablaSimbolos;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;

public class TablaSimbolos {

    private LinkedList<HashMap<String, Id>> tablaSimbolos;

    public TablaSimbolos() {
        tablaSimbolos = new LinkedList<HashMap<String, Id>>();
    }

    /*
     *  Crea un contexto vacío
     */
    public void agregarContexto() { 
        HashMap<String, Id> contexto = new HashMap<String, Id>();
        this.tablaSimbolos.add(contexto);
    }

    /*
     *  Elimina un contexto
     */
    public void quitarContexto() {
        this.tablaSimbolos.removeLast();
    }

    /*
     *  Busca que la variable no sea usada, ni sea la función main,
     *  y si esto se cumple 
     *  se fija que no sea instancia de Funcion y no esté inicializada, si no se cumple lo agrega a la lista
     *  
     *  retorna una lista de las variables sin usar
     */

    public ArrayList<String> idSinUsar() {
        ArrayList<String> variablesSinUsar = new ArrayList<String>();
        for (Entry<String, Id> entrada : tablaSimbolos.getLast().entrySet()) {
            if(!entrada.getValue().isUsado() && !entrada.getValue().getTokenNombre().equals("main")){
                if (entrada.getValue() instanceof Funcion && !entrada.getValue().isInicializado()) {
                    continue;
                }else{
                    variablesSinUsar.add(entrada.getKey()); 
                }
            }
        }
        return variablesSinUsar;
    }

    /*
     *  Pisa los valores del contexto vacío con lo que recibe por parametro
     */ 
    public void agregarEnContextoActual(String clave, Id valor){
        this.tablaSimbolos.getLast().put(clave, valor);
    }

    /*
     *  Busca el id en el contexto local, si no lo encuentra, lo busca en el resto de contextos
     */

    public Id buscarIdGlobal(String id){
        Id simbolo = buscarIdLocal(id);
        if(simbolo != null){
            return simbolo;
        }else{
            ListIterator<HashMap<String, Id>> iteratorSimbolos = this.tablaSimbolos.listIterator(tablaSimbolos.size() - 1);
            while(iteratorSimbolos.hasPrevious()){
                simbolo = iteratorSimbolos.previous().get(id);
                if( simbolo != null ){
                    return simbolo;
                }
            }
        }
        return simbolo;
    }

    /*
     *  Busca el id en el contexto local
     */
    public Id buscarIdLocal(String id){
        Id simbolo = tablaSimbolos.getLast().get(id);
        return simbolo;
    } 
    
    /*
     *  retorna la tabla de simbolos con todos los contextos
     */
    public LinkedList<HashMap<String, Id>> getTablaSimbolos() {
        return tablaSimbolos;
    }

    @Override
    public String toString() {
        return tablaSimbolos.toString();
    }
    
}