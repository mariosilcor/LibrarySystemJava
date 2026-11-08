package com.biblioteca.LibrarySystemJava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

// @Entity le dice a Spring Data JPA que esta clase será una tabla en la base de datos (Equivalente a DbContext DbSet)
@Entity
@Table(name = "Libros")
public class Libro {

    // --- ATRIBUTOS (Privados para cumplir con el Encapsulamiento del Módulo 2) ---

    @Id // Equivalente a [Key]
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea Autonumérico
    private int id;

    @NotBlank(message = "El título es obligatorio.") // Equivalente a [Required]
    @Size(max = 100, message = "El título no puede exceder los 100 caracteres.") // Equivalente a [StringLength]
    private String titulo;

    @NotBlank(message = "El autor es obligatorio.")
    @Size(max = 50, message = "El nombre del autor no puede exceder los 50 caracteres.")
    private String autor;

    @NotNull(message = "El año de publicación es obligatorio.")
    @Min(value = 1901, message = "El año de publicación debe ser mayor a 1900.") // Equivalente a la validación de negocio con [Range]
    private Integer añoPublicacion;

    // Constructor vacío (Requerido por JPA/Hibernate)
    public Libro() {
    }

    // --- GETTERS Y SETTERS (Módulo 2: Encapsulamiento) ---
    // En Java no existen las propiedades { get; set; } automáticas como en C#,
    // debemos crear los métodos para leer (get) y escribir (set) los atributos privados.

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Integer getAñoPublicacion() { return añoPublicacion; }
    public void setAñoPublicacion(Integer añoPublicacion) { this.añoPublicacion = añoPublicacion; }
}