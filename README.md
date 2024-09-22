# chisl_method2
Конечно, давайте разберем код по методам и шагам, чтобы понять, как он работает.

### 1. Чтение данных из файла

```java
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
```

**Объяснение:**
- Создаются два списка `xValues` и `yValues` для хранения значений `x` и `y` соответственно.
- Используется `BufferedReader` для чтения данных из файла `data.txt`.
- В цикле `while` читаются строки из файла.
- Каждая строка разбивается на части с помощью метода `split(",")`, и значения `x` и `y` преобразуются в `double` и добавляются в соответствующие списки.

### 2. Преобразование списков в массивы

```java
double[] xArray = xValues.stream().mapToDouble(Double::doubleValue).toArray();
double[] yArray = yValues.stream().mapToDouble(Double::doubleValue).toArray();
```

**Объяснение:**
- Списки `xValues` и `yValues` преобразуются в массивы `xArray` и `yArray` с помощью стримов и метода `mapToDouble`.

### 3. Создание интерполятора

```java
SplineInterpolator interpolator = new SplineInterpolator();
PolynomialSplineFunction function = interpolator.interpolate(xArray, yArray);
```

**Объяснение:**
- Создается объект `SplineInterpolator` из библиотеки Apache Commons Math.
- Метод `interpolate` используется для создания интерполированной функции на основе массивов `xArray` и `yArray`.

### 4. Генерация данных для визуализации

```java
XYSeries series = new XYSeries("Interpolated Function");
for (double x = xArray[0]; x <= xArray[xArray.length - 1]; x += 0.1) {
    series.add(x, function.value(x));
}
```

**Объяснение:**
- Создается объект `XYSeries` для хранения данных интерполированной функции.
- В цикле `for` генерируются значения `x` с шагом 0.1 в диапазоне от минимального до максимального значения `xArray`.
- Для каждого значения `x` вычисляется значение функции `function.value(x)` и добавляется в серию.

### 5. Добавление исходных точек

```java
XYSeries originalSeries = new XYSeries("Original Points");
for (int i = 0; i < xArray.length; i++) {
    originalSeries.add(xArray[i], yArray[i]);
}
```

**Объяснение:**
- Создается объект `XYSeries` для хранения исходных точек.
- В цикле `for` добавляются исходные точки `(xArray[i], yArray[i])` в серию.

### 6. Создание набора данных

```java
XYSeriesCollection dataset = new XYSeriesCollection();
dataset.addSeries(series);
dataset.addSeries(originalSeries);
```

**Объяснение:**
- Создается объект `XYSeriesCollection` для хранения всех серий данных.
- В коллекцию добавляются серии `series` и `originalSeries`.

### 7. Создание графика

```java
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
```

**Объяснение:**
- Создается объект `JFreeChart` с помощью метода `createXYLineChart`.
- Задаются заголовок графика, метки осей, набор данных и ориентация графика.

### 8. Отображение графика

```java
ChartPanel chartPanel = new ChartPanel(chart);
chartPanel.setPreferredSize(new Dimension(800, 600));
JFrame frame = new JFrame("Interpolation Example");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.add(chartPanel, BorderLayout.CENTER);
frame.pack();
frame.setVisible(true);
```

**Объяснение:**
- Создается объект `ChartPanel` для отображения графика.
- Задается предпочтительный размер панели.
- Создается объект `JFrame` для отображения окна.
- В окно добавляется панель с графиком.
- Окно упаковывается и делается видимым.

### Итог

Этот код выполняет следующие шаги:
1. Читает данные из файла `data.txt`.
2. Преобразует списки в массивы.
3. Создает интерполятор и интерполированную функцию.
4. Генерирует данные для визуализации интерполированной функции.
5. Добавляет исходные точки для визуализации.
6. Создает набор данных.
7. Создает график.
8. Отображает график в окне.

Этот пример демонстрирует базовую интерполяцию и визуализацию с чтением данных из файла. Вы можете адаптировать его под свои конкретные требования и данные.
Создается объект ChartPanel для отображения графика.
Задается предпочтительный размер панели.
Создается объект JFrame для отображения окна.
В окно добавляется панель с графиком.
Окно упаковывается и делается видимым.
