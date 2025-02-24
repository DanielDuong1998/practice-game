package com.redrock.practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.io.Serializable;

public class PlayerInput implements Serializable {
    public int playerId;  // ID của người chơi gửi input
    public float x;       // Tọa độ X muốn cập nhật
    public float y;       // Tọa độ Y muốn cập nhật
    public boolean isShooting; // Trạng thái bắn (thêm để mở rộng)

    // Constructor không tham số (yêu cầu của Kryonet)
    public PlayerInput() {
    }

    // Constructor có tham số
    public PlayerInput(int playerId, float x, float y) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.isShooting = false; // Mặc định không bắn
    }

    @Override
    public String toString() {
        return "PlayerInput{playerId=" + playerId + ", x=" + x + ", y=" + y + ", isShooting=" + isShooting + "}";
    }
}