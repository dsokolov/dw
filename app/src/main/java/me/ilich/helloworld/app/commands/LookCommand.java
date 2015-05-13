package me.ilich.helloworld.app.commands;

import java.util.List;

public class LookCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute lookOnExecute = (controller, params) -> {
            controller.showRoomDescription();
        };
        cases.add(new Case("look", lookOnExecute));
        cases.add(new Case("look ([\\w\\s]*)", lookOnExecute));
    }

}
