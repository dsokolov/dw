package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.primitives.Containable;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Pickable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

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
                    filter(entity -> ((Titlelable) entity).getTitle().equalsIgnoreCase(param)).
                    collect(Collectors.toCollection(ArrayList::new));
            switch (entities.size()) {
                case 0:
                    controller.println(String.format("Здесь нет %s.", param));
                    break;
                case 1:
                    Entity entity = entities.get(0);
                    if (entity instanceof Pickable) {
                        ((Pickable) entity).onPickup(controller, entity, controller.getPlayer());
                        controller.println(String.format("Вы взяли %s.", ((Titlelable) entity).getTitle()));
                    } else {
                        controller.println(String.format("Невозможно взять %s.", ((Titlelable) entity).getTitle()));
                    }
                    break;
                default:
                    controller.println(String.format("Что такое %s?", param));
            }
        };
        Action.OnExecute twoParam = (controller, params) -> {
            String containerItemName = params[1];
            String itemName = params[0];
            List<Entity> containerItems = controller.
                    getCurrentRoomEntities(Titlelable.class).
                    stream().
                    filter(entity -> ((Titlelable) entity).getTitle().equalsIgnoreCase(containerItemName)).
                    collect(Collectors.toCollection(ArrayList::new));
            switch (containerItems.size()) {
                case 0:
                    controller.println(String.format("Здесь нет предмета '%s'.", containerItemName));
                    break;
                case 1:
                    Entity conteinerItem = containerItems.get(0);
                    if (conteinerItem instanceof Containable) {
                        List<Entity> items = controller.
                                getChildEntities(conteinerItem.getId(), Titlelable.class).
                                stream().
                                filter(entity -> ((Titlelable) entity).getTitle().equalsIgnoreCase(itemName)).
                                collect(Collectors.toCollection(ArrayList::new));
                        switch (items.size()) {
                            case 0:
                                controller.println(String.format("В %s нет '%s'.", ((Titlelable) conteinerItem).getTitle(), itemName));
                                break;
                            case 1:
                                Entity item = items.get(0);
                                if (item instanceof Pickable) {
                                    ((Pickable) item).onPickup(controller, item, controller.getPlayer());
                                    controller.println(String.format("Вы взяли %s из %s.", ((Titlelable) item).getTitle(), ((Titlelable) conteinerItem).getTitle()));
                                } else {
                                    controller.println(String.format("Невозможно взять %s из %s.", ((Titlelable) item).getTitle(), ((Titlelable) conteinerItem).getTitle()));
                                }
                                break;
                            default:
                                controller.println(String.format("В %s несколько '%s'.", ((Titlelable) conteinerItem).getTitle(), itemName));
                        }
                    } else {
                        controller.println(String.format("В %s нельзя класть что-либо.", ((Titlelable) conteinerItem).getTitle()));
                    }
                    break;
                default:
                    controller.println(String.format("Здесь несколько '%s'.", containerItemName));
            }
            /*Item containerItem = null;


            } else {
                if (containerItem.isContainable()) {
                    Item aItem = containerItem.getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(itemName)).findFirst().orElse(null);
                    if (aItem == null) {
                        controller.println(String.format("В %s нет ничего похожего на %s.", containerItem.getTitle(), itemName));
                    } else {
                        if (aItem.isPickable()) {
                            aItem.onMove(containerItem.getItems(), controller.getInventory());
                            controller.println(String.format("Вы взяли %s из %s.", aItem.getTitle(), containerItem.getTitle()));
                        } else {
                            controller.println(String.format("Вам не удаётся взять %s из %s.", aItem.getTitle(), containerItem.getTitle()));
                        }
                    }
                    //aItem.onMove(room.getItems(), controller.getInventory());
                    //controller.println(String.format("Вы взяли %s.", aItem.getTitle()));
                } else {
                    controller.println(String.format("Из %s невозможно что-либо достать.", containerItem.getTitle()));
                }
            }*/
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
