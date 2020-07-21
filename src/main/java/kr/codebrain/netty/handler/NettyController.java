package kr.codebrain.netty.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import kr.codebrain.netty.NettyClient;
import kr.codebrain.netty.entity.NettyCheck;
import kr.codebrain.netty.entity.NettyConstant;
import kr.codebrain.netty.entity.NettyControl;

public class NettyController {
	
	private ChannelHandlerContext ctx;
	private StringBuffer controlData;

	public void startTimeAction(ChannelHandlerContext ctx, NettyClient client){
		this.ctx = ctx;
		
		controlData = new StringBuffer();

		lineCheck();
		requestAllData();
		//monitorControl(client);
		sendControl();
	}
	
	/*
	 * Redis 제어요청 온 것 모니터링 하여 변수에 저장
	 */
	public void monitorControl(NettyClient client){
		int sleepSec = 500;
		final ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		exec.scheduleAtFixedRate(new Runnable(){
            public void run(){
                try {
                	
                	NettyControl control = client.getControlData();
                	if(control != null)	{
                		controlData.append(control.getValue());
                	}
                	
                } catch (Exception e) {
                    e.printStackTrace();
                    //exec.shutdown() ;
                }
            }
        }, 0, sleepSec, TimeUnit.MILLISECONDS);
	}
	
	/*
	 * Control 서버에 전송
	 */
	public void sendControl(){
		int sleepSec = 1000;
		controlData.append("senddata**************");
		final ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		exec.scheduleAtFixedRate(new Runnable(){
            public void run(){
                try {
                	if(controlData.length() > 0)	{
                		NettyControl control = new NettyControl();
                		control.setValue(controlData.toString());
                		ctx.writeAndFlush(control);
               			controlData = new StringBuffer();
                	}
                } catch (Exception e) {
                    e.printStackTrace();
                    //exec.shutdown() ;
                }
            }
        }, 0, sleepSec, TimeUnit.MILLISECONDS);
	}
	
	/*
	 * 초기 데이터 요청으로 REdis에 상태 저장
	 */
	public void requestAllData(){
		//첫번째 앱 구동 시 SI로 부터 전체자료 요청 받아 Redis에 저장 한다.
		//전체 자료 요청 protocol (R|YYMMDDhhmm|S)
		//RollScreenCheck.java 에 데이터 set 하여 넘겨준다.
		NettyCheck allRequest = new NettyCheck();
		allRequest.setCmd(NettyConstant.CMD_REQ);
		allRequest.setControl(NettyConstant.CONTROL_LINE);
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmm");
		allRequest.setAddress(format.format(now));

		ctx.writeAndFlush(allRequest);
	}
	
	/*
	 * 1분에 한번씩 라인체크를 위해서 서버에 데이터 전송
	 */
	public void lineCheck(){
		int sleepSec = 60;
		
		// 주기적인 작업은 위함
		final ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		
		exec.scheduleAtFixedRate(new Runnable(){
            
            public void run(){
                try {
                	
                	NettyCheck checkLine = new NettyCheck();
            		checkLine.setCmd(NettyConstant.CMD_LINE);
            		checkLine.setControl(NettyConstant.CONTROL_LINE);
            		Date now = new Date();
            		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmm");
            		checkLine.setAddress(format.format(now));

            		ctx.writeAndFlush(checkLine);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 에러 발생시 Executor를 중지시킨다
                    //exec.shutdown() ;
                }
            }
        }, 0, sleepSec, TimeUnit.SECONDS);
	}
	

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	
}
