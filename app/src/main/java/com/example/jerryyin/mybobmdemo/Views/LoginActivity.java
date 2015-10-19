package com.example.jerryyin.mybobmdemo.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerryyin.mybobmdemo.R;
import com.example.jerryyin.mybobmdemo.model.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by JerryYin on 10/19/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    /**Views*/
    private EditText mUserName, mPassword, mPhoneNum;
    private Button mbtnLogin;
    private TextView mbtnReg;

    private MyUser mUser;
    private Context mContext;
    public static final int REQUSET = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUSET:
                if (resultCode == RESULT_OK){
                    String userName = data.getStringExtra(RegisterActivity.KEY_USER_NAME);
                    mUserName.setText(userName);
                }
                break;
            default:break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mContext = getApplicationContext();
        setUpViews();
//        initUser();

    }

    private void setUpViews() {
        mUserName = (EditText) findViewById(R.id.et_user_name);
        mPassword = (EditText) findViewById(R.id.et_password);
        mPhoneNum = (EditText) findViewById(R.id.et_phone_num);
        mbtnLogin = (Button) findViewById(R.id.btn_login);
        mbtnReg = (TextView) findViewById(R.id.btn_register);
        mbtnLogin.setOnClickListener(this);
        mbtnReg.setOnClickListener(this);
    }
//
//    private void initUser() {
//        mUser = new MyUser();
//        mUser.setUsername("");
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_register:
                Intent i = new Intent(this, RegisterActivity.class);
                startActivityForResult(i, REQUSET);
                break;

            case R.id.btn_login:
//                loginMdth1();
                loginMdth2();
                break;

            default:
                break;
        }
    }

    private void loginMdth1() {
        MyUser myUser = new MyUser();
        myUser.setUsername(mUserName.getText().toString());
        myUser.setPassword(mPassword.getText().toString());
        myUser.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "登录成功！", Toast.LENGTH_SHORT).show();
                goToHomePage();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(mContext, "登录失败！" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginMdth2() {
        MyUser.loginByAccount(this,
                mUserName.getText().toString(),
                mPassword.getText().toString(),
                new LogInListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                Toast.makeText(mContext, "登录成功！", Toast.LENGTH_SHORT).show();
                goToHomePage();
            }
        });
    }

    private void goToHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
