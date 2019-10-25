package com.example.durgshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
 * ��ѯҩƷ
 * */
public class SearchActivity extends Activity {

	private ListView lv;
	private DBAdapter dbAdepter;
	private Durgs[] durgs;
	private Button durgdetail;
	private TextView dname, dprice;
	private String durgname, did;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		dbAdepter = new DBAdapter(this);
		dbAdepter.open();
		init();
	}

	private void init() {
		durgname = Datadurg.durgname;
		System.out.println(durgname);
		durgs = dbAdepter.querygDurgbyname(durgname);
		if (durgs != null) {
			lv = (ListView) findViewById(R.id.durglv_search);
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
						LayoutInflater inflater = SearchActivity.this
								.getLayoutInflater();
						convertView = inflater.inflate(R.layout.durgdetails,
								null);
						durgdetail = (Button) convertView
								.findViewById(R.id.bt_detail_durgdetail);
						dname = (TextView) convertView
								.findViewById(R.id.tv_dname_durgdetail);
						dprice = (TextView) convertView
								.findViewById(R.id.tv_price_durgdetail);
						view = convertView;
					} else {
						view = convertView;
					}
					// position�൱�������±�,����ʵ������ȡ����
					dname.setText(durgs[position].name);
					dprice.setText(durgs[position].price);
					did = durgs[position].did;
					durgdetail.setTag(position);
					durgdetail.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
	        				int pos = (Integer) v.getTag();
							Datadurg.durgid = durgs[pos].did;
							System.out.println("did=" + Datadurg.durgid);
							Datadurg.durgname = durgs[pos].name;
							System.out.println("dname=" + Datadurg.durgname);
							Datadurg.durgprice = durgs[pos].price;
							System.out.println("dprice=" + Datadurg.durgprice);
							Intent intent1 = new Intent(SearchActivity.this,
									ThedetailActivity.class);
							startActivity(intent1);
						}
					});
					return convertView;
				}
			});
		} else {
			Toast.makeText(SearchActivity.this, "ʲô��û��", Toast.LENGTH_SHORT)
					.show();
		}
	}
}