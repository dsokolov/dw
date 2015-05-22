package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.Room;
import me.ilich.helloworld.app.entities.primitives.Containable;
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
            String containerName = params[1];
            String itemName = params[0];
            List<Entity> entities = controller.
                    getInventoryEntities(Titlelable.class).
                    stream().
                    filter(item -> TitleUtils.isSuitable(item, itemName)).
                    collect(Collectors.toCollection(ArrayList::new));
            MultiEntitiesUtils.process(controller, itemName, entities, new MultiEntitiesUtils.InventoryProcessor() {

                @Override
                public void onOne(Controller controller, String userInput, Entity itemEntity) {
                    List<Entity> containerEntities = controller.
                            getCurrentRoomEntities(Titlelable.class).
                            stream().
                            filter(item -> TitleUtils.isSuitable(item, containerName)).
                            collect(Collectors.toCollection(ArrayList::new));
                    MultiEntitiesUtils.process(controller, containerName, containerEntities, new MultiEntitiesUtils.GroundProcessor() {
                                @Override
                                public void onOne(Controller controller, String userInput, Entity containerEntity) {
                                    if (containerEntity instanceof Containable) {
                                        itemEntity.setParentId(containerEntity.getId());
                                        controller.println(String.format("Вы положили %s в %s.", TitleUtils.v(itemEntity), TitleUtils.v(containerEntity)));
                                    } else {
                                        controller.println(String.format("В %s нельзя ничего класть.", TitleUtils.v(containerEntity)));
                                    }
                                }
                            }
                    );
                }
            });
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
                    controller.println(String.format("Вы положили %s.", TitleUtils.v(entity)));
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
