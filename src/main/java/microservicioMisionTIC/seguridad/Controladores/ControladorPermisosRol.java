package microservicioMisionTIC.seguridad.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import microservicioMisionTIC.seguridad.Modelos.Rol;
import microservicioMisionTIC.seguridad.Modelos.Permiso;
import microservicioMisionTIC.seguridad.Modelos.PermisosRol;
import microservicioMisionTIC.seguridad.Repositorios.RepositorioPermiso;
import microservicioMisionTIC.seguridad.Repositorios.RepositorioRol;
import microservicioMisionTIC.seguridad.Repositorios.RepositorioPermisosRol;

@CrossOrigin
@RestController
@RequestMapping("/permisos-rol")

public class ControladorPermisosRol {
    @Autowired
    private RepositorioPermisosRol miRepositorioPermisoRol;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @GetMapping("")
    public List<PermisosRol> mostrarPermisosRoles(){
        return this.miRepositorioPermisoRol.findAll();
    }

    @GetMapping("{id}")
    public PermisosRol mostrarPermisosRol(@PathVariable String id){
        PermisosRol permisosRol1 = this.miRepositorioPermisoRol.findById(id).orElse(null);
        return permisosRol1;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRol crear(@PathVariable String id_rol, @PathVariable String id_permiso){
        PermisosRol permisosRol1 = new PermisosRol();
        Rol rol1 = this.miRepositorioRol.findById(id_rol).orElse(null);
        Permiso permiso1 = this.miRepositorioPermiso.findById(id_permiso).orElse(null);
        if (rol1 != null && permiso1 != null){
            permisosRol1.setPermiso(permiso1);
            permisosRol1.setRol(rol1);
            return this.miRepositorioPermisoRol.save(permisosRol1);
        }else{
            return null;
        }
    }

    @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRol actualizar(@PathVariable String id, @PathVariable String id_rol, @PathVariable String id_permiso){
        PermisosRol permisosRol1 = this.miRepositorioPermisoRol.findById(id).orElse(null);
        Rol rol1 = this.miRepositorioRol.findById(id_rol).orElse(null);
        Permiso permiso1 = this.miRepositorioPermiso.findById(id_permiso).orElse(null);

        if( permisosRol1 != null && rol1 != null & permiso1 != null){
            permisosRol1.setRol(rol1);
            permisosRol1.setPermiso(permiso1);
            return this.miRepositorioPermisoRol.save(permisosRol1);
        }
        else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void eliminar (@PathVariable String id){
        this.miRepositorioPermisoRol.deleteById(id);
    }

    @GetMapping("validar-permiso/rol/{id_rol}")
    public PermisosRol getPermiso(@PathVariable String id_rol, @RequestBody Permiso infoPermiso){
        Permiso permiso1 = this.miRepositorioPermiso.getPermiso(infoPermiso.getUrl(), infoPermiso.getMetodo());
        Rol rol1 = this.miRepositorioRol.findById(id_rol).orElse(null);
        if (permiso1 != null && rol1!= null){
            return this.miRepositorioPermisoRol.getPermisoRol(rol1.get_id(), permiso1.get_id());
        }else{
            return null;
        }
    }
}
