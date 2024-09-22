package concrete.goonie;

import concrete.goonie.chart.ChartPanel;
import concrete.goonie.chart.dataTypes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static JFrame frame;
    private static ChartPanel chartPanel;

    public static void main(String[] args) {

        frame = new JFrame("Interactive Stock Chart");
        chartPanel = new ChartPanel();

        JPanel controlPanel = new JPanel();
        JButton addCandlestickButton = new JButton("Add Candlestick");
        JButton showVolume = new JButton("Show Volume");
        JButton addLineData = new JButton("Add LineData");
        JButton changeColorButton = new JButton("Add second LineData");

        controlPanel.add(addCandlestickButton);
        controlPanel.add(showVolume);
        controlPanel.add(addLineData);
        controlPanel.add(changeColorButton);

        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);


        // Change color of selected element
//        changeColorButton.addActionListener(e -> {
//            Color newColor = JColorChooser.showDialog(frame, "Choose Color", Color.WHITE);
//            if (newColor != null) {
//                chartPanel.setSelectedElementColor(newColor);
//            }
//        });
        CSVReader csvReader = new CSVReader();

        CandleData candleData = csvReader.readCandlesFromCSV("EURUSD_H1.csv");
        BarData barData = new BarData();
        LineData lineDataClose = new LineData();
        LineData lineDataOpen = new LineData();
        LineData lineDataHigh = new LineData();
        LineData lineDataLow = new LineData();

        int i =0;
        for (Candle candle : candleData.getCandles()) {
            Bar bar;
            if (candle.getOpen() < candle.getClose()) {
                bar = new Bar(1, (int) candle.getTickVol());
            } else {
                bar = new Bar(0, (int) candle.getTickVol());
            }
            barData.addBar(bar);
            lineDataClose.addPoint(new Point2D.Double(i, candle.getClose()));
            lineDataHigh.addPoint(new Point2D.Double(i, candle.getHigh()));
            i++;
        }


        addCandlestickButton.addActionListener(e -> {
            chartPanel.setCandleData(candleData);
        });

        showVolume.addActionListener(e -> {
            chartPanel.setBarData(barData);
        });

        addLineData.addActionListener(e -> {
            chartPanel.addLineData(lineDataClose);
        });
        changeColorButton.addActionListener(e -> {
            lineDataHigh.setColor(Color.BLUE);
            chartPanel.addLineData(lineDataHigh);
        });

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}