
package egg.biblioteca2.servicios;

import egg.biblioteca2.entidades.Usuario;
import egg.biblioteca2.enumeraciones.Rol;
import egg.biblioteca2.excepciones.MiException;
import egg.biblioteca2.repositorio.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioService implements UserDetailsService{
    
    @Autowired
    UsuarioRepositorio userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Usuario usuario = userRepo.buscarxEmail(email);
        
        if(usuario != null){
            
         List<GrantedAuthority> permisos = new ArrayList();
         
         GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol());
         
         permisos.add(p);
         
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", usuario);
            
         return new User(usuario.getEmail(), usuario.getPassword(),permisos);   
            
        }else{
        
        return null;
        }
        
        
        
    }
    
@Transactional
public void registrar (String nombre, String email, String password, String password2) throws MiException{
    validar(nombre, email, password, password2);
    Usuario usuario = new Usuario();
    usuario. setNombre(nombre);
    usuario. setEmail(email);
    
    usuario. setPassword(new BCryptPasswordEncoder().encode(password));
    usuario. setRol(Rol. USER);
    userRepo.save(usuario);
  
}

private void validar (String nombre, String email, String password, String password2) throws MiException{
    
    if(nombre.isEmpty() || nombre == null)
         throw new MiException ("el nombre no puede ser nulo O estar vacío");
     
     if(email.isEmpty() || email == null)
         throw new MiException("e1 email no puede ser nulo O estar vacio");
                                                                                
     
     if(password.isEmpty() || password == null || password.length () <= 5) 
         throw new MiException ("La contraseña no puede estar vacía, y debe teher más de 5 dígitos");
     if (!password.equals (password2) ) 
         throw new MiException ("Las contraseñas ingresadas deben ser iguales");

}
    
    
}
