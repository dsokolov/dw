package tgn.rkvy.deep.entities;


public class Location implements Sceneable {

    private final String settingId;
    private final String locationId;
    private final String title;
    private final String description;

    public Location(String settingId, String locationId, String title, String description) {
        this.settingId = settingId;
        this.locationId = locationId;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return locationId;
    }

    public String getSettingId() {
        return settingId;
    }

    @Override
    public void processScene(Scene scene) {
        scene.setLocationTitle(title);
        scene.setDescription(description);
    }

    public boolean isSame(String settingId, String locationId) {
        return this.settingId.equalsIgnoreCase(settingId) && this.locationId.equalsIgnoreCase(locationId);
    }

    public Location copy() {
        return new Location(settingId, locationId, title, description);
    }
}
