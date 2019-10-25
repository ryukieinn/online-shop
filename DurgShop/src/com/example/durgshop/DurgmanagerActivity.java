package com.example.durgshop;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
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
 * ҩƷ����
 * */
public class DurgmanagerActivity extends Activity {
	private ListView lv;
	private Button add, deletedurg;
	private DBAdapter dbAdepter;
	private Durgs[] durgs;
	private TextView did, dname, dprice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_durgmanager);
		add = (Button) findViewById(R.id.bt_adddurg_durgmanager);
		add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DurgmanagerActivity.this,
						AdddurgActivity.class);
				startActivity(intent);
			}
		});
		dbAdepter = new DBAdapter(this);
		dbAdepter.open();
		init();
	}

	private void init() {
		durgs = dbAdepter.queryAllDurgs();
		if (durgs != null) {
			System.out.println("durgs[0].dname=" + durgs[0].name);
			System.out.println("durgs[0].price=" + durgs[0].price);
			lv = (ListView) findViewById(R.id.durglist);
			lv.setAdapter(new BaseAdapter() {
				/*
				 * ΪListView����һ�������� getCount()�������ݸ��� getView()Ϊÿһ������һ����Ŀ
				 */
				@Override
				public int getCount() {
					return durgs.length;
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
						LayoutInflater inflater = DurgmanagerActivity.this
								.getLayoutInflater();
						convertView = inflater.inflate(R.layout.durgs, null);
						deletedurg = (Button) convertView
								.findViewById(R.id.bt_durgdelete_durg);
						did = (TextView) convertView
								.findViewById(R.id.tv_did_durg);
						dname = (TextView) convertView
								.findViewById(R.id.tv_dname_durg);
						dprice = (TextView) convertView
								.findViewById(R.id.tv_price_durg);
						view = convertView;
					} else {
						view = convertView;
					}
					// position�൱�������±�,����ʵ������ȡ����
					did.setText(durgs[position].did);
					System.out.println(durgs[position].name);
					System.out.println(durgs[position].price);
					dname.setText(durgs[position].name);
					dprice.setText(durgs[position].price);
					deletedurg.setTag(position);
					deletedurg.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							int pos = (Integer) v.getTag();
							String id = String.valueOf(durgs[pos].did);
							long result = dbAdepter.deleteOneDurg(id);
							String msg = "ɾ��IDΪ" + id + "������"
									+ (result > 0 ? "�ɹ�" : "ʧ��");
							Toast.makeText(DurgmanagerActivity.this, msg,
									Toast.LENGTH_SHORT).show();
						}
					});
					return convertView;
				}
			});
		} else {
			Toast.makeText(DurgmanagerActivity.this, "ʲô��û��",
					Toast.LENGTH_SHORT).show();
		}
	}
}