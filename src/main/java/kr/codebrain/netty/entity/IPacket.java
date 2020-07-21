package kr.codebrain.netty.entity;

import io.netty.buffer.ByteBuf;

public interface IPacket {
	public ByteBuf encode();
}
