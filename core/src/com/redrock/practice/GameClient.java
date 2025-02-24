package com.redrock.practice;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class GameClient extends ApplicationAdapter {
    private SpriteBatch batch;
    private Client client;
    private GameState gameState;
    private int myPlayerId;
    private Texture texture;
    private boolean isConnected = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture("texture/fish.png"); // Đảm bảo có file player.png trong assets

        // Khởi tạo client
        client = new Client();
        client.start();

        // Đăng ký các class
        client.getKryo().register(PlayerInput.class);
        client.getKryo().register(GameState.class);
        client.getKryo().register(Player.class);
        client.getKryo().register(java.util.HashMap.class);
        client.getKryo().register(java.util.Map.class);

        // Chạy kết nối trong thread riêng
        new Thread(() -> {
            try {
                client.connect(5000, "127.0.0.1", 54555, 54777); // Kết nối đến server
                myPlayerId = client.getID();
                isConnected = true;
                System.out.println("Connected to server. Player ID: " + myPlayerId);
            } catch (IOException e) {
                e.printStackTrace();
                Gdx.app.exit(); // Thoát nếu không kết nối được
            }
        }).start();

        // Listener để nhận dữ liệu từ server
        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof GameState) {
                    gameState = (GameState) object;
                    System.out.println("Received: " + gameState);
                }
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Gọi client.update() trong thread chính để duy trì kết nối
        try {
            client.update(10); // Cập nhật Kryonet với timeout nhỏ
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Gửi input nếu đã kết nối
        if (isConnected) {
            PlayerInput input = new PlayerInput(myPlayerId, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            client.sendTCP(input);
        }

        // Hiển thị trạng thái game
        if (gameState != null) {
            batch.begin();
            for (Player player : gameState.players.values()) {
                batch.draw(texture, player.x, player.y, 32, 32);
            }
            batch.end();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        client.stop();
    }
}
