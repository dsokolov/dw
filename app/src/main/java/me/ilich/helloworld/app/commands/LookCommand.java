package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;
import me.ilich.helloworld.app.entities.primitives.Entity;
import me.ilich.helloworld.app.entities.primitives.Lookable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LookCommand extends Command {

    public LookCommand() {
        super("смотреть", "Осмотреть предмет");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute noParams = (controller, params) -> controller.showRoomDescription();
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            Room room = controller.getCurrentRoom();
            Item aItem = room.getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(param)).findFirst().orElse(null);
            if (aItem == null) {
                List<Entity> roomEntities = controller.getCurrentRoomEntities();
                List<Entity> lookables = roomEntities.stream().filter(entity -> {
                    boolean l = entity instanceof Lookable;
                    boolean t = entity instanceof Titlelable;
                    boolean tt = t && Objects.equals(((Titlelable) entity).getTitle(), param);
                    return l && t && tt;
                }).collect(Collectors.toCollection(ArrayList::new));
                if (lookables.size() == 0) {
                    controller.println("Вы оглядываетесь вокруг в поисках " + param + ".");
                } else if (lookables.size() == 1) {
                    Lookable lookable = (Lookable) lookables.get(0);
                    lookable.onLook(controller);
                } else {
                    controller.println("много такого");
                }
            } else {
                controller.println("Вы осматриваете " + param + ".");
                //controller.println(aItem.getDescription()); //TODO looable
                if (aItem.isPickable()) {
                    controller.println(String.format("%s можно взять с собой.", aItem.getTitle()));
                }
                if (aItem.isContainable()) {
                    if (aItem.getItems().size() == 0) {
                        controller.println(String.format("Внтури %s пусто.", aItem.getTitle()));
                    } else {
                        controller.println(String.format("Внтури %s %s предметов:", aItem.getTitle(), aItem.getItems().size()));
                        aItem.getItems().forEach(item -> controller.println(item.getTitle()));
                    }
                }
            }
        };

        cases.add(new Case("look", noParams));
        cases.add(new Case("look ([\\w\\s]*)", oneParam));

        cases.add(new Case("см", noParams));
        cases.add(new Case("см ([\\w\\s]*)", oneParam));
    }

}
