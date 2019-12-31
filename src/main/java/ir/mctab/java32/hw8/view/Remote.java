package ir.mctab.java32.hw8.view;


import ir.mctab.java32.hw8.entities.Article;
import ir.mctab.java32.hw8.entities.User;
import ir.mctab.java32.hw8.repositories.ArticleDAO;
import ir.mctab.java32.hw8.repositories.UserDAO;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Remote {

    Session session;
    UserDAO userDAO = new UserDAO();
    ArticleDAO articleDAO = new ArticleDAO();

    //*********************************************
    public Remote(Session session) {
        this.session = session;
        userDAO.setSession(session);
        articleDAO.setSession(session);
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
    public void userArticle(User user) throws Exception{
        List<Article> articles1 = userDAO.userArticle(user);
        articles1.forEach(System.out::println);

    }

    //6
    public Article cotumeArticle(Scanner scanner, Scanner scannerInt, User user) throws Exception {
        userArticle(user);
        System.out.println("Insert your article id: ");
        Long articleId = scannerInt.nextLong();
        Article article = articleDAO.loadArticle(articleId);
        if (article.getUser()!=user){
            throw new Exception("its not your article");
        }
        System.out.println("Insert your new data in this order: [brief,content,title]");
        String costume = scanner.nextLine();
        String[] costumeArray = costume.split(",");
        if (costumeArray.length != 3) {
            throw new Exception("wrong input");
        }
        article = articleDAO.costumeArticle(costumeArray, article);
        if(article!=null){
            System.out.println("Article with id: "+article.getId()+"has been changed");
        }
        return article;
    }

}
