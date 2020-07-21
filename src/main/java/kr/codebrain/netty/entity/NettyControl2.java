package kr.codebrain.netty.entity;

import java.io.Serializable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyControl2 implements Serializable, IPacket {

	private static final long serialVersionUID = -8259739583103038269L;
	
	private String cmd ;
	private String address;
	private String control;
	private String data;
	
	@Override
	public ByteBuf encode() {
		// TODO Auto-generated method stub
		ByteBuf buffer = null;
		
		//메시지 스트링으로 변환 
		StringBuffer sendMsg = new StringBuffer();
		sendMsg.append( NettyConstant.STX );
		sendMsg.append( this.getCmd() );
		sendMsg.append( NettyConstant.DIV );
		sendMsg.append( this.getAddress() );
		sendMsg.append( NettyConstant.DIV );
		sendMsg.append( this.getControl() );
		sendMsg.append( this.getData() );
		sendMsg.append( NettyConstant.ETX );
		
		if( this.getCmd().equals( NettyConstant.CMD_CUSTOM ) ){
			buffer = Unpooled.buffer( NettyConstant.MASSAGE_LEN );
			buffer.writeBytes( String.valueOf( sendMsg ).getBytes() );
			
			return buffer;
			
		}else if( this.getCmd().equals( NettyConstant.CMD_SCOPE ) ){
			buffer = Unpooled.buffer( NettyConstant.SCOPE_LEN );
			buffer.writeBytes( String.valueOf( sendMsg ).getBytes() );
			
			return buffer;
			
		}
		return null;
	}
	
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
