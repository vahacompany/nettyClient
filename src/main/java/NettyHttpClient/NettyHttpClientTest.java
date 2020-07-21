package NettyHttpClient;

public class NettyHttpClientTest {
	
	public static void main(String agrs[]) {
		
		NettyHttpClient nettyClient = new NettyHttpClient();
		
		nettyClient.connect("localhost", 8080);
		try {
			nettyClient.createRequest("localhost", 8080, "/welcomeResponse", "홍길동");
			nettyClient.createRequest("localhost", 8080, "/welcomeResponse", "박길동");
			nettyClient.createRequest("localhost", 8080, "/welcomeResponse", "최길동");
			nettyClient.createRequest("localhost", 8080, "/welcomeResponse", "조길동");
			nettyClient.createRequest("localhost", 8080, "/welcomeResponse", "bye");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//nettyClient.close();
	}

}
