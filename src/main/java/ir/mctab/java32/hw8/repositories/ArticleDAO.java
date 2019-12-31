package ir.mctab.java32.hw8.repositories;

import ir.mctab.java32.hw8.entities.Article;
import ir.mctab.java32.hw8.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.lang.reflect.Array;
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
        session.update(article);
        return article;
    }
}
