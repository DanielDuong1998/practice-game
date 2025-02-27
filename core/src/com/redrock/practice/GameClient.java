package com.redrock.practice;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GameClient {
    private final String host; // Địa chỉ server
    private final int port;    // Cổng server
    private Channel channel;   // Kênh kết nối
    private EventLoopGroup group;

    public GameClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder()); // Decode dữ liệu từ server
                            pipeline.addLast(new StringEncoder()); // Encode dữ liệu gửi đi
                            pipeline.addLast(new GameClientHandler()); // Xử lý dữ liệu
                        }
                    });

            // Kết nối đến server
            ChannelFuture future = bootstrap.connect(host, port).sync();
            channel = future.channel();
            System.out.println("Connected to server at " + host + ":" + port);
        } catch (Exception e) {
            group.shutdownGracefully();
            throw e;
        }
    }

    public void sendMessage(String message) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(message);
        }
    }

    public void stop() {
        if (channel != null) {
            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }
}
