/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_hack;

import static com.mycompany.proyecto_hack.Instruccion.TipoInstruccion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Norman
 */
public class Parser {
    
    // En un arreglo almacenamos las instrucciones del ensabmlador
    private final ArrayList<String> instrucciones = new ArrayList<>();
   // Posicion actual de la instruccion que se esta procesando
    private int currentCommandIndex = 0;
    String currentCommand;
    
    
    // Recibimos el archivo del emsablador
    public Parser(String fileName){
        try (FileReader fileReader = new FileReader(fileName)){
            BufferedReader reader = new BufferedReader(fileReader);
            String strLine;
            // leemos linea por linea y evitamos espacios en blanco
            while ((strLine = reader.readLine()) != null){
                String command = quitarComentsySpaces(strLine);
                if (!command.equals("")){
                    instrucciones.add(command);
                    System.out.println(command);
                }
            }
            fileReader.close();
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    // Obtender el indice de la instruccion actual
      public int getCurrentCommandIndex(){
        return currentCommandIndex;
    }
    private String quitarComentsySpaces(String strLine){
        String strWhiteSpace = strLine;
        
        if (strWhiteSpace.contains("//")){
            int nOffSet = strWhiteSpace.indexOf("//");
            strWhiteSpace = strWhiteSpace.substring(0, nOffSet).trim();
        }
        
        if (!strWhiteSpace.equals("")){
            strWhiteSpace = strWhiteSpace.trim().replaceAll("\\s","");
        }
        return strWhiteSpace;
    }
    // Verificamos si hay mas instrucciones por procesar
    
    public boolean hasMoreCommands(){
        return currentCommandIndex < instrucciones.size();
    }
    // Proxima instruccion
    public String getNextCommand(){
        currentCommand = instrucciones.get(currentCommandIndex);
        currentCommandIndex++;
        return currentCommand;
    }
    
    // Verificaremos el tipo de instruccion actual
    public TipoInstruccion commandType(){
     if (currentCommand.charAt(0) == '@'){
         return TipoInstruccion.Instruccion_A;
     } else if (currentCommand.charAt(0) == '('){
         return TipoInstruccion.Etiqueta;
     } else {
         return TipoInstruccion.Instruccion_C;
     }
    }
    
    public String Simbolo(){
        return currentCommand.substring(1);
    }
    
    public void removeInstruction (int index){
      instrucciones.remove(index);
    }
    
    public void reset(){
        currentCommandIndex = 0;
    }
    
    // devuelve null sino esta presente
    public String dest(){
        String dest;
        if (currentCommand.contains("=")){
            int nIndex = currentCommand.indexOf("=");
            dest = currentCommand.substring(0, nIndex);
        } else {
            dest = null;
        }
        return dest;
    }
            
    // Realizamos el calculo de la instruccion actual //comp
    public String comp(){
        String comp;
        
        if (currentCommand.contains("=") && currentCommand.contains(";")){
            int equalsIndex = currentCommand.indexOf("=");
            int semiIndex = currentCommand.indexOf(";");
            comp = currentCommand.substring(equalsIndex + 1, semiIndex);
            
        } else if (currentCommand.contains("=")){
            int equalsIndex = currentCommand.indexOf("=");
            comp = currentCommand.substring(equalsIndex + 1);
        } else if (currentCommand.contains(";")) {
            int semiIndex = currentCommand.indexOf(";");
            comp = currentCommand.substring(0, semiIndex);
        } else {
            return null;
        } return comp;
    }
    
    
    // Parte de salto de la instruccion 
    public String jump(){
        String jump;
        if(currentCommand.contains(";")){
            int semiIndex = currentCommand.indexOf(";");
            jump = currentCommand.substring(semiIndex + 1);
        } else {
            jump = null;
        }
        return jump;
    }

    public void imprimirAsmProgram(){
    instrucciones.forEach(System.out::println);
    }
}




