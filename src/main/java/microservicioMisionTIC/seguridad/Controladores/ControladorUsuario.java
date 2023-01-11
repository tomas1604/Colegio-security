package microservicioMisionTIC.seguridad.Controladores;
import microservicioMisionTIC.seguridad.Modelos.Usuario;
import microservicioMisionTIC.seguridad.Repositorios.RepositorioUsuario;
import microservicioMisionTIC.seguridad.Modelos.Rol;
import microservicioMisionTIC.seguridad.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")

public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<Usuario> mostrarUsuarios(){
        return this.miRepositorioUsuario.findAll();
    }

    @GetMapping("{id}")
    public Usuario mostrarUsuario(@PathVariable String id){
        Usuario usuario1 = this.miRepositorioUsuario.findById(id).orElse(null);
        return usuario1;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario crear(@RequestBody Usuario infoUsuario){
        String contrasena = infoUsuario.getContrasena();
        infoUsuario.setContrasena(convertirSHA256(contrasena));
        return this.miRepositorioUsuario.save(infoUsuario);
    }

    @PutMapping("{id}")
    public Usuario actualizar(@PathVariable String id, @RequestBody Usuario infoUsuario){
        Usuario usuario1 = this.miRepositorioUsuario.findById(id).orElse(null);
        if(usuario1 != null){
            if(infoUsuario.getContrasena()!= ""){
                usuario1.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
            }
            usuario1.setSeudonimo(infoUsuario.getSeudonimo());
            usuario1.setCorreo(infoUsuario.getCorreo());
            return this.miRepositorioUsuario.save(usuario1);
        }
        else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void eliminar(@PathVariable String id){
        this.miRepositorioUsuario.deleteById(id);
    }

    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRol(@PathVariable String id, @PathVariable String id_rol){
        Usuario usuario1 = this.miRepositorioUsuario.findById(id).orElse(null);
        Rol rol1 = this.miRepositorioRol.findById(id_rol).orElse(null);
        if (usuario1 != null && rol1 != null){
            usuario1.setRol(rol1);
            return this.miRepositorioUsuario.save(usuario1);
        }
        else{
            return null;
        }
    }

    @PostMapping("/login")
    public Usuario  autenticar(@RequestBody Usuario infoUsuario, final HttpServletResponse response)throws IOException {
        Usuario usuario1 = this.miRepositorioUsuario.getUserByEmail(infoUsuario.getCorreo());
        String contrasena1 = convertirSHA256(infoUsuario.getContrasena());
        if(usuario1 != null && usuario1.getContrasena().equals(contrasena1)){
            usuario1.setContrasena("");
            return usuario1;
        }
        else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
