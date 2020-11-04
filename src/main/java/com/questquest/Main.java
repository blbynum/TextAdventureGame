package com.questquest;

import com.questquest.model.character.Player;

public class Main {
    public static Player currentPlayer;

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

}
