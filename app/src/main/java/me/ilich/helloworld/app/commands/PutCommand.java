package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;

import java.util.List;

public class PutCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            String itemName = params[0];
            Room room = controller.getCurrentRoom();
            Item aItem = controller.getInventory().stream().filter(item -> item.getTitle().equalsIgnoreCase(itemName)).findFirst().orElse(null);
            if (aItem == null) {
                System.out.println(String.format("У вас нет %s.", itemName));
            } else {
                if (aItem.isPickable()) {
                    aItem.onMove(controller.getInventory(), room.getItems());
                    System.out.println(String.format("Вы положили %s.", aItem.getTitle()));
                } else {
                    System.out.println(String.format("Вам не удаётся положить %s.", aItem.getTitle()));
                }
            }
        };
        Action.OnExecute twoParam = (controller, params) -> {
            String containerName = params[1];
            String itemName = params[0];
            Item aItem = controller.getInventory().stream().filter(item -> item.getTitle().equalsIgnoreCase(itemName)).findFirst().orElse(null);
            if (aItem == null) {
                System.out.println(String.format("У вас нет %s.", itemName));
            } else {
                Item containerItem = controller.getCurrentRoom().getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(containerName)).findFirst().orElse(null);
                if (containerItem == null) {
                    System.out.println(String.format("Здесь нет %s.", containerName));
                } else {
                    if (aItem.isPickable() && containerItem.isContainable()) {
                        aItem.onMove(controller.getInventory(), containerItem.getItems());
                        System.out.println(String.format("Вы положили %s в %s.", aItem.getTitle(), containerItem.getTitle()));
                    } else {
                        System.out.println(String.format("Вам не удаётся положить %s в %s.", aItem.getTitle(), containerItem.getTitle()));
                    }
                }
            }
        };

        cases.add(new Case("положить ([\\w\\s]*) в ([\\w\\s]*)", twoParam));
        cases.add(new Case("пл ([\\w\\s]*) в ([\\w\\s]*)", twoParam));

        cases.add(new Case("положить ([\\w\\s]*)", oneParam));
        cases.add(new Case("пл ([\\w\\s]*)", oneParam));

    }

}
