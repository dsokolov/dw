package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Containable;
import me.ilich.helloworld.app.entities.primitives.Pickable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;
import me.ilich.helloworld.app.utils.MultiEntitiesUtils;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PickUpCommand extends Command {

    public PickUpCommand() {
        super("взять", "Премещает предмет в инвентарь, если это возможно.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute noParam = (controller, params) -> {
            controller.println("Взять что?");
        };
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            List<Entity> entities = controller.
                    getCurrentRoomEntities(Titlelable.class).
                    stream().
                    filter(entity -> TitleUtils.isSuitable(entity, param)).
                    collect(Collectors.toCollection(ArrayList::new));
            MultiEntitiesUtils.process(controller, param, entities, new MultiEntitiesUtils.GroundProcessor() {
                @Override
                public void onOne(Controller controller, String userInput, Entity entity) {
                    if (entity instanceof Pickable) {
                        ((Pickable) entity).onPickup(controller, entity, controller.getPlayer());
                        controller.println(String.format("Вы взяли %s.", TitleUtils.v(entity)));
                    } else {
                        controller.println(String.format("Невозможно взять %s.", TitleUtils.v(entity)));
                    }
                }
            });
        };
        Action.OnExecute twoParam = (controller, params) -> {
            String containerItemName = params[1];
            String itemName = params[0];
            List<Entity> containerItems = controller.
                    getCurrentRoomEntities(Titlelable.class).
                    stream().
                    filter(entity -> TitleUtils.isSuitable(entity, containerItemName)).
                    collect(Collectors.toCollection(ArrayList::new));
            MultiEntitiesUtils.process(controller, containerItemName, containerItems, new MultiEntitiesUtils.GroundProcessor() {
                @Override
                public void onOne(Controller controller, String userInput, Entity conteinerItem) {
                    if (conteinerItem instanceof Containable) {
                        List<Entity> items = controller.
                                getChildEntities(conteinerItem.getId(), Titlelable.class).
                                stream().
                                filter(entity -> TitleUtils.isSuitable(entity, itemName)).
                                collect(Collectors.toCollection(ArrayList::new));
                        //TODO MultiEntitiesUtils
                        switch (items.size()) {
                            case 0:
                                controller.println(String.format("В %s нет '%s'.", TitleUtils.r(conteinerItem), itemName));
                                break;
                            case 1:
                                Entity item = items.get(0);
                                if (item instanceof Pickable) {
                                    ((Pickable) item).onPickup(controller, item, controller.getPlayer());
                                    controller.println(String.format("Вы взяли %s из %s.", TitleUtils.v(item), TitleUtils.r(conteinerItem)));
                                } else {
                                    controller.println(String.format("Невозможно взять %s из %s.", TitleUtils.v(item), TitleUtils.r(conteinerItem)));
                                }
                                break;
                            default:
                                controller.println(String.format("В %s есть несколько предметов с именем '%s'.", TitleUtils.r(conteinerItem), itemName));
                        }
                    } else {
                        controller.println(String.format("Из %s нельзя ничего достать.", TitleUtils.r(conteinerItem)));
                    }
                }
            });
        };

        cases.add(new Case("взять ([\\w\\s]*) из ([\\w\\s]*)", twoParam));
        cases.add(new Case("вз ([\\w\\s]*) из ([\\w\\s]*)", twoParam));

        cases.add(new Case("pick up ([\\w\\s]*)", oneParam));
        cases.add(new Case("взять ([\\w\\s]*)", oneParam));
        cases.add(new Case("вз ([\\w\\s]*)", oneParam));

        cases.add(new Case("взять", noParam));
        cases.add(new Case("вз", noParam));

    }

}
