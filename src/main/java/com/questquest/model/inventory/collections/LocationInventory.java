package com.questquest.model.inventory.collections;

import com.questquest.model.inventory.items.Item;

import java.util.Map;

public class LocationInventory extends Inventory {

    public LocationInventory(int capacity, Map<Integer, Item> slots) {
        super(capacity, slots);
    }
}
