package netty.client;

public class Main {

    public static void main(String[] args) throws Exception {

        int port = 8080;

        if(args != null && args.length > 0){

            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }

        }
       // new TimeClient().connect(port, "127.0.0.1");
       // new EchoClient().connect(port, "127.0.0.1");
        new EchoClient_MsgPack("127.0.0.1", port,10).run();
    }
}
