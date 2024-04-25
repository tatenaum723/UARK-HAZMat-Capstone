import 'package:firebase_database/firebase_database.dart';
import 'dart:math'; 

class SessionData {
  String sessionId;
  List<dynamic> methanePercentage;
  List<dynamic> temperature;
  List<dynamic> lelPercentage;
  double maxMethaneValue = 0;
  double maxTemperatureValue = 0;

  SessionData({
    required this.sessionId,
    required this.methanePercentage,
    required this.temperature,
    required this.lelPercentage,
  });

  factory SessionData.fromSnapshot(String sessionId, DataSnapshot snapshot) {
    final data = snapshot.value as Map<dynamic, dynamic>;
    final rawMethanePercentage = _parseCustomListFormat(data['methanePercentage'] ?? '[]');

    // Adjust the methane percentage values
    final adjustedMethanePercentage = rawMethanePercentage.map((dataPoint) {
      return {
        'time': dataPoint['time'],
        'value': dataPoint['value'] * 0.05,
      };
    }).toList();

    // Calculate max values
    double maxMethane = adjustedMethanePercentage.fold(0.0, (max, dp) => max > dp['value'] ? max : dp['value']);
    final temperature = _parseCustomListFormat(data['temperature'] ?? '[]');
    double maxTemp = temperature.fold(0.0, (max, dp) => max > dp['value'] ? max : dp['value']);

    // Use the adjusted methane values to calculate the LEL percentage
    final lelPercentage = _convertToLELPercentage(adjustedMethanePercentage);

    return SessionData(
      sessionId: sessionId,
      methanePercentage: adjustedMethanePercentage,
      temperature: temperature,
      lelPercentage: lelPercentage,
    )
      ..maxMethaneValue = maxMethane * 1.5
      ..maxTemperatureValue = maxTemp * 1.5;
  }
  
  factory SessionData.empty() {
    return SessionData(
      sessionId: '',
      methanePercentage: [],
      temperature: [],
      lelPercentage: [],
    );
  }
  
  void clear() {
    sessionId = '';
    methanePercentage.clear();
    temperature.clear();
    lelPercentage.clear();
    maxMethaneValue = 0;
    maxTemperatureValue = 0;
  } 

  static List<dynamic> _parseCustomListFormat(String rawData) {
    final List<dynamic> parsedList = [];
    final regex = RegExp(r'\((\d+), ([\d.]+)\)');
    final matches = regex.allMatches(rawData);
    for (final match in matches) {
      final time = int.parse(match.group(1)!);
      final value = double.parse(match.group(2)!);
      parsedList.add({'time': time, 'value': value});
    }
    return parsedList;
  }

  // Convert methane percentage data to LEL percentage data
  static List<dynamic> _convertToLELPercentage(List<dynamic> methaneData) {
    return methaneData.map((dataPoint) {
      final lelValue = ((dataPoint['value'] / 5) * 100); // Methane value converted to LEL percentage
      return {
        'time': dataPoint['time'],
        'value': lelValue > 100 ? 100 : lelValue, // LEL value should not exceed 100%
      };
    }).toList();
  }
}
