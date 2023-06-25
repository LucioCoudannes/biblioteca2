
package egg.biblioteca2.controladores;


import egg.biblioteca2.entidades.Editorial;
import egg.biblioteca2.excepciones.MiException;
import egg.biblioteca2.servicios.EditorialService;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialService es;
    
    @GetMapping("/registrar")
    public String registrar(){
        
        return "editorial_form.html";
        
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre ){
        
        try {
            es.crearEditorial(nombre);
        } catch (MiException ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "editorial_form.html";
        }
        return "editorial_form.html";
    }
    
    @GetMapping("/editoriales")
    public String listar(ModelMap modelo){
        
        List <Editorial> editoriales = es.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        
        return "editorial_list.html";
        
    }
    
      @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
       modelo.put("editorial", es.getOne(id));
       
       return "editorial_modificar.html";
        
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        
        try {
            es.modificarEditorial(nombre, id);
            
            return "redirect:../editoriales";
        } catch (MiException ex){
            
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
   
    }
    
    
}
