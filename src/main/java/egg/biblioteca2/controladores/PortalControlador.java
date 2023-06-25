package egg.biblioteca2.controladores;

import egg.biblioteca2.entidades.Usuario;
import egg.biblioteca2.excepciones.MiException;
import egg.biblioteca2.servicios.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")

public class PortalControlador {

    @Autowired
    private UsuarioService us;

    @GetMapping("/")
    public String index() {

        return "index.html";

    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            us.registrar(nombre, email, password, password2);

            modelo.put("exito", "Usuario registrado correctamente");

            return "index.html";

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registro.html";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario O Contraseña invalidos!");
        }

        return "login.html";

    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping ("/inicio")
    public String inicio (HttpSession session){
        
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        if(logueado.getRol().toString().equals("ADMIN")){
            return "redirect:/admin/dashboard";
            
        }       
        
     return "inicio.html";
    }


}
