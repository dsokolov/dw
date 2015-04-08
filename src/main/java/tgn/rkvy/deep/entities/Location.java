package tgn.rkvy.deep.entities;


public class Location implements Sceneable {

    private final Point point;
    private final String title;
    private final String description;

    public Location(Point point, String title, String description) {
        this.point = point;
        this.title = title;
        this.description = description;
    }

    @Override
    public void processScene(Scene scene) {
        scene.setLocationTitle(title);
        scene.setDescription(description);
    }

    public Location copy() {
        return new Location(point, title, description);
    }

    public Point getPoint() {
        return point;
    }
}
