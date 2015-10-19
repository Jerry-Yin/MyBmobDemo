package com.example.jerryyin.mybobmdemo.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jerryyin.mybobmdemo.R;
import com.example.jerryyin.mybobmdemo.model.MyUser;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by JerryYin on 10/19/15.
 */
public class RegisterActivity extends Activity {

    /**Views*/
    private EditText mEtUserName, mEtPassword, mEtPhoneNum;
    private Button mbtnReg;

    private Context mContext;
    private MyUser mUser;

    public static final String KEY_USER_NAME = "KEY_USER_NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mContext = getApplicationContext();
        setUpViews();
//        initBmobReg();
    }

    private void setUpViews() {
        mEtUserName = (EditText) findViewById(R.id.et_user_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtPhoneNum = (EditText) findViewById(R.id.et_phone_num);
        mbtnReg = (Button) findViewById(R.id.btn_register);
        mbtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser = new MyUser();
                mUser.setUsername(mEtUserName.getText().toString());
                mUser.setPassword(mEtPassword.getText().toString());
//                mUser.set
                mUser.signUp(getApplicationContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(mContext, "注册成功！", Toast.LENGTH_SHORT).show();
                        backToLogin();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(mContext, "注册失败！" + s, Toast.LENGTH_SHORT).show();
                        if (mEtUserName.getText().toString().equals(mUser.getUsername())
                                || s.equals("username 'YJF' already taken.")) {
                           backToLogin();
                        }
                    }
                });
            }
        });
    }

    private void backToLogin(){
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(KEY_USER_NAME, mEtUserName.getText().toString());
        setResult(RESULT_OK, intent);
        this.finish();
    }

}
