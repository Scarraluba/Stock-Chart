Here's an updated version of the `README.md` with the addition that the analysis tools are not yet complete:

---

# Stock Chart 

## Overview

This project is a **Stock Chart ** that provides developers and traders with a robust platform to visualize stock market data. It supports multiple chart elements such as candlesticks, trendlines, Fibonacci retracements, and aims to include advanced analysis tools. The tool is designed with performance optimizations to handle large datasets and dynamic interactions like zooming and panning.

### Key Features:
- **Candlestick Rendering**: Visualize OHLC (Open, High, Low, Close) data.
- **Dynamic Grid System**: Automatically adjusts grid lines based on chart position and zoom level.
- **Big Data Performance**: Optimized to handle large datasets efficiently.
- **Y-Axis Scaling**: Automatically adjusts to data (e.g., yMin = 0, yMax = 1000, or based on actual values).
- **Zooming and Panning**: Provides smooth zooming capabilities without sacrificing performance.
- **Modular Chart Design**: Allows integration of multiple charting elements (trendlines, Fibonacci tools, etc.).

---

## Unfinished Features and Known Issues

The project is still under development, and several important features and analysis tools are not fully implemented or remain challenging. Below is a list of unfinished components:

1. **Analysis Tools (Not Completed)**:
   - **Issue**: Key analysis tools (such as moving averages, indicators,or geometric shapes and automated patterns) are not yet implemented.
   - **Next Steps**: Build additional analysis tools and integrate them into the chart in a modular fashion.

2. **Precise Zoom Control**:
   - **Issue**: Zooming in and out, while synchronizing with the grid, is still difficult to manage for smooth user interaction.
   - **Next Steps**: Refine zooming logic to decouple grid scaling from data rendering.


4. **Custom Y-Axis Scaling for Small Values**:
   - **Issue**: The chart struggles with very small values (e.g., currency pairs around 1), creating large grid line gaps.
   - **Next Steps**: Create better logic to handle very small ranges without excessive spacing between grid lines.
---

## Getting Started

### Prerequisites

- **Java 8+**
- **Maven/Gradle**
- **IntelliJ IDEA** (or any other Java IDE)

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/YourUsername/StockChartVisualization.git
   cd StockChart
   ```

2. **Build the Project**:
   - If using Maven:
     ```bash
     mvn clean install
     ```
   - If using Gradle:
     ```bash
     gradle build
     ```

3. **Run the Project**:
   - Open the project in IntelliJ IDEA and run the `main()` method in the appropriate class (e.g., `StockChartApp.java`).

---

## Usage

## Performance Tips

- **Optimize for Large Datasets**: Use batch rendering techniques for large datasets to minimize UI thread load.

---

## Contributing

1. **Fork the Repository**.
2. **Clone your Fork**.
   ```bash
   git clone https://github.com/YourUsername/StockChartVisualization.git
   ```
3. **Create a Feature Branch**.
   ```bash
   git checkout -b feature-branch
   ```
4. **Commit your Changes**.
   ```bash
   git commit -m 'Added new feature'
   ```
5. **Push to your Fork**.
   ```bash
   git push origin feature-branch
   ```
6. **Submit a Pull Request**.

---

## License

This project is licensed under the MIT License.

---
