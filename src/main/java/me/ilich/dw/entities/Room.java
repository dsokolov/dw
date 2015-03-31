package me.ilich.dw.entities;


public class Room implements Sceneable {

    private final String settingId;
    private final String roomId;
    private final String title;
    private final String description;

    public Room(String settingId, String roomId, String title, String description) {
        this.settingId = settingId;
        this.roomId = roomId;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return roomId;
    }

    public String getSettingId() {
        return settingId;
    }

    @Override
    public void processScene(Scene scene) {
        scene.setRoomTitle(title);
        scene.setDescription(description);
    }

}
