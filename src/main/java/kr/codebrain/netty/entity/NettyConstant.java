package kr.codebrain.netty.entity;

public class NettyConstant {
	public static final String STX = "(";
	public static final String DIV = "|";
	public static final String ETX = ")";
	
	//제어
	public static final String CMD_CUSTOM = "C";
	public static final String CMD_SCOPE = "A";
	public static final String CMD_LINE = "L";
	public static final String CMD_REQ = "R";
	
	public static final String CONTROL_OPEN = "O";
	public static final String CONTROL_CLOSE = "C";
	public static final String CONTROL_STOP = "S";
	public static final String CONTROL_RATE = "P";
	public static final String CONTROL_LINE = "S";
	
	//상태
	public static final String CMD_STATUS = "B";
	public static final String STATUS_OPEN = "O";
	public static final String STATUS_CLOSE = "C";
	public static final String STATUS_STOP = "S";
	public static final String ERR_Y = "Y";
	public static final String ERR_N = "N";
	
	//길이
	public static final int MASSAGE_LEN = 15;
	public static final int SCOPE_LEN = 12;
	public static final int LINE_LEN = 16;
	public static final int STATUS_LEN = 25;
}
