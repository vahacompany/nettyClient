package kr.codebrain.netty.entity;

import java.io.Serializable;

public class NettyStatus implements Serializable {

	private static final long serialVersionUID = -1438078001456390154L;

	private String cmd ;
	private String address;
	private String status;
	private String err;
	
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	
}
