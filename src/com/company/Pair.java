package com.company;

public class Pair {

    private final Request workerRequest;
    private final Request employerRequest;
    private final User employer;
    private final User worker;

    public Pair(Request workerRequest, Request employerRequest, User employer, User worker) {

        this.employerRequest = employerRequest;
        this.workerRequest = workerRequest;
        this.employer = employer;
        this.worker = worker;
    }

    public Request getWorkerRequest() {

        return workerRequest;
    }

    public Request getEmployerRequest() {

        return employerRequest;
    }

    public User getEmployer() {

        return employer;
    }

    public User getWorker() {

        return worker;
    }
}
