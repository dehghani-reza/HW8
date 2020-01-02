package ir.mctab.java32.hw8.repositories;

import ir.mctab.java32.hw8.entities.Article;
import ir.mctab.java32.hw8.entities.Tag;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Queue;
import java.util.Set;

public class TagDAO {
    Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public Tag createTeg(String name) {
        Tag tag = new Tag();
        tag.setTagName(name);
        session.save(tag);
        return tag;
    }

    public List<Tag> loadAllTas(){
        Query<Tag> query1 = session.createQuery("From Tag ");
        List<Tag> tags = query1.list();
        return tags;
    }

    public Tag findById(Long id){
        Tag tag = session.load(Tag.class,id);
        return tag;
    }

    public Tag costumeTag(String name , Tag tag){
        tag.setTagName(name);
        session.update(tag);
        return tag;
    }
   /* public List<Article> loadArticles(Tag tag){
        Query<Article> query1 = session.createQuery("From Article where Tag ="+tag);
        List<Article> articles = query1.list();
        return articles;
    }*/
}
