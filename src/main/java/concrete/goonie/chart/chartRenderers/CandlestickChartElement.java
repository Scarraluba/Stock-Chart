package concrete.goonie.chart.chartRenderers;

import concrete.goonie.chart.ChartElement;
import concrete.goonie.chart.dataTypes.Candle;
import concrete.goonie.chart.dataTypes.CandleData;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

class CandlestickChartElement extends ChartElement {

    private CandleData candleData;
    private ArrayList<Rectangle2D> bars;

    private Rectangle2D body;

    public CandlestickChartElement() {
        super(new Color(4));
        bars = new ArrayList<>();
        candleData = new CandleData();
    }

    @Override
    public boolean contains(Point2D point) {

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

        for (int i = 0; i < candleData.getCandles().size(); i++) {
            Candle data = candleData.getCandles().get(i);

            double xPos = i * 1.0; // X position for the candlestick (space between candles)

            // Transform coordinates
            Point2D openPoint = transform.transform(new Point2D.Double(xPos, data.getOpen()), null);
            Point2D highPoint = transform.transform(new Point2D.Double(xPos, data.getHigh()), null);
            Point2D lowPoint = transform.transform(new Point2D.Double(xPos, data.getLow()), null);
            Point2D closePoint = transform.transform(new Point2D.Double(xPos, data.getClose()), null);

            int x = (int) openPoint.getX();
            int yOpen = (int) openPoint.getY();
            int yClose = (int) closePoint.getY();
            int yHigh = (int) highPoint.getY();
            int yLow = (int) lowPoint.getY();

            if (x + barWidth / 2 < 0 || x - barWidth / 2 > width || yHigh > height || yLow < 0) {
                continue; // Skip drawing if the bar is not visible
            }

            // Draw the open-close rectangle
            int barHeight = Math.abs(yOpen - yClose);

            if (data.getClose() > data.getOpen()) {
                g2d.setColor(bullColor);
            } else {
                g2d.setColor(bearColor);
            }
            body = new Rectangle2D.Double(x - (int) (barWidth / 2), Math.min(yOpen, yClose), (int) barWidth, barHeight);
            g2d.drawLine(x, yHigh, x, yLow);

            g2d.fill(body);

            bars.add(body);
        }
        System.out.println("CandlestickElement : Draw");
    }

    @Override
    public void move(double dx, double dy) {

    }

    public CandleData getCandleData() {
        return candleData;
    }

    public void setCandleData(CandleData candleData) {
        this.candleData = candleData;
    }
}