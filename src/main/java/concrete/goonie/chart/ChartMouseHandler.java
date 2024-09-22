package concrete.goonie.chart;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.List;

public class ChartMouseHandler extends MouseAdapter implements MouseMotionListener {
    private final List<ChartElement> elements;
    private final ChartPanel chartPanel;
    protected ChartElement selectedElement;

    private double xScale = 1.0, yScale = 1.0;
    private double panX = 0, panY = 0;
    private double minXScale = 0.01;

    private Point2D lastMousePoint;
    private Point2D lastMousePoint0;

    private boolean isCtrlPressed;


    public ChartMouseHandler(ChartPanel chartPanel, List<ChartElement> elements) {
        this.chartPanel = chartPanel;
        this.elements = elements;

        chartPanel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        panX += 1;  // Pan left, adjusting for zoom level
                        break;
                    case KeyEvent.VK_RIGHT:
                        panX -= 1;  // Pan right, adjusting for zoom level
                        break;
                    case KeyEvent.VK_UP:
                        panY -= 0.5;  // Pan up, adjusting for zoom level
                        break;
                    case KeyEvent.VK_DOWN:
                        panY += 0.5;  // Pan down, adjusting for zoom level
                        break;
                }
                chartPanel.repaint();
            }
        });
        chartPanel.setFocusable(true);
        chartPanel.requestFocusInWindow();
    }

    // Method to find the chart element at the mouse click point
    private ChartElement getElementAt(Point2D point) {
        for (ChartElement element : elements) {
            if (element.contains(point)) {
                System.out.println("ChartMouseHandler : Contains = True");

                return element;
            }
        }
        return null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point2D clickPoint = e.getPoint();

        if (e.getX() >= chartPanel.getWidthX() || e.getY() >= chartPanel.getHeightY()) {
            lastMousePoint = clickPoint;
        } else {
            selectedElement = getElementAt(clickPoint);
            lastMousePoint0 = clickPoint;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        lastMousePoint = null;
        lastMousePoint0 = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D currentPoint = e.getPoint();
        if (lastMousePoint != null) {
            double dx = e.getX() - lastMousePoint.getX();
            double dy = e.getY() - lastMousePoint.getY();

            if (e.getX() >= chartPanel.getWidthX()) {
                yScale *= (1.0 - dy / chartPanel.getHeightY());
            } else if (e.getY() >= chartPanel.getHeightY()) {
                xScale *= (1.0 - dx / chartPanel.getWidthX());
            }
            lastMousePoint = new Point2D.Double(e.getX(), e.getY());
        }
        if (lastMousePoint0 != null) {
            double dx = currentPoint.getX() - lastMousePoint0.getX();
            double dy = currentPoint.getY() - lastMousePoint0.getY();

            if (selectedElement != null) {
                selectedElement.move(dx, dy);
            }

            double PAN_SPEED = 3.5;
            panX += PAN_SPEED * Math.signum(dx);
            panY -= PAN_SPEED * Math.signum(dy);
            lastMousePoint0 = currentPoint;
        }
        chartPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point2D clickPoint = e.getPoint();

        int width = chartPanel.getWidthX();
        int height = chartPanel.getHeightY();

        if (e.getX() >= width && e.getY() <= height) {
            chartPanel.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        } else if (e.getX() <= width && e.getY() >= height) {
            chartPanel.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        } else if (e.getX() >= width && e.getY() >= height) {
            chartPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } else {
            if (getElementAt(clickPoint) != null) {
                chartPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                chartPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }
        }

        chartPanel.repaint();

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);
        double scaleFactor = (e.getWheelRotation() < 0) ? 1.1 : 0.9;


    }

    public double getXScale() {
        return xScale;
    }

    public double getPanY() {
        return panY;
    }

    public double getPanX() {
        return panX;
    }

    public double getYScale() {
        return yScale;
    }
}
