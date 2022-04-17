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
public class Author implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String surname;

    @Column
    private String name;

    @Column
    private String middle_name;

    public Author(String surname, String name, String middle_name) {
        this.surname = surname;
        this.name = name;
        this.middle_name = middle_name;
    }
}
