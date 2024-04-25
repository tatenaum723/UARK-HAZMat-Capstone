import 'package:flutter/material.dart';
import 'SettingsScreen.dart';
import 'SessionsScreen.dart';
import 'GraphicsScreen.dart';
import 'ListsScreen.dart';
import 'SessionData.dart';
import 'AppBar.dart';
enum Screen { MAIN_MENU, SETTINGS, SESSIONS, GRAPHICS, LISTS, MENU_4 }

class AppContent extends StatefulWidget {
  @override
  _AppContentState createState() => _AppContentState();
}

class _AppContentState extends State<AppContent> {
  Screen currentScreen = Screen.MAIN_MENU;
  String? selectedSessionId;
  SessionData sessionData = SessionData.empty(); // Initialize with empty data

  void _selectSession(String sessionId) {
    setState(() {
      selectedSessionId = sessionId;
      currentScreen = Screen.MAIN_MENU; // Navigate back to the main menu
    });
  }

  @override
  Widget build(BuildContext context) {
    ColorScheme colorScheme = Theme.of(context).colorScheme;
    return Scaffold(
      body: _buildBody(),
      bottomNavigationBar: selectedSessionId != null
        ? BottomAppBar(
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Text('Session ID: $selectedSessionId'),
            ),
          )
        : null,
    );
  }

  Widget _buildBody() {
    switch (currentScreen) {
      case Screen.MAIN_MENU:
        return MainMenuScreen(
          onScreenSelected: (selectedScreen) => setState(() => currentScreen = selectedScreen),
          isSessionSelected: selectedSessionId != null,
        );
      case Screen.SETTINGS:
        // Pass onBack function to return to the main menu
        return SettingsScreen(onBack: () => setState(() => currentScreen = Screen.MAIN_MENU));
      case Screen.SESSIONS:
        return SessionsScreen(
          onBack: () => setState(() => currentScreen = Screen.MAIN_MENU),
          onSessionSelected: _selectSession,
          onSessionDataFetched: (fetchedSessionData) {
            setState(() {
              sessionData = fetchedSessionData;
            });
          },
        );
      case Screen.GRAPHICS:
        return GraphicsScreen(
          sessionData: sessionData,
          onBack: () => setState(() => currentScreen = Screen.MAIN_MENU)
        );
      case Screen.LISTS:
        return ListsScreen(
          sessionData: sessionData,
          onBack: () => setState(() => currentScreen = Screen.MAIN_MENU)
        );
      default:
        return DefaultScreen(onBack: () => setState(() => currentScreen = Screen.MAIN_MENU));
    }
  }

}

class MainMenuScreen extends StatelessWidget {
  final Function(Screen) onScreenSelected;
  final bool isSessionSelected;

  MainMenuScreen({
    required this.onScreenSelected,
    required this.isSessionSelected,
  });

  @override
  Widget build(BuildContext context) {
    ColorScheme colorScheme = Theme.of(context).colorScheme;
    // Responsive tile size based on screen size
    final double screenWidth = MediaQuery.of(context).size.width;
    final double tileWidth = screenWidth < 600 ? screenWidth / 2 - 20 : 250.0;
    final double tileHeight = tileWidth;

    List<Widget> tiles = [
      _createDashboardTile(
        context: context,
        icon: Icons.analytics,
        title: 'Graphics',
        color: colorScheme.onSecondaryContainer,
        enabled: isSessionSelected,
        onTap: () => onScreenSelected(Screen.GRAPHICS),
      ),
      _createDashboardTile(
        context: context,
        icon: Icons.list,
        title: 'Lists',
        color: colorScheme.onSecondaryContainer,
        enabled: isSessionSelected,
        onTap: () => onScreenSelected(Screen.LISTS),
      ),
      _createDashboardTile(
        context: context,
        icon: Icons.date_range,
        title: 'Sessions',
        color: colorScheme.onSecondaryContainer,
        onTap: () => onScreenSelected(Screen.SESSIONS),
      ),
      _createDashboardTile(
        context: context,
        icon: Icons.speed,
        title: 'Module',
        color: colorScheme.onSecondaryContainer,
        onTap: () => onScreenSelected(Screen.MENU_4),
      ),
      _createDashboardTile(
        context: context,
        icon: Icons.settings,
        title: 'Settings',
        color: colorScheme.onSecondaryContainer,
        onTap: () => onScreenSelected(Screen.SETTINGS),
      ),
    ];

    return Scaffold(
      appBar: CustomAppBar(
        title: 'Dashboard',
        backgroundColor: Theme.of(context).colorScheme.surface,
        titleColor: Theme.of(context).colorScheme.onSurface,
        titleFontSize: 20,
        iconSize: 24,
      ),
      body: Center(
        child: Wrap(
          spacing: 20, // Spacing between tiles horizontally
          runSpacing: 20, // Spacing between tiles vertically
          alignment: WrapAlignment.center, // Center tiles horizontally
          children: tiles.map((tile) {
            // Wrap each tile in a sized box to maintain the size
            return SizedBox(
              width: tileWidth,
              height: tileHeight,
              child: tile,
            );
          }).toList(),
        ),
      ),
    );
  }

  Widget _createDashboardTile({
    required BuildContext context,
    required IconData icon,
    required String title,
    required Color color,
    required VoidCallback onTap,
    bool enabled = true,  // Default value set to true
  }) {
    return Card(
      color: enabled ? color : color.withOpacity(0.5),  // Adjust disabled color opacity
      child: InkWell(
        onTap: enabled ? onTap : null,
        child: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Icon(icon, size: 50, color: Colors.white),
              const SizedBox(height: 20),
              Text(
                title,
                style: const TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}




class DefaultScreen extends StatelessWidget {
  final VoidCallback onBack;

  const DefaultScreen({required this.onBack});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Unimplemented Screen"),
        leading: IconButton(icon: const Icon(Icons.arrow_back), onPressed: onBack),
      ),
      body: const Center(child: Text("This module has not been implemented yet, but can be used to expand the application's functionality.")),
    );
  }
}
