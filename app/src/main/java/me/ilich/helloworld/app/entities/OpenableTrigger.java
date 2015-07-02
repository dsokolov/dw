package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.entities.primitives.Openable;

public class OpenableTrigger extends Trigger<Openable> {

    public void onOpened(OpenCloseDoor openCloseDoor) {
        for (Openable openable : getList()) {
            if (!openable.equals(openCloseDoor)) {
                openable.setOpenState(openCloseDoor.getOpenState());
            }
        }
    }

    public void onClosed(OpenCloseDoor openCloseDoor) {
        for (Openable openable : getList()) {
            if (!openable.equals(openCloseDoor)) {
                openable.setOpenState(openCloseDoor.getOpenState());
            }
        }
    }

}
