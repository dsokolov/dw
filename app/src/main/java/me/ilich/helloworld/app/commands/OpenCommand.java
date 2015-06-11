package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Coordinable;
import me.ilich.helloworld.app.entities.primitives.Openable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;
import me.ilich.helloworld.app.utils.CoordUtils;
import me.ilich.helloworld.app.utils.MultiEntitiesUtils;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OpenCommand extends Command {

    public OpenCommand() {
        super("ОТКРЫТЬ", "Открыть дверь");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            List<Entity> entities = controller.getCurrentRoomEntities(Openable.class);
            List<Entity> suitableEntities = entities.stream().filter(entity -> {
                final boolean b;
                if (entity instanceof Titlelable) {
                    b = TitleUtils.isSuitable(entity, param);
                } else if (entity instanceof Coordinable) {
                    Coord coord = DefaultMoveCommand.getDirection(param);
                    b = CoordUtils.isSuitable(entity, coord);
                } else {
                    b = false;
                }
                return b;
            }).collect(Collectors.toCollection(ArrayList::new));

            MultiEntitiesUtils.process(controller, param, suitableEntities, new MultiEntitiesUtils.GroundProcessor() {
                @Override
                public void onOne(Controller controller, String userInput, Entity entity) {
                    ((Openable) entity).open();
                }
            });
        };
        cases.add(new Case("открыть ([\\w\\s]*)", oneParam));
        cases.add(new Case("откр ([\\w\\s]*)", oneParam));

        Action.OnExecute noParams = (controller, params) -> {
            controller.println("Открыть что?");
        };
        cases.add(new Case("открыть", noParams));
        cases.add(new Case("откр", noParams));

    }

}
