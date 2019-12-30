package ir.mctab.java32.hw8;

import ir.mctab.java32.hw8.config.hibernate.HibernateUtil;
import ir.mctab.java32.hw8.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ArticleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);


        SessionFactory sessionFactory = HibernateUtil.getSession();
        Session session = sessionFactory.openSession();

        User user = null;
        Command command = new Command();
        int inputCommand = 0;

        System.out.println("git address: https://github.com/dehghani-reza/HW8");
        do {
            session.beginTransaction();
            command.setUser(user);
            command.commandList(user);
            try {
                inputCommand = scannerInt.nextInt();
                command.commandCheck(inputCommand);
            }catch (InputMismatchException e){
                System.out.println("wrong type of command");
                scannerInt = new Scanner(System.in);
                session.getTransaction().rollback();
                continue;
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
                session.getTransaction().rollback();
                continue;
            }


        switch (inputCommand) {
            case 1:
                System.out.println("Enter your username: ");
                String username = scanner.nextLine();
                System.out.println("Enter your password");
                String password = scanner.nextLine();
                Query<User> query = session.createQuery("FROM User where userName = " + username + " and password = " + password);
                user = query.uniqueResult();
                if(user==null){
                    System.out.println(Color.ANSI_PURPLE+"Wrong Username or Password"+Color.ANSI_GREEN+"\ntry again...."+Color.ANSI_RESET);
                    session.getTransaction().rollback();
                    continue;
                }
                System.out.println("Hello " + user.getUserName());
                break;
            case 2:
                System.out.println("Enter your username: ");
                String username1 = scanner.nextLine();
                System.out.println("Enter your birthday: ");
                String birthday = scanner.nextLine();
                System.out.println("Enter your nationalCode: ");
                Long nationalId = scannerInt.nextLong();
                user = new User(username1, nationalId, birthday);
                Long id = (Long) session.save(user);
                System.out.println("user " + user.getUserName() + " with id " + id + " has been created");
                break;
            case 3:
                Query<Article> query1 = session.createQuery("From Article");
                List<Article> articles = query1.list();
                articles.forEach(System.out::println);
                //toDo

                break;
            case 4:
                System.out.println("enter your new password: ");
                String pass = scanner.nextLine();
                user = session.load(User.class, user.getId());
                user.setPassword(pass);
                System.out.println("your password changed");
                break;
            case 5:
                Query<Article> query2 = session.createQuery("From Article where user = " + user);
                List<Article> articles1 = query2.list();
                articles1.forEach(System.out::println);
                break;
            case 6:
                System.out.println("Insert your article id: ");
                Long articleId = scannerInt.nextLong();
                Article article = session.load(Article.class, articleId);
                System.out.println("");
                System.out.println("Insert your new data in this order: [brief,content,title]");
                String costume = scanner.nextLine();
                String[] costumeArray = costume.split(",");
                article.setBrief(costumeArray[0]);
                article.setContent(costumeArray[1]);
                article.setTitle(costumeArray[2]);
                session.update(article);
                break;
            case 7:
                String createDate = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
                System.out.println("please enter brief of your article: ");
                String brief = scanner.nextLine();
                System.out.println("please enter content of your article: ");
                String content = scanner.nextLine();
                boolean isPublish = false;
                System.out.println("please enter title of your article: ");
                String title = scanner.nextLine();
                System.out.println("here is list of category: ");
                Query<Category> query3 = session.createQuery("From Category ");
                List<Category> categories = query3.list();
                categories.forEach(System.out::println);
                System.out.println("for choosing press 1 and for adding category press 0");
                int categoryChoose = scannerInt.nextInt();
                Category category = null;
                if (categoryChoose == 1) {
                    System.out.println("Enter category id: ");
                    Long categoryId = scannerInt.nextLong();
                    Query<Category> query4 = session.createQuery("FROM Category where id = " + categoryId);
                    category = query4.uniqueResult();
                }
                if (categoryChoose == 0) {
                    System.out.println("Enter Title of this category");
                    String title1 = scanner.nextLine();
                    System.out.println("Enter description of your category");
                    String description = scanner.nextLine();
                    category = new Category();
                    category.setTitle(title1);
                    category.setDescription(description);
                    session.save(category);
                }
                session.save(new Article(title, brief, content, createDate, isPublish, user, category));
                break;
            case 8:
                Query<Article> query5 = session.createQuery("From Article where isPublish = false and user =" + user);
                List<Article> articles2 = query5.list();
                articles2.forEach(System.out::println);
                System.out.println("choose article which you want to publish ");
                Long publishId = scannerInt.nextLong();
                Article article1 = session.load(Article.class, publishId);
                article1.setPublish(true);
                session.update(article1);


        }
        session.getTransaction().commit();
    } while(inputCommand !=9);

        System.out.println("have nice day .....");


}//end of method main

}
