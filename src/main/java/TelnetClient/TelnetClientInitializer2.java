package TelnetClient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.BlockingQueue;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class TelnetClientInitializer2 extends ChannelInitializer<SocketChannel> {

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private final String user;
    private final String pw;
    private final BlockingQueue<String> queue;

    public TelnetClientInitializer2(String user, String pw, BlockingQueue<String> queue) {
        this.user = user;
        this.pw = pw;
        this.queue = queue;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ClientAuthHandler(user, pw, queue));
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);
        // and then business logic.
        pipeline.addLast(new TelnetClientHandler2(queue));
    }
}