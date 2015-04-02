package me.ilich.dw.commands;


public class HelpCommand extends Command {

    public HelpCommand(String[] aliases) {
        super(aliases);
    }

    @Override
    protected void onExecute(Controller controller, Alias[] params) {
        controller.getIO().out("TODO help"); //TODO
    }

}
