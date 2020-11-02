package com.questquest.model.character.npc;

import com.questquest.model.character.npc.NPC;

import java.util.*;

public class SarcasticNPC extends NPC {

    public SarcasticNPC(int npcID, String name, String description, Map<String, String> preparedResponses, List<String> randomResponses) {
        super(npcID, name, description, preparedResponses, randomResponses);
    }

    @Override
    public Map<String, String> getPreparedResponses() {
        Map<String, String> preparedResponses = new HashMap<>();
        preparedResponses.put("Hello", "Yes hello what do you want?");
        preparedResponses.put("hello", "Yes? What do you want?");
        preparedResponses.put("Hey", "I've no time for your shenanigans. What do you want?");
        preparedResponses.put("hey", "I've no time for your shenanigans. What do you want?");
        return preparedResponses;
    }

    @Override
    public void interact(Scanner scanner) {
        System.out.println(super.getName() + ": What do you want?");
        boolean goodbye = false;
        while(!goodbye) {
                System.out.print("You: ");
            String interaction = scanner.nextLine();
            if(interaction.toLowerCase().contains("bye")) {
                System.out.println(super.getName() + ": Be gone!");
                goodbye = true;
            } else {
                if(getPreparedResponses().containsKey(interaction.toLowerCase())) {
                    System.out.println(super.getName() + ": " + getPreparedResponses().get(interaction.toLowerCase()));;
                } else {

                    char[] interactionChars = interaction.toCharArray();
                    Random random = new Random();
                    for(int i = 0; i < interactionChars.length; i++) {
                        int randomIndex = random.nextInt(2);
                        switch (randomIndex) {
                            case 0:
                                interactionChars[i] = Character.toLowerCase(interactionChars[i]);
                                break;
                            case 1:
                                interactionChars[i] = Character.toUpperCase(interactionChars[i]);
                                break;
                        }
                    }
                    String sarcasticResponse = new String(interactionChars);
                    System.out.println(super.getName() + ": " + sarcasticResponse);
                }
            }
        }
    }
}
