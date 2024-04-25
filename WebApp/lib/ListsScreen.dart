import 'package:flutter/material.dart';
import 'SessionData.dart';
import 'AppBar.dart';
@immutable
class ListsScreen extends StatelessWidget {
  final SessionData sessionData;
  final VoidCallback onBack;

  ListsScreen({required this.sessionData, required this.onBack});

  @override
  Widget build(BuildContext context) {
    ColorScheme colorScheme = Theme.of(context).colorScheme;
    TextStyle dataTextStyle = TextStyle(color: colorScheme.onSurface);  

    return Scaffold(
      appBar: CustomAppBar(
        title: 'Lists View',
        backgroundColor: Theme.of(context).colorScheme.surface,
        titleColor: Theme.of(context).colorScheme.onSurface,
        titleFontSize: 20,
        iconSize: 24,
        onBack: onBack,
      ),
      body: SingleChildScrollView(
        scrollDirection: Axis.horizontal, 
        child: SingleChildScrollView(
          child: DataTable(
            headingTextStyle: TextStyle(color: colorScheme.onPrimary),
            dataTextStyle: dataTextStyle,
            columns: [
              DataColumn(label: Text('Time', style: dataTextStyle)),
              DataColumn(label: Text('Methane Percentage', style: dataTextStyle)),
              DataColumn(label: Text('LEL Percentage', style: dataTextStyle)),
              DataColumn(label: Text('Temperature', style: dataTextStyle)),
            ],
            rows: List.generate(sessionData.methanePercentage.length, (index) {
              final methaneItem = sessionData.methanePercentage[index];
              final lelItem = sessionData.lelPercentage[index]; 
              final temperatureItem = sessionData.temperature[index];
              return DataRow(cells: [
                DataCell(Text('${methaneItem["time"]}', style: dataTextStyle)),
                DataCell(Text('${methaneItem["value"].toStringAsFixed(4)}', style: dataTextStyle)),
                DataCell(Text('${lelItem["value"].toStringAsFixed(4)}', style: dataTextStyle)), 
                DataCell(Text('${temperatureItem["value"].toStringAsFixed(4)}', style: dataTextStyle)), 
              ]);
            }),
          ),
        ),
      ),

    );
  }
}
