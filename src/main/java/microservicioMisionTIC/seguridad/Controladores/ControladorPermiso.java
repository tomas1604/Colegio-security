package microservicioMisionTIC.seguridad.Controladores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import microservicioMisionTIC.seguridad.Modelos.Permiso;
import microservicioMisionTIC.seguridad.Repositorios.RepositorioPermiso;

@CrossOrigin
@RestController
@RequestMapping("/permisos")

public class ControladorPermiso {
    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @GetMapping("")
    public List<Permiso> mostrarPermisos(){
        return this.miRepositorioPermiso.findAll();
    }

    @GetMapping("{id}")
    public Permiso mostrarPermiso(@PathVariable String id){
        Permiso permiso1 = this.miRepositorioPermiso.findById(id).orElse(null);
        return permiso1;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permiso crear(@RequestBody Permiso infoPermiso){
        return this.miRepositorioPermiso.save(infoPermiso);
    }

    @PutMapping("{id}")
    public Permiso actualizar(@RequestBody Permiso infoPermiso, @PathVariable String id){
        Permiso permiso1 = this.miRepositorioPermiso.findById(id).orElse(null);
        if(permiso1 != null){
            permiso1.setMetodo(infoPermiso.getMetodo());
            permiso1.setUrl(infoPermiso.getUrl());
            return this.miRepositorioPermiso.save(permiso1);
        }
        else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void eliminar(@PathVariable String id){
        this.miRepositorioPermiso.deleteById(id);
    }

}
