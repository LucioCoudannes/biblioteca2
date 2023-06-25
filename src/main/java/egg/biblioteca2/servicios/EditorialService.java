
package egg.biblioteca2.servicios;

import egg.biblioteca2.entidades.Editorial;
import egg.biblioteca2.excepciones.MiException;
import egg.biblioteca2.repositorio.EditorialRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialService {
    
    @Autowired
    EditorialRepositorio EditorialRepo;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
          if(nombre==null){
            
            throw new MiException("El campo nombre no puede estar vacio");
        }
        
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        
        EditorialRepo.save(editorial);
        
    }
    
    public List<Editorial> listarEditoriales(){
        
        List<Editorial> listaE = new ArrayList();
        
        listaE = EditorialRepo.findAll();
        
        return listaE;
        
        
    }
    
    public Editorial getOne(String id){
        
        
        return EditorialRepo.getOne(id);
        
    }
    
     public void modificarEditorial(String nombre, String id)throws MiException{
         
         if(nombre==null){
            
            throw new MiException("El campo nombre no puede estar vacio");
        }
         
         if(id.isEmpty() || id==null){
             
             throw new MiException("El campo ID esta vacio");
         }
        
        Optional<Editorial> respuestaE = EditorialRepo.findById(id);
        
        if(respuestaE.isPresent()){
            
            Editorial editorial = respuestaE.get();
            
            editorial.setNombre(nombre);
            
            EditorialRepo.save(editorial);
            
        }
     }
    
}
