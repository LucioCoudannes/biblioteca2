package egg.biblioteca2.servicios;

import egg.biblioteca2.entidades.Autor;
import egg.biblioteca2.excepciones.MiException;
import egg.biblioteca2.repositorio.AutorRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    AutorRepositorio AutorRepo;

    @Transactional
    public void crearAutor(String nombre) throws MiException {
        
        if(nombre==null){
            
            throw new MiException("El campo nombre no puede estar vacio");
        }

        Autor autor = new Autor();

        autor.setNombre(nombre);

        AutorRepo.save(autor);

    }

    public List<Autor> listaAutores() {

        List<Autor> listaA = new ArrayList();

        listaA = AutorRepo.findAll();

        return listaA;

    }
    
    public Autor getOne(String id){
        
        
        return AutorRepo.getOne(id);
        
    }
    
    
    public void modificarAutor(String nombre, String id) throws MiException{
        
        if(id.isEmpty() || id==null){
             
             throw new MiException("El campo ID esta vacio");
         }
        
        Optional<Autor> respuestaA = AutorRepo.findById(id);
        
        if(respuestaA.isPresent()){
            
            Autor autor = respuestaA.get();
            
            autor.setNombre(nombre);
            
            AutorRepo.save(autor);
            
        }
        
        
    }
    
}
