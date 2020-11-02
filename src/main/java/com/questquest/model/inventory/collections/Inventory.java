package com.questquest.model.inventory.collections;

import com.questquest.model.inventory.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private static ArrayList<Integer> inventoryIDs;
    private final int inventoryID;
    private final int capacity;
    private Map<Integer, Item> slots;

    public Inventory(int capacity, Map<Integer, Item> slots) {
        if(inventoryIDs == null) {
            inventoryIDs = new ArrayList<>();
            inventoryID = 1;
            inventoryIDs.add(inventoryID);
        } else {
            this.inventoryID = inventoryIDs.size();
        }
        this.capacity = capacity;
        if(slots != null) {
            this.slots = new HashMap<>(slots);
        } else {
            this.slots = new HashMap<>();
        }
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public int getCapacity() {
        return capacity;
    }

    public Map<Integer, Item> getSlots() {
        return new HashMap<>(slots);
    }

    /**
     * Add item to inventory if capacity is not reached.
     * @param newItem
     * @return returns boolean signifying whether item was added or not.
     */
    public boolean addItem(Item newItem) {
        if(newItem == null) {
            return false;
        }
        if (slots.size() < capacity) {
            slots.put(slots.size()+1, newItem);
            return true;
        } else {
            System.out.println("Your inventory is full");
            return false;
        }
    }

    /**
     * Remove item from inventory. Will return null if item not found.
     * @param itemID
     * @return
     */
    public Item removeItem(int itemID) {
        if(!slots.containsKey(itemID)) {
            System.out.println("Item not found");
        }
        return slots.remove(itemID);
    }

    /**
     * Remove item from inventory. Will return null if item not found.
     * @param itemName
     * @return
     */
    public Item removeItem(String itemName) {
        for(int itemID : slots.keySet()) {
            if (slots.get(itemID).getName().equals(itemName)) {
                return slots.remove(itemID);
            }
        }
        return null;
    }

    /**
     * Inspect an item in the inventory.
     * @param itemID
     */
    public void inspectItem(int itemID) {
        if(slots.containsKey(itemID)) {
            System.out.println(slots.get(itemID).getDescription());
        } else {
            System.out.println("Not a valid item number.");
        }
    }

    /**
     * Inspect an item in the inventory.
     * @param itemName
     */
    public void inspectItem(String itemName) {
        for(int itemID : slots.keySet()) {
            if (slots.get(itemID).getName().equals(itemName)) {
                System.out.println(slots.get(itemID).getDescription());
                return;
            }
        }
        System.out.println("Not a valid item name.");
    }

    /**
     * Print ID, Name, and Quantity of each item in inventory.
     */
    public void printItems() {
        if(slots.keySet().size() > 0) {
            System.out.println("Items:");
            for(Integer itemID : slots.keySet()) {
                Item item = slots.get(itemID);
                System.out.println(itemID + ". " + item.getName() + " - Quanity: " + item.getQuantity());
            }
        } else {
            System.out.println("Your inventory is empty.");
        }

    }

    public boolean doesItemIDExist(int itemID) {
        if(slots.keySet().contains(itemID)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean doesItemTypeExist(Class<?> cls) {
        for (int itemID : slots.keySet()) {
            if(cls.isInstance(slots.get(itemID))) {
                return true;
            }
        }
        return false;
    }

}
