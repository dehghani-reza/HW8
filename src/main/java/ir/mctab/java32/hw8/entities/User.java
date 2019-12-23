package ir.mctab.java32.hw8.entities;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userName", nullable = false, length = 22)
    private String userName;

    @Column(name = "nationalCode", nullable = false, length = 22)
    private Long nationalCode;

    @Column(name = "birthday", nullable = false, length = 22)
    private String birthday;

    @Column(name = "password", nullable = false, length = 22)
    private String password;

    //complete constructor
    public User(String userName, Long nationalCode, String birthday) {
        this.userName = userName;
        this.nationalCode = nationalCode;
        this.birthday = birthday;
        this.password = String.valueOf(nationalCode);
    }

    //base constructor
    public User() {
    }


    //setters and getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(Long nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


