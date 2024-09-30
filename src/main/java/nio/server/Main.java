package nio.server;

public class Main {

    public static void main(String[] args) {


        int port = 8080;

        if(args !=null && args.length>0){
            try {
                port = Integer.valueOf(args[0]);

            }catch (NumberFormatException e){
                System.err.println("port must be integer");
            }
        }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);

        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
