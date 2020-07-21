package kr.codebrain.netty;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import kr.codebrain.netty.entity.NettyControl;
import kr.codebrain.netty.handler.RedisController;
import kr.codebrain.netty.handler.NettyClientHandler;
import kr.codebrain.netty.handler.NettyDecoder;
import kr.codebrain.netty.handler.NettyEncoder;

public class NettyClient {
	
	private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

	// 호스트를 정의합니다. 로컬 루프백 주소를 지정합니다.
	private String HOST = "127.0.0.1";
	// 접속할 포트를 정의합니다.
	private int PORT = 7011;
	private StringRedisTemplate template;
	private RedisController redis;
	
	private Bootstrap bootstrap = new Bootstrap();
	private EventLoopGroup loop = new NioEventLoopGroup();
	
	public EventLoopGroup getLoop() {
		return loop;
	}

	public void setLoop(EventLoopGroup loop) {
		this.loop = loop;
	}

	public void createBootstrap() throws Exception {  
		try{
			if (bootstrap != null) {  
				
				NettyClientHandler handler = new NettyClientHandler(this);  
				bootstrap.group(loop);  
				bootstrap.channel(NioSocketChannel.class);  
				bootstrap.option(ChannelOption.SO_KEEPALIVE, true);  
				bootstrap.handler(new ChannelInitializer<SocketChannel>() {  
				    @Override  
				    protected void initChannel(SocketChannel socketChannel) throws Exception {  
				    	ChannelPipeline cp = socketChannel.pipeline();
						cp.addLast(new NettyDecoder(), new NettyEncoder(), handler);
				    }  
				});  
				
				bootstrap.connect(HOST, PORT).sync().channel().closeFuture().sync();
				
			}  
		} catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } finally {
	        loop.shutdownGracefully();
	        System.exit(0);
	    }
	}
	
	/*
	 * Redis에서 제어 데이타 가져오기
	 */
	public NettyControl getControlData(){
		Map<String, String> req = redis.requestControl();
    	
    	if(req != null){
    		String value = "";
    		for(String key : req.keySet()){
    			value = value + req.get(key);
    			redis.delData(key);
    		}
    		System.out.println("redis control value :: "+value);
    		
    		NettyControl control = new NettyControl();
			control.setKey("");
			control.setValue(value);
			
			return control;
    		
    	}
    	
    	return null;
	}
	
	public void run() throws Exception {  
		logger.info("Running...");
		this.redis = new RedisController(template);
	    createBootstrap();
	    //eventBootstrap();
	    
	}

	public StringRedisTemplate getTemplate() {
		return template;
	}

	public void setTemplate(StringRedisTemplate template) {
		this.template = template;
	}  
	
	public String getHOST() {
		return HOST;
	}

	public void setHOST(String hOST) {
		HOST = hOST;
	}

	public int getPORT() {
		return PORT;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
	}

}
