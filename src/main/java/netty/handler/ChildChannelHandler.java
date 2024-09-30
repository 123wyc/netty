package netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import netty.handler.server.TimeServerHandler;

public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {


        /**
         * LineBasedFrameDecoder+StringDecoder组合就是按行切换的文本解码
         * 器，它被设计用来支持TCP的粘包和拆包。
         */
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
        ch.pipeline().addLast(new StringDecoder());
        ch.pipeline().addLast(new TimeServerHandler());
    }
}
