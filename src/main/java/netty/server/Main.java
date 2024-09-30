package netty.server;

public class Main {

    public static void main(String[] args) throws Exception {
        int port = 8080;


        if (args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }

       // new TimeServer().bind(port);

        //new EchoServer().bind(port);

    }
}
