package Lab4Service.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement
@Entity
public class Point implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "X must be not null")
    private double x;
    @NotNull(message = "Y must be not null")
    private double y;
    @NotNull(message = "R must be not null")
    private double r;
    private boolean inArea;
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    private User owner;

    public Point(){}

    public Point(double x, double y, double r, boolean inArea, User owner) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inArea = inArea;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
