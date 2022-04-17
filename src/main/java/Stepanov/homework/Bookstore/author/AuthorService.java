package Stepanov.homework.Bookstore.author;

import Stepanov.homework.Bookstore.entity.Author;
import Stepanov.homework.Bookstore.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.getById(id) ;
    }
}
