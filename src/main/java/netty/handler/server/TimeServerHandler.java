package netty.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;


/**
 * 用于对网路事件进行读写操作
 */
public class TimeServerHandler extends ChannelHandlerAdapter {


    private  int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String body = (String) msg;
        System.out.println("The time server receive order:"+ body+"; the count is: "+ ++counter);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
                System.currentTimeMillis()).toString() : "BAD ORDER";


        currentTime = currentTime+System.getProperty("line.separator");

        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }


    @Override
    public void channelReadComplete(io.netty.channel.ChannelHandlerContext ctx) throws Exception {
        /**
         * 为了防止频繁地唤醒Selector进行消息发送，
         * Netty的write方法并不直接将消息写入SocketChannel中，调用write
         * 方法只是把待发送的消息放到发送缓冲数组中，再通过调用flush方
         * 法，将发送缓冲区中的消息全部写到SocketChannel中
         */
        ctx.flush();
    }

    @Override
    public void exceptionCaught(io.netty.channel.ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
