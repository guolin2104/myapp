package com.gl.myapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gl.myapp.R;
import com.gl.myapp.presenter.LoginPresenterCompl;

public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    
    private EditText edtLoginName;
    private EditText edtLoginPassword;
    private Button btnLoginCommit;

    private LoginPresenterCompl loginPresenterCompl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginName = (EditText) findViewById(R.id.edt_login_name);
        edtLoginPassword = (EditText) findViewById(R.id.edt_login_password);
        btnLoginCommit = (Button) findViewById(R.id.btn_login_commit);

        btnLoginCommit.setOnClickListener(this);

        loginPresenterCompl = new LoginPresenterCompl(this);
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        System.out.println("code = " + code);
        if (result) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_commit:
                String name = edtLoginName.getText().toString();
                String password = edtLoginPassword.getText().toString();
                loginPresenterCompl.doLogin(name, password);
                break;
        }
    }
}
