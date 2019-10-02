package samuelmovi.familyLibraryJava.repo;

import samuelmovi.familyLibraryJava.model.BookView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookViewRepository extends CrudRepository<BookView, Long>{

}
