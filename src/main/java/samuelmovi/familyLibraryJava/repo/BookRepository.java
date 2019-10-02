package samuelmovi.familyLibraryJava.repo;

import java.util.List;

import samuelmovi.familyLibraryJava.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>{

    List<Book> findByLoaned(boolean loaned);
    //List<Book> findByField(String field, String value);

}
