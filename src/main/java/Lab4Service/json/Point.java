package Lab4Service.json;

import java.util.ArrayList;
import java.util.List;

public class Point {
    private double x;
    private double y;
    private double r;
    private boolean inArea;

    public Point(){}

    public Point(double x, double y, double r, boolean inArea) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inArea = inArea;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isInArea() {
        return inArea;
    }

    public void setInArea(boolean inArea) {
        this.inArea = inArea;
    }

    public static List<Point> getJsonPoints(List<Lab4Service.entities.Point> points){
        List<Point> jpoints = new ArrayList<>();
        for (Lab4Service.entities.Point p : points){
            jpoints.add(new Point(p.getX(), p.getY(), p.getR(), p.isInArea()));
        }
        return jpoints;
    }
}
