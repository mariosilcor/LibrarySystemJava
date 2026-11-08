package com.biblioteca.LibrarySystemJava;

import com.biblioteca.LibrarySystemJava.model.Libro;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LibroTests {

    private Validator validator;

    // @BeforeEach se ejecuta antes de cada prueba (Anotación de JUnit)
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // @Test indica que este método es una prueba unitaria (Anotación de JUnit)
    @Test
    public void Validacion_AñoPublicacion_MenorA1900_DebeFallar() {
        // Arrange (Preparar)
        Libro libroPrueba = new Libro();
        libroPrueba.setTitulo("Libro de Prueba");
        libroPrueba.setAutor("Mario Eduardo");
        libroPrueba.setAñoPublicacion(1899); // Año inválido intencionalmente

        // Act (Actuar)
        // Ejecutamos las validaciones (equivalente a validar el ModelState)
        Set<ConstraintViolation<Libro>> violations = validator.validate(libroPrueba);

        // Assert (Afirmar)
        // La prueba PASA si la lista de violaciones NO está vacía (es decir, detectó el error)
        assertFalse(violations.isEmpty(), "La validación debería fallar si el año es menor a 1900");
    }
}