package com.brilyong.technology.activity;



import com.brilyong.technology.R;
import com.brilyong.technology.adapter.RelationSelecedAdapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RelationShipActivity extends BaseUIActivityUtil{
    private ListView relation_listview;
    private SharedPreferences share;
    private int relationTypePosition;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relationship_list);
		relation_listview = (ListView) findViewById(R.id.relationship_list);
		final RelationSelecedAdapter adapter = new RelationSelecedAdapter(this);
		relation_listview.setAdapter(adapter);
		relation_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				relationTypePosition = position;
				share = getSharedPreferences("config", MODE_PRIVATE);
				Editor edit = share.edit();
				edit.putInt("relationType", position);
				edit.commit();
				adapter.showRelationTypeSelectedState();
			}
		});
	}
	
	public void addRelationShip(View v){
		Intent intent = new Intent(this,AddRelationShipActivity.class);
		intent.putExtra("relationName", relationTypePosition);
		setResult(RESULT_OK, intent);
		finish();
//		Intent intent = new Intent(this,AddRelationShipActivity.class);
//		switch (relationTypePosition) {
//		case 0:
//			intent.putExtra("relationName", 0);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 1:
//			intent.putExtra("relationName", 1);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 2:
//			intent.putExtra("relationName", 2);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 3:
//			intent.putExtra("relationName", 3);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 4:
//			intent.putExtra("relationName", 4);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 5:
//			intent.putExtra("relationName", 5);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 6:
//			intent.putExtra("relationName", 6);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 7:
//			intent.putExtra("relationName", 7);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 8:
//			intent.putExtra("relationName", 8);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 9:
//			intent.putExtra("relationName", 9);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		case 10:
//			intent.putExtra("relationName", 10);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
//		default:
//			break;
//		}
	}
	
	
}
