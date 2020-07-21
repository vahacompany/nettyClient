package kr.codebrain.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import kr.codebrain.netty.config.NettyProperties;

@SpringBootApplication
public class NettyClientApplication implements CommandLineRunner {

	private final NettyProperties properties;
	private static final Logger logger = LoggerFactory.getLogger( NettyClientApplication.class );

//	@Autowired
//	private StringRedisTemplate template;
	
	public NettyClientApplication(NettyProperties properties) {
		this.properties = properties;
	}
	
//	public void setRedis(String key, String value){
//		ValueOperations<String, String> ops = this.getTemplate().opsForValue();
//		ops.set(key, value);
//	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("=========================================");
		logger.info("Sample host: " + this.properties.getHost());
		logger.info("Sample port: " + this.properties.getPort());
		logger.info("=========================================");
		logger.info("RollScreen running...");
		
		NettyClient client = new NettyClient();
		client.setHOST(this.properties.getHost());
		client.setPORT(this.properties.getPort());
//		client.setTemplate(getTemplate());
		client.run();
		
	}

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(NettyClientApplication.class).run(args);
	}

//	public StringRedisTemplate getTemplate() {
//		return template;
//	}

//	public void setTemplate(StringRedisTemplate template) {
//		this.template = template;
//	}
	
}
