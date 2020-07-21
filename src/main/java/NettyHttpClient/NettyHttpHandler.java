package NettyHttpClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class NettyHttpHandler extends SimpleChannelInboundHandler<HttpObject> {
	 
    private EventLoopGroup group;
    private SocketChannel sc;
    private int count=0;
    
 
    public NettyHttpHandler(EventLoopGroup group, SocketChannel sc) {
        this.group = group;
        this.sc =sc;
    }
 
 
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelActive(ctx);
//        System.out.println("connect:"+ctx.channel().isActive());
    }
    
 
 
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // TODO Auto-generated method stub
    	System.err.println("=====channelRead0=====");
        
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            
            
            System.err.println("STATUS: " + response.status());
            System.err.println("VERSION: " + response.protocolVersion());
            
 
            if (!response.headers().isEmpty()) {
                for (CharSequence name : response.headers().names()) {
                    for (CharSequence value : response.headers().getAll(name)) {
                        System.err.println("HEADER: " + name + " = " + value);
                    }
                }
                System.err.println();
            }
 
            if (HttpUtil.isTransferEncodingChunked(response)) {
                System.err.println("CHUNKED CONTENT {");
            } else {
                System.err.println("CONTENT {");
            }
        }
        if (msg instanceof HttpContent) {
            count++;
            HttpContent content = (HttpContent) msg;
            String lMsg = content.content().toString(CharsetUtil.UTF_8);
            System.err.println(count+". create url = "+lMsg);
            System.err.flush();
 
            if (content instanceof LastHttpContent) {
                System.err.println("} END OF CONTENT");
            }
            
            if (lMsg.equals("bye")) {
                ctx.close();
                sc.close();
                group.shutdownGracefully();
            }
        }
    }
 
        
 
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
        sc.close();
        group.shutdownGracefully();
    }
}