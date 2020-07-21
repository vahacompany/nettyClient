package NettyClient;

import javax.net.ssl.SSLException;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

public class NettyChannelInit extends ChannelInitializer<SocketChannel>{
    
    private boolean ssl = false;
    private EventLoopGroup group;
    
     public NettyChannelInit(EventLoopGroup group) {
         this.group = group;
    }
    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        // TODO Auto-generated method stub
        
        ChannelPipeline p = sc.pipeline();
        if(ssl) {
            SslContext sslCtx = null;
            try {
                sslCtx = SslContextBuilder.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                 p.addLast(sslCtx.newHandler(sc.alloc()));
            } catch (SSLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    
        /**
         *
         * Message Decoder, FastLz...
        */
    
    //    p.addLast(new MessageDecoder());
 
        
        //chunked 된 응답을 집계하는 코덱
//        p.addLast("chunked",new HttpObjectAggregator(1048576));
//        p.addLast("codec",new HttpClientCodec());
        p.addLast(new NettyHandler(group, sc));
    }
}
