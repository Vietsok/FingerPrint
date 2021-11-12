package com.lokavida.fingerprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

// UI Views
    private TextView authStatusTv;
    private Button authBtn;


    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    // Init UI Views
        authStatusTv = findViewById(R.id.authStatusTv);
        authBtn = findViewById(R.id.authBtn);

        // init bio metric

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                authStatusTv.setText("Authentication Error:"+errString);
                Toast.makeText(MainActivity.this, "Authentication error:"+errString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // authentication succeed, continue tasts that requires auth
                authStatusTv.setText("Authentication succeed...  ");
                Toast.makeText(MainActivity.this, "Authentication succeed...", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // failed authentification, stop taks that requires auth

                authStatusTv.setText("Authentication failed...  ");
                Toast.makeText(MainActivity.this, "Authentication failed...", Toast.LENGTH_LONG).show();
            }
        });


        //setup title, description on auth dialog

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentification")
                .setSubtitle("Login using fingerprint authentification")
                .setNegativeButtonText("User App Password")
        .build();


        /** handle authBtn click, start Authentication **/

        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show auth dialog
                biometricPrompt.authenticate(promptInfo);


            }
        });



    }
}