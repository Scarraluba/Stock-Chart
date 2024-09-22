package concrete.goonie.chart.chartRenderers;


import concrete.goonie.chart.ChartElement;
import concrete.goonie.chart.dataTypes.LineData;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class LineChartElement extends ChartElement {

    private final List<LineData> lineData = new ArrayList<>();
    private ArrayList<Line2D> lines;
    private int w = 0, h = 0;
    private Graphics2D g2d;

    private AffineTransform transform;

    public LineChartElement() {
        super(new Color(0, 0, 0));
    }

    @Override
    protected boolean contains(Point2D point) {
        for (LineData lineData1 : lineData) {
            List<Point2D> points = lineData1.getPoints();

            for (int x = 1; x < points.size(); x++) {
                Point2D start = points.get(x - 1);
                Point2D end = points.get(x);

                Point2D transformedStart = transform.transform(start, null);
                Point2D transformedEnd = transform.transform(end, null);
                int b = (int) transformedStart.getX();
                if (b < h && b > 0) {
                    Line2D.Double line = new Line2D.Double(transformedStart, transformedEnd);
                    if (line.ptSegDist(point) <= 5) { // 5 pixels tolerance
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Override
    public void draw(Graphics2D g2d, AffineTransform transform, int width, int height) {
        w = width;
        h = height;
        this.transform =transform;
        this.g2d =g2d;

        for (LineData lineData1 : lineData) {
            List<Point2D> points = lineData1.getPoints();

            g2d.setColor(lineData1.getColor());
            g2d.setStroke(new BasicStroke(lineData1.getWidth())); // Set line width

            if (points.size() < 2) return;

            for (int x = 1; x < points.size(); x++) {
                Point2D start = points.get(x - 1);
                Point2D end = points.get(x);

                Point2D transformedStart = transform.transform(start, null);
                Point2D transformedEnd = transform.transform(end, null);
                int b = (int) transformedStart.getX();
                if (b < width && b > 0) {
                    Line2D.Double line = new Line2D.Double(transformedStart, transformedEnd);
                    g2d.draw(line);
                }
//
            }
        }
    }

    public void add(LineData lineData) {

        if (!this.lineData.contains(lineData)) {
            this.lineData.add(lineData);
        }
    }

    public int size() {
        return lineData.size();
    }

    public LineData get(int i) {
        return lineData.get(i);
    }

    public boolean contains(LineData lineData) {
        return this.lineData.contains(lineData);
    }

    @Override
    protected void move(double dx, double dy) {

    }
}
