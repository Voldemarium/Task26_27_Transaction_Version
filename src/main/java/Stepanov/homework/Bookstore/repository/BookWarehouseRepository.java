package Stepanov.homework.Bookstore.repository;

import Stepanov.homework.Bookstore.entity.BookWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

@Repository
public interface BookWarehouseRepository extends JpaRepository<BookWarehouse, Long> {

    @Query(value = "from BookWarehouse b inner join b.book where b.book.id = :book_id")
    BookWarehouse findByBook_Id(Long book_id);

    @Modifying
    @Query(value = "update BookWarehouse b set b.balance = :balance, b.version = b.version + 1 " +
            "where b.book.id = :book_id and b.version = :version")
    void saveBookWarehouse(@Param("balance") int balance, @Param("book_id")Long book_id, @Param("version") Long version);
}
