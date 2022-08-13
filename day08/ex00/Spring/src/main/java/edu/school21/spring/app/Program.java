package edu.school21.spring.app;

import edu.school21.spring.printer.Printer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printer1 = context.getBean("printerWithPrefix", Printer.class);
        printer1.print("Hello, I'm message with prefix.");

        Printer printer2 = context.getBean("printerWithDateTime", Printer.class);
        printer2.print("I'm message with date+time.");


    }

}

