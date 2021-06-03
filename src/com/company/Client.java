package com.company;

public class Client extends User {

    public Client(String name, String login, String password) {

        super(name, login, password);
    }
    public void takeResult(Request request) {

        System.out.println(getName() + " your request is accepted, you have received job " + request.getJob() + " from " + request.getRequester().getName());
    }
}
