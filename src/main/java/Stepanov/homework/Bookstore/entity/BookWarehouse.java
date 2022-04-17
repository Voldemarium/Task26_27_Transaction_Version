package Stepanov.homework.Bookstore.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BookWarehouse implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Book book;

    @Column
    private Integer balance;

    @Version
    private Long version;

    public BookWarehouse(Book book, Integer balance) {
        this.book = book;
        this.balance = balance;
    }
}
