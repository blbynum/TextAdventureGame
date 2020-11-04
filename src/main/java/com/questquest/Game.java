package com.questquest;

import com.questquest.model.character.Player;
import com.questquest.model.character.npc.NPC;
import com.questquest.model.character.npc.SarcasticNPC;
import com.questquest.model.inventory.items.Torch;
import com.questquest.model.map.DarkLocation;
import com.questquest.model.map.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game extends Thread {
    private Map<Integer, Location> locations;
    private int currentLocationID;
    private Map<String, Integer> currentExits;
    private Player currentPlayer;
    private Scanner scanner;

    public Game(Player currentPlayer, int startingLocation) {
        this.currentPlayer = currentPlayer;
        this.locations = initializeLocations();
        this.currentLocationID = startingLocation;
        this.currentExits = locations.get(currentLocationID).getExits();

    }

    @Override
    public void run() {
        scanner = new Scanner(System.in);
        printStartText();

        boolean quit = false;
        while (!quit) {
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();
            quit = parseCommand(command, getPrimaryCommand(command), getSecondaryCommand(command));
            System.out.println("----------------------------------------------------------");
        }
    }

    /**
     * Parse the commands entered by the player.
     * @param fullCommand The full command entered by the player.
     * @param primaryCommand The first word of the command.
     * @param secondaryCommand The rest of the command after the first word.
     * @return A boolean that will be true if player has chosen to quit.
     */
    private boolean parseCommand(String fullCommand, String primaryCommand, String secondaryCommand) {
        // TODO: Create lists of various ways to type each command. Replace Cases
        switch (primaryCommand) {
            case "quit":
            case "q":
                printQuitText();
                return true;
            case "help":
                printHelpText();
                break;
            case "map":
                printAvailableExits();
                break;
            case "exit":
                exit(secondaryCommand);
                break;
            case "inspect":
                inspect(secondaryCommand);
                break;
            case "interact":
                interact(secondaryCommand);
                break;
            case "inventory":
                inspectInventory(secondaryCommand);
                break;
            case "take":
                take(secondaryCommand);
                break;
            case "place":
                place(secondaryCommand);
                break;
            default:
                System.out.println("\"" + fullCommand + "\" is not a valid command. Try typing \"help\" for a list of things to try.");
        }
        return false;
    }

    /**
     * Inspect the players surroundings or a specific character or item (not an inventory item).
     * @param objectName Name of object to inspect. Pass an empty string to inspect current location.
     */
    private void inspect(String objectName) {
        //TODO: Make this case insensitive
        if (objectName.isEmpty()) {
            System.out.println(locations.get(currentLocationID).getLongDescription());
        } else {
            //TODO: Write code for inspecting item or character
        }
    }

    /**
     * Place an item from the player's inventory in the current location's inventory.
     * @param itemNameEntered Name of item to place
     */
    private void place(String itemNameEntered) {
        //TODO: Make this case insensitive
        if(itemNameEntered.isEmpty()) {
            System.out.println("What would you like to place here?");
        } else {
            int itemID;
            try {
                itemID = Integer.parseInt(itemNameEntered);
                boolean success = false;
                String itemName = "";
                if(currentPlayer.getInventory().doesItemIDExist(itemID)) {
                    itemName = currentPlayer.getInventory().getSlots().get(itemID).getName();
                    success = locations.get(currentLocationID).placeItem(currentPlayer.getInventory().removeItem(itemID));
                }
                if(success){
                    System.out.println("You place " + itemName + " in " + locations.get(currentLocationID).getName() + ".");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Item number must be an integer value.");
            }
        }
    }

    /**
     * Take an item from the current location and place it in the player's inventory.
     * @param itemNameEntered Name of item to take
     */
    private void take(String itemNameEntered) {
        //TODO: Make this case insensitive
        if(itemNameEntered.isEmpty()) {
            System.out.println("What would you like to take?");
        } else {
            try {
                boolean success = currentPlayer.getInventory().addItem(locations.get(currentLocationID).takeItem(itemNameEntered));
                if (success) {
                    System.out.println("You take " + itemNameEntered + " and add it to your inventory.");
                } else {
                    System.out.println(itemNameEntered + " is not a valid item.");
                }
            } catch (NullPointerException npe) {
                System.out.println(itemNameEntered + " is not a valid item.");
            }
        }
    }

    /**
     * Inspect the player's inventory. Either print a list or get the description of a specific item.
     * @param ItemIDString ID of item in inventory to inspect. Pass an empty string to print entire inventory.
     */
    private void inspectInventory(String ItemIDString) {
        if(ItemIDString.isEmpty()) {
            currentPlayer.getInventory().printItems();
        } else {
            int itemID;
            try {
                itemID = Integer.parseInt(ItemIDString);
                currentPlayer.getInventory().inspectItem(itemID);
            } catch (NumberFormatException nfe) {
                System.out.println("Item number must be an integer value.");
            }

        }
    }

    /**
     * Get the first word from the full command string entered by player.
     * @param fullCommand Full command string entered by player
     * @return
     */
    private String getPrimaryCommand(String fullCommand) {
        String[] words = fullCommand.split(" ");
        return words[0];
    }

    /**
     * Get everything after the first word from the full command string entered by player.
     * @param fullCommand Full command string entered by player
     * @return
     */
    private String getSecondaryCommand(String fullCommand) {
        String[] words = fullCommand.split(" ");
        StringBuilder secondaryCommand = new StringBuilder();
        for(int i =1; i< words.length; i++) {
            if (!(i == words.length -1)) {
                secondaryCommand.append(words[i]).append(" ");
            } else {
                secondaryCommand.append(words[i]);
            }
        }
        return secondaryCommand.toString();
    }

    /**
     * Interact with an NPC
     * @param npcName The name of an NPC that exists in current location.
     */
    private void interact(String npcName) {
        //TODO: Make this case insensitive
        //TODO: Add functionality to interact with items
        if(npcName.isEmpty()) {
            System.out.println("You interact with nothing. Nothing happens. Perhaps try interacting with something?");
            return;
        }
        Map<String, NPC> npcs = locations.get(currentLocationID).getNPCs();
        if(npcs.containsKey(npcName)) {
            NPC npc = npcs.get(npcName);
            npc.interact(scanner);
        } else {
            System.out.println(npcName + " is  not here. Are you okay?");
        }
    }

    /**
     * Moves the player to the specified available location.
     * @param direction The direction of the location the player wants to move to.
     */
    private void exit(String direction) {
        if (direction.isEmpty()) {
            System.out.println("You must specify a direction");
            return;
        }
        if(!Location.directions.contains(direction.toUpperCase())) {
            System.out.println("That is not a valid direction.");
        } else {
            direction = direction.toUpperCase();
            if(currentExits.containsKey(direction)) {
                currentLocationID = currentExits.get(direction);
                currentExits = locations.get(currentLocationID).getExits();
                System.out.println(locations.get(currentLocationID).getLongDescription());
            }else {
                System.out.println("You cannot go in that direction");
            }
        }
    }

    /**
     * Prints currently available exits.
     */
    private void printAvailableExits() {
        System.out.println("Available exits: ");
        for(String exit: currentExits.keySet()) {
            System.out.println(exit + " - " + locations.get(locations.get(currentLocationID).getExits().get(exit)).getName());
        }
        System.out.println();
    }

    /**
     * Prints a list of helpful commands player can use.
     */
    private void printHelpText() {
        //TODO: Create list of known commands that will be listed upon help command call.
        // Ability to discover new commands throughout the game.
        System.out.println("Known Commands:");
        System.out.println("Quit ----------------------------- Exit the game");
        System.out.println("Help ----------------------------- Get a list of known commands");
        System.out.println("Map ------------------------------ View known exits");
        System.out.println("Exit <North|South|East|West> ----- Move to a new location");
        System.out.println("Inspect <optional:NPC|Item> ------ Inspect your surroundings or a specific thing");
        System.out.println("Interact <NPC|Item> -------------- Interact with an NPC or Item");
        System.out.println("Inventory <Item Number> ---------- Check what's in your inventory or inspect an item in your inventory");
        System.out.println("Take <Item Name> ----------------- Take an item from your current location");
        System.out.println("Place <Item ID> ------------------- Place an item from your inventory at your current location....you might lose it.");
    }

    /**
     * Prints text for game start.
     */
    private void printStartText() {
        System.out.println("----------------------------------------------------------");
        System.out.println(locations.get(currentLocationID).getLongDescription());
    }

    /**
     * Prints text for game exit.
     */
    private void printQuitText() {
        System.out.println("You begin to wake up...was it all a dream?");
        System.out.println("----------------------------------------------------------");
    }

    /**
     * Initialized all location data. This is a temporary solution until configuration classes are created.
     * @return
     */
    private Map<Integer, Location> initializeLocations() {
        //TODO: Move all of this out into configuration classes that load this data from files.
        locations = new HashMap<>();
        Map<String, Integer> tempExit = new HashMap<>();
        Map<String, Integer> tempDarkExit = new HashMap<>();
        Map<String, NPC> tempNPCs = new HashMap<>();
        tempExit.put("SOUTH", 1);
        locations.put(0, new Location(0, "Home", "You are at home in your thatched-roof cottage.", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("NORTH", 0);
        tempExit.put("SOUTH", 12);
        tempExit.put("EAST", 3);
        tempExit.put("WEST", 2);
        tempNPCs.put("Larry", new NPC(1, "Larry", "It's just Larry. He lives here. Larry's a pretty nice guy.", null, null));
        tempNPCs.put("Tom", new NPC(2, "Tom", "Tom is just a regular townsfolk peasant.", null, null));
        tempNPCs.put("Jim", new NPC(3, "Jim", "Jim is Jim.", null, null));
        locations.put(1, new Location(1, "Town","You are standing in the town square next to a well. The townsfolk are going about their days.", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("EAST", 1);
        tempExit.put("SOUTH", 9);
        tempNPCs.put("Wizard", new SarcasticNPC(4, "Wizard", "The old wizard has a long grey robe, a wrinkled face, and looks quite sarcastic.", null, null));
        locations.put(2, new Location(2, "Wizard's House", "You are in the Wizard's house. Books line the walls like a library.", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("NORTH", 4);
        tempExit.put("WEST", 1);
        locations.put(3, new Location(3, "Forest", "You are in the forest. The trees are massive.", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("NORTH", 5);
        locations.put(4, new Location(4, "Evil Woods", "You are in the evil woods. Is something watching you?", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("SOUTH", 4);
        tempExit.put("WEST", 6);
        locations.put(5, new Location(5, "Fiery Badlands", "You are in the Fiery Badlands. Nothing but black rock and lava flows.", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("EAST", 5);
        tempExit.put("WEST", 7);
        locations.put(6, new Location(6, "The Great Mountain", "You are on The Great Mountain. Hey look you can see your house!", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("EAST", 8);
        tempExit.put("WEST", 6);
        locations.put(7, new Location(7, "Friendly Valley", "You are in the Friendly Valley. A quaint brook flows as the birds chirp.", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("EAST", 7);
        tempExit.put("SOUTH", 1);
        locations.put(8, new Location(8, "Mysterious Old Man's House", "You are in the house of a mysterious old man. He seems friendly, but familiar...", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("SOUTH", 10);
        locations.put(9, new Location(9, "Dungeon Entrance", "You are in the entrance of a dungeon. Looks like the door back to the Wizard's house is locked from this side.", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempDarkExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempDarkExit.put("NORTH", 9);
        tempExit.put("EAST", 11);
        locations.put(10, new DarkLocation(10, "Dungeon Halls", "You are in the Dungeon Halls. It's pitch black. You hear water dripping as large rats run in the shadows as you stumble in the darkness. Chains clink to the east. An exit maybe?", "You are in the Dungeon Halls. Your torch lights the way. You hear water dripping as large rats run in the shadows.", "You are in the Dungeon Halls. A single torch is the only source of light. You hear water dripping as large rats run in the shadows.", tempExit, tempDarkExit, tempNPCs, null));
        locations.get(10).getInventory().addItem(new Torch());

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("NORTH", 12);
        tempExit.put("WEST", 10);
        locations.put(11, new Location(11, "Dungeon Cells", "You are in the Dungeon cells. It seems long abandoned. There are skeletons chained in some of the cells.", tempExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempDarkExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("NORTH", 1);
        tempDarkExit.put("EAST", 13);
        tempDarkExit.put("SOUTH", 11);
        locations.put(12, new DarkLocation(12, "Cave", "You are in a cave. It's too dark to see anything but the cave entrance to the north.", "Your torch lights the cave. Are those two doors? In a cave???", "A torch lights the cave.  Are those two doors? In a cave???", tempExit, tempDarkExit, tempNPCs, null));

        tempExit = new HashMap<>();
        tempNPCs = new HashMap<>();
        tempExit.put("WEST", 12);
        locations.put(13, new Location(13, "Secret Room", "You are in a secret room found in a cave! How did this get here? What's it for?", tempExit, tempNPCs, null));

        return locations;
    }

}
