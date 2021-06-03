package com.company;

public class User {

    private String name;
    private String login;
    private String password;

    public User(String name, String login, String password) {

        this.name = name;
        this.login = login;
        this.password = password;
    }

    public boolean enter(String login, String password) {

        if (login.equals(this.login)) {

            if (password.equals(this.password)) {

                return true;
            } else {

                System.out.println("Was entered wrong password");
                return false;
            }
        } else {

            System.out.println("Was entered wrong login");
            return false;
        }
    }
    public String getName() {

        return this.name;
    }

    public boolean changeLogin (String login) {

        this.login = login;
        return true;
    }

    public boolean changeName (String name) {

        this.name = name;
        return true;
    }

    public boolean changePassword (String password) {

        this.password = password;
        return true;
    }
}
