package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.ChildChannelHandler;


public class TimeServer {
    public  void  bind(int port) throws Exception {

        /**
         *  一个用于 接收客户端连接
         *  一个用于socketChannel的网络读写
         */
         NioEventLoopGroup bossGroup = new NioEventLoopGroup(); //创建线程组，相当于reactor线程组
         NioEventLoopGroup workerGroup = new NioEventLoopGroup();

         try {

             ServerBootstrap b = new ServerBootstrap(); // 用于启动Nio服务的辅助启动类
             b.group(bossGroup, workerGroup)
              .channel(NioServerSocketChannel.class) //type 指定NioServerSocketChannel为通道实现
                     .option(ChannelOption.SO_BACKLOG, 1024) //tcp 参数
              .childHandler(new ChildChannelHandler()); //设置事件处理类，处理网络io事件

             //绑定端口 同步等待成功
             ChannelFuture f = b.bind(port).sync();

             //等待服务端监听端口关闭
             f.channel().closeFuture().sync();
         } finally {

             //释放线程资源
             bossGroup.shutdownGracefully();
             workerGroup.shutdownGracefully();
         }
    }
}
