package concrete.goonie.chart;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


public abstract class ChartElement {
    protected Color color;
    protected int width = 1;

    protected static Color bullColor = new Color(38, 166, 154);
    protected static Color bearColor = new Color(239, 83, 80);
    protected static Color bullColorVol = new Color(bullColor.getRed(), bullColor.getGreen(), bullColor.getBlue(), 50);
    protected static Color bearColorVol = new Color(bearColor.getRed(), bearColor.getGreen(), bearColor.getBlue(), 50);


    public ChartElement(Color color) {
        this.color = color;
    }

    protected abstract boolean contains(Point2D point);

    public abstract void draw(Graphics2D g2d, AffineTransform transform, int width, int height);

    protected abstract void move(double dx, double dy);

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}