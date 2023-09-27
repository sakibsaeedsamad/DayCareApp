package com.example.daycareapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.daycareapp.util.AuthToken;
import com.example.daycareapp.R;
import com.example.daycareapp.network.RetrofitAPIClient;
import com.example.daycareapp.models.User;
import com.example.daycareapp.network.response.GoogleLoginResponseModel;
import com.example.daycareapp.network.service.GoogleLoginService;
import com.example.daycareapp.util.SharedRefs;
import com.example.daycareapp.viewmodels.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private ImageButton googleLoginButton;
    private Button loginButton, aboutUs;
    private ActivityResultLauncher<Intent> googleLoginActivityResultLauncher;
    private AuthViewModel authViewModel;
    SharedRefs sharedRefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedRefs = new SharedRefs(getApplicationContext());
        aboutUs = findViewById(R.id.aboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        googleLoginActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            GoogleLoginResponseModel googleLoginResponseModel = GoogleLoginService.getGoogleAccountInfo(task);
                            if (googleLoginResponseModel == null) {
                                Toast.makeText(LoginActivity.this, "Service Error!", Toast.LENGTH_LONG).show();
                                return;
                            }
                            loginWithGoogle(googleLoginResponseModel);
                        }
                    }
                });

        //To change status bar icon color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        googleLoginButton = (ImageButton) findViewById(R.id.google_login_button);
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInClient mGoogleSignInClient = GoogleLoginService.getInstance(LoginActivity.this);
                Intent loginIntent = mGoogleSignInClient.getSignInIntent();
                googleLoginActivityResultLauncher.launch(loginIntent);
            }
        });

        loginButton = (Button) findViewById(R.id.circularLoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextEmail = findViewById(R.id.editTextEmail);
                String email = editTextEmail.getText().toString();
                EditText editTextPassword = findViewById(R.id.editTextPassword);
                String password = editTextPassword.getText().toString();
                if (email == null || email.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                } else if (password == null || password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(LoginActivity.this, "Password : "+ password, Toast.LENGTH_SHORT).show();
                    loginWithForm(email, password);
                }
            }
        });
    }

    private void loginWithForm(String username, String password) {
        authViewModel.login(username, password).observe(LoginActivity.this, isLoginSuccessful -> {
            if (isLoginSuccessful.equals("success")) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, ProtectedActivity.class));
                finish();
            }else if(isLoginSuccessful.equals("failed")){
                Toast.makeText(LoginActivity.this, "Wrong email/password!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(LoginActivity.this, "Error: "+isLoginSuccessful, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginWithGoogle(GoogleLoginResponseModel googleLoginResponseModel) {
        authViewModel.googleOAuthLogin(googleLoginResponseModel).observe(LoginActivity.this, isLoginSuccessful -> {
            if (isLoginSuccessful) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, ProtectedActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Service Error!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        finish();
    }

//    private void loginUser(String email, String password) {
//
//        Call<ResponseBody> call = RetrofitAPIClient
//                .getInstance()
//                .getAPI()
//                .checkUser(new User(email, password));
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("hello", call.request().toString());
//                if (response.isSuccessful() && response.code() == 200) {
//                    Log.d("token", response.headers().get("Authorization"));
//
//                    String authToken = response.headers().get("Authorization");
//                    AuthToken.authToken = authToken;
//                    Toast.makeText(getApplicationContext(), "Successfully Logged in!", Toast.LENGTH_LONG).show();
//                    SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("auth", authToken);
//                    editor.apply();
//                    finish();
//                    startActivity(new Intent(getApplicationContext(), ProtectedActivity.class));
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Wrong Credentials! Try again!", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                Log.d("error socket", t.getMessage());
//            }
//        });
//
//    }
//
}
