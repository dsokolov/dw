package me.ilich.dw.commands;


public class LookAroundCommand extends Command {

    private final String lookAtItem;
    private final String failText;

    public LookAroundCommand(String[] aliases, String lookAtItem, String failtText) {
        super(aliases);
        this.lookAtItem = lookAtItem;
        this.failText = failtText;
    }

    @Override
    protected void onExecute(Controller controller, Alias[] params) {
        switch (params.length) {
            case 0:
                controller.renderCurrentScene();
                break;
            case 1:
                controller.getIO().out(String.format(lookAtItem, params[0].getAliasText()));
                controller.getIO().out(params[0].getEntity().getDescription());
                break;
            default:
                controller.getIO().out(failText);
        }
    }

}
