package microservicioMisionTIC.seguridad.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Document
public class PermisosRol {
    @Id
    private String _id;
    @DBRef
    private Rol rol;
    @DBRef
    private Permiso permiso;

    public PermisosRol(){}

    public String get_id() {
        return _id;
    }

    public Rol getRol() {
        return rol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}
