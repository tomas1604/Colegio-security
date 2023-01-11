package microservicioMisionTIC.seguridad.Controladores;
import microservicioMisionTIC.seguridad.Modelos.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import microservicioMisionTIC.seguridad.Modelos.Rol;
import microservicioMisionTIC.seguridad.Repositorios.RepositorioRol;

@CrossOrigin
@RestController
@RequestMapping("/roles")


public class ControladorRol {
    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<Rol> mostrarRoles(){
        return this.miRepositorioRol.findAll();
    }

    @GetMapping("{id}")
    public Rol mostrarRol(@PathVariable String id){
        Rol rol1 = this.miRepositorioRol.findById(id).orElse(null);
        return rol1;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Rol crear(@RequestBody Rol infoRol){
        return this.miRepositorioRol.save(infoRol);
    }
    @PutMapping("{id}")
    public Rol actualizar(@RequestBody Rol infoRol, @PathVariable String id){
        Rol rol1 = this.miRepositorioRol.findById(id).orElse(null);
        if(rol1 != null){
            rol1.setDescripcion(infoRol.getDescripcion());
            rol1.setNombre(infoRol.getNombre());
            return this.miRepositorioRol.save(rol1);
        }
        else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void eliminar(@PathVariable String id){
        this.miRepositorioRol.deleteById(id);
    }
}
