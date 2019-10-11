package samuelmovi.familyLibraryJava.repo;

import samuelmovi.familyLibraryJava.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long>{

    List<Location> findAll();
    Location findByAddress(String address);
}
