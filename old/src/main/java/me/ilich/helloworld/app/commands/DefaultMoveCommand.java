package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.Coord;

import java.util.List;

public class DefaultMoveCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Action> patterns) {
        ExecuteMoveAction north = new ExecuteMoveAction(Coord.north());
        ExecuteMoveAction northEast = new ExecuteMoveAction(Coord.xy(1, 1));
        ExecuteMoveAction east = new ExecuteMoveAction(Coord.xy(1, 0));
        ExecuteMoveAction southEast = new ExecuteMoveAction(Coord.xy(1, -1));
        ExecuteMoveAction south = new ExecuteMoveAction(Coord.xy(0, -1));
        ExecuteMoveAction southWest = new ExecuteMoveAction(Coord.xy(-1, -1));
        ExecuteMoveAction west = new ExecuteMoveAction(Coord.xy(-1, 0));
        ExecuteMoveAction northWest = new ExecuteMoveAction(Coord.xy(-1, 1));

        patterns.add(new Action("с", north));
        patterns.add(new Action("св", northEast));
        patterns.add(new Action("в", east));
        patterns.add(new Action("юв", southEast));
        patterns.add(new Action("ю", south));
        patterns.add(new Action("юз", southWest));
        patterns.add(new Action("з", west));
        patterns.add(new Action("сз", northWest));

        patterns.add(new Action("n", north));
        patterns.add(new Action("ne", northEast));
        patterns.add(new Action("e", east));
        patterns.add(new Action("se", southEast));
        patterns.add(new Action("s", south));
        patterns.add(new Action("sw", southWest));
        patterns.add(new Action("w", west));
        patterns.add(new Action("nw", northWest));
    }

    private class ExecuteMoveAction implements Action.OnExecute {

        private final Coord coord;

        private ExecuteMoveAction(Coord coord) {
            this.coord = coord;
        }

        @Override
        public void onExecute(Controller controller) {
            controller.tryMoveBy(coord);
        }

    }

}
