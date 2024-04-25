import 'package:flutter/material.dart';
import 'package:charts_flutter/flutter.dart' as charts;
import 'SessionData.dart';
import 'AppBar.dart';

class GraphicsScreen extends StatelessWidget {
  final SessionData sessionData;
  final VoidCallback onBack;

  GraphicsScreen({required this.sessionData, required this.onBack});

  @override
  Widget build(BuildContext context) {
    ColorScheme colorScheme = Theme.of(context).colorScheme;
    TextStyle textStyle = TextStyle(color: colorScheme.onSurface);

    final screenWidth = MediaQuery.of(context).size.width;
    final chartWidth = screenWidth > 1200 ? 1200.0 : screenWidth;

    return Scaffold(
      appBar: CustomAppBar(
        title: 'Graphics View',
        backgroundColor: Theme.of(context).colorScheme.surface,
        titleColor: Theme.of(context).colorScheme.onSurface,
        titleFontSize: 20,
        iconSize: 24,
        onBack: onBack,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: <Widget>[
            buildChartSection(context, 'Methane Percentage', sessionData.methanePercentage.cast<Map<String, dynamic>>(), sessionData.maxMethaneValue * 1.5),
            SizedBox(height: 20),
            buildChartSection(context, 'LEL Percentage', sessionData.lelPercentage.cast<Map<String, dynamic>>(), 100),
            SizedBox(height: 20),
            buildChartSection(context, 'Temperature', sessionData.temperature.cast<Map<String, dynamic>>(), sessionData.maxTemperatureValue * 1.5),
          ],
        ),
      ),
    );
  }

  Widget buildChartSection(BuildContext context, String title, List<Map<String, dynamic>> data, double maxVal) {
    TextStyle textStyle = TextStyle(color: Theme.of(context).textTheme.bodyText2!.color);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(title, style: textStyle),
        Container(
          height: 250,
          child: _createScatterPlotChart(context, data, title, maxVal),
        ),
      ],
    );
  }

  Widget _createScatterPlotChart(BuildContext context, List<Map<String, dynamic>> data, String title, double maxVal) {
    ColorScheme colorScheme = Theme.of(context).colorScheme;

    // Adjust font size for axis labels
    final axisTextStyle = charts.TextStyleSpec(
      color: charts.ColorUtil.fromDartColor(colorScheme.onSurface),
      fontSize: 10, 
    );

    List<charts.Series<Map<String, dynamic>, num>> series = [
      charts.Series<Map<String, dynamic>, num>(
        id: title,
        domainFn: (Map<String, dynamic> row, _) => row['time'] as num,
        measureFn: (Map<String, dynamic> row, _) => row['value'] as num,
        data: data,
        colorFn: (_, __) => charts.ColorUtil.fromDartColor(colorScheme.onSecondary),
        fillColorFn: (_, __) => charts.ColorUtil.fromDartColor(colorScheme.onSecondary),
        radiusPxFn: (_, __) => 4,
        areaColorFn: (_, __) => charts.ColorUtil.fromDartColor(colorScheme.onPrimary),
      ),
    ];

    return charts.ScatterPlotChart(
      series,
      animate: true,
      domainAxis: charts.NumericAxisSpec(
        renderSpec: charts.SmallTickRendererSpec(
          labelStyle: axisTextStyle,
          labelOffsetFromAxisPx: 16,
          labelAnchor: charts.TickLabelAnchor.centered,
        ),
      ),
      primaryMeasureAxis: charts.NumericAxisSpec(
        renderSpec: charts.SmallTickRendererSpec(
          labelStyle: axisTextStyle,
          labelOffsetFromAxisPx: 5,
          labelAnchor: charts.TickLabelAnchor.centered,
        ),
        tickProviderSpec: charts.BasicNumericTickProviderSpec(desiredTickCount: 6),
        viewport: charts.NumericExtents(0, maxVal),
      ),
      behaviors: [
        charts.ChartTitle('Time (s)',
          behaviorPosition: charts.BehaviorPosition.bottom,
          titleOutsideJustification: charts.OutsideJustification.middleDrawArea,
          titleStyleSpec: charts.TextStyleSpec(fontSize: 14), 
        ),
        charts.ChartTitle(title,
          behaviorPosition: charts.BehaviorPosition.start,
          titleOutsideJustification: charts.OutsideJustification.middleDrawArea,
          titleStyleSpec: charts.TextStyleSpec(fontSize: 14), 
        ),
      ],
    );
  }
}
