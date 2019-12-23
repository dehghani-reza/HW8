package ir.mctab.java32.hw8.entities;

import javax.persistence.*;

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
//****************************************************************

    public Article(String title, String brief, String content, String createDate, String lastUpdateDate, String publishDate, boolean isPublish) {
        this.title = title;
        this.brief = brief;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.publishDate = publishDate;
        this.isPublish = isPublish;
    }

    public Article() {

    }
//***************************************************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isPublish() {
        return isPublish;
    }

    public void setPublish(boolean publish) {
        isPublish = publish;
    }
}



