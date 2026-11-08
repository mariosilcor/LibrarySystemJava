package com.biblioteca.LibrarySystemJava.controller;

import com.biblioteca.LibrarySystemJava.model.Libro;
import com.biblioteca.LibrarySystemJava.repository.LibroRepository;
import com.biblioteca.LibrarySystemJava.service.ExportarReporteTask;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/libros") // Esto hace que todas las rutas empiecen con /libros
public class LibroController {

    // Inyección de dependencias (equivalente a pasar el DbContext por el constructor)
    @Autowired
    private LibroRepository libroRepository;

    // --- ACCIÓN INDEX (Listar y Buscar) ---
    @GetMapping
    public String index(Model model, @RequestParam(required = false) String searchString) {

        // El equivalente a ViewBag: pasamos el mensaje a la vista
        model.addAttribute("mensajeBienvenida", "Bienvenido al Sistema de Gestión de Libros (Java Edition)");

        // Lógica del buscador (Equivalente al LINQ que usamos antes)
        if (searchString != null && !searchString.trim().isEmpty()) {
            model.addAttribute("libros", libroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(searchString, searchString));
        } else {
            model.addAttribute("libros", libroRepository.findAll());
        }

        return "libros/index"; // Retorna la vista index.html dentro de la carpeta libros
    }

    // --- ACCIÓN CREATE (GET) ---
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("libro", new Libro());
        return "libros/create";
    }

    // --- ACCIÓN CREATE (POST) ---
    @PostMapping("/create")
    public String createSubmit(@Valid @ModelAttribute Libro libro, BindingResult bindingResult) {
        // @Valid y BindingResult hacen el equivalente a "ModelState.IsValid"
        if (bindingResult.hasErrors()) {
            return "libros/create"; // Si hay error (ej. año menor a 1900), vuelve a mostrar el form
        }
        libroRepository.save(libro); // Guarda en la BD
        return "redirect:/libros"; // Redirige a la lista
    }

    // --- ACCIONES DELETE (Simplificadas para el ejemplo) ---
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        libroRepository.deleteById(id);
        return "redirect:/libros";
    }

    // --- ACCIÓN EDIT (GET) - Mostrar formulario para editar ---
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        // Buscamos el libro por su ID. Si no existe, lanzamos una excepción.
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de libro inválido:" + id));
        model.addAttribute("libro", libro);
        return "libros/edit";
    }

    // --- ACCIÓN EDIT (POST) - Guardar cambios con Manejo de Excepciones (Módulo 3) ---
    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable int id, @Valid @ModelAttribute Libro libro, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            libro.setId(id);
            return "libros/edit";
        }

        // APLICACIÓN DEL MÓDULO 3: Bloque try-catch para manejar errores de base de datos
        try {
            libroRepository.save(libro);
        } catch (Exception e) {
            // Si algo falla al guardar (ej. error de conexión), capturamos la excepción
            model.addAttribute("errorMessage", "Error al intentar actualizar el libro: " + e.getMessage());
            return "libros/edit"; // Regresamos a la vista mostrando el error
        }

        return "redirect:/libros";
    }

    // --- ACCIÓN EXPORTAR (Módulo 5: Iniciar el Hilo) ---
    @GetMapping("/exportar")
    public String exportarReporte(Model model) {
        // Obtenemos los datos
        java.util.List<Libro> listaLibros = libroRepository.findAll();

        // Creamos y arrancamos el hilo en segundo plano usando la clase Thread
        Thread hiloExportacion = new Thread(new ExportarReporteTask(listaLibros));
        hiloExportacion.start(); // El programa no se congela, el hilo trabaja por su cuenta

        // Avisamos al usuario y recargamos la vista
        model.addAttribute("mensajeBienvenida", "¡La exportación .txt se está procesando en segundo plano!");
        model.addAttribute("libros", listaLibros);
        return "libros/index";
    }
}