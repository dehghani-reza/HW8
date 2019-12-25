package ir.mctab.java32.hw8.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 22)
    private String title;

    @Column(name = "description", nullable = false, length = 88)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<Article> articles = new ArrayList<>();

}
