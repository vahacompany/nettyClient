package kr.codebrain.netty.entity;

import java.io.Serializable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyControl implements Serializable, IPacket {

	private static final long serialVersionUID = -8259739583103038269L;
	
	private String key ;
	private String value;
	
	@Override
	public ByteBuf encode() {
		// TODO Auto-generated method stub
		ByteBuf buffer = null;
		
		//메시지 스트링으로 변환 
		StringBuffer sendMsg = new StringBuffer();
		sendMsg.append( this.getValue() );
		
		buffer = Unpooled.buffer( sendMsg.length() );
		buffer.writeBytes( String.valueOf( sendMsg ).getBytes() );
		
		return buffer;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "RollScreenControl [key=" + key + ", value=" + value + "]";
	}
	
}
