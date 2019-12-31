package ir.mctab.java32.hw8;

import ir.mctab.java32.hw8.config.hibernate.HibernateUtil;
import ir.mctab.java32.hw8.entities.*;
import ir.mctab.java32.hw8.view.Color;
import ir.mctab.java32.hw8.view.Command;
import ir.mctab.java32.hw8.view.Remote;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        Remote remote = new Remote(session);

        System.out.println("git address: https://github.com/dehghani-reza/HW8");
        do {
            session.beginTransaction();
            command.setUser(user);
            command.commandList(user);
            try {
                inputCommand = scannerInt.nextInt();
                command.commandCheck(inputCommand);
            } catch (InputMismatchException e) {
                System.out.println(Color.ANSI_RED+"wrong type of command"+Color.ANSI_RESET);
                scannerInt = new Scanner(System.in);
                session.getTransaction().rollback();
                continue;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                session.getTransaction().rollback();
                continue;
            }


            switch (inputCommand) {
                case 1:
                    user = remote.loginCommand(scanner, user);
                    if (user == null) {
                        System.out.println(Color.ANSI_PURPLE + "Wrong Username or Password" + Color.ANSI_GREEN + "\ntry again...." + Color.ANSI_RESET);
                        session.getTransaction().rollback();
                        continue;
                    }
                    System.out.println(Color.ANSI_CYAN + "Hello " + user.getUserName() + Color.ANSI_RESET);
                    break;
                case 2:
                    try {
                        user = remote.signupCommand(scannerInt, scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 3:
                    try {
                        remote.showAllArticle(scannerInt);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED+e.getMessage()+Color.ANSI_RESET);
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 4:
                    remote.changePasswprd(scanner,user);
                    break;
                case 5:
                    try {
                        remote.userArticle(user);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED+e.getMessage()+Color.ANSI_RESET);
                        session.getTransaction().commit();
                        continue;
                    }
                    break;
                case 6:
                    try {
                        remote.cotumeArticle(scanner,scannerInt,user);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED+e.getMessage()+Color.ANSI_RESET);
                        session.getTransaction().rollback();
                        continue;
                    }
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
                case 10:
                    user = null;


            }
            session.getTransaction().commit();
        } while (inputCommand != 9);

        System.out.println("have a nice day .....");


    }//end of method main

}
