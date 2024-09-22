package concrete.goonie;


import concrete.goonie.chart.dataTypes.Candle;
import concrete.goonie.chart.dataTypes.CandleData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CSVReader {

    public CandleData readCandlesFromCSV(String fileName) {
        CandleData candleData = new CandleData();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Load the file from resources
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found! " + fileName);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            // Skip the header if it exists
            br.readLine(); // Assuming the first line is the header

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");

                // Parse Date and Time
                LocalDate date = LocalDate.parse(values[0], dateFormatter);
                LocalTime time = LocalTime.parse(values[1], timeFormatter);
                LocalDateTime dateTime = LocalDateTime.of(date, time);

                // Parse other fields
                double open = Double.parseDouble(values[2]);
                double high = Double.parseDouble(values[3]);
                double low = Double.parseDouble(values[4]);
                double close = Double.parseDouble(values[5]);
                int tickVol = Integer.parseInt(values[6]);
                int volume = Integer.parseInt(values[7]);
                int spread = Integer.parseInt(values[8]);

                // Create a Candle object and add it to the list
                Candle candle = new Candle(dateTime, open, high, low, close, tickVol, volume, spread);
                candleData.addCandle(candle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return candleData;
    }
}
