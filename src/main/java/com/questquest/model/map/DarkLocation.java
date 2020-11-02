package com.questquest.model.map;

import com.questquest.Main;
import com.questquest.model.character.npc.NPC;
import com.questquest.model.inventory.collections.Inventory;
import com.questquest.model.inventory.items.Torch;

import java.util.HashMap;
import java.util.Map;

public class DarkLocation extends Location {

    private final Map<String, Integer> litExits;
    private final String litByPlayerDescription;
    private final String litByLocationDescription;
    private Map<String, Integer> visibleExits;

    public DarkLocation(int locationID, String name, String darkDescription, String litByPlayerDescription, String litByLocationDescription, Map<String, Integer> darkExits, Map<String, Integer> litExits, Map<String, NPC> npcs, Inventory inventory) {
        super(locationID, name, darkDescription, darkExits, npcs, inventory);
        this.litExits = litExits;
        this.litByPlayerDescription = litByPlayerDescription;
        this.litByLocationDescription = litByLocationDescription;
    }

    private void setVisibleExits() {
        Inventory currentPlayerInventory = Main.currentPlayer.getInventory();
        boolean isLit = currentPlayerInventory.doesItemTypeExist(Torch.class) || super.getInventory().doesItemTypeExist(Torch.class);
        visibleExits = new HashMap<>(super.getExits());
        if (isLit) {
            for (String direction : litExits.keySet()) {
                visibleExits.put(direction, litExits.get(direction));
            }
            super.setExits(new HashMap<>(visibleExits));
        }
    }

    @Override
    public String getLongDescription() {
        if (Main.currentPlayer.getInventory().doesItemTypeExist(Torch.class)) {
            return litByPlayerDescription + " " + super.getCharacterDescription();
        } else if (super.getInventory().doesItemTypeExist(Torch.class)) {
            return litByLocationDescription + " " + super.getCharacterDescription();
        } else {
            return super.getLongDescription();
        }
    }

    @Override
    public Map<String, Integer> getExits() {
        setVisibleExits();
        return new HashMap<>(visibleExits);
    }
}
