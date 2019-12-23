package ir.mctab.java32.hw8;

import ir.mctab.java32.hw8.config.hibernate.HibernateUtil;
import ir.mctab.java32.hw8.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Scanner;

public class ArticleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);


        SessionFactory sessionFactory = HibernateUtil.getSession();
        Session session = sessionFactory.openSession();

        System.out.println("SignIn: 1 | signUp: 2 | showAllArticle: 3 | exit: 4");
        int command = scannerInt.nextInt();

        while (command!=4){
            switch (command){
                case 1:
                    System.out.println("Enter your username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter your password");
                    String password = scanner.nextLine();
                    User user = session.load(User.class );

            }
        }



    }
}
