package nio.client;

public class Main {


    private static int port = 8080;
    public static void main(String[] args) {

        if (args == null || args.length > 0){

            try {
                assert args != null;
                port = Integer.parseInt(args[0]);

            }catch (NumberFormatException e){

                // do nothing
            }
        }

        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
    }
}
