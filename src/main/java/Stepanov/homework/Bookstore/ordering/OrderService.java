package Stepanov.homework.Bookstore.ordering;

import Stepanov.homework.Bookstore.bookWarehouse.BookWarehouseService;
import Stepanov.homework.Bookstore.buyer.BuyerService;
import Stepanov.homework.Bookstore.entity.BookWarehouse;
import Stepanov.homework.Bookstore.entity.Ordering;
import Stepanov.homework.Bookstore.entity.OrderingDetails;
import Stepanov.homework.Bookstore.ordering_details.BookAndQuanity;
import Stepanov.homework.Bookstore.ordering_details.OrderingDetailsService;
import Stepanov.homework.Bookstore.repository.OrderingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    private final OrderingRepository orderingRepository;

    public OrderService(OrderingRepository orderingRepository) {
        this.orderingRepository = orderingRepository;
    }

    public Ordering createOrdering(Ordering ordering) {
        return orderingRepository.save(ordering);
    }

    public List<Ordering> getOrderings() {
        return orderingRepository.findAll();
    }

    public Optional<Ordering> getOrderingById(Long id) {
        return orderingRepository.findById(id);
    }

    public void deleteOrder(Ordering ordering) {
        orderingRepository.delete(ordering);
    }

    @Transactional
    public void update(Ordering ordering) {
        orderingRepository.save(ordering);
    }

    //СОЗДАНИЕ ОРДЕРА НА ПОКУПКУ
    @Transactional
    public void createPurchase(BuyerService buyerService,
                               BookWarehouseService bookWarehouseService,
                               OrderService orderService,
                               OrderingDetailsService orderingDetailsService, Long bayer_id, List<BookAndQuanity> bookAndQuanityList) {

        Ordering ordering = orderService.createOrdering(new Ordering(buyerService.getBuyerById(bayer_id), 0, null));
        List<OrderingDetails> orderingDetailsList = new ArrayList<>();

        //Составляем List<OrderingDetails> orderingDetailsList при balance > 0
        for (BookAndQuanity bookAndQuanity : bookAndQuanityList) {
            Long book_id = bookAndQuanity.getBook().getId();
            int quantity = bookAndQuanity.getQuantity();
            int balance = bookWarehouseService.getBookById(book_id).getBalance();
            if (balance > 0 && quantity <= balance) {
                OrderingDetails orderingDetails = new OrderingDetails(ordering, bookAndQuanity.getBook(), bookAndQuanity.getQuantity());
                orderingDetailsList.add(orderingDetails);
                orderingDetailsService.createOrderingDetails(orderingDetails);
            } else {
                throw new RuntimeException("На складе осталось " + balance + " книг с id " + book_id + "!");
            }
        }

        int purchase_amount = 0;

        for (OrderingDetails orderDetails : orderingDetailsList) {
            Long book_id = orderDetails.getBook().getId();
            int quantity = orderDetails.getQuantity();

            BookWarehouse bookWarehouse = bookWarehouseService.getBookById(book_id);
            bookWarehouse.setBalance(bookWarehouse.getBalance() - quantity);

            try {
                bookWarehouseService.update(bookWarehouse);
                int summ = orderDetails.getBook().getPrice() * quantity;
                purchase_amount += summ;

            } catch (RuntimeException e) {
                log.warn("Optimistic lock exception for {}", bayer_id);
            }
        }

        ordering.setPurchase_amount(purchase_amount);
        orderService.update(ordering);

    }
}

