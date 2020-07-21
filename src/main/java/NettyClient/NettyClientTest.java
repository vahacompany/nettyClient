package NettyClient;

public class NettyClientTest {
	
	public static void main(String agrs[]) {
		
		NettyClient nettyClient = new NettyClient();
		
		nettyClient.connect("localhost", 8023);
		
		try {
			nettyClient.createMsg("sendMsg1");
			nettyClient.createMsg("sendMsg2");
			nettyClient.createMsg("sendMsg3");
			nettyClient.createMsg("sendMsg4");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			nettyClient.close();
		}
		
		nettyClient.close();
	}

}
