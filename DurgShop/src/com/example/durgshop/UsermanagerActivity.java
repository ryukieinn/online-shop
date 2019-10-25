package com.example.durgshop;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * �û�����
 * */
public class UsermanagerActivity extends Activity {
	private ListView lv;
    private DBAdapter dbAdepter;
    private Users[] users;
    private Button deleteuser;
    private TextView uid,uname,upwd; 
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermanager);
        dbAdepter = new DBAdapter(this);
        dbAdepter.open();
        init();
    }

    private void init() {
    	users = dbAdepter.queryAllData();
        if(users != null){
	        lv = (ListView)findViewById(R.id.userlist);
	        lv.setAdapter(new BaseAdapter() {
	            /*
	             * ΪListView����һ��������
	             * getCount()�������ݸ���
	             * getView()Ϊÿһ������һ����Ŀ
	             * */
	            @Override
	            public int getCount() {
	                return users.length;
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
	 
	                View view ;
	                /**��ListView���Ż���convertViewΪ��ʱ������һ������ͼ��
	                 * convertView��Ϊ��ʱ���������ǹ���,
	                 * ����Recycler�е���ͼ,����Ҫ�õ�����layout��
	                 * ����inflate(),ͬһ��ͼ����fiindViewBy()
	                 * **/  
	                if(convertView == null)
	                {
	                    LayoutInflater inflater = UsermanagerActivity.this.getLayoutInflater();
	                    convertView = inflater.inflate(R.layout.users,null);
	                    deleteuser = (Button) convertView.findViewById(R.id.bt_udelete_users);
	                    uid = (TextView)convertView.findViewById(R.id.tv_uid_users);
	                    upwd = (TextView)convertView.findViewById(R.id.tv_upwd_users);
	                    uname = (TextView)convertView.findViewById(R.id.tv_uname_users);
	                    view = convertView; 
	                }
	                else
	                {
	                     view = convertView;
	                }
	                //position�൱�������±�,����ʵ������ȡ����
	                uid.setText(String.valueOf(users[position].id));
	                uname.setText(users[position].name);
	                upwd.setText(users[position].password);
	                deleteuser.setTag(position);
	                deleteuser.setOnClickListener(new OnClickListener() {
	        			public void onClick(View v) {
	        				int pos = (Integer) v.getTag();
	        				String id = String.valueOf(users[pos].id);
	        				long result = dbAdepter.deleteOneData(id);
	        				String msg = "ɾ��IDΪ"+id+"������" + (result>0?"�ɹ�":"ʧ��");
	        				Toast.makeText(UsermanagerActivity.this, msg, Toast.LENGTH_SHORT).show();
	        			}
	        		});
	                return convertView;
	            }
	        });
        }
        else{
        	Intent intent1 = new Intent(this,NullActivity.class);
			startActivity(intent1);
        }
    } 
}