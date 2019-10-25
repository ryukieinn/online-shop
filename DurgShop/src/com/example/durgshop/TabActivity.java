package com.example.durgshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * ����������ײ�Ŀ��Activity�����е�Fragment���������ڴ�Activity�����ڵ�
 */
public class TabActivity extends FragmentActivity implements OnClickListener {

	// ����ײ��Ĳ˵���ť
	private ImageView[] bt_menu = new ImageView[2];
	// ����ײ��Ĳ˵���ťid
	private int[] bt_menu_id = { R.id.iv_home_tab, R.id.iv_user_tab };
	// ����ײ���ѡ�в˵���ť��Դ
	private int[] select_off = { R.drawable.home_no, R.drawable.user_no };
	// ����ײ���δѡ�в˵���ť��Դ
	private int[] select_on = { R.drawable.home_select, R.drawable.user_select };

	/** ������ */
	private HomeFragment home_F;
	/** �ҵ��Ա����� */
	private UserFragment user_F;
	private String uid;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		uid = Datauser.userid;
		initView();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// ������������������򲻱���״̬���ٴν����Ļ�����ʾĬ��tab
		// ����ִ�������������ø���ȥ������ͼ���״̬
		// super.onSaveInstanceState(outState);
	}

	// ��ʼ�����
	private void initView() {
		// �ҵ��ײ��˵��İ�ť�����ü���
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
			bt_menu[i].setOnClickListener(this);
		}

		// ��ʼ��Ĭ����ʾ�Ľ���
		if (home_F == null) {
			home_F = new HomeFragment();
			addFragment(home_F);
			showFragment(home_F);
		} else if (!home_F.isHidden()) {
			if (!user_F.isHidden()) {
				removeFragment(user_F);
			}
			removeFragment(home_F);
			removeFragment(user_F);
			showFragment(home_F);
		} else if (!user_F.isHidden()) {
			if (!home_F.isHidden()) {
				removeFragment(home_F);
			}
			removeFragment(user_F);
			removeFragment(home_F);
			showFragment(home_F);
		}
		// ����Ĭ����ҳΪ���ʱ��ͼƬ
		bt_menu[0].setImageResource(select_on[0]);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_home_tab:
			// ������
			home_F = new HomeFragment();
			removeFragment(user_F);
			addFragment(home_F);
			showFragment(home_F);
			break;
		case R.id.iv_user_tab:
			// �ҵ��Ա�����
			user_F = new UserFragment();
			// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
			removeFragment(home_F);
			addFragment(user_F);
			showFragment(user_F);
			break;
		}

		// ���ð�ť��ѡ�к�δѡ����Դ
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if (v.getId() == bt_menu_id[i]) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}

	/** ���Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.add(R.id.show_layout, fragment);
		ft.commit();
	}

	/** ɾ��Fragment **/
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** ��ʾFragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();

		// �ж�ҳ���Ƿ��Ѿ�����������Ѿ���������ô�����ص�
		if (home_F != null) {
			ft.hide(home_F);
		}
		if (user_F != null) {
			ft.hide(user_F);
		}
		ft.show(fragment);
		ft.commitAllowingStateLoss();
	}

	/** ���ذ�ť�ļ��� */
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}
}