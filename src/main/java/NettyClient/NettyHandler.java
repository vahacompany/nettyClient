package NettyClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

public class NettyHandler extends SimpleChannelInboundHandler<String> {
	 
    private EventLoopGroup group;
    private SocketChannel sc;
    private int count=0;
    
 
    public NettyHandler(EventLoopGroup group, SocketChannel sc) {
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
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

		// TelnetPipelineFactory에 지정된 StringEncoder클래스가 문자열을 버퍼로 변환해 주므로
		// ChannelBuffer에 전송할 값을 복사하여 전송하지 않고 직접 문자열을 전송한다.
		//String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
		String readMessage = msg.toString();
		System.out.println(readMessage);

	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}