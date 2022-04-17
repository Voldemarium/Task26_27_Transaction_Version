package Stepanov.homework.Bookstore.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Ordering {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Buyer buyer;

    @Column
    private Integer purchase_amount;

    @OneToMany(mappedBy = "ordering", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.JOIN)
    private List<OrderingDetails> orderingDetailsList;

    public Ordering(Buyer buyer, Integer purchase_amount, List<OrderingDetails> orderingDetailsList) {
        this.buyer = buyer;
        this.purchase_amount = purchase_amount;
        this.orderingDetailsList = orderingDetailsList;

    }
}
