package tgn.rkvy.deep.entities;


public class Setting implements Sceneable {

    private final String settingId;
    private final String title;

    public Setting(String id, String title) {
        this.settingId = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getSettingId() {
        return settingId;
    }

    @Override
    public void processScene(Scene scene) {
        scene.setSettingTitle(title);
    }

    public boolean isSame(String settingId) {
        return this.settingId.equalsIgnoreCase(settingId);
    }

}
