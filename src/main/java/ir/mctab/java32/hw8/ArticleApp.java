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
                    try {
                        remote.nawArticle(scanner,scannerInt,user);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED+e.getMessage()+Color.ANSI_RESET);
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 8:
                    try {
                        remote.publishArticle(scannerInt,user);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED+e.getMessage()+Color.ANSI_RESET);
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 10:
                    user = null;
                    break;


            }
            session.getTransaction().commit();
        } while (inputCommand != 9);

        System.out.println("have a nice day .....");


    }//end of method main

}
