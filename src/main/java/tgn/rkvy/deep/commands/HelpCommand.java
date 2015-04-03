package tgn.rkvy.deep.commands;


import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand(String id, String[] aliases) {
        super(id, aliases);
    }

    @Override
    protected void onExecute(Controller controller) {
        controller.getIO().out("TODO help"); //TODO
    }

    @Override
    protected void onExecute(Controller controller, Alias param) {
        controller.getIO().out("TODO help"); //TODO
    }

    @Override
    protected void onExecute(Controller controller, Alias param1, Alias param2) {
        //TODO fail
    }

    @Override
    protected void onExecute(Controller controller, List<Alias> params) {
        //TODO fail
    }

}
