package Stepanov.homework.Bookstore.repository;

import Stepanov.homework.Bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "from Book b inner join b.author where b.author.id = :author_id")
    List<Book> findAllByAuthor_Id(Long author_id);

    @Query(value = "from Book b inner join b.author " +
            "where b.author.surname = :surname and b.author.name = :name and b.author.middle_name = :middle_name")
    List<Book> findAllByAuthor_SurnameAndAuthor_NameAndAuthor_Middle_name(String surname, String name, String middle_name);

    @Query(value = "from Book b join fetch b.author where b.id = :id")
    Book findBooksById(Long id);
}
