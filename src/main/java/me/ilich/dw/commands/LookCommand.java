package me.ilich.dw.commands;


import java.util.List;

public class LookCommand extends Command {

    private final String lookAtItem;
    private final String failText;

    public LookCommand(String id, String[] aliases, String lookAtItem, String failtText) {
        super(id, aliases);
        this.lookAtItem = lookAtItem;
        this.failText = failtText;
    }

    @Override
    protected void onExecute(Controller controller) {
        controller.renderCurrentScene();
    }

    @Override
    protected void onExecute(Controller controller, Alias param) {
        controller.getIO().outln(String.format(lookAtItem, param.getAliasText()));
        controller.getIO().outln(param.getEntity().getLongText());
    }

    @Override
    protected void onExecute(Controller controller, Alias param1, Alias param2) {
        controller.getIO().out(failText);
    }

    @Override
    protected void onExecute(Controller controller, List<Alias> params) {
        controller.getIO().out(failText);
    }

}
