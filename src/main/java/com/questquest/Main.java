package com.questquest;

import com.questquest.model.character.Player;

public class Main {
    public static Player currentPlayer;

    public static void main(String[] args) {
        currentPlayer = new Player(0, "Lonk", null);
        Game game = new Game(currentPlayer, 0);
        game.play();
    }

}
