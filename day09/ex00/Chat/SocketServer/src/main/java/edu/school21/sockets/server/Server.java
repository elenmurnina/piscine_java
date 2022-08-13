package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server {

    @Autowired
    private UsersService usersService;
    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public void run() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started, waiting for a client...");
            Socket socket = serverSocket.accept();
            processClient(socket);
        }
    }

    private void processClient(Socket socket) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
            println(writer, "Hello from Server!");

            while (true) {
                String line = readNonEmpty(reader);
                if (!"signUp".equals(line)) {
                    println(writer, "Invalid command");
                    continue;
                }
                System.out.println("New user registration");
                println(writer, "Enter username:");
                String username = readNonEmpty(reader);
                System.out.println("Username saved");

                println(writer, "Enter password:");
                String password = readNonEmpty(reader);
                System.out.println("Password saved");

                usersService.register(username, password);
                println(writer, "Successful!");
                System.out.println("Registration completed successfully.");

                break;
            }
        } finally {
            socket.close();
        }
    }

    private String readNonEmpty(BufferedReader reader) throws IOException {
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

    private void println(PrintWriter writer, String message) {
        writer.println(message);
        writer.flush();
    }

}
