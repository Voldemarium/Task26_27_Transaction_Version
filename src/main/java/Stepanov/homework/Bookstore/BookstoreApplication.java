package Stepanov.homework.Bookstore;

import Stepanov.homework.Bookstore.author.AuthorService;
import Stepanov.homework.Bookstore.book.BookService;
import Stepanov.homework.Bookstore.bookWarehouse.BookWarehouseService;
import Stepanov.homework.Bookstore.buyer.BuyerService;
import Stepanov.homework.Bookstore.entity.*;
import Stepanov.homework.Bookstore.ordering.OrderService;
import Stepanov.homework.Bookstore.ordering_details.BookAndQuanity;
import Stepanov.homework.Bookstore.ordering_details.OrderingDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleStateException;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableCaching
public class BookstoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BookstoreApplication.class, args);

        AuthorService authorService = context.getBean(AuthorService.class);
        BookService bookService = context.getBean(BookService.class);
        BookWarehouseService bookWarehouseService = context.getBean(BookWarehouseService.class);
        BuyerService buyerService = context.getBean(BuyerService.class);
        OrderService orderService = context.getBean(OrderService.class);
        OrderingDetailsService orderingDetailsService = context.getBean(OrderingDetailsService.class);

        //   authorService.createAuthor(new Author("Petrov", "Ivan", "Ivanovich")); //"Ivanov", "Ivan", "Ivanovich"
        //   bookService.createBook(new Book("Spring", authorService.getAuthorById(3L), 2005, 240, 700));
        //  bookWarehouseService.createBookWarehouse(new BookWarehouse(bookService.getBookById(17L), 4));
        //        buyerService.createBuyer(new Buyer("Ivanov", "Vladimir", "Ivanovich",
        //                LocalDate.of(1993, 5, 5)));

        log.info("Search result books with the author_id 3: {}", bookService.getBooksByAuthorID(3L));

        log.info("Search result books with the author Kozlov Vlad Ivanovich: {}",
                bookService.getBooksByAuthor_SurnameAndAuthor_NameAndAuthor_Middle_name(
                        "Kozlov", "Vlad", "Ivanovich"
                )
        );

        for (int i = 0; i < 3; i++) {
            Runnable task = () -> {
                try {
                    orderService.createPurchase(buyerService, bookWarehouseService, orderService, orderingDetailsService,
                            7L,
                            List.of(
                                    new BookAndQuanity(bookService.getBookById(21L), 1),
                                    new BookAndQuanity(bookService.getBookById(4L), 1)
                            )
                    );
                } catch (StaleStateException | ObjectOptimisticLockingFailureException e) {
                    log.warn("The book was bought by another buyer");
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
