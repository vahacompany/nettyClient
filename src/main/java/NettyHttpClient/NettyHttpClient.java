package NettyHttpClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;

public class NettyHttpClient {

	ChannelFuture cf;
	EventLoopGroup group;

	public void connect(String host, int port) {

		group = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new NettyHttpChannelInit(group));

			cf = b.connect(host, port).sync();
//            cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// post Request Encoder : attribute를 지정하고 싶다면, PostRquestEncoder를 통해 요청해야한다.

	public void createRequest(String host, int port, String url, String msg) throws Exception {

		HttpRequest request = null;
		HttpPostRequestEncoder postRequestEncoder = null;

		request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, url
		);
		request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);
		request.headers().set(HttpHeaderNames.HOST, host + ":" + port);
		request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, url.length());

		postRequestEncoder = new HttpPostRequestEncoder(request, false);
		postRequestEncoder.addBodyAttribute("url", url);
		postRequestEncoder.addBodyAttribute("param", msg);
		request = postRequestEncoder.finalizeRequest();
		postRequestEncoder.close();
//            cf.channel().writeAndFlush(request).addListener(ChannelFutureListener.CLOSE);

		cf.channel().writeAndFlush(request);
		
		System.out.println("createRequest uri ---> " + request.uri());
		System.out.println("createRequest method ---> " + request.method());

	}

	public void close() {
		cf.channel().close();
		group.shutdownGracefully();
	}

}
