package com.redrock.practice;

import java.io.Serializable;

public class Player implements Serializable {
    public int id;      // ID duy nhất của người chơi (do Kryonet gán)
    public float x;     // Tọa độ X
    public float y;     // Tọa độ Y
    public int health;  // Máu (thêm để mở rộng sau này)

    // Constructor không tham số (yêu cầu của Kryonet để deserialize)
    public Player() {
    }

    // Constructor có tham số để khởi tạo người chơi
    public Player(int id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.health = 100; // Máu mặc định
    }

    @Override
    public String toString() {
        return "Player{id=" + id + ", x=" + x + ", y=" + y + ", health=" + health + "}";
    }
}

