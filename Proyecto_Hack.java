/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyecto_hack;
import static com.mycompany.proyecto_hack.Instruccion.TipoInstruccion.Instruccion_A;
import static com.mycompany.proyecto_hack.Instruccion.TipoInstruccion.Instruccion_C;
import static com.mycompany.proyecto_hack.Instruccion.TipoInstruccion.Etiqueta;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Norman
 */
public class Proyecto_Hack {

    public static void main(String[] args) {
   
        // probando el Max.asm y establecemos la ruta
        String fileName = "C:\\Users\\Norman\\Desktop\\Tareas\\Microprocesadores\\Microprocesors\\Microprocesors\\projects\\06\\max\\max.asm";
         if (args.length == 0){
            return;
        } else {
            fileName = args[args.length -1 ];
        }
         // Analizamos el archivos
         Parser parser = new Parser(fileName);
         // Creamos la tabla para almacernar los labels, escaneamos y hacemos los pasos
         Simbolos tabla = new Simbolos();
         
         scannEtiqueta(parser, tabla);
         
         parser.reset();
         
         imprimirStep(parser, tabla);
         // Iniciamos el proceso con este metodo de emsablaje de ASM a Bin
         ASMaBIN(parser, tabla);
}
    
    
    private static void imprimirStep (Parser parser, Simbolos tabla){
        System.out.println("************************");
        System.out.println("Codigo ASM despues de remover etiquetas");
        parser.imprimirAsmProgram();
        System.out.println("************************");
        
        System.out.println("************************");
        System.out.println("Tabla de simbolos");
        tabla.imprimirTabla();
        System.out.println("************************");
        
        System.out.println("************************");
        System.out.println("Traduccion de ASM a Binario :) ");
        System.out.println("************************");
    }
    
    private static void ASMaBIN( Parser parser, Simbolos tabla){
        try (FileWriter fileWriter = new FileWriter("resultado.hack")){
            while (parser.hasMoreCommands()){
                StringBuilder command = new StringBuilder();
                String binary;
                String symbol = parser.Simbolo();
                
                if (parser.commandType().equals(Instruccion_A)
                    && Character.isLetter(symbol.charAt(0))
                    && tabla.contains(symbol)){
                    
                    int address = tabla.getAddress(symbol);
                    binary = Integer.toBinaryString(address);
                    command.append(binary);
                        
                    }else if (parser.commandType().equals(Instruccion_A)
                            && Character.isLetter(symbol.charAt(0))
                            && !tabla.contains(symbol)){
                        
                        
                        tabla.AnadirSymbol(symbol);
                        int address =  tabla.getAddress(symbol);
                        binary = Integer.toBinaryString(address);
                        command.append(binary);
                                
                     }else if (parser.commandType().equals(Instruccion_A)){
                         binary = Integer.toBinaryString(Integer.parseInt(symbol));
                         command.append(binary);
                         
                    }else if (parser.commandType().equals(Instruccion_C)){
                        String strComp = parser.comp();
                        String strDest = parser.dest();
                        String strJump = parser.jump();
                        command.append("111");
                        command.append(Cifrado.comp(strComp));
                        command.append(Cifrado.dest(strDest));
                        command.append(Cifrado.jump(strJump));
                    } else {
                        command.append("ERROR!!!");
                        
                    }
                
                //añadimos ceros a la izq para sea siempre de 16 bit la lonng
                if(command.length() < 15){
                    command = llenarconCeros(command);
                }
                System.out.println(command);
                fileWriter.write(command + "\n");
            }   
        }  catch (IOException e) { // Esta excepcion la manejamos para facilitar el debug del problema, y es mas que todo por si el archivo FileWriter no existe
            e.printStackTrace();
        }
    }
    
    
    private static StringBuilder llenarconCeros (StringBuilder command){
        return new StringBuilder("0".repeat(Math.max(0, 16 - command.length()))+ command );
    }
    
    private static void scannEtiqueta(Parser parser, Simbolos tabla){
        while (parser.hasMoreCommands()) {
            String currentCommand = parser.getNextCommand();
            if (parser.commandType().equals(Etiqueta)){
                int line = parser.getCurrentCommandIndex();
                String symbol = parser.Simbolo();
                tabla.AnadirSymbolaAddrs(symbol.substring(0, symbol.length()- 1), line -1);
                parser.removeInstruction (line - 1);
            }
        }
    }
    
}