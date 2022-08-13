public class Program {

    private static final String COUNT_PREFIX = "--count=";

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1 || !args[0].startsWith(COUNT_PREFIX)) {
            error();
        }

        int count = 0;
        try {
            count = Integer.parseInt(args[0].substring(COUNT_PREFIX.length()));
        } catch (NumberFormatException ignored) {
            error();
        }
        if (count < 0 || count > 1000000) {
            error();
        }
        int finalCount = count;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < finalCount; i++) {
                    System.out.println("Egg");
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < finalCount; i++) {
                    System.out.println("Hen");
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        for (int i = 0; i < finalCount; i++) {
            System.out.println("Human");
        }
    }

    private static void error() {
        System.err.println("Expected single argument like --count=n, where n is integer between 0 and 1000000");
        System.exit(-1);
    }
}
