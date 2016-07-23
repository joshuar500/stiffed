package com.stiffedapp.stiffed;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.models.User;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;


public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;

    private static final String TAG = "EmailPassword";

    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailpassword);

        // Fix White Bar on top
        setTheme(R.style.AppTheme_NoActionBar);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void createAccount(final String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        StiffedApi stiffedApi = ServiceGenerator.createService(StiffedApi.class);

        HashMap<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(credentials)).toString());

        Call<User> loggedIn = stiffedApi.login(body);

        loggedIn.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                hideProgressDialog();

                // check if success
                if(response.code() == 200) {
                    // continue to main activity
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getBaseContext(), "Please make sure you are connected to the Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void signOut() {
        updateUI(null);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(User user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText("HARDCODED EMAIL TEXT");
            mDetailTextView.setText("This is DETAIL TEXT VIEW");

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_field).setVisibility(View.GONE);
            findViewById(R.id.password_field).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_field).setVisibility(View.VISIBLE);
            findViewById(R.id.password_field).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_create_account_button:
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                break;
            case R.id.email_sign_in_button:
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }
}
