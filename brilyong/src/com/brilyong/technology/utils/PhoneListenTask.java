package com.brilyong.technology.utils;

import com.brilyong.technology.R;
import com.brilyong.technology.activity.BaseUIActivityUtil;
import com.brilyong.technology.entity.Acount;
import com.brilyong.technology.httputils.HttpHelperUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class PhoneListenTask extends AsyncTask<Void,Void,Integer>{
	private Context context;
	private String listenerNumber;
	public PhoneListenTask(Context context,String number) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.listenerNumber = number;
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			return HttpHelperUtils.Dev_Dingtong_SetListen(Acount.getCurrentDevice().getId(),listenerNumber);
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
			switch (result) {
			case 1:
				Toast.makeText(context, R.string.set_true, 0).show();
				break;
			case 0:
				Toast.makeText(context, context.getString(R.string.set_false), 0).show();
				break;
			case -1:
				Toast.makeText(context, context.getString(R.string.set_false), 0).show();
				break;
			case -10:
				((BaseUIActivityUtil) context).quitToLogin();
				break;
			case -11:
				((BaseUIActivityUtil) context).equipmentOffLine();
				break;
			default:
				break;
			}
		}else{
			if(!NetworkUtils.isNetworkConnected(context)){
				Toast.makeText(context, R.string.please_check_internet, 0).show();
			}
		}
		
	}

	
	
	

}
