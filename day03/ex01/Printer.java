public class Printer {
    private int ping = 1;

    public synchronized void printMessage(String message, Type type) throws InterruptedException {
        if (type == Type.CONSUMER) {
            while (ping == 0) {
                wait();
            }
            ping = 0;
        } else {
            while (ping == 1) {
                wait();
            }
            ping = 1;
        }
        System.out.println(message);
        notify();
    }
}