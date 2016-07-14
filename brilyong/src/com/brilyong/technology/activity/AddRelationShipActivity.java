package com.brilyong.technology.activity;



import com.brilyong.technology.R;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.httputils.HttpHelperUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddRelationShipActivity extends BaseUIActivityUtil implements OnClickListener{
    private TextView relation_ship_text;
    private boolean hideRelationName = false;
    private Button selected_relation_type,relation_type_save;
    private EditText relation_phone_edit;
    static final int REQUEST_ONE=1;
	private String relation_add;
	private int name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_relation_type);
		relation_ship_text = (TextView) findViewById(R.id.relation_ship_text);
		selected_relation_type = (Button) findViewById(R.id.selected_relation_type);
		selected_relation_type.setOnClickListener(this);
		relation_type_save = (Button) findViewById(R.id.relation_type_save);
		relation_type_save.setOnClickListener(this);
		relation_phone_edit = (EditText) findViewById(R.id.relation_phone_edit);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
//	public void hideRelationName(){
//		hideRelationName = false;
//	}
//	public void showRelationName(){
//		hideRelationName = true;
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.selected_relation_type:
			Intent intent = new Intent(this,RelationShipActivity.class);
			this.startActivityForResult(intent, REQUEST_ONE);
			break;
		case R.id.relation_type_save:
			relation_add = relation_phone_edit.getText().toString();
			if(TextUtils.isEmpty(relation_add)){
				Toast.makeText(getApplicationContext(), "您输入的号码为空，请重新输入", 0).show();
			}else{
				new AddRelationTask().execute();
			}
			break;

		default:
			break;
		}
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, arg2);
		if(requestCode==REQUEST_ONE){
			switch (resultCode) {
			case RESULT_OK:
				name = arg2.getIntExtra("relationName", 0);
				switch (name) {
				case 0:
					relation_ship_text.setText(getString(R.string.sos));
					break;
				case 1:
					relation_ship_text.setText(getString(R.string.father));
					break;
				case 2:
					relation_ship_text.setText(getString(R.string.mather));
					break;
				case 3:
					relation_ship_text.setText(getString(R.string.grandfa));
					break;
				case 4:
					relation_ship_text.setText(getString(R.string.grandma));
					break;
				case 5:
					relation_ship_text.setText(getString(R.string.grandfater));
					break;
				case 6:
					relation_ship_text.setText(getString(R.string.grandmather));
					break;
				case 7:
					relation_ship_text.setText(getString(R.string.uncle));
					break;
				case 8:
					relation_ship_text.setText(getString(R.string.aunty));
					break;
				case 9:
					relation_ship_text.setText(getString(R.string.brother));
					break;
				case 10:
					relation_ship_text.setText(getString(R.string.sister));
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
	}
	
	

	  private class AddRelationTask extends AsyncTask<Void, Void, Integer>{

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return HttpHelperUtils.XdtSettingFamilyNumber(Acount.getCurrentDevice().getId(),relation_add,name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null){
				switch (result.intValue()) {
				case -1:
					Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();
					break;
				case 0:
					Toast.makeText(getApplicationContext(), getString(R.string.set_false), 0).show();			
					break;
				case 1:
					Toast.makeText(getApplicationContext(), getString(R.string.set_true), 0).show();
					Acount.setUnbindState(true);
					Intent intent = new Intent(AddRelationShipActivity.this,RelationListActivity.class);
					startActivity(intent);
					break;
				case -10:
					quitToLogin();			
					break;
				case -11:
					equipmentOffLine();
					break;
				default:
					break;
				}
			}
		}
	  }
	
	
	

}
