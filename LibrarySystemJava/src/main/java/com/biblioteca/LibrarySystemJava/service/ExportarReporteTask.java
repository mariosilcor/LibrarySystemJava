package com.biblioteca.LibrarySystemJava.service;

import com.biblioteca.LibrarySystemJava.model.Libro;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

// Implementamos Runnable para crear un Hilo (Módulo 5: Multithreading)
public class ExportarReporteTask implements Runnable {

    private List<Libro> librosLista;

    public ExportarReporteTask(List<Libro> libros) {
        this.librosLista = libros;
    }

    @Override
    public void run() {
        // 1. Convertir la Colección a un Array Unidimensional (Módulo 1: Arrays)
        Libro[] librosArray = new Libro[librosLista.size()];
        librosArray = librosLista.toArray(librosArray);

        // 2. Escribir el archivo físico (Módulo 3: I/O y Excepciones)
        File archivo = new File("reporte_biblioteca.txt");

        // Bloque try-with-resources que maneja PrintWriter y FileWriter
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("--- REPORTE OFICIAL DE LIBROS ---");

            // Iteración sobre el Array
            for (int i = 0; i < librosArray.length; i++) {
                pw.println("ID: " + librosArray[i].getId() +
                        " | Título: " + librosArray[i].getTitulo() +
                        " | Autor: " + librosArray[i].getAutor());
            }
            System.out.println("ÉXITO: Reporte generado en segundo plano en: " + archivo.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("ERROR I/O: No se pudo escribir el archivo. " + e.getMessage());
        }
    }
}