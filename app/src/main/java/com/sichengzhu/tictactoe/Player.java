package com.sichengzhu.tictactoe;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private char playerMarker;

    public Player(String name, char playerMarker){
        this.name = name;
        this.playerMarker = playerMarker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getPlayerMarker() {
        return playerMarker;
    }

    public void setPlayerMarker(char playerMarker) {
        this.playerMarker = playerMarker;
    }
}
