package Stepanov.homework.Bookstore.bookWarehouse;

import Stepanov.homework.Bookstore.entity.BookWarehouse;
import Stepanov.homework.Bookstore.repository.BookWarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.LockModeType;
import java.util.List;

@Slf4j
@Service
public class BookWarehouseService {

    private SessionFactory sessionFactory;

    private final BookWarehouseRepository bookWarehouseRepository;

    public BookWarehouseService(BookWarehouseRepository bookWarehouseRepository) {
        this.bookWarehouseRepository = bookWarehouseRepository;
    }

    public BookWarehouse createBookWarehouse(BookWarehouse bookWarehouse) {
        return bookWarehouseRepository.save(bookWarehouse);
    }

    public List<BookWarehouse> getBooks() {
        return bookWarehouseRepository.findAll();
    }

    //  @Cacheable(value = "bookWarehouse")
    public BookWarehouse getBookById(Long id) {
        return bookWarehouseRepository.findByBook_Id(id);
    }

    @Transactional
    //    @CacheEvict(value = "bookWarehouse", key = "#bookWarehouse.id")     //чистится кеш по ключу обьекта
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public void update(BookWarehouse bookWarehouse) {
        bookWarehouseRepository.save(bookWarehouse);
    }
}
