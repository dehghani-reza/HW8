package ir.mctab.java32.hw8.view;

import ir.mctab.java32.hw8.entities.Role;

import ir.mctab.java32.hw8.entities.User;

public class Command {

    private User user ;
    Role admin = new Role("Admin");

    public void setUser(User user) {
        this.user = user;
    }

    public void commandList(User user) {

        if (user == null) {
            System.out.println("SignIn: 1 | signUp: 2 | showAllArticle: 3 | exit: 9");
        } else if(user.getRoles().contains(admin)) {
            System.out.println(Color.ANSI_BLUE+"showAllArticle: 3 | changing pass: 4  | change role:15 | exit: 9 | signOut: 10"
                    +Color.ANSI_CYAN+
                    "\npublish article: 8 | repeal publish:11 | create category:12 | create tag:13 | delete Article:14"
                    +Color.ANSI_RESET);
        }else if(!user.getRoles().contains(admin)){
            System.out.println(Color.ANSI_BLUE+"changing pass: 4 | see all of your article: 5"
                    +Color.ANSI_CYAN+
                    "\ncostume article: 6 | new article: 7 | exit: 9 | signOut: 10"
                    +Color.ANSI_RESET);
        }
    }

    public void commandCheck(int commandInput) throws RuntimeException {
        if (this.user == null && !(commandInput==1 || commandInput ==2 || commandInput ==3 ||commandInput ==9)){
            throw new RuntimeException (Color.ANSI_RED+"False command first signIn or signUp"+Color.ANSI_RESET);
        }
        if(this.user!=null && (commandInput==1 || commandInput ==2) ){
            throw new RuntimeException(Color.ANSI_RED+"for this command you should exit first"+Color.ANSI_RESET);
        }
        if(commandInput>10 || commandInput<1){
            throw new RuntimeException(Color.ANSI_RED+"wrong command"+Color.ANSI_RESET);
        }
    }
}
