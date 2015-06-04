package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Coord;

import java.util.List;

public class OpenCommand extends Command {

    public OpenCommand() {
        super("ОТКРЫТЬ", "Открыть дверь");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute noParams = (controller, params) -> {
            controller.println("Открыть что?");
        };
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            Coord coord = DefaultMoveCommand.getDirection(param);
            if (coord == null) {
                controller.println(String.format("Что такое %s?", param));
            } else {
/*                Coord currentCoord = controller.getCurrentCoord();
                Coord doorCoord = Coord.sum(coord, currentCoord);
                FreeWayByCoordDoor door = controller.getDoor(currentCoord, doorCoord);
                if (door == null) {
                    controller.println("В этом направлении нечего открывать.");
                } else {
                    switch (door.getState()){
                        case CLOSE:
                            controller.println("Вы открыли дверь.");
                            door.setState(FreeWayByCoordDoor.State.OPEN);
                            break;
                        case OPEN:
                            controller.println("Уже открыто.");
                            break;
                        case LOCKED:
                            controller.println("Заперто.");
                            break;
                        default:
                            controller.println("Невозможно открыть.");
                    }
                }*/
            }
        };
        cases.add(new Case("открыть ([\\w\\s]*)", oneParam));
        cases.add(new Case("открыть", noParams));

    }

}
