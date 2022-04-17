package Stepanov.homework.Bookstore.repository;

import Stepanov.homework.Bookstore.entity.OrderingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderingDetailsRepository extends JpaRepository<OrderingDetails, Long> {

}
