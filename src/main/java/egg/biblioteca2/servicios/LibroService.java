package egg.biblioteca2.servicios;

import egg.biblioteca2.entidades.Autor;
import egg.biblioteca2.entidades.Editorial;
import egg.biblioteca2.entidades.Libro;
import egg.biblioteca2.excepciones.MiException;
import egg.biblioteca2.repositorio.AutorRepositorio;
import egg.biblioteca2.repositorio.EditorialRepositorio;
import egg.biblioteca2.repositorio.LibroRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepo;

    @Autowired
    private AutorRepositorio AutorRepo;

    @Autowired
    private EditorialRepositorio EditorialRepo;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String IdAutor, String IdEditorial) throws MiException {

        validar(isbn, titulo, ejemplares, IdAutor, IdEditorial);

        Libro libro = new Libro();

        //Busco por ID un autor y lo guardo
        Autor autor = AutorRepo.findById(IdAutor).get();
        //Busco por ID una editorial y la guardo
        Editorial editorial = EditorialRepo.findById(IdEditorial).get();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        //Persisto el libro con todo sus datos en la base SQL
        libroRepo.save(libro);

    }

    public List<Libro> listaLibros() {

        List<Libro> listaL = new ArrayList();

        listaL = libroRepo.findAll();

        return listaL;

    }

    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer Ejemplares) throws MiException {
        
        validar(isbn, titulo, Ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepo.findById(isbn);
        Optional<Autor> respuestaA = AutorRepo.findById(idAutor);
        Optional<Editorial> respuestaE = EditorialRepo.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaA.isPresent()) {

            autor = respuestaA.get();

        }

        if (respuestaE.isPresent()) {

            editorial = respuestaE.get();
        }

        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();

            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(Ejemplares);

            libroRepo.save(libro);

        }

    }
    
    private void validar(Long isbn, String titulo, Integer ejemplares, String IdAutor, String IdEditorial) throws MiException {
        
           if (isbn == null) {

            throw new MiException("El isbn no puede ser nulo");

        }

        if (titulo.isEmpty() || titulo == null) {

            throw new MiException("El titulo no puede ser nulo o estar vacio ");

        }
        if (ejemplares == null) {

            throw new MiException("El valor de Ejemplares no puede estar nulo");

        }
        if (IdAutor.isEmpty() || IdAutor == null) {

            throw new MiException("El Autor esta vacio ");

        }

        if (IdEditorial.isEmpty() || IdEditorial == null) {

            throw new MiException("Falta el dato de Editorial");
        }
        
    }
    
    public Libro getOne(Long isbn){
        
        
        return libroRepo.findById(isbn).get();
        
    }     

}
