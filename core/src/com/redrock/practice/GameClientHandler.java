package com.redrock.practice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GameClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        // Nhận tin nhắn từ server
        System.out.println("Server says: " + msg);
        // Xử lý dữ liệu tùy theo game của bạn (ví dụ: cập nhật vị trí người chơi)
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Client connected to server");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("Disconnected from server");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}