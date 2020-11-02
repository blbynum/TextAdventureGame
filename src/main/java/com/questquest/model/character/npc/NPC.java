package com.questquest.model.character.npc;

import java.util.*;

public class NPC {
    private int npcID;
    private final String name;
    private final String description;
    private final Map<String, String> preparedResponses;
    private final List<String> randomResponses;

    public NPC(int npcID, String name, String description, Map<String, String> preparedResponses, List<String> randomResponses) {
        this.npcID = npcID;
        this.name = name;
        this.description = description;
        if(preparedResponses != null) {
            this.preparedResponses = new HashMap<>(preparedResponses);
        } else {
            this.preparedResponses = getDefaultPreparedResponses();
        }
        if(randomResponses != null) {
            this.randomResponses = new ArrayList<>(randomResponses);
        } else {
            this.randomResponses = getDefaultRandomResponses();
        }
    }

    private List<String> getDefaultRandomResponses() {
        List<String> randomResponses = new ArrayList<String>() {{
            add("What are you on about?");
            add("I have no idea what you're saying.");
            add("What?");
        }};
        return randomResponses;
    }

    private Map<String, String> getDefaultPreparedResponses() {
        Map<String, String> preparedResponses = new HashMap<>();
        preparedResponses.put("Hello", "Why hello there!");
        preparedResponses.put("hello", "Hi!");
        preparedResponses.put("Hey", "Hey yourself!");
        preparedResponses.put("hey", "Hey yourself!");
        return preparedResponses;
    }

    public int getNpcID() {
        return npcID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getPreparedResponses() {
        return preparedResponses;
    }

    public List<String> getRandomResponses() {
        return randomResponses;
    }

    public void interact(Scanner scanner) {
        System.out.println(name + ": Hello there.");
        boolean goodbye = false;
        while(!goodbye) {
                System.out.print("You: ");
            String interaction = scanner.nextLine();
            if(interaction.toLowerCase().contains("bye")) {
                System.out.println(name + ": Farewell!");
                goodbye = true;
            } else {
                if(preparedResponses.containsKey(interaction.toLowerCase())) {
                    System.out.println(name + ": " + preparedResponses.get(interaction.toLowerCase()));;
                } else {
                    Random random = new Random();
                    int randomIndex = random.nextInt(randomResponses.size());
                    String randomResponse = randomResponses.get(randomIndex);
                    System.out.println(name + ": " + randomResponse);
                }
            }
        }

    }
}
