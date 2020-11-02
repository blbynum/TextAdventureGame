package com.questquest.model.character;

import com.questquest.model.inventory.collections.Inventory;

public class Player {
    private int playerID;
    private final String name;
    private Inventory inventory;

    public Player(int playerID, String name, Inventory inventory) {
        this.playerID = playerID;
        this.name = name;
        if(inventory != null) {
            this.inventory = inventory;
        } else {
            this.inventory = new Inventory(10, null);
        }
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
