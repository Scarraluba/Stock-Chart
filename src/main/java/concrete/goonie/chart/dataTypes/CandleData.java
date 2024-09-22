package concrete.goonie.chart.dataTypes;

import java.util.ArrayList;
import java.util.List;

public class CandleData {
    private final List<Candle> candles;


    public CandleData() {
        candles = new ArrayList<>();
    }

    public void addCandle(Candle candle) {
        candles.add(candle);
    }

    public List<Candle> getCandles() {
        return candles;
    }


}
