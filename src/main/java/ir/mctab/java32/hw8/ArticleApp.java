package ir.mctab.java32.hw8;

import ir.mctab.java32.hw8.config.hibernate.HibernateUtil;
import ir.mctab.java32.hw8.entities.Article;
import ir.mctab.java32.hw8.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class ArticleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);


        SessionFactory sessionFactory = HibernateUtil.getSession();
        Session session = sessionFactory.openSession();

        User user = null;
        int command;

        do {
            ArticleApp.commands(user);
            command = scannerInt.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Enter your username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter your password");
                    String password = scanner.nextLine();
                    Query<User> query = session.createQuery("FROM User where userName = " + username + " and password = " + password);
                    user = query.uniqueResult();
                    System.out.println("Hello "+user.getUserName());
                    break;
                case 2:
                    session.beginTransaction();
                    System.out.println("Enter your username: ");
                    String username1 = scanner.nextLine();
                    System.out.println("Enter your birthday: ");
                    String birthday = scanner.nextLine();
                    System.out.println("Enter your nationalCode: ");
                    Long nationalId = scannerInt.nextLong();
                    user = new User(username1, nationalId, birthday);
                    Long id = (Long) session.save(user);
                    session.getTransaction().commit();
                    System.out.println("user "+user.getUserName() +" with id "+id+" has been created");
                    break;
                case 3:
                    Query<Article> query1 = session.createQuery("From Article ");
                    List<Article> articles = query1.list();
                    articles.forEach(System.out::println);
                    break;

            }

        } while (command != 9);

        System.out.println("have nice day .....");

    }//end of method main

    private static void commands(User user) {
        if (user == null) {
            System.out.println("SignIn: 1 | signUp: 2 | showAllArticle: 3 | exit: 9");
        }else {
            System.out.println("changing pass: 4 | see all of your article: 5 | costume article: 6 | new article: 7 | publish article: 8 | exit: 9");
        }
    }
}
