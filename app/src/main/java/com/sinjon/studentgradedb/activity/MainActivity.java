package com.sinjon.studentgradedb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinjon.studentgradedb.R;
import com.sinjon.studentgradedb.dao.GradeDao;
import com.sinjon.studentgradedb.domain.Student;

import java.util.List;

/**
 * 成绩登记主界面
 *
 * @作者 xinrong
 * @创建日期 2017/12/7 15:47
 */

public class MainActivity extends Activity {

    private EditText et_class; //班级
    private EditText et_name; //姓名
    private EditText et_grade; //成绩
    private ImageButton bt_add; //添加学生成绩
    private GradeDao dao;
    private List<Student> StuDatas;
    private ListView lv_stus;
    private StuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); //初始化界面
        initData(); //初始化数据
        initEvent(); //初始化事件
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //初始化dao
        dao = new GradeDao(getApplicationContext());
        //同步数据库信息的学生成绩表
        StuDatas = dao.queryAll();

        //成绩表适配器
        adapter = new StuAdapter();
        lv_stus.setAdapter(adapter);


    }


    /**
     * 初始化事件
     */
    private void initEvent() {
        //bt_add增加学生成绩事件监听
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将信息添加到数据库
                //将信息封装到Student类中
                Student stu = new Student();
                String clazz = et_class.getText().toString();
                String name = et_name.getText().toString();
                String grade = et_grade.getText().toString();

                if (!clazz.matches("[0-9]+\\.[0-9]+") || TextUtils.isEmpty(clazz) || TextUtils.isEmpty(name) || TextUtils.isEmpty(grade)) {
                    Toast.makeText(getApplicationContext(), "插入数据均不能为空\n" +
                            "第一项的格式为“年级.班级”", Toast.LENGTH_SHORT).show();

                } else {
                    stu.setClazz(clazz);
                    stu.setName(name);
                    stu.setGrade(grade);
                    dao.insert(stu);
                    //添加到信息表中
                    StuDatas.add(stu);
                    adapter.notifyDataSetChanged();
                    lv_stus.setSelection(StuDatas.size()-1);
                    Toast.makeText(getApplicationContext(), stu.toString() + "\n数据插入成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 初始化界面
     */
    private void initView() {
        setContentView(R.layout.activity_main);

        //初始化控件
        et_class = findViewById(R.id.et_class);
        et_name = findViewById(R.id.et_name);
        et_grade = findViewById(R.id.et_grade);

        bt_add = findViewById(R.id.ib_add);

        //学生成绩表
        lv_stus = findViewById(R.id.lv_students);


    }

    /**
     * 学生成绩信息适配器
     */
    class StuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return StuDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_lv_stu, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_id = convertView.findViewById(R.id.tv_item_id);
                viewHolder.tv_class = convertView.findViewById(R.id.tv_item_class);
                viewHolder.tv_name = convertView.findViewById(R.id.tv_item_name);
                viewHolder.tv_grade = convertView.findViewById(R.id.tv_item_grade);
                viewHolder.iv_up = convertView.findViewById(R.id.iv_item_up);
                viewHolder.iv_down = convertView.findViewById(R.id.iv_item_down);
                viewHolder.iv_delete = convertView.findViewById(R.id.iv_item_delete);

                //保存viewHolder
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            //获得位于position位置的Student类
            final Student stu = StuDatas.get(position);
            System.out.println("------------------------------" + stu.toString());
            //加载信息
            viewHolder.tv_id.setText("" + stu.getId());
            String clazz = stu.getClazz();
            String[] split = clazz.split("\\.");
            viewHolder.tv_class.setText(split[0] + "级" + split[1] + "班");
            viewHolder.tv_name.setText(stu.getName());
            viewHolder.tv_grade.setText(stu.getGrade());

            //事件监听
            viewHolder.iv_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stu.setGrade(Integer.valueOf(stu.getGrade())+1+"");
                    dao.update(stu);
                    adapter.notifyDataSetChanged();
                }
            });
            viewHolder.iv_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stu.setGrade(Integer.valueOf(stu.getGrade())-1+"");
                    dao.update(stu);
                    adapter.notifyDataSetChanged();
                }
            });
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dao.delete(stu.getId());
                    StuDatas.remove(stu);
                    adapter.notifyDataSetChanged();
                }
            });


            return convertView;
        }
    }

    class ViewHolder {
        TextView tv_id; //id编号
        TextView tv_class; //班级
        TextView tv_name; //姓名
        TextView tv_grade; //成绩
        ImageView iv_up; //增加按钮
        ImageView iv_down; //减少按钮
        ImageView iv_delete; //删除按钮
    }


}
