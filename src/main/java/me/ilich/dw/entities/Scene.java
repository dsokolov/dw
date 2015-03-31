package me.ilich.dw.entities;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private String settingTitle;
    private String roomTitle;
    private String description;
    private List<String> doors = new ArrayList<>();

    public void render() {
        System.out.println(roomTitle + " (" + settingTitle + ")");
        System.out.println(description);
        for (String door : doors) {
            System.out.println(door);
        }
    }

    public void setSettingTitle(String s) {
        settingTitle = s;
    }

    public void setRoomTitle(String s) {
        roomTitle = s;
    }

    public void setDescription(String s) {
        description = s;
    }

    public void addDoor(String description) {
        doors.add(description);
    }
}
