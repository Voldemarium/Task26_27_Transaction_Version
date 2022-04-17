package Stepanov.homework.Bookstore.book;

import Stepanov.homework.Bookstore.entity.Book;
import Stepanov.homework.Bookstore.repository.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "book")
    public Book getBookById(Long id) {
        return bookRepository.findBooksById(id) ;
    }

    @Cacheable(value = "book")
    public List<Book> getBooksByAuthorID(Long id) {
        return bookRepository.findAllByAuthor_Id(id);
    }

    @Cacheable(value = "book")
    public List<Book> getBooksByAuthor_SurnameAndAuthor_NameAndAuthor_Middle_name(String surname, String name, String middle_name) {
        return bookRepository.findAllByAuthor_SurnameAndAuthor_NameAndAuthor_Middle_name(surname, name, middle_name);
    }

    @Transactional
    @CacheEvict(value = "book", key = "#book.id")     //чистится кеш по ключу обьекта
    public void update(Book book) {
        bookRepository.save(book);
    }
}


