package tgn.rkvy.deep.entities;


public class Location implements Sceneable {

    private final Point point;
    private final String title;

    public Location(Point point, String title) {
        this.point = point;
        this.title = title;
    }

    @Override
    public void processScene(Scene scene) {
        scene.setLocationTitle(title);
    }

    public Location copy() {
        return new Location(point, title);
    }

    public Point getPoint() {
        return point;
    }
}
