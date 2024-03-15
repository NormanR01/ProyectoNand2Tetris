/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_hack;
import java.util.HashMap;

/**
 *
 * @author Norman
 */
public class Simbolos {
    // Para mas facilidad utilizaremos este Hash que tiene como key un string y value un Int
    private HashMap < String, Integer> tabla;
    private int siguienteAddr = 16;
    
    //Inicializamos la tabla de simbolos con los simbolos ya definidos y su direccion o address
    public Simbolos(){
        tabla = new HashMap<>();
        tabla.put("SP",0);
        tabla.put("LCL", 1);
        tabla.put("ARG",2);
        tabla.put("THIS", 3);
        tabla.put("THAT", 4);
        for (int Cn = 0; Cn < 16; Cn++){
            tabla.put("R" + Cn, Cn);
        }
        tabla.put("SCREEN", 16384);
        tabla.put("KBD", 24576);
    }
    public void AnadirSymbolaAddrs(String symbol, int symbolAddr){
        tabla.put(symbol, symbolAddr);
    }
    
    public void AnadirSymbol (String symbol){
        tabla.put(symbol, siguienteAddr);
        siguienteAddr++;
    }
    
    public boolean contains(String symbol){
        return (tabla.containsKey(symbol));
    }
    
    public int getAddress(String symbol){
        return (tabla.get(symbol));
        
    }
    
    public void imprimirTabla(){
        tabla.forEach((s,a) -> System.out.println("symbol: " + s + ", Address " +a));
    }
}