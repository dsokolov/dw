package tgn.rkvy.deep.entities;

import tgn.rkvy.deep.IOController;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final IOController ioController;
    private String settingTitle;
    private String locationTitle;
    private String roomTitle;
    private String roomDetails;
    private List<String> doors = new ArrayList<>();
    private List<String> events = new ArrayList<>();
    private String teleport = "";

    public Scene(IOController ioController) {
        this.ioController = ioController;
    }

    public void renderTitle() {
        String title = String.format("*** %s (%s, %s) ***", roomTitle, settingTitle, locationTitle);
        ioController.outln(title);
    }

    public void renderDetails(String elseText) {
        if (roomDetails == null || roomDetails.isEmpty()) {
            ioController.outln(elseText);
        } else {
            ioController.outln(roomDetails);
        }
    }

    public void renderDoors() {
        if (doors.size() > 0) {
            ioController.outln(doors, "[", "] [", "]");
        }
    }

    public void setSettingTitle(String s) {
        settingTitle = s;
    }

    public void setLocationTitle(String s) {
        locationTitle = s;
    }

    public void setRoomTitle(String s) {
        roomTitle = s;
    }

    public void setRoomDetails(String s) {
        roomDetails = s;
    }

    public void addDoor(String description) {
        doors.add(description);
    }

    public void addEvent(String event) {
        events.add(event);
    }

    public void addTeleport(String description) {
        this.teleport = description;
    }

}
