package ir.mctab.java32.hw8.repositories;

import ir.mctab.java32.hw8.entities.Article;
import ir.mctab.java32.hw8.entities.Category;
import ir.mctab.java32.hw8.entities.Tag;
import ir.mctab.java32.hw8.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    Session session;


    public void setSession(Session session) {
        this.session = session;
    }

    public List showAllArticle(){
        Query<Article> query1 = session.createQuery("From Article");
        List<Article> articles = query1.list();
        return articles;
    }


    public Article loadArticle(Long id){
        Article article = session.load(Article.class, id);
        System.out.println(article);
        return article;
    }

    public Article costumeArticle(String [] costumeArray , Article article){
        article.setBrief(costumeArray[0]);
        article.setContent(costumeArray[1]);
        article.setTitle(costumeArray[2]);
        article.setLastUpdateDate(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        session.update(article);
        return article;
    }

    public Article saveArticle(String title , String brief , String content , boolean isPublish , User user , Category category){
        String createDate = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
        Article article = new Article(title, brief, content, createDate, isPublish, user, category);
        session.save(article);
        return article;
    }

    public void publishArticle(User user , Long publishId) throws Exception{
        Article article1 = session.load(Article.class, publishId);
        if(!article1.getUser().getId().equals(user.getId())) {
            throw new Exception("you cant publish this article because its not yours");
        }
        article1.setPublish(true);
        article1.setPublishDate(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        session.update(article1);
    }

    public List<Article> loadPublishArticle(User user){
        Query<Article> query5 = session.createQuery("From Article where isPublish = false and user_id=" + user.getId());
        List<Article> articles2 = query5.list();
        return articles2;
    }

    public void addTag (Tag tag , Article article){
        article.getTagSet().add(tag);
        session.update(article);
    }
}
