package tgn.rkvy.deep.entities;

public class Event implements Sceneable {

    private final String settingId;
    private final String id;
    private final String text;

    public Event(String settingId, String id, String text) {
        this.settingId = settingId;
        this.id = id;
        this.text = text;
    }

    public String getSettingId() {
        return settingId;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public void processScene(Scene scene) {
        scene.addEvent(text);
    }
}
