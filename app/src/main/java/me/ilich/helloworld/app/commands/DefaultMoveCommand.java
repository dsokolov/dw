package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Coord;

import java.util.List;

public class DefaultMoveCommand extends Command {

    public DefaultMoveCommand() {
        super("ИДТИ", true);
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        ExecuteMoveAction north = new ExecuteMoveAction(Coord.north());
        ExecuteMoveAction northEast = new ExecuteMoveAction(Coord.xy(1, 1));
        ExecuteMoveAction east = new ExecuteMoveAction(Coord.east());
        ExecuteMoveAction southEast = new ExecuteMoveAction(Coord.xy(1, -1));
        ExecuteMoveAction south = new ExecuteMoveAction(Coord.south());
        ExecuteMoveAction southWest = new ExecuteMoveAction(Coord.xy(-1, -1));
        ExecuteMoveAction west = new ExecuteMoveAction(Coord.west());
        ExecuteMoveAction northWest = new ExecuteMoveAction(Coord.xy(-1, 1));

        cases.add(new Case("n", north));
        cases.add(new Case("ne", northEast));
        cases.add(new Case("e", east));
        cases.add(new Case("se", southEast));
        cases.add(new Case("s", south));
        cases.add(new Case("sw", southWest));
        cases.add(new Case("w", west));
        cases.add(new Case("nw", northWest));

        cases.add(new Case("с", north));
        cases.add(new Case("св", northEast));
        cases.add(new Case("в", east));
        cases.add(new Case("юв", southEast));
        cases.add(new Case("ю", south));
        cases.add(new Case("юз", southWest));
        cases.add(new Case("з", west));
        cases.add(new Case("сз", northWest));
    }

    private class ExecuteMoveAction implements Action.OnExecute {

        private final Coord coord;

        private ExecuteMoveAction(Coord coord) {
            this.coord = coord;
        }

        @Override
        public void onExecute(Controller controller, String[] params) {
            controller.tryMoveBy(coord);
        }

    }

}
