package ir.mctab.java32.hw8.repositories;

import ir.mctab.java32.hw8.entities.Article;
import ir.mctab.java32.hw8.view.Color;
import ir.mctab.java32.hw8.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO {
    Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public User loginUser(String username, String password, User user){
        Query<User> query = session.createQuery("FROM User where userName = '" + username + "' and password = '" + password+"'");
        user = query.uniqueResult();
        return user;
    }

    public User signup(String username, Long nationalId, String birthday) throws Exception{
        Query<User> query = session.createQuery("From User ");
        List<User> users = query.list();
        if(users.stream().anyMatch(p-> p.getUserName().equals(username))){
            throw new Exception(Color.ANSI_RED+"this user name is reserved"+Color.ANSI_RESET);
        }
        User user = new User(username, nationalId, birthday);
        session.save(user);
        return user;
    }

    public void changiPassword(User user , String pass){
        user = session.load(User.class, user.getId());
        user.setPassword(pass);
    }

    public List userArticle(User user) throws Exception{
        Query<Article> query2 = session.createQuery("From Article where user_id = " + user.getId());
        List<Article> articles1 = query2.list();
        if(articles1.isEmpty()){
            throw new Exception("you have no article");
        }
        return articles1;
    }
}
