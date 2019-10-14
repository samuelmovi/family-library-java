package samuelmovi.familyLibraryJava.repo;

import samuelmovi.familyLibraryJava.model.BookView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookViewRepository extends CrudRepository<BookView, Long>{

    List<BookView> findAll();

}
