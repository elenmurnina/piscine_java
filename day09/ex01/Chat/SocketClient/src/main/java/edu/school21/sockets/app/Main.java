package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Parameters(separators = "=")
public class Main {
    @Parameter(names = "--server-port",
            description = "Port",
            required = true,
            validateWith = PositiveInteger.class)
    @SuppressWarnings("unused")
    private int serverPort;

    private void run(String[] args) throws IOException {
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);

        runClient();
    }

    private void runClient() throws IOException {
        try (Socket socket = new Socket("localhost", serverPort);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
            Thread readerThread = new Thread(() -> {
                String line;
                while (true) {
                    try {
                        line = reader.readLine();
                    } catch (IOException ignored) {
                        break;
                    }

                    if (line == null) {
                        break;
                    }

                    System.out.println(line);
                }

                System.exit(0);
            });
            readerThread.start();

            Scanner scanner = new Scanner(System.in);
            while (readerThread.isAlive()) {
                String line = scanner.nextLine();
                writer.println(line);
                writer.flush();
            }

            System.out.println("Good bye.");
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().run(args);
    }
}
