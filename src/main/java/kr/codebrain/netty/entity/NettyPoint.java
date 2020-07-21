package kr.codebrain.netty.entity;

import java.io.Serializable;

public class NettyPoint implements Serializable {

	private static final long serialVersionUID = 7323335567193772475L;
	
	private String floorName;
	private String cbNo;
	private String curtainAdress;
	
	public String getFloorName() {
		return floorName;
	}
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	public String getCbNo() {
		return cbNo;
	}
	public void setCbNo(String cbNo) {
		this.cbNo = cbNo;
	}
	public String getCurtainAdress() {
		return curtainAdress;
	}
	public void setCurtainAdress(String curtainAdress) {
		this.curtainAdress = curtainAdress;
	}

}
