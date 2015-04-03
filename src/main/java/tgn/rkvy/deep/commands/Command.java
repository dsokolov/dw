package tgn.rkvy.deep.commands;


import tgn.rkvy.deep.entities.Entity;

import java.util.List;

public abstract class Command extends Entity {

    private final String id;

    public Command(String id, String[] aliases, String description) {
        super(aliases, description);
        this.id = id;
    }

    public Command(String id, String[] aliases) {
        super(aliases);
        this.id = id;
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
