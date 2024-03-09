import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_auth/firebase_auth.dart';

import 'package:flutter/material.dart';

class LoginScreen extends StatefulWidget {
  final VoidCallback onLoginSuccess;
  final VoidCallback onLoginFailed;
  final bool loginError;

  const LoginScreen({
    Key? key,
    required this.onLoginSuccess,
    required this.onLoginFailed,
    this.loginError = false,
  }) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}


class _LoginScreenState extends State<LoginScreen> {
  final emailController = TextEditingController();
  final passwordController = TextEditingController();
  bool isLoading = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: isLoading
            ? const CircularProgressIndicator()
            : Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  if (widget.loginError)
                    const Text(
                      "Login Failed. Try again.",
                      style: TextStyle(color: Colors.red),
                    ),
                  TextField(
                    controller: emailController,
                    decoration: const InputDecoration(labelText: "Email"),
                  ),
                  TextField(
                    controller: passwordController,
                    decoration: const InputDecoration(labelText: "Password"),
                    obscureText: true,
                  ),
                  ElevatedButton(
                    onPressed: _login,
                    child: const Text("Login"),
                  ),
                ],
              ),
      ),
    );
  }

void _login() async {
  setState(() => isLoading = true);
  try {
    await FirebaseAuth.instance.signInWithEmailAndPassword(
      email: emailController.text.trim(),
      password: passwordController.text.trim(),
    ).then((userCredential) {
      if (userCredential.user != null) {
        // Here you can handle the user ID as needed, for example:
        // String uid = userCredential.user!.uid;
        // Do something with the uid or save it for later use.

        // Call the success callback without passing the uid.
        widget.onLoginSuccess();
      } else {
        widget.onLoginFailed();
      }
    });
  } on FirebaseAuthException catch (e) {
    print(e); // For debugging
    widget.onLoginFailed();
  } finally {
    if (mounted) {
      setState(() => isLoading = false);
    }
  }
}




  @override
  void dispose() {
    emailController.dispose();
    passwordController.dispose();
    super.dispose();
  }
}
