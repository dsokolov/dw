package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.FreeWayDoor;

import java.util.List;

public class CloseCommand extends Command {

    public CloseCommand() {
        super("ЗАКРЫТЬ", "Закрыть дверь");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute noParams = (controller, params) -> {
            controller.println("Закрыть что?");
        };
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            Coord coord = DefaultMoveCommand.getDirection(param);
            if (coord == null) {
                controller.println(String.format("Что такое %s?", param));
            } else {
                Coord currentCoord = controller.getCurrentCoord();
                Coord doorCoord = Coord.sum(coord, currentCoord);
                FreeWayDoor door = controller.getDoor(currentCoord, doorCoord);
                if (door == null) {
                    controller.println("В этом направлении нечего закрыть.");
                } else {
                    switch (door.getState()){
                        case OPEN:
                            controller.println("Вы закрыли дверь.");
                            door.setState(FreeWayDoor.State.CLOSE);
                            break;
                        case CLOSE:
                            controller.println("Уже закрыто.");
                            break;
                        case LOCKED:
                            controller.println("Заперто.");
                            break;
                        default:
                            controller.println("Невозможно закрыть.");
                    }
                }
            }
        };
        cases.add(new Case("закрыть ([\\w\\s]*)", oneParam));
        cases.add(new Case("закрыть", noParams));

    }

}
