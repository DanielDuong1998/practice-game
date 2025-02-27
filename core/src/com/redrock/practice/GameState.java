package com.redrock.practice;

import java.util.HashMap;

public class GameState {
    public HashMap<Integer, Player> players = new HashMap<>();
    public float circleX = 400;    // Tâm vòng bo
    public float circleY = 300;
    public float circleRadius = 200; // Bán kính ban đầu

    @Override
    public String toString() {
        return "GameState{" +
                "players=" + players +
                ", circleX=" + circleX +
                ", circleY=" + circleY +
                ", circleRadius=" + circleRadius +
                '}';
    }
}

