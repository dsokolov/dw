package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.Room;
import me.ilich.helloworld.app.entities.primitives.Pickable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;
import me.ilich.helloworld.app.utils.MultiEntitiesUtils;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PutCommand extends Command {

    public PutCommand() {
        super("положить", "Перемещает предмет из инвентаря.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {

        Action.OnExecute twoParam = (controller, params) -> {
            //TODO
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

        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            List<Entity> entities = controller.
                    getInventoryEntities(Pickable.class, Titlelable.class).
                    stream().
                    filter(entity -> TitleUtils.isSuitable(entity, param)).
                    collect(Collectors.toCollection(ArrayList::new));
            MultiEntitiesUtils.process(controller, param, entities, new MultiEntitiesUtils.InventoryProcessor() {
                @Override
                public void onOne(Controller controller, String userInput, Entity entity) {
                    Room room = controller.getCurrentRoom();
                    entity.setParentId(room.getId());
                    controller.println(String.format("Вы положили %s.", TitleUtils.r(entity)));
                }
            });
        };
        cases.add(new Case("положить ([\\w\\s]*)", oneParam));
        cases.add(new Case("пл ([\\w\\s]*)", oneParam));

        Action.OnExecute noParam = (controller, params) -> controller.println("Положить что?");
        cases.add(new Case("положить", noParam));
        cases.add(new Case("пл", noParam));

    }

}
