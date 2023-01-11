package microservicioMisionTIC.seguridad.Repositorios;
import microservicioMisionTIC.seguridad.Modelos.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepositorioPermiso extends MongoRepository<Permiso,String> {
    @Query("{'url': ?0, 'metodo':?1}") // SELECT * FROM PERMISO WHERE URL = ? AND METODO =?
    Permiso getPermiso(String url, String metodo);
}
