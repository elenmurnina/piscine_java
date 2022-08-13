package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class Server {
    private static final String SIGN_UP_COMMAND = "signUp";
    private static final String SIGN_IN_COMMAND = "signIn";
    private static final String EXIT_COMMAND = "Exit";

    @Autowired
    private UsersService usersService;
    @Autowired
    private MessagesService messagesService;
    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    private List<Client> clients;

    public void run() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.err.println("Server started, waiting for a client...");
            clients = new ArrayList<>();

            //noinspection InfiniteLoopStatement
            while (true) {
                Socket socket = serverSocket.accept();
                Thread clientThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Client client = null;
                        try {
                            System.err.println("Got new TCP connection from client");
                            client = new Client(socket);
                            clients.add(client);
                            client.run();
                        } catch (ExitException ignored) {
                            System.err.println("Client has been exited [user="
                                    + (client == null ? null : client.user) + "].");
                        } catch (IOException ignored) {
                            System.err.println("Client disconnected with error [user="
                                    + (client == null ? null : client.user) + "].");
                        } finally {
                            System.err.println("Removed client from the list"
                                    + " of the connected clients [user="
                                    + (client == null ? null : client.user) + "].");
                            clients.remove(client);
                        }
                    }
                });
                clientThread.start();
            }
        }
    }

    public final class Client {
        private final Socket socket;
        private final BufferedReader reader;
        private final PrintWriter writer;
        private User user;

        public Client(Socket socket) throws IOException {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        }

        public void run() throws IOException {
            try {
                println("Hello from Server!");

                while (true) {
                    String command = readCommand();
                    System.err.println("Got " + command + " from client");

                    if (SIGN_UP_COMMAND.equals(command)) {
                        signUp();
                        continue;
                    }

                    if (SIGN_IN_COMMAND.equals(command)) {
                        signIn();
                        continue;
                    }

                    println("Enter username:");
                    String name = readNonEmpty();

                    println("Enter password:");
                    String password = readNonEmpty();

                    usersService.register(name, password);
                    println("Successful!");

                    break;
                }
            } finally {
                close();
            }
        }

        public void close() throws IOException {
            reader.close();
            writer.close();
            socket.close();
        }

        private void signUp() throws IOException {
            println("Enter username:");
            String name = readNonEmpty();

            println("Enter password:");
            String password = readNonEmpty();

            usersService.register(name, password);
            println("Successful!");
        }

        private void signIn() throws IOException {
            println("Enter username:");
            String name = readNonEmpty();

            println("Enter password:");
            String password = readNonEmpty();

            User user = usersService.authenticate(name, password);
            if (user == null) {
                println("Invalid name or password");
            } else {
                this.user = user;
                System.err.println("Client signed in as " + user);
                startMessaging();
            }
        }

        private void startMessaging() throws IOException {
            println("Start messaging");

            //noinspection InfiniteLoopStatement
            while (true) {
                String message = readNonEmpty();

                if (EXIT_COMMAND.equals(message)) {
                    println("You have left the chat.");
                    throw new ExitException();
                }

                messagesService.publishMessage(user, message);
                publishMessage(message);
            }
        }

        private void publishMessage(String message) {
            for (Client client : clients) {
                client.writer.println(user.getUsername() + ": " + message);
                client.writer.flush();
            }
        }

        private String readCommand() throws IOException {
            while (true) {
                String line = readNonEmpty();
                if (SIGN_UP_COMMAND.equals(line) || SIGN_IN_COMMAND.equals(line)) {
                    return line;
                }
                println("Incorrect command, expected '" + SIGN_UP_COMMAND
                        + "' or '" + SIGN_IN_COMMAND + "'");
            }
        }

        private String readNonEmpty() throws IOException {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    throw new RuntimeException("Client is disconnected");
                }

                line = line.trim();
                if (!line.isEmpty()) {
                    return line;
                }
            }
        }

        private void println(String message) {
            writer.println(message);
            writer.flush();
        }
    }
}
