package me.ilich.dw.entities;


public class Door implements Sceneable {

    private final String settingId;
    private final String sourceRoomId;
    private final String destinationRoomId;
    private final String description;

    public Door(String settingId, String sourceRoomId, String destinationRoomId, String description) {
        this.settingId = settingId;
        this.sourceRoomId = sourceRoomId;
        this.destinationRoomId = destinationRoomId;
        this.description = description;
    }

    public String getSettingId() {
        return settingId;
    }

    public String getSourceRoomId() {
        return sourceRoomId;
    }

    public String getDestinationRoomId() {
        return destinationRoomId;
    }

    @Override
    public void processScene(Scene scene) {
        scene.addDoor(description);
    }

}
