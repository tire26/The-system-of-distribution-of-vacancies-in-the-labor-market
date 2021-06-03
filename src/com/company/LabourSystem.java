package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LabourSystem {

    private static ArrayList<Client> users = new ArrayList<>();
    private static Map<User, Request> workersRequest = new HashMap<>();
    private static Map<User, Request> employersRequest = new HashMap<>();
    private static Client currentUser;

    public static String enterInteger(Deque<String> startCommands) {

        Scanner scanner = new Scanner(System.in);
        String number = "";
        boolean check = false;

        if (isDequeEmpty(startCommands)) {
            while (!check) {

                if (scanner.hasNextInt()) {

                    number = scanner.nextLine();
                    check = true;

                } else {

                    String buffer = scanner.nextLine();
                    if (buffer.equals(END_COMMAND)) {

                        return buffer;
                    }
                    System.out.println("Was entered not a number, try again: ");
                    check = false;
                }
            }
        } else {

            number = startCommands.pollFirst();
        }
        return number;
    }

    private static User addUser(String name, String password, String login) {

        Client user = new Client(name, login, password);
        users.add(user);
        return user;
    }

    private static void addRequest(Client requester, String job, int payment, int hoursInWeek, String type) {

        Request request = new Request(requester, job, payment, hoursInWeek, type);
        if (type.equals(WORKER)) {

            workersRequest.put(requester, request);
        } else {

            employersRequest.put(requester, request);
        }
    }

    private static void clearWorkerRequest(User worker) {

        Request request = new Request();
        workersRequest.put(worker, request);
    }

    private static Client findUser(String login, String password) {

        for (Client user :
                users) {
            if (user.enter(login, password)) {

                return user;
            }
        }
        System.out.println("No such user");
        return null;
    }

    private static void processRequest(Request request, Client user) {

        user.takeResult(request);
    }

    private static final String END_COMMAND = "END", CREATE_VACANCY_COMMAND = "create-vacancy",
            CREATE_RESUME_COMMAND = "create-resume", GET_COMMAND = "get", HELP_COMMAND = "help", REG_USER_COMMAND = "reg-user",
            YES_COMMAND = "y", NO_COMMAND = "n", WORKER = "worker", EMPLOYER = "employer";

    private static void showListOfCommand() {

        System.out.println("""
                List of command
                END - program completion
                help - show list of command
                reg-user - registration of user
                create-vacancy - create vacancy
                create-resume - create resume
                get - receiving pairs worker-employer
                """);
    }

    private static boolean isCommand(String string) {


        return string.equals(END_COMMAND) || string.equals(CREATE_VACANCY_COMMAND) || string.equals(CREATE_RESUME_COMMAND)
                || string.equals(GET_COMMAND) || string.equals(HELP_COMMAND) || string.equals(REG_USER_COMMAND);
    }

    private static boolean isYorNCommand(String string) {

        return string.equals(YES_COMMAND) || string.equals(NO_COMMAND) || string.equals(END_COMMAND);
    }

    private static boolean isType(String string) {

        return string.equals(WORKER) || string.equals(EMPLOYER);
    }

    private static boolean isDequeEmpty(Deque<String> startCommands) {

        return startCommands.isEmpty();
    }

    private static boolean isRequestFits(Request employerRequest, Request workerRequest) {

        return employerRequest.getJob().equals(workerRequest.getJob()) && employerRequest.getPayment() == (workerRequest.getPayment())
                && employerRequest.getHoursInWeek() == (workerRequest.getHoursInWeek());
    }

    private static String enterCommand(Deque<String> startCommands, Scanner scanner) {

        boolean check = false;
        String string;
        if (isDequeEmpty(startCommands)) {
            do {

                string = scanner.nextLine();
                if (isCommand(string)) {

                    check = true;
                } else {

                    System.out.println("Was entered wrong command");
                }
            } while (!check);
        } else {

            string = startCommands.pollFirst();
        }
        return string;
    }

    private static String enterYorNCommand(Deque<String> startCommands, Scanner scanner) {

        boolean check = false;
        String string;
        if (isDequeEmpty(startCommands)) {
            do {

                string = scanner.nextLine();
                if (isYorNCommand(string)) {

                    check = true;
                } else {

                    System.out.println("Was entered wrong command");
                }
            } while (!check);
        } else {

            string = startCommands.pollFirst();
        }
        return string;
    }

    private static String enterType(Deque<String> startCommands, Scanner scanner) {

        boolean check = false;
        String string;
        if (isDequeEmpty(startCommands)) {
            System.out.println("Enter type of user worker or employer");
            do {

                string = scanner.nextLine();
                if (isType(string) || string.equalsIgnoreCase(END_COMMAND)) {

                    check = true;
                } else {

                    System.out.println("Was entered wrong command");
                }
            } while (!check);
        } else {

            string = startCommands.pollFirst();
        }
        return string;
    }

    private static User enterUsersField(Deque<String> startCommands, Scanner scanner, StringBuilder commands) {

        String name;
        if (isDequeEmpty(startCommands)) {

            System.out.println("Enter name of user");
            name = scanner.nextLine();
        } else {

            name = startCommands.pollFirst();
            if (name == null || name.equalsIgnoreCase(END_COMMAND)) {

                return null;
            }
        }

        String login;
        if (isDequeEmpty(startCommands)) {

            System.out.println("Enter login of user");
            login = scanner.nextLine();
        } else {

            login = startCommands.pollFirst();
            if (login == null || login.equalsIgnoreCase(END_COMMAND)) {

                return null;
            }
        }
        String password;
        if (isDequeEmpty(startCommands)) {

            System.out.println("Enter password");
            password = scanner.nextLine();
        } else {

            password = startCommands.pollFirst();
            if (password == null || password.equalsIgnoreCase(END_COMMAND)) {

                return null;
            }
        }
        commands.append(name).append("\n").append(password).append("\n").append(login).append("\n");
        return addUser(name, password, login);
    }

    private static void enterRequestField(Deque<String> startCommands, String type, Scanner scanner, StringBuilder commands) {

        String job;
        if (isDequeEmpty(startCommands)) {

            System.out.println("Enter job");
            job = scanner.nextLine();
        } else {

            job = startCommands.pollFirst();
            if (job == null || job.equalsIgnoreCase(END_COMMAND)) {

                return;
            }
        }
        String buf;
        if (isDequeEmpty(startCommands)) {

            System.out.println("Enter payment");
        }
        buf = enterInteger(startCommands);
        if (buf.equalsIgnoreCase(END_COMMAND)) {

            return;
        }
        int payment = Integer.parseInt(buf);
        if (isDequeEmpty(startCommands)) {

            System.out.println("Enter hours in week");
        }
        buf = enterInteger(startCommands);
        if (buf.equalsIgnoreCase(END_COMMAND)) {

            return;
        }
        int hoursInWeek = Integer.parseInt(buf);
        commands.append(job).append("\n").append(payment).append("\n").append(hoursInWeek).append("\n").append(type).append("\n");
        addRequest(currentUser, job, payment, hoursInWeek, type);
    }

    private static void createRequest(String bufferString, String type, Deque<String> startCommands, Scanner scanner, StringBuilder commands) {

        switch (bufferString) {
            case NO_COMMAND -> {
                currentUser = (Client) enterUsersField(startCommands, scanner, commands);
                if (currentUser == null) {

                    return;
                }
                enterRequestField(startCommands, type, scanner, commands);
            }
            case YES_COMMAND -> {
                do {
                    String login;
                    if (isDequeEmpty(startCommands)) {
                        System.out.println("Enter login");

                        login = scanner.nextLine();
                    } else {

                        login = startCommands.pollFirst();
                        if (login == null || login.equalsIgnoreCase(END_COMMAND)) {

                            return;
                        }
                    }
                    String password;
                    if (isDequeEmpty(startCommands)) {

                        System.out.println("Enter password");
                        password = scanner.nextLine();
                    } else {

                        password = startCommands.pollFirst();
                        if (password == null || password.equalsIgnoreCase(END_COMMAND)) {

                            return;
                        }
                    }
                    currentUser = findUser(login, password);
                } while (currentUser == null);
                enterRequestField(startCommands, type, scanner, commands);
            }
        }
    }

    public static void main(String[] args) {

        ArrayList<Pair> pairs = new ArrayList<>();
        boolean isEnd = false;
        Scanner scanner = new Scanner(System.in);
        StringBuilder commands = new StringBuilder();
        String logFilename = null;
        if (args.length == 2) {
            if (args[0].equals("-f") || args[0].equals("--file")) {
                logFilename = args[1];
            }
        }
        Deque<String> startCommands = new LinkedList<>();
        if (isDequeEmpty(startCommands) && logFilename != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(logFilename))) {
                String s;
                while ((s = reader.readLine()) != null) {
                    startCommands.add(s);
                }
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }
        do {
            String bufferString;
            if (isDequeEmpty(startCommands)) {

                System.out.print("Enter command: ");
                bufferString = enterCommand(startCommands, scanner);
            } else {

                bufferString = startCommands.pollFirst();
                if (bufferString == null) {

                    System.out.print("Enter command: ");
                    bufferString = enterCommand(startCommands, scanner);
                }
            }
            switch (bufferString) {
                case CREATE_RESUME_COMMAND -> {
                    commands.append(bufferString).append("\n");
                    System.out.println("You want create resume for existing user?");
                    bufferString = enterYorNCommand(startCommands, scanner);
                    commands.append(bufferString).append("\n");
                    if (bufferString.equalsIgnoreCase(END_COMMAND)) {

                        break;
                    }
                    createRequest(bufferString, WORKER, startCommands, scanner, commands);
                }
                case CREATE_VACANCY_COMMAND -> {
                    commands.append(bufferString).append("\n");
                    System.out.println("You want create vacancy for existing user?");
                    bufferString = enterYorNCommand(startCommands, scanner);
                    if (bufferString.equalsIgnoreCase(END_COMMAND)) {

                        break;
                    }
                    commands.append(bufferString).append("\n");
                    createRequest(bufferString, EMPLOYER, startCommands, scanner, commands);
                }
                case HELP_COMMAND -> {
                    commands.append(bufferString).append("\n");
                    showListOfCommand();
                }
                case GET_COMMAND -> {

                    commands.append(bufferString).append("\n");
                    for (User employer :
                            employersRequest.keySet()) { // проходим по всем заявкам от работодателей

                        Request employerRequest = employersRequest.get(employer);
                        do {
                            for (User worker :
                                    workersRequest.keySet()) { // проход по всем работникам

                                Request workerRequest = workersRequest.get(worker);
                                do {
                                    if (isRequestFits(employerRequest, workerRequest)) {

                                        Pair currentPair = new Pair(workerRequest, employerRequest, employer, worker);
                                        pairs.add(currentPair);
                                        clearWorkerRequest(worker);
                                    }
                                    workerRequest = workerRequest.getNextRequest();
                                } while (workerRequest != null);
                            }

                            employerRequest = employerRequest.getNextRequest();
                        } while (employerRequest != null);
                    }

                    for (Pair pair :
                            pairs) {

                        processRequest(pair.getEmployerRequest(), (Client) pair.getWorker());
                    }
                }
                case REG_USER_COMMAND -> {

                    commands.append(bufferString).append("\n");
                    String type = enterType(startCommands, scanner);
                    commands.append(type).append("\n");
                    if (type.equalsIgnoreCase(END_COMMAND)) {

                        break;
                    }
                    currentUser = (Client) enterUsersField(startCommands, scanner, commands);
                }
                case END_COMMAND -> {
                    System.out.println("Want you to save all data");
                    bufferString = enterYorNCommand(startCommands, scanner);
                    commands.append(bufferString).append("\n");
                    if (bufferString.equalsIgnoreCase(YES_COMMAND)) {

                        SimpleDateFormat formatter = new SimpleDateFormat("dd_M_yyyy_hh_mm_ss", Locale.ENGLISH);
                        Date date = new Date();
                        String path = "result_" + formatter.format(date) + ".txt";
                        try (FileWriter writer = new FileWriter(path, false)) {
                            writer.append(commands);
                            for (Pair pair :
                                    pairs) {

                                writer.append(pair.getWorker().getName()).append(" request is accepted, have received job ").append(pair.getEmployerRequest().getJob()).append(" from ")
                                        .append(pair.getEmployerRequest().getRequester().getName());
                            }
                            writer.flush();
                        } catch (IOException ex) {

                            System.out.println(ex.getMessage());
                        }
                    }
                    isEnd = true;
                }
                default -> System.out.println("Was entered wrong command");
            }
        } while (!isEnd);
        scanner.close();
    }
}
