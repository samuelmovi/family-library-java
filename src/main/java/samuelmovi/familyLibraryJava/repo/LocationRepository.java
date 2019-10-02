package samuelmovi.familyLibraryJava.repo;

import samuelmovi.familyLibraryJava.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long>{


}
