package concrete.goonie.chart;


import concrete.goonie.chart.chartRenderers.ChartData;
import concrete.goonie.chart.dataTypes.BarData;
import concrete.goonie.chart.dataTypes.CandleData;
import concrete.goonie.chart.dataTypes.LineData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;

public class ChartPanel extends JPanel {

    private ChartData data;
    private ChartMouseHandler mouseHandler;
    private AffineTransform g2c = new AffineTransform();

    private XAxisRenderer xAxisRenderer;
    private YAxisRenderer yAxisRenderer;

    private int widthX = 0;
    private int heightY = 0;
    private double xScale;
    private double yScale;
    private int panX;
    private int panY;

    private int minGridLines = 5;
    private int maxGridLines = 14;
    private double minGridSpacing = 25;

    public ChartPanel() {
        setBackground(Color.WHITE);
        data = new ChartData();

        mouseHandler = new ChartMouseHandler(this, data.getData());
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

        xAxisRenderer = new XAxisRenderer(this);
        yAxisRenderer = new YAxisRenderer(this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateViewportBounds();
            }
        });
    }

    private void updateViewportBounds() {
        Dimension panelSize = getSize();

        widthX = (panelSize.width - 80);
        heightY = (panelSize.height - 40);
    }

   /* public void addElement(ChartElement element) {
        elements.add(element);
        repaint();
    }*/

//    public void setSelectedElementColor(Color color) {
//        if (mouseHandler.selectedElement != null) {
//            mouseHandler.selectedElement.setColor(color);
//            repaint();
//        }
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2c = new AffineTransform();

        xScale = mouseHandler.getXScale();
        yScale = mouseHandler.getYScale();
        panX = (int) mouseHandler.getPanX();
        panY = (int) mouseHandler.getPanY();

        g2c = new AffineTransform();
        double currXScale = (xScale * (widthX / (xAxisRenderer.getXMax() - xAxisRenderer.getXMin())));
        double currYScale = -(yScale * (heightY / (yAxisRenderer.getYMax() - yAxisRenderer.getYMin())));

        g2c.translate(widthX, (double) heightY / 2);
        g2c.scale(currXScale, currYScale);
        g2c.translate(-(data.size() + 2) + (panX / currXScale), -panY / currYScale);

        xAxisRenderer.drawXAxisGrid(g2d, g2c);
        yAxisRenderer.drawYAxisGrid(g2d, g2c);
        xAxisRenderer.drawXAxisLabel(g2d, g2c);
        yAxisRenderer.drawYAxisLabels(g2d, g2c);

        data.draw(g2d, g2c, widthX, heightY);
        g2c.scale(51, 1);
    }

    public void setCandleData(CandleData candleData) {
        data.setCandleData(candleData);
        repaint();
    }
    public void setBarData(BarData barData) {
        data.setBarData(barData);
        repaint();
    }
    public void addLineData(LineData lineData ) {
        data.addLineData(lineData);
    }

    public int getMinGridLines() {
        return minGridLines;
    }

    public int getMaxGridLines() {
        return maxGridLines;
    }

    public double getMinGridSpacing() {
        return minGridSpacing;
    }

    public int getWidthX() {
        return widthX;
    }

    public int getHeightY() {
        return heightY;
    }

    public double getyScale() {
        return yScale;
    }

    public double getxScale() {
        return xScale;
    }

    public int getPanX() {
        return panX;
    }

    public int getPanY() {
        return panY;
    }



}
