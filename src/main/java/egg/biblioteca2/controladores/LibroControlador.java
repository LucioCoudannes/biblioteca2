package egg.biblioteca2.controladores;

import egg.biblioteca2.entidades.Autor;
import egg.biblioteca2.entidades.Editorial;
import egg.biblioteca2.entidades.Libro;
import egg.biblioteca2.excepciones.MiException;
import egg.biblioteca2.servicios.AutorService;
import egg.biblioteca2.servicios.EditorialService;
import egg.biblioteca2.servicios.LibroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")

public class LibroControlador {

    @Autowired
    private AutorService as;

    @Autowired
    private EditorialService es;

    @Autowired
    private LibroService ls;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Autor> autores = as.listaAutores();
        List<Editorial> editoriales = es.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";

    }

    @PostMapping("/registro")

    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares,
            @RequestParam String IdAutor, @RequestParam String IdEditorial, ModelMap modelo) {

        try {
            ls.crearLibro(isbn, titulo, ejemplares, IdAutor, IdEditorial);
            modelo.put("exito", "El libro fue cargado correctamente");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "libro_form.html";
        }
        return "libro_form.html";
    }

    @PostMapping("/index")
    public String index() {

        return "index.html";

    }

    @GetMapping("/libros")
    public String listar(ModelMap modelo) {
        List<Libro> libros = ls.listaLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";

    }
    
     @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo){
        
       modelo.put("libro", ls.getOne(isbn));
       List<Autor> autores = as.listaAutores();
       List<Editorial> editoriales = es.listarEditoriales();
       
       modelo.addAttribute("autores", autores);
       modelo.addAttribute("editoriales", editoriales);
       
       return "libro_modificar.html";
        
    }
    
    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares, ModelMap modelo){
        
        try {
            ls.modificarLibro(isbn, titulo, idAutor, idEditorial, ejemplares);
            
            return "redirect:../libros";
        } catch (MiException ex){
            
            modelo.put("error", ex.getMessage());
            return "index.html";
        }
        
    }
   

}
