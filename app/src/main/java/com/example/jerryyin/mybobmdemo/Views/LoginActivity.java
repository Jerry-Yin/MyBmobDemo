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

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by JerryYin on 10/19/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    /**Constants*/
    private static final String APPLICATION_ID = "6e2a924f0e2256b5fe08cebfd3904dae";
    public static final int REQUSET = 1;

    /**Views*/
    private EditText mUserName, mPassword, mPhoneNum;
    private Button mbtnLogin;
    private TextView mbtnReg;

    /**values*/
    private MyUser mUser;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mContext = getApplicationContext();
        initBmobSdk();
        setUpViews();
//        initUser();

    }

    /**
     *  初始化 Bmob SDK
     *  使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
     */
    private void initBmobSdk() {
        Bmob.initialize(this, APPLICATION_ID);
    }

    /**
     * 注册成功或是第一次登录成功,都会在本地磁盘中有一个缓存的用户对象，这样，你可以通过获取这个缓存的用户对象来进行登录：
     */
    @Override
    protected void onStart() {
        super.onStart();
        checkUserAndLogin();
    }

    private void checkUserAndLogin() {
        MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
        if(myUser != null){
            // 允许用户使用应用
            goToHomePage();
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…

        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_register:
                Intent i = new Intent(this, RegisterActivity.class);
                startActivityForResult(i, REQUSET);
                break;

            case R.id.btn_login:
                loginMdth1();
//                loginMdth2();
                break;

            default:
                break;
        }
    }

     /**
      * 注册成功后直接返回登录界面，并填充用户名
      */
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

    /**
     * 用户登录的两个方法，均可以
     */
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
                if (myUser != null){
                    Toast.makeText(mContext, "登录成功！", Toast.LENGTH_SHORT).show();
                    goToHomePage();
                }
            }
        });
    }

    private void goToHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
