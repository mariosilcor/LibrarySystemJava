package com.biblioteca.LibrarySystemJava.controller;

import com.biblioteca.LibrarySystemJava.model.Libro;
import com.biblioteca.LibrarySystemJava.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController indica que esta clase devuelve datos (JSON), no pantallas HTML (Módulo 6)
@RestController
@RequestMapping("/api/libros")
public class LibroRestController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }
}