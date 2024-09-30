package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.handler.client.EchoClientHandler_MsgPack;
import netty.handler.codec.messagePack.MsgPackDecoder;
import netty.handler.codec.messagePack.MsgPackEncoder;

public class EchoClient_MsgPack {

    private final String host;
    private final int port;
    private final int sendNumber;

    public EchoClient_MsgPack(String host, int port, int sendNumber){
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }

    public void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 增加粘包和半包考虑
                            socketChannel.pipeline().addLast("mespack decoder", new MsgPackDecoder());
                            socketChannel.pipeline().addLast("msgpack encoder", new MsgPackEncoder());
                            socketChannel.pipeline().addLast(new EchoClientHandler_MsgPack(sendNumber));
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;

        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new EchoClient_MsgPack("127.0.0.1", port, 1000).run();
    }
}
