package com.questquest.model.inventory.items;

import java.util.ArrayList;

public abstract class Item {
    private static ArrayList<Integer> itemIDs;
    private int itemID;
    private final String name;
    private final String description;
    private final int maxStack;
    private int quantity;
    private final boolean takeable;

    public Item(String name, String description, int maxStack, int quantity, boolean takeable) {
        if(itemIDs == null) {
            itemIDs = new ArrayList<>();
            itemID = 0;
            itemIDs.add(itemID);
        } else {
            this.itemID = itemIDs.size();
        }
        this.name = name;
        this.description = description;
        this.maxStack = maxStack;
        this.quantity = quantity;
        this.takeable = takeable;
    }

    public int getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isTakeable() {
        return takeable;
    }
}
