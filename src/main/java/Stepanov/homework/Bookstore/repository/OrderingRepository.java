package Stepanov.homework.Bookstore.repository;

import Stepanov.homework.Bookstore.entity.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {

    @Query(value = "from Ordering o inner join fetch o.orderingDetailsList where o.id = :id")
    Optional<Ordering> findById(Long id);

}
