package com.example.durgshop;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �ҵĶ�������
 * */
public class MypaidActivity extends Activity {
	private ListView lv;
	private DBAdapter dbAdepter;
	private Paiddurg[] buy;
	private Button sendmess;
	private String uid;
	private TextView dname, dprice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypaid);
		uid = Datauser.userid;
		dbAdepter = new DBAdapter(this);
		dbAdepter.open();
		init();
	}

	private void init() {
		buy = dbAdepter.queryAllPaid(uid);
		if (buy != null) {
			lv = (ListView) findViewById(R.id.mypaidlist);
			lv.setAdapter(new BaseAdapter() {
				/*
				 * ΪListView����һ�������� getCount()�������ݸ��� getView()Ϊÿһ������һ����Ŀ
				 */
				@Override
				public int getCount() {
					return buy.length;
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
				public View getView(int position, View convertView,
						ViewGroup parent) {

					View view;
					/*
					 * ��ListView���Ż���convertViewΪ��ʱ������һ������ͼ��
					 * convertView��Ϊ��ʱ���������ǹ���, ����Recycler�е���ͼ,����Ҫ�õ�����layout��
					 * ����inflate(),ͬһ��ͼ����fiindViewBy() *
					 */
					if (convertView == null) {
						LayoutInflater inflater = MypaidActivity.this
								.getLayoutInflater();
						convertView = inflater.inflate(R.layout.mypaid, null);
						sendmess = (Button) convertView
								.findViewById(R.id.btn_mess_mypaid);
						dname = (TextView) convertView
								.findViewById(R.id.tv_dname_mypaid);
						dprice = (TextView) convertView
								.findViewById(R.id.tv_pirce_mypiad);
						view = convertView;
					} else {
						view = convertView;
					}
					// position�൱�������±�,����ʵ������ȡ����
					String did = buy[position].did;
					Durgs[] durg = dbAdepter.querygDurgbyid(did);
					dname.setText(durg[0].name);
					dprice.setText(durg[0].price);
					sendmess.setTag(position);
					sendmess.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
	        				int pos = (Integer) v.getTag();
							CreatDialog(buy[pos].did, uid);
						}
					});
					return convertView;
				}
			});
		} else {
			Intent intent1 = new Intent(this, NullActivity.class);
			startActivity(intent1);
		}
	}

	public void CreatDialog(String id, String uid) {
		final EditText editText = new EditText(this);
		final Dmess mess = new Dmess();
		final String did = id;
		final String userid = uid;
		AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
		inputDialog.setTitle("����������").setView(editText);
		inputDialog.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String cont = editText.getText().toString();
						mess.cont = cont;
						mess.did = did;
						mess.uid = userid;
						long colunm = dbAdepter.adddmess(mess);
						if (colunm == -1) {
							Toast.makeText(MypaidActivity.this, "���ʧ�ܣ�",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(MypaidActivity.this, "��ӳɹ���",
									Toast.LENGTH_SHORT).show();
						}
					}
				}).show();
	}
}