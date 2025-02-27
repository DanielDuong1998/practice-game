package com.redrock.practice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameClientHandler1 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = (String) msg;
        System.out.println("📩 Nhận dữ liệu từ server: " + message);

        // Xử lý dữ liệu theo loại thông điệp từ server
        if (message.startsWith("UPDATE_POSITION")) {
            // Ví dụ: Server gửi "UPDATE_POSITION player1 200 300"
            String[] parts = message.split(" ");
            String playerId = parts[1];
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[3]);

            // TODO: Cập nhật tọa độ của nhân vật trong game
            System.out.println("🔄 Cập nhật vị trí " + playerId + " => X: " + x + ", Y: " + y);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}