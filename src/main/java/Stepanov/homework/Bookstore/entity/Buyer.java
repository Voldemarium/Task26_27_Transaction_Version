package Stepanov.homework.Bookstore.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Buyer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String surname;

    @Column
    private String name;

    @Column
    private String middle_name;

    @Column
    private LocalDate birth_date;

    public Buyer(String surname, String name, String middle_name, LocalDate birth_date) {
        this.surname = surname;
        this.name = name;
        this.middle_name = middle_name;
        this.birth_date = birth_date;
    }
}
