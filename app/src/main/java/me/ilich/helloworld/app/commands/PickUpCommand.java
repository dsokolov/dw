package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;

import java.util.List;

public class PickUpCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            Room room = controller.getCurrentRoom();
            Item aItem = room.getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(params[0])).findFirst().orElse(null);
            if (aItem == null) {
                System.out.println(String.format("Здесь нет %s.", params[0]));
            } else {
                if (aItem.isPickable()) {
                    aItem.onMove(room.getItems(), controller.getInventory());
                    System.out.println(String.format("Вы взяли %s.", aItem.getTitle()));
                } else {
                    System.out.println(String.format("Вам не удаётся взять %s с собой.", aItem.getTitle()));
                }
            }
        };
        Action.OnExecute twoParam = (controller, params) -> {
            String fromItemName = params[1];
            String itemName = params[0];
            Room room = controller.getCurrentRoom();
            Item containerItem = room.getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(fromItemName)).findFirst().orElse(null);
            if (containerItem == null) {
                System.out.println(String.format("Здесь нет %s.", fromItemName));
            } else {
                if (containerItem.isContainable()) {
                    Item aItem = containerItem.getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(itemName)).findFirst().orElse(null);
                    if (aItem == null) {
                        System.out.println(String.format("В %s нет ничего похожего на %s.", containerItem.getTitle(), itemName));
                    } else {
                        if (aItem.isPickable()) {
                            aItem.onMove(containerItem.getItems(), controller.getInventory());
                            System.out.println(String.format("Вы взяли %s из %s.", aItem.getTitle(), containerItem.getTitle()));
                        } else {
                            System.out.println(String.format("Вам не удаётся взять %s из %s.", aItem.getTitle(), containerItem.getTitle()));
                        }
                    }
                    //aItem.onMove(room.getItems(), controller.getInventory());
                    //System.out.println(String.format("Вы взяли %s.", aItem.getTitle()));
                } else {
                    System.out.println(String.format("Из %s невозможно что-либо достать.", containerItem.getTitle()));
                }
            }
        };

        cases.add(new Case("взять ([\\w\\s]*) из ([\\w\\s]*)", twoParam));
        cases.add(new Case("вз ([\\w\\s]*) из ([\\w\\s]*)", twoParam));

        cases.add(new Case("pick up ([\\w\\s]*)", oneParam));
        cases.add(new Case("взять ([\\w\\s]*)", oneParam));
        cases.add(new Case("вз ([\\w\\s]*)", oneParam));

    }

}
