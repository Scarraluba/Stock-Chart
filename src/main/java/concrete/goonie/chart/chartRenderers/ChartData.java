package concrete.goonie.chart.chartRenderers;

import concrete.goonie.chart.ChartElement;
import concrete.goonie.chart.dataTypes.BarData;
import concrete.goonie.chart.dataTypes.CandleData;
import concrete.goonie.chart.dataTypes.LineData;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class ChartData extends ChartElement {

    private final List<ChartElement> data;
    private final CandlestickChartElement candlestickChartElement;
    private final BarChartElement barChartElement;
    private final LineChartElement lineChartElement;

    public ChartData() {
        super(new Color(1));
        data = new ArrayList<>();

        candlestickChartElement = new CandlestickChartElement();
        data.add(candlestickChartElement);

        lineChartElement = new LineChartElement();
        data.add(lineChartElement);

        barChartElement = new BarChartElement();
        data.add(barChartElement);

    }

    @Override
    protected boolean contains(Point2D point) {
        return false;
    }

    @Override
    public void draw(Graphics2D g2d, AffineTransform transform, int width, int height) {

        for (ChartElement element : data) {
            element.draw(g2d, transform, width, height);

        }
        System.out.println("ChartData : draw");
    }

    @Override
    protected void move(double dx, double dy) {

    }

    public List<ChartElement> getData() {
        return data;
    }

    public void setCandleData(CandleData candleData) {
        candlestickChartElement.setCandleData(candleData);
    }

    public void setBarData(BarData barData) {
        barChartElement.setBarData(barData);
    }
    public void addLineData(LineData lineData) {
        lineChartElement.add(lineData);

    }


    public int size() {
        return candlestickChartElement.getCandleData().getCandles().size();
    }


}
