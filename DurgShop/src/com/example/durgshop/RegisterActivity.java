package com.example.durgshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ע��
 * */
public class RegisterActivity extends Activity implements View.OnClickListener {

	private EditText edit_register, edit_setpassword, edit_resetpassword;
	private Button btn_yes, btn_cancel;
	private DBAdapter dbAdepter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		dbAdepter = new DBAdapter(this);
		dbAdepter.open();
		init();
	}

	protected void init() {
		edit_register = (EditText) findViewById(R.id.edit_register_register);
		edit_register.setFilters(new InputFilter[] { new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				for (int i = start; i < end; i++) {
					if (!Character.isLetterOrDigit(source.charAt(i))
							&& !Character.toString(source.charAt(i))
									.equals("_")) {
						Toast.makeText(RegisterActivity.this,
								"ֻ��ʹ��'_'����ĸ�����֡�����ע�ᣡ", Toast.LENGTH_SHORT)
								.show();
						return "";
					}
				}
				return null;
			}
		} });
		edit_register
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							edit_register.clearFocus();
							InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(
									edit_register.getWindowToken(), 0);
						}
						return false;
					}
				});
		edit_setpassword = (EditText) findViewById(R.id.edit_setpassword_register);
		edit_resetpassword = (EditText) findViewById(R.id.edit_resetpassword_register);
		edit_resetpassword
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						//������������رռ���
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							edit_resetpassword.clearFocus();
							InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
							im.hideSoftInputFromWindow(
									edit_resetpassword.getWindowToken(), 0);
						}
						return false;
					}
				});
		btn_yes = (Button) findViewById(R.id.btn_yes_register);
		btn_yes.setOnClickListener(this);
		btn_cancel = (Button) findViewById(R.id.btn_cancle_register);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_yes_register:
			if (CheckIsDataAlreadyInDBorNot(edit_register.getText().toString())) {
				Toast.makeText(this, "���û����ѱ�ע�ᣬע��ʧ��", Toast.LENGTH_SHORT)
						.show();
			} else {
				if (edit_setpassword.getText().toString().trim()
						.equals(edit_resetpassword.getText().toString())) {
					registerUserInfo(edit_register.getText().toString(),
							edit_setpassword.getText().toString());
					Toast.makeText(this, "ע��ɹ���", Toast.LENGTH_SHORT).show();
					Intent register_intent = new Intent(RegisterActivity.this,
							MainActivity.class);
					startActivity(register_intent);
				} else {
					Toast.makeText(this, "�����������벻ͬ�����������룡", Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;
		case R.id.btn_cancle_register:
			Intent login_intent = new Intent(RegisterActivity.this,
					MainActivity.class);
			startActivity(login_intent);
			break;
		default:
			break;
		}
	}

	/**
	 * ����sql����Ƕ��ʽ���ݿ����ע�����
	 */
	private void registerUserInfo(String username, String userpassword) {
		Users user = new Users();
		user.name = username;
		user.password = userpassword;
		dbAdepter.insert(user);
	}

	/**
	 * �����û����Ƿ��Ѿ�ע��
	 */
	public boolean CheckIsDataAlreadyInDBorNot(String value) {
		Users[] user = dbAdepter.queryRegisterData(value);
		//���ù���Ա
		if (user != null || value.equals("admin")) {
			return true;
		}
		return false;
	}
}
