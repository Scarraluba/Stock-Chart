package concrete.goonie.chart.dataTypes;

import java.util.ArrayList;
import java.util.List;

public class BarData {
    private final List<Bar> bars;

    public BarData() {
        bars = new ArrayList<>();
    }

    public void addBar(Bar bar) {
        bars.add(bar);
    }

    public List<Bar> getBars() {
        return bars;
    }
}

