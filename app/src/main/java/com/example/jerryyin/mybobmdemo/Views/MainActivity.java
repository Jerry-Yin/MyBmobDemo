package com.example.jerryyin.mybobmdemo.Views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerryyin.mybobmdemo.R;
import com.example.jerryyin.mybobmdemo.model.MyUser;
import com.example.jerryyin.mybobmdemo.model.Student;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    /**Constant*/
    private static final String APPLICATION_ID = "6e2a924f0e2256b5fe08cebfd3904dae";
    private static final int MENU_ITEM_LOGOUT_ID = 0;

    /**Views*/
    private Button mbtnAdd;
    private Button mbtnQuery;
    private Button mbtnUpdate;
    private Button mbtnDelete;
    private EditText mtxtName, mtxtAge, mtxtSex, mtxtClassNum, mtxtQueryId, mtxtUpdate, mtxtDelete;
    private TextView mtvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
//        initBmobSdk();
    }

    private void setupView() {
        mbtnAdd = (Button) findViewById(R.id.btn_add);
        mbtnAdd.setOnClickListener(this);
        mbtnQuery = (Button) findViewById(R.id.btn_query);
        mbtnQuery.setOnClickListener(this);
        mbtnUpdate = (Button) findViewById(R.id.btn_update);
        mbtnUpdate.setOnClickListener(this);
        mbtnDelete = (Button) findViewById(R.id.btn_delete_id);
        mbtnDelete.setOnClickListener(this);
        mtxtName = (EditText) findViewById(R.id.et_name);
        mtxtAge = (EditText) findViewById(R.id.et_age);
        mtxtSex = (EditText) findViewById(R.id.et_sex);
        mtxtClassNum = (EditText) findViewById(R.id.et_calss_num);
        mtxtQueryId = (EditText) findViewById(R.id.et_query_id);
        mtvResult = (TextView) findViewById(R.id.tv_result);
        mtxtUpdate = (EditText) findViewById(R.id.et_update_id);
        mtxtDelete = (EditText) findViewById(R.id.et_delete_id);
    }

    /**
     *  初始化 Bmob SDK
     *  使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
     */
    private void initBmobSdk() {
        Bmob.initialize(this, APPLICATION_ID);
    }


    private void AddBmobData() {
        Student student = new Student();
        student.setName(mtxtName.getText().toString());
        student.setSex(mtxtSex.getText().toString());
        String age = mtxtAge.getText().toString();
        student.setAge(Integer.valueOf(age));
        String classNum = mtxtClassNum.getText().toString();
        student.setClassNum(Integer.valueOf(classNum));
//        student.setObjectId("adcd111");

        student.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "数据存储成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getApplicationContext(), "数据存储失败！" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void queryBmobData() {
        BmobQuery<Student> query = new BmobQuery<Student>();
        String objectId = mtxtQueryId.getText().toString();
        query.getObject(getApplicationContext(), objectId, new GetListener<Student>() {
            @Override
            public void onSuccess(Student student) {
                Toast.makeText(getApplicationContext(), "查询成功", Toast.LENGTH_SHORT).show();
                mtvResult.setText("id: " + student.getObjectId() + " name: " + student.getName() + " Age: " + student.getAge());
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getApplicationContext(), "查询失败,原因：" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void upDateBmobData(){
        Student student = new Student();
        student.setName("更新数据");
        String objectId = mtxtUpdate.getText().toString();
        student.update(this, objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), " 更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getApplicationContext(), "更新失败,原因：" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeleteBmobData() {
        Student student = new Student();
        String objectId = mtxtDelete.getText().toString();
        student.setObjectId(objectId);
        student.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getApplicationContext(), "删除失败,原因：" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                AddBmobData();
                break;

            case R.id.btn_query:
                queryBmobData();
                break;

            case R.id.btn_update:
                upDateBmobData();
                break;

            case R.id.btn_delete_id:
                DeleteBmobData();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, Menu.FIRST+1, 0, "LogOut").setIcon(R.drawable.ic_highlight_off_black_24dp);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case Menu.FIRST+1:
                logOut();
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 退出登录，清除本地缓存的用户信息
     */
    private void logOut() {
        MyUser.logOut(this);
        MyUser currentUser = BmobUser.getCurrentUser(this, MyUser.class);
    }
}
