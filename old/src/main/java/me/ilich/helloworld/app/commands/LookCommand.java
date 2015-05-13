package me.ilich.helloworld.app.commands;

import java.util.List;

public class LookCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Action> patterns) {
        Action.OnExecute lookOnExecute = controller -> {
            controller.showRoomDescription();
        };
        patterns.add(new Action("look", lookOnExecute));
        patterns.add(new Action("look ([\\w\\s]*)", lookOnExecute));
    }

}
