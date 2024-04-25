import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'menu.dart'; // Ensure 'menu.dart' is correctly imported to access AppContent
import 'theme_data.dart'; // Import the ThemeManager to access isDarkMode

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final emailController = TextEditingController();
  final passwordController = TextEditingController();
  bool isLoading = false;
  bool loginError = false; // Local state for login error

  @override
  Widget build(BuildContext context) {
    ColorScheme colorScheme = Theme.of(context).colorScheme;
    TextTheme textTheme = Theme.of(context).textTheme;

    // Determine which image to show based on the theme mode
    String imagePath = ThemeManager.isDarkMode ? 'assets/images/dronedarkmode.png' : 'assets/images/dronelightmode.png';

    return Scaffold(
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(32.0),
          child: Column(
            children: [
              Text(
                "Welcome to Air Sentinel",
                style: textTheme.headline2!.copyWith(color: colorScheme.onSurface),
              ),
              SizedBox(height: 20),
              Image.asset(imagePath, width: 391, height: 198), 
              Card(
                elevation: 4.0,
                color: colorScheme.surface,
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: isLoading
                      ? CircularProgressIndicator()
                      : Column(
                          mainAxisSize: MainAxisSize.min,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text("Sign In", style: textTheme.headline6),
                            const SizedBox(height: 16),
                            if (loginError)
                              Text("Login Failed. Try again.", style: TextStyle(color: colorScheme.error)),
                            TextFormField(
                              controller: emailController,
                              decoration: InputDecoration(
                                labelText: "Email",
                                border: OutlineInputBorder(),
                                prefixIcon: Icon(Icons.email, color: colorScheme.onSurface),
                              ),
                            ),
                            const SizedBox(height: 16),
                            TextFormField(
                              controller: passwordController,
                              obscureText: true,
                              decoration: InputDecoration(
                                labelText: "Password",
                                border: OutlineInputBorder(),
                                prefixIcon: Icon(Icons.lock, color: colorScheme.onSurface),
                              ),
                            ),
                            const SizedBox(height: 24),
                            Center(
                              child: ElevatedButton(
                                style: ElevatedButton.styleFrom(
                                  minimumSize: Size(200, 50),
                                  backgroundColor: colorScheme.secondary,
                                  shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(30)),
                                ),
                                onPressed: _login,
                                child: Text("Sign In", style: TextStyle(color: colorScheme.onSurface)),
                              ),
                            ),
                            const SizedBox(height: 24),
                          ],
                        ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _login() async {
    setState(() => isLoading = true);
    try {
      UserCredential userCredential = await FirebaseAuth.instance.signInWithEmailAndPassword(
        email: emailController.text.trim(),
        password: passwordController.text.trim(),
      );
      if (userCredential.user != null) {
        Navigator.of(context).pushReplacement(MaterialPageRoute(builder: (_) => AppContent()));
      } else {
        setState(() => loginError = true);
      }
    } on FirebaseAuthException catch (e) {
      setState(() => loginError = true);
    } finally {
      setState(() => isLoading = false);
    }
  }

  @override
  void dispose() {
    emailController.dispose();
    passwordController.dispose();
    super.dispose();
  }
}
