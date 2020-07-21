package kr.codebrain.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import kr.codebrain.netty.entity.NettyCheck;
import kr.codebrain.netty.entity.NettyControl;

//public class RollScreenEncoder extends ChannelOutboundHandlerAdapter {
public class NettyEncoder extends MessageToByteEncoder<Object> {

	private static final Logger logger = LoggerFactory.getLogger(NettyEncoder.class);
	
	@Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception
    {
		logger.info("msg :: {}", msg.toString());
		
		if(msg instanceof NettyCheck){
			NettyCheck checkLine = (NettyCheck) msg;
			ByteBuf buf = checkLine.encode();
			out.writeBytes(buf);
			
		}else if(msg instanceof NettyControl){
			NettyControl control = (NettyControl) msg;
			ByteBuf buf = control.encode();
			
			out.writeBytes(buf); // (1)
		}else{
			String sendMsg = (String) msg;
			ByteBuf buffer = Unpooled.buffer( sendMsg.length() );
			buffer.writeBytes( sendMsg.getBytes() );
			
			out.writeBytes(buffer); // (1)
		}
    }

    
    
//	@Override
//	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//		// TODO Auto-generated method stub
//		logger.info("msg :: {}", msg.toString());
//
//		if(msg instanceof RollScreenCheck){
//			RollScreenCheck checkLine = (RollScreenCheck) msg;
//			ByteBuf buf = checkLine.encode();
//	
//			ctx.write(buf, promise); // (1)
//		}else if(msg instanceof RollScreenControl){
//			RollScreenControl control = (RollScreenControl) msg;
//			ByteBuf buf = control.encode();
//			
//			ctx.write(buf, promise); // (1)
//		}else{
//			String sendMsg = (String) msg;
//			ByteBuf buffer = Unpooled.buffer( sendMsg.length() );
//			buffer.writeBytes( sendMsg.getBytes() );
//			
//			ctx.write(buffer, promise); // (1)
//		}
//	}

	
}
