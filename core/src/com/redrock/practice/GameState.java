package com.redrock.practice;

import java.util.HashMap;

public class GameState {
    public HashMap<Integer, Player> players; // ID -> vị trí, máu, v.v.
    public float circleX, circleY, circleRadius; // Vòng bo

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
