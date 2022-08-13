package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;
import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@Parameters(separators = "=")
public class Main {
    @Parameter(names = "--port",
            description = "Port",
            required = true,
            validateWith = PositiveInteger.class)
    @SuppressWarnings("unused")
    private int port;

    private void run(String[] args) throws IOException {
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);

        try (AnnotationConfigApplicationContext context
                     = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class)) {
            Server server = context.getBean("server", Server.class);
            server.setPort(port);
            server.run();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().run(args);
    }
}
