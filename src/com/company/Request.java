package com.company;

public class Request {

    private final Client requester;
    private String job;
    private int payment;
    private int hoursInWeek;
    private String type;
    private Request nextRequest;

    public Request(Client requester, String job, int payment, int hoursInWeek, String type) {

        if (payment < 0 || hoursInWeek < 0 || hoursInWeek > 168) {

            throw new IllegalArgumentException("Was entered wrong value");
        }
        this.requester = requester;
        this.job = job;
        this.payment = payment;
        this.hoursInWeek = hoursInWeek;
        this.type = type;
    } // TODO: 17.05.2021 провекра type

    public Request() {

        this(null, null, 0, 0, null);
        nextRequest = null;
    }

    public Client getRequester() {

        return requester;
    }

    public String getJob() {

        return job;
    }

    public int getHoursInWeek() {

        return hoursInWeek;
    }

    public String getType() {

        return type;
    }

    public int getPayment() {

        return payment;
    }

    public boolean changeJob(String job) {

        this.job = job;
        return true;
    }

    public boolean changePayment(int payment) {

        if (payment < 0) {

            throw new IllegalArgumentException("Was entered wrong value");
        }
        this.payment = payment;
        return true;
    }

    public boolean changeHoursInWeek(int hoursInWeek) {

        if (hoursInWeek < 0 || hoursInWeek > 24) {

            throw new IllegalArgumentException("Was entered wrong value");
        }
        this.hoursInWeek = hoursInWeek;
        return true;
    }

    public boolean changeType(String type) {

        this.type = type;
        return true;
    }

    public Request getNextRequest() {

        return nextRequest;
    }

    public void setNextRequest(Request request) {

        nextRequest = request;
    }

}
