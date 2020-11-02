package com.questquest.model.map;

import com.questquest.model.character.npc.NPC;
import com.questquest.model.inventory.collections.Inventory;
import com.questquest.model.inventory.items.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ben Bynum on 10/31/2020
 */
public class Location {
    public final static ArrayList<String> directions = new ArrayList<String>(Arrays.asList("NORTH", "SOUTH", "EAST", "WEST"));
    private final int locationID;
    private final String name;
    private final String description;
    private Map<String, Integer> exits;
    private final Map<String, NPC> npcs;
    private Inventory inventory;

    public Location(int locationID, String name, String description, Map<String, Integer> exits, Map<String, NPC> npcs, Inventory inventory) {
        this.locationID = locationID;
        this.name = name;
        this.description = description;
        if(exits != null) {
            this.exits = new HashMap<>(exits);
        } else {
            this.exits = new HashMap<>();
        }
        if(npcs != null) {
            this.npcs = new HashMap<>(npcs);
        } else {
            this.npcs = new HashMap<>();
        }
        if(inventory != null) {
            this.inventory = inventory;
        } else {
            this.inventory = new Inventory(100, null);
        }
    }

    public int getLocationID() {
        return locationID;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns description of location plus description of present NPCs.
     * @return
     */
    public String getLongDescription() {
        String fullDescription = getShortDescription() + " " + getCharacterDescription();


        return fullDescription;
    }

    /**
     * Returns location description without character or item information.
     * @return
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Returns a sentence describing which NPCs are present in location. All this trouble for the oxford comma and it was worth it.
     * @return
     */
    public String getCharacterDescription() {
        String characterDescription = "";
        if (npcs.size() > 0) {
            String npcNames = "";
            String delimiter = "";
            int counter = 1;
            for(String npc : npcs.keySet()) {
                String npcName = "";
                if (counter == npcs.size()) {
                    delimiter = " ";
                } else if (counter == npcs.size() - 1) {
                    if(npcs.size() > 2) {
                        delimiter = ", and ";
                    } else {
                        delimiter = " and ";
                    }
                } else {
                    delimiter = ", ";
                }
                npcName = npcs.get(npc).getName() + delimiter;
                npcNames += npcName;
                counter++;
            }
            characterDescription += npcNames;
            if(npcs.size() > 1) {
                characterDescription += "are also here.";
            } else if (npcs.size() == 1) {
                characterDescription += "is also here.";
            }
        }
        return characterDescription;
    }

    public Map<String, Integer> getExits() {
        return new HashMap<>(exits);
    }

    public Map<String, NPC> getNPCs() {
        return new HashMap<>(npcs);
    }

    public void setExits(Map<String, Integer> exits) {
        this.exits = exits;
    }

    public Item takeItem(String itemName) {
        return inventory.removeItem(itemName);
    }

    public boolean placeItem(Item item) {
        return inventory.addItem(item);
    }

    public Inventory getInventory() {
        return inventory;
    }

}
