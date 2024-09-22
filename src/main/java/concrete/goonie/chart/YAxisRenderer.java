package concrete.goonie.chart;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

public class YAxisRenderer {

    private static final int DEFAULT_Y_AXIS_SIZE = 80;
    private ChartPanel chartPanel;

    private double yMin = 0, yMax = 0.11;
    private DecimalFormat decimalFormat = new DecimalFormat("#0.0000");
    private Point2D topLeft = new Point2D.Double();
    private Point2D bottomRight = new Point2D.Double();
    private double effectiveYMin;
    private double effectiveYMax;
    private double range;
    private double gridSpacing;
    private double startGrid;

    public YAxisRenderer(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }

    public void drawYAxisGrid(Graphics2D g2d, AffineTransform g2c) {

        try {
            topLeft = g2c.inverseTransform(new Point2D.Double(0, 0), null);
            bottomRight = g2c.inverseTransform(new Point2D.Double(0, chartPanel.getHeightY()), null);
        } catch (NoninvertibleTransformException e) {
            System.out.println();
        }

        // Determine the effective Y range in world coordinates
        effectiveYMin = Math.min(topLeft.getY(), bottomRight.getY());
        effectiveYMax = Math.max(topLeft.getY(), bottomRight.getY());

        // Calculate the range
        range = effectiveYMax - effectiveYMin;

        // Determine dynamic grid spacing
        gridSpacing = findDynamicYGridSpacing(range);

        // Find the first grid line to draw (starting at the first multiple of gridSpacing)
        startGrid = Math.floor(effectiveYMin / gridSpacing) * gridSpacing;

        for (double y = startGrid; y <= effectiveYMax; y += gridSpacing) {
            // Transform world coordinates to screen coordinates
            Point2D pt = g2c.transform(new Point2D.Double(0, y), null);
            double screenY = pt.getY();

            // Only draw if the line is within the visible area
            if (screenY > 0 && screenY < chartPanel.getHeightY()) {
                g2d.drawLine(0, (int) screenY, chartPanel.getWidthX(), (int) screenY);
            }
        }
    }

    public void drawYAxisLabels(Graphics2D g2d, AffineTransform g2c) {

        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));

        for (double y = startGrid; y <= effectiveYMax; y += gridSpacing) {
            // Transform world coordinates to screen coordinates
            Point2D pt = g2c.transform(new Point2D.Double(0, y), null);
            double screenY = pt.getY();

            // Only draw if the line is within the visible area
            if (screenY > 0 && screenY < chartPanel.getHeightY()) {
                String label = decimalFormat.format(y).replace(',', '.');
                FontMetrics fm = g2d.getFontMetrics();
                int labelWidth = fm.stringWidth(label);
                int labelHeight = fm.getAscent();

                // Position label to the left of the Y-axis grid line
                int labelX = chartPanel.getWidthX() + (labelWidth / 6);
                int labelY = (int) screenY + labelHeight / 4;

                g2d.drawString(label, labelX, labelY);
            }
        }
    }

    private double findDynamicYGridSpacing(double range) {
        double[] yIntervals = {
                0.01, 0.05, 0.1, 0.5,
                1, 5, 10, 20, 50, 100,
                200, 500, 1000, 2000,
                5000, 10000, 25000,
                50000, 100000
        };

        // Determine the number of lines that can fit into the range
        for (double interval : yIntervals) {
            double numberOfLines = range / interval;
            if (numberOfLines >= chartPanel.getMinGridLines() && numberOfLines <= chartPanel.getMaxGridLines()) {
                return interval;
            }
        }

        // Fallback to the largest interval if no suitable spacing is found
        return yIntervals[yIntervals.length - 1];
    }

    public double getYMin() {
        return yMin;
    }

    public double getYMax() {
        return yMax;
    }

    public int getYAxisSize() {
        return DEFAULT_Y_AXIS_SIZE;
    }
}
