package netty.handler.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;

public class EchoClientHandler extends ChannelHandlerAdapter {


    private  int counter;

    static final String ECHO_REQ = "Hi,WangYiChao. Welcome to Netty.$_";

    public EchoClientHandler ()
    {

    }


    @Override
    public void channelActive(io.netty.channel.ChannelHandlerContext ctx) throws Exception
    {


        for (int i = 0; i < 10; i++){

            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println("This is " + ++counter + " times receive server : [" + msg + "]");
    }


    @Override
    public void channelReadComplete(io.netty.channel.ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(io.netty.channel.ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
