import 'package:firebase_database/firebase_database.dart';

class SessionData {
  String sessionId;
  List<dynamic> methanePercentage;
  List<dynamic> methaneVolume;

  SessionData({
    required this.sessionId,
    required this.methanePercentage,
    required this.methaneVolume,
  });

  factory SessionData.fromSnapshot(String sessionId, DataSnapshot snapshot) {
    final data = snapshot.value as Map<dynamic, dynamic>;
    final methanePercentage = _parseCustomListFormat(data['methanePercentage'] ?? '[]');
    final methaneVolume = _parseCustomListFormat(data['methaneVolume'] ?? '[]');
    return SessionData(
      sessionId: sessionId,
      methanePercentage: methanePercentage,
      methaneVolume: methaneVolume,
    );
  }
  
  factory SessionData.empty() {
    return SessionData(
      sessionId: '',
      methanePercentage: [],
      methaneVolume: [],
    );
  }
  
  // Clear method to reset data
  void clear() {
    sessionId = '';
    methanePercentage.clear();
    methaneVolume.clear();
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
}
