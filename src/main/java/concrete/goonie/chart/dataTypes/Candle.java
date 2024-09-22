package concrete.goonie.chart.dataTypes;

import java.time.LocalDateTime;

public class Candle {

    private LocalDateTime date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long tickVol;
    private long volume;
    private long spread;

    public Candle(double open, double high, double low, double close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public Candle(LocalDateTime date, double open, double high, double low,
                  double close, long tickVol, long volume, long spread) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.tickVol = tickVol;
        this.volume = volume;
        this.spread = spread;
    }

    public Candle() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getTickVol() {
        return tickVol;
    }

    public void setTickVol(long tickVol) {
        this.tickVol = tickVol;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getSpread() {
        return spread;
    }

    public void setSpread(long spread) {
        this.spread = spread;
    }
}
