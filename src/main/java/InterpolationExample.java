import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InterpolationExample {
    public static void main(String[] args) {
        // Чтение данных из файла
        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                xValues.add(Double.parseDouble(values[0]));
                yValues.add(Double.parseDouble(values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[] xArray = xValues.stream().mapToDouble(Double::doubleValue).toArray();
        double[] yArray = yValues.stream().mapToDouble(Double::doubleValue).toArray();

        // Создаем интерполятор
        SplineInterpolator interpolator = new SplineInterpolator();
        PolynomialSplineFunction function = interpolator.interpolate(xArray, yArray);

        // Генерируем данные для визуализации
        XYSeries series = new XYSeries("Interpolated Function");
        for (double x = xArray[0]; x <= xArray[xArray.length - 1]; x += 0.1) {
            series.add(x, function.value(x));
        }

        // Добавляем исходные точки
        XYSeries originalSeries = new XYSeries("Original Points");
        for (int i = 0; i < xArray.length; i++) {
            originalSeries.add(xArray[i], yArray[i]);
        }

        // Создаем набор данных
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(originalSeries);

        // Создаем график
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Interpolation Example",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Отображаем график
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("Interpolation Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
