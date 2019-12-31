package ir.mctab.java32.hw8.view;


import ir.mctab.java32.hw8.entities.Article;
import ir.mctab.java32.hw8.entities.Category;
import ir.mctab.java32.hw8.entities.User;
import ir.mctab.java32.hw8.repositories.ArticleDAO;
import ir.mctab.java32.hw8.repositories.CategoryDAO;
import ir.mctab.java32.hw8.repositories.UserDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Remote {

    Session session;
    UserDAO userDAO = new UserDAO();
    ArticleDAO articleDAO = new ArticleDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    //*********************************************
    public Remote(Session session) {
        this.session = session;
        userDAO.setSession(session);
        articleDAO.setSession(session);
        categoryDAO.setSession(session);
    }

    //*********************************************

    //1
    public User loginCommand(Scanner scanner, User user) {
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();
        user = userDAO.loginUser(username, password, user);
        return user;
    }

    //2
    public User signupCommand(Scanner scannerInt, Scanner scanner) throws Exception {
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your birthday: YYYY-MM-DD");
        String birthday = scanner.nextLine();
        if (birthday.length() != 10) {
            throw new Exception(Color.ANSI_RED + "Bad birthday date please write in correct order" + Color.ANSI_RESET);
        }
        System.out.println("Enter your nationalCode: ");
        Long nationalId = scannerInt.nextLong();
        if (nationalId < 10000000 || nationalId > 99999999) {
            throw new Exception(Color.ANSI_RED + "Bad NationalCode please write correct" + Color.ANSI_RESET);
        }
        User user = userDAO.signup(username, nationalId, birthday);
        System.out.println("user " + user.getUserName() + " with id " + user.getId() + " has been created" + "\nremember your default pass is your nationalNumber");
        return user;
    }

    //3
    public void showAllArticle(Scanner scannerInt) throws Exception {
        List<Article> articles = articleDAO.showAllArticle();
        articles.stream().forEach(article -> System.out.println(Color.ANSI_YELLOW + "Id: " + Color.ANSI_RESET + article.getId()
                + Color.ANSI_YELLOW + "\tTitle: " + Color.ANSI_RESET + article.getTitle()
                + Color.ANSI_YELLOW + "\t\tBrief: " + Color.ANSI_RESET + article.getBrief()));
        System.out.println("Please enter id of your article which you want to see: \nif you don't press '0' ");
        Long id = null;
        if (scannerInt.hasNextLong()) {
            id = scannerInt.nextLong();
        } else {
            throw new Exception("invalid input");
        }
        Long finalId = id;
        Optional<Article> article = articles.stream().filter(p -> p.getId().equals(finalId)).findFirst();
        if (article.isPresent()) {
            System.out.println(article);
        }
    }

    //4
    public void changePasswprd(Scanner scanner, User user) {
        System.out.println("enter your new password: ");
        String pass = scanner.nextLine();
        user = session.load(User.class, user.getId());
        user.setPassword(pass);
        userDAO.changiPassword(user, pass);
        System.out.println(Color.ANSI_GREEN + "your password changed" + Color.ANSI_RESET);
    }

    //5
    public void userArticle(User user) throws Exception {
        List<Article> articles1 = userDAO.userArticle(user);
        articles1.forEach(System.out::println);

    }

    //6
    public Article cotumeArticle(Scanner scanner, Scanner scannerInt, User user) throws Exception {
        userArticle(user);
        System.out.println("Insert your article id: ");
        Long articleId = scannerInt.nextLong();
        Article article = articleDAO.loadArticle(articleId);
        if (!article.getUser().getId().equals(user.getId())) {
            throw new Exception("its not your article");
        }
        System.out.println("Insert your new data in this order: [brief,content,title]");
        String costume = scanner.nextLine();
        String[] costumeArray = costume.split(",");
        if (costumeArray.length != 3) {
            throw new Exception("wrong input");
        }
        article = articleDAO.costumeArticle(costumeArray, article);
        if (article != null) {
            System.out.println("Article with id: " + article.getId() + " has been changed");
        }
        return article;
    }
    //7
    public void nawArticle(Scanner scanner , Scanner scannerInt , User user) throws Exception{
        System.out.println("please enter brief of your article: ");
        String brief = scanner.nextLine();
        System.out.println("please enter content of your article: ");
        String content = scanner.nextLine();
        boolean isPublish = false;
        System.out.println("please enter title of your article: ");
        String title = scanner.nextLine();
        System.out.println("here is list of category: ");
        List<Category> categories = categoryDAO.categoryList();
        categories.forEach(System.out::println);
        System.out.println("for choosing press 1 and for adding category press 0");
        int categoryChoose = scannerInt.nextInt();
        Category category = null;
        if (categoryChoose == 1) {
            System.out.println("Enter category id: ");
            Long categoryId = scannerInt.nextLong();
            category = categoryDAO.loadCategory(categoryId);
        }else if (categoryChoose == 0) {
            System.out.println("Enter Title of this category");
            String title1 = scanner.nextLine();
            System.out.println("Enter description of your category");
            String description = scanner.nextLine();
            category = categoryDAO.addCategory(title1,description);
        }else {
            throw new Exception("wrong command");
        }
        Article article = articleDAO.saveArticle(title, brief, content, isPublish, user, category);
        System.out.println("Article with "+Color.ANSI_YELLOW+"id: "+Color.ANSI_RESET+article.getId()+" has been created");
    }
    //8
    public void publishArticle(Scanner scannerInt , User user) throws Exception{
        List<Article> articles2 = articleDAO.loadPublishArticle(user);
        articles2.forEach(System.out::println);
        System.out.println("choose article which you want to publish ");
        Long publishId = scannerInt.nextLong();
        articleDAO.publishArticle(user,publishId);
        System.out.println(Color.ANSI_GREEN+"your article published"+Color.ANSI_RESET);
    }

}
