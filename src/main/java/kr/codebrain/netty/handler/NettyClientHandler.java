package kr.codebrain.netty.handler;

import java.io.IOException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import kr.codebrain.netty.NettyClient;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	
	private NettyClient client;  
	
	public NettyClientHandler(NettyClient rollScreenClient) {  
		this.client = rollScreenClient;  
	}  
	   
	@Override  
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {  
		
	}
 
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
		NettyController controller = new NettyController();
		controller.startTimeAction(ctx, client);

	}
 
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException, ClassNotFoundException {
    	
        if(msg instanceof StringBuilder){
        	StringBuilder buf = (StringBuilder) msg;
            //logger.info("msg ::{}", buf);
            
            int length = buf.length();
            for(int i = 0 ; i < length ; i++){
            	char c = buf.charAt(i);
            	if(c == '('){
            		StringBuilder buffer = new StringBuilder(buf.substring(i+1));
            		buffer.deleteCharAt(buffer.length()-1);
            		
            		//logger.info("message result :: {}", buffer.toString());

            		//Redis 에 저장
            		if(buffer.charAt(0) == 'B'){
            			String key = buffer.substring(32, 36);
            			key = key + buffer.substring(1, 8);
            			
                		RedisController redis = new RedisController(client.getTemplate());
                		redis.setData(key, buffer.toString());
            		}
            		
            		break;
            	}
            }
        }
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException {
        cause.printStackTrace();
        ctx.close();
    }

}
