package ir.mctab.java32.hw8.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Article")

public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 22)
    private String title;

    @Column(name = "brief", nullable = false, length = 80)
    private String brief;

    @Column(name = "content", nullable = false, length = 22)
    private String content;

    @Column(name = "createDate", nullable = false, length = 22)
    private String createDate;

    @Column(name = "lastUpdate", nullable = false, length = 22)
    private String lastUpdateDate;

    @Column(name = "publishDate", nullable = false, length = 22)
    private String publishDate;

    @Column(name = "isPublish", nullable = false)
    private boolean isPublish;

}



