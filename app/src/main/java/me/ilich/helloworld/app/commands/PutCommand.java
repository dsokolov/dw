package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.Room;
import me.ilich.helloworld.app.entities.primitives.Pickable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PutCommand extends Command {

    public PutCommand() {
        super("положить", "Перемещает предмет из инвентаря.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            List<Entity> entities = controller.
                    getInventoryEntities(Pickable.class, Titlelable.class).
                    stream().
                    filter(entity -> Titlelable.isSuitable(entity, param)).
                    collect(Collectors.toCollection(ArrayList::new));
            switch (entities.size()) {
                case 0:
                    controller.println(String.format("У вас нет предмета '%s'.", param));
                    break;
                case 1:
                    Entity entity = entities.get(0);
                    Room room = controller.getCurrentRoom();
                    entity.setParentId(room.getId());
                    controller.println(String.format("Вы положили %s.", Titlelable.r(entity)));
                    break;
                default:
                    controller.println(String.format("Что такое '%s'?", param));
            }
        };
        Action.OnExecute twoParam = (controller, params) -> {
            String containerName = params[1];
            String itemName = params[0];
            /*Item aItem = controller.getInventory().stream().filter(item -> item.getTitle().equalsIgnoreCase(itemName)).findFirst().orElse(null);
            if (aItem == null) {
                controller.println(String.format("У вас нет %s.", itemName));
            } else {
                //Item containerItem = controller.getCurrentRoom().getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(containerName)).findFirst().orElse(null);
                Item containerItem = null;
                if (containerItem == null) {
                    controller.println(String.format("Здесь нет %s.", containerName));
                } else {
                    if (aItem.isPickable() && containerItem.isContainable()) {
                        aItem.onMove(controller.getInventory(), containerItem.getItems());
                        controller.println(String.format("Вы положили %s в %s.", aItem.getTitle(), containerItem.getTitle()));
                    } else {
                        controller.println(String.format("Вам не удаётся положить %s в %s.", aItem.getTitle(), containerItem.getTitle()));
                    }
                }
            }*/
        };

        cases.add(new Case("положить ([\\w\\s]*) в ([\\w\\s]*)", twoParam));
        cases.add(new Case("пл ([\\w\\s]*) в ([\\w\\s]*)", twoParam));

        cases.add(new Case("положить ([\\w\\s]*)", oneParam));
        cases.add(new Case("пл ([\\w\\s]*)", oneParam));

    }

}
