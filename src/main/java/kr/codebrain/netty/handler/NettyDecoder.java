package kr.codebrain.netty.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NettyDecoder extends ByteToMessageDecoder { // (1)
	private static final Logger logger = LoggerFactory.getLogger(NettyDecoder.class);
	private String cmd = "";
	private Object obj = null;
	private StringBuilder sb1 = new StringBuilder();
	private StringBuilder sb2 = new StringBuilder();
	
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
    	int length = in.readableBytes();
    	logger.info("size :: {}", length);
    	
    	if(length < 1) return;
    	
        byte[] read = new byte[length];
        
        for(int i = 0 ; i < length ; i++){
        	read[i] = in.readByte();
        }
        
        sb1.append(new String(read));
        
        length = sb1.length();
        for(int i = 0 ; i < length ; i++){
        	char c = sb1.charAt(i);
        	if(c == ')'){
        		sb2 = new StringBuilder(sb1.substring(0, i+1));
        		sb1.delete(0, i+1);
        		
        		out.add(sb2); // (4)

                sb2 = null;
                sb2 = new StringBuilder();
        		return;
        	}
        }
        
    }

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}


}
