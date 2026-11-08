package com.biblioteca.LibrarySystemJava.repository;

import com.biblioteca.LibrarySystemJava.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository indica que esta interfaz se encarga de la base de datos.
// Al heredar (extends) de JpaRepository, Spring nos regala automáticamente
// todos los métodos CRUD (save, findAll, findById, deleteById) sin tener que escribirlos.
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    // Y para cumplir con el Módulo 6, podemos crear métodos de búsqueda (equivalente a LINQ)
    // Spring traducirá esto automáticamente a una consulta SQL "SELECT * FROM Libros WHERE titulo LIKE %?% OR autor LIKE %?%"
    java.util.List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo, String autor);
}