package netty.handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {

    private static  final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());


    private  int counter;

    private  byte[] req;



    public TimeClientHandler()
    {
       req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(io.netty.channel.ChannelHandlerContext ctx) throws Exception
    {

        ByteBuf message = null;
        for(int i=0;i<100;i++)
        {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        String body =(String)msg;
        System.out.println("Now is :"+body+"; the counter is:"+ ++counter);
    }

    @Override
    public void exceptionCaught(io.netty.channel.ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        logger.warning("Unexpected exception from downstream :"+cause.getMessage());
        ctx.close();
    }
}
