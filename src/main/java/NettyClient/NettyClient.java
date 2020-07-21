package NettyClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	 
    ChannelFuture cf;
    EventLoopGroup group;
    
    public void connect(String host, int port) {
 
       group = new NioEventLoopGroup();
 
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new NettyChannelInit(group));
 
            cf = b.connect(host, port).sync();
//            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //post Request Encoder : attribute를 지정하고 싶다면, PostRquestEncoder를 통해 요청해야한다.
    
    public void createMsg(String msg) throws Exception {

            cf.channel().writeAndFlush(msg+"---1");
            System.out.println("send Msg====>" +msg);
            cf.channel().writeAndFlush(msg+"---2");
            System.out.println("send Msg====>" +msg);
            cf.channel().writeAndFlush(msg+"---3");
            System.out.println("send Msg====>" +msg);
    }
    
    public void close() {
        cf.channel().close();
        group.shutdownGracefully();
    }
    
    
}
