package me.ilich.dw.entities;


public class Setting implements Sceneable {

    private final String id;
    private final String title;

    public Setting(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    @Override
    public void processScene(Scene scene) {
        scene.setSettingTitle(title);
    }

}
