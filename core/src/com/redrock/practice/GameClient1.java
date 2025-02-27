package com.redrock.practice;

import com.badlogic.gdx.ApplicationAdapter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class GameClient1 extends ApplicationAdapter {
//    private static final String SERVER_IP = "localhost"; // IP của Server
    private static final String SERVER_IP = "192.168.1.3"; // IP của Server
    private static final int SERVER_PORT = 54555;

    private EventLoopGroup group;
    private Channel channel;

    @Override
    public void create() {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                                new LineBasedFrameDecoder(1024), // Xử lý dữ liệu theo từng dòng
                                new StringDecoder(),  // Chuyển byte thành String
                                new StringEncoder(),  // Chuyển String thành byte để gửi
                                new GameClientHandler1() // Xử lý dữ liệu từ Server
                        );
                    }
                });

        try {
            ChannelFuture future = bootstrap.connect(SERVER_IP, SERVER_PORT).sync();
            channel = future.channel();
            System.out.println("✅ Kết nối tới Server thành công!");
//            channel.writeAndFlush("Start Send");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        if (channel != null && channel.isActive()) {
            // Gửi dữ liệu lên server mỗi frame (ví dụ: tọa độ nhân vật)
            channel.writeAndFlush("PLAYER_MOVE 100 200\n");
        }
    }

    @Override
    public void dispose() {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush("DISCONNECT").addListener(ChannelFutureListener.CLOSE);
        }
        group.shutdownGracefully();
    }
}