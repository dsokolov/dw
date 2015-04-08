package tgn.rkvy.deep.entities;


public class Setting implements Sceneable {

    private final Point point;
    private final String title;

    public Setting(Point point, String title) {
        this.point = point;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void processScene(Scene scene) {
        scene.setSettingTitle(title);
    }

    public Setting copy() {
        return new Setting(point, title);
    }

    public Point getPoint() {
        return point;
    }
}
