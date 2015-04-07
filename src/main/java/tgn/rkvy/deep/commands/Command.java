package tgn.rkvy.deep.commands;


import tgn.rkvy.deep.actions.Action;
import tgn.rkvy.deep.entities.Entity;

import java.util.List;

public abstract class Command extends Entity {

    private final String id;
    private final Action defaultAction;

    public Command(String id, String[] aliases, String description, Action defaultAction) {
        super(aliases, description);
        this.id = id;
        this.defaultAction = defaultAction;
    }

    public Command(String id, String[] aliases, Action defaultAction) {
        super(aliases);
        this.id = id;
        this.defaultAction = defaultAction;
    }

    public String getId() {
        return id;
    }

    public void execute(Controller controller, List<Alias> params) {
        switch (params.size()) {
            case 0:
                onExecute(controller);
                break;
            case 1:
                onExecute(controller, params.get(0));
                break;
            case 2:
                onExecute(controller, params.get(0), params.get(1));
                break;
        }
    }

    protected abstract void onExecute(Controller controller);

    protected abstract void onExecute(Controller controller, Alias param);

    protected abstract void onExecute(Controller controller, Alias param1, Alias param2);

    protected abstract void onExecute(Controller controller, List<Alias> params);

}
