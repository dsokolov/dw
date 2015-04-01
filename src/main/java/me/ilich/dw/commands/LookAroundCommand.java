package me.ilich.dw.commands;


public class LookAroundCommand extends Command {

    public LookAroundCommand(String[] aliases, String actionText) {
        super(aliases, actionText);
    }

    @Override
    protected void onExecute(Controller controller, String[] params) {
        controller.out("LookAroundCommand");
    }

}
