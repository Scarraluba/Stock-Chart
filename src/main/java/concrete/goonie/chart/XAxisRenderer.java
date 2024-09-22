package concrete.goonie.chart;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

public class XAxisRenderer {

    private static final int DEFAULT_X_AXIS_SIZE = 40;
    private ChartPanel chartPanel;

    private double xMin = 0, xMax = 80;
    private double xScale= 0;
    private DecimalFormat decimalFormat = new DecimalFormat("#0.0000");
    private double effectiveXMin;
    private double effectiveXMax;
    private double worldGap;
    private double gridSpacing;
    private double startGrid;

    private Font labelFont = new Font("SansSerif", Font.BOLD, 12);
    private Color labelColor = new Color(0xB3B3B3);

    public XAxisRenderer(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
        xScale= chartPanel.getxScale();
    }

    public void render(Graphics2D g2d, AffineTransform g2c) {
        //drawXAxisGrid(g2d, g2c, xScale);
        //drawXAxisLabel(g2d, g2c);
    }

    public void drawXAxisLabel(Graphics2D g2d, AffineTransform g2c) {
        g2d.setColor(labelColor);

        for (int x = (int) startGrid; x <= effectiveXMax; x += (int) gridSpacing) {

            Point2D pt = g2c.transform(new Point2D.Double(x, 0), null);
            double screenX = pt.getX();
            String label = String.valueOf(x);

            if (screenX > 0 && screenX < chartPanel.getWidthX()) {

               /*   LocalDateTime dateTime = startDateTime.plusMinutes(x);
                boolean isNewYear = x == (int) startGrid || dateTime.getYear() != startDateTime.getYear() + (x / 525600); // 525600 = minutes in a year
                boolean isNewMonth = !isNewYear && dateTime.getMonth() != startDateTime.plusMinutes((long) (x - gridSpacing)).getMonth();
                boolean isNewDay = !isNewYear && !isNewMonth && dateTime.getDayOfMonth() != startDateTime.plusMinutes((long) (x - gridSpacing)).getDayOfMonth();

              if (isNewYear) {
                    labelFont = new Font("SansSerif", Font.BOLD, 16);
                    label = String.valueOf(dateTime.getYear()); // Print only the year
                } else if (isNewMonth) {
                    label = dateTime.getMonth().name();
                    labelFont = new Font("SansSerif", Font.BOLD, 14);
                } else if (isNewDay) {
                    label = String.valueOf(dateTime.getDayOfMonth());
                    labelFont = new Font("SansSerif", Font.BOLD, 12);
                } else {
                    label = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                    labelFont = new Font("SansSerif", Font.PLAIN, 12);
                }*/

                FontMetrics fm = g2d.getFontMetrics();
                int labelWidth = fm.stringWidth(label);
                int labelHeight = fm.getAscent();

                // Position label below the X-axis grid line
                int labelX = (int) screenX - labelWidth / 2;
                int labelY = (int) (chartPanel.getHeightY() + 10 + labelHeight);

                g2d.setFont(labelFont);
                g2d.drawString(label, labelX, labelY);
            }
        }

    }

    public void drawXAxisGrid(Graphics2D g2d, AffineTransform g2c) {
        xScale= chartPanel.getxScale();

         effectiveXMin = (xMin - Math.abs(g2c.getTranslateX())) / xScale;
         effectiveXMax = (xMax + Math.abs(g2c.getTranslateX())) / xScale;

         worldGap = chartPanel.getMinGridSpacing() / (xScale * (chartPanel.getWidthX() / (xMax - xMin)));

         gridSpacing = findDynamicGridSpacing(worldGap);

         startGrid = Math.floor(effectiveXMin / gridSpacing) * gridSpacing;

         g2d.setColor(labelColor);

        for (double x = startGrid; x <= effectiveXMax; x += gridSpacing) {

            Point2D pt = g2c.transform(new Point2D.Double(x, 0), null);
            double screenX = pt.getX();

            if (screenX >= 0 && screenX < chartPanel.getWidthX()) {
                g2d.drawLine((int) screenX, 0, (int) screenX, chartPanel.getHeightY());

            }
        }

    }
    private double findDynamicGridSpacing(double worldGap) {
        double[] intervals = {
                1, 5, 10, 15, 30, 60,
                90, 120, 300, 600,
                900, 1200, 1800, 3600,
                7200, 10800, 14400, 21600,
                43200, 86400,
                172800, 259200,
                604800, 1209600,
                2592000, 7776000,
                15552000, 31536000,
                63072000, 315360000,
                3155760000L,
                10000 // Fallback
        };

        for (double interval : intervals) {
            double numberOfLines = (xMax - xMin) / (interval * xScale);
            //   System.out.println("Interval: " + interval + ", Number of Lines: " + numberOfLines);

            // Check if number of lines is within the desired constraints
            if (numberOfLines >= chartPanel.getMinGridLines() && numberOfLines <= chartPanel.getMaxGridLines()) {
                return interval;
            }
        }

        // Consider returning the smallest interval or a default that makes sense
        // System.out.println("No suitable grid spacing found. Using fallback value.");
        return intervals[0]; // Or another sensible default
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public int getXAxisSize() {
        return DEFAULT_X_AXIS_SIZE;
    }
}
