package me.ilich.helloworld.app.commands;

import com.sun.istack.internal.Nullable;
import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Coord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultMoveCommand extends Command {

    @Nullable
    public static Action.OnExecute getOnExecute(String s) {
        return actions.get(s);
    }

    private static final String[] NORTH = {
            "север",
            "с",
            "north",
            "n"
    };

    private static final String[] EAST = {
            "восток",
            "в",
            "east",
            "e"
    };

    private static final String[] SOUTH = {
            "юг",
            "ю",
            "south",
            "s"
    };

    private static final String[] WEST = {
            "запад",
            "з",
            "west",
            "w"
    };

    public static final ExecuteMoveAction MOVE_NORTH = new ExecuteMoveAction(Coord.north());
    private static final ExecuteMoveAction northEast = new ExecuteMoveAction(Coord.xy(1, 1));
    public static final ExecuteMoveAction MOVE_EAST = new ExecuteMoveAction(Coord.east());
    private static final ExecuteMoveAction southEast = new ExecuteMoveAction(Coord.xy(1, -1));
    public static final ExecuteMoveAction MOVE_SOUTH = new ExecuteMoveAction(Coord.south());
    private static final ExecuteMoveAction southWest = new ExecuteMoveAction(Coord.xy(-1, -1));
    public static final ExecuteMoveAction MOVE_WEST = new ExecuteMoveAction(Coord.west());
    private static final ExecuteMoveAction northWest = new ExecuteMoveAction(Coord.xy(-1, 1));

    private static Map<String, Action.OnExecute> actions = new HashMap<>();

    static {
        for (String s : NORTH) {
            actions.put(s, MOVE_NORTH);
        }
        for (String s : EAST) {
            actions.put(s, MOVE_EAST);
        }
        for (String s : SOUTH) {
            actions.put(s, MOVE_SOUTH);
        }
        for (String s : WEST) {
            actions.put(s, MOVE_WEST);
        }
    }

    public DefaultMoveCommand() {
        super("ИДТИ", "", true);
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {

        actions.keySet().forEach(s -> {
            Action.OnExecute onExecute = actions.get(s);
            cases.add(new Case(s, onExecute));
        });

        cases.add(new Case("ne", northEast));
        cases.add(new Case("se", southEast));
        cases.add(new Case("sw", southWest));
        cases.add(new Case("nw", northWest));

        cases.add(new Case("св", northEast));
        cases.add(new Case("юв", southEast));
        cases.add(new Case("юз", southWest));
        cases.add(new Case("сз", northWest));
    }

    private static class ExecuteMoveAction implements Action.OnExecute {

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
