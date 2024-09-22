package concrete.goonie.chart.chartRenderers;

import concrete.goonie.chart.ChartElement;
import concrete.goonie.chart.dataTypes.Bar;
import concrete.goonie.chart.dataTypes.BarData;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class BarChartElement extends ChartElement {

    private BarData barData;
    private ArrayList<Rectangle2D> bars;
    private Rectangle2D body;

    public BarChartElement() {
        super(new Color(4));
        bars = new ArrayList<>();
        barData = new BarData();
    }

    @Override
    protected boolean contains(Point2D point) {

        for (Rectangle2D rectangle2D : bars) {
            if (rectangle2D.contains(point))
                return rectangle2D.contains(point);
        }
        return false;
    }

    @Override
    public void draw(Graphics2D g2d, AffineTransform transform, int width, int height) {
        bars.clear();

        int n = (int) (transform.getScaleX() - 1);
        int barWidth = n % 2 == 0 ? n - 1 : n;

        double minValue = barData.getBars().stream().mapToDouble(Bar::getValue).min().orElse(0.0);
        double maxValue = barData.getBars().stream().mapToDouble(Bar::getValue).max().orElse(1.0);
        double range = maxValue - minValue;

        int h = height / 5;
        for (int i = 0; i < barData.getBars().size(); i++) {
            Bar data = barData.getBars().get(i);
            double xPos = i * 1.0;

            Point2D point = new Point2D.Double(xPos, data.getValue());
            Point2D transformedPoint = transform.transform(point, null);

            int x = (int) transformedPoint.getX();
            int y = (int) transformedPoint.getY();

            int barHeight = h - (int) (((data.getValue() - minValue) / range) * h);
            g2d.setColor(data.getDirection() == 1 ? bullColorVol : bearColorVol);
            if (x + (barWidth / 2) < width && x - (barWidth / 2) > 0) {

                body = new Rectangle2D.Double(x - (int) (barWidth / 2), height - barHeight, (int) barWidth, barHeight);
                g2d.fill(body);
                bars.add(body);
                g2d.fillRect(x - (barWidth / 2), height - barHeight, barWidth, barHeight);

            }

        }
    }

    @Override
    protected void move(double dx, double dy) {

    }

    public void setBarData(BarData barData) {
        this.barData = barData;
    }

    public BarData getBarData() {
        return barData;
    }

}
