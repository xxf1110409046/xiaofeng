package com.brilyong.technology.adapter;

import java.io.File;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brilyong.technology.app.MyApplication;
import com.brilyong.technology.entity.Message;
import com.brilyong.technology.entity.UserMessage;
import com.brilyong.technology.httputils.HttpHelperUtils;
import com.brilyong.technology.service.GetMessageService;
import com.brilyong.technology.utils.AddressUtils;
import com.brilyong.technology.utils.DialogUtils;
import com.brilyong.technology.R;

public class MessageAdapter extends BaseAdapter {

	private static final int AGREEATTION = 0;
	private static final int AGREEDEVICEATTION = 1;
	private static final int REFUSERAGREEATTION = 3;
	private static final int REFUSERAGREEDEVICEATTION = 4;
	private Context context;
	private ArrayList<Message> list;
	private ViewHolder holder;
	private String attentionId;
	private String userId;
	private String loginName;
	private int messageType;
	private Dialog dialog;
	private Handler handler;

	public enum UserMessageType {
		msg_device_static, msg_device_activity, msg_device_alarm, msg_device_out_service, msg_usr_attention_dev, msg_usr_invite_attention_dev, msg_push_ad, voice_record, dev_single_location
	}

	public MessageAdapter(Context context, ArrayList<Message> list,
			String loginName) {
		super();
		this.context = context;
		this.list = list;
		this.loginName = loginName;
		dialog = DialogUtils.getDialog(context, context.getString(R.string.is_loading));

	}

	public void refreshData(ArrayList<Message> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public class ViewHolder {
		LinearLayout message_longonclik;
		LinearLayout message_agress;
		LinearLayout message_record;
		LinearLayout record;
		ImageView message_type;
		TextView message_time_one;
		TextView message_content;
		Button message_isaccept_button;
		Button message_isagree_button;
		Button message_isrefuse_button;
		Button message_isignore_button;
		Button message_agree_button;
		Button message_refuse_button;
		Button message_accept_button;
		Button message_ignore_button;
		public TextView message_record_to_play;
		TextView message_record_to_plays;
		ImageView message_delete;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = new ViewHolder();
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = View.inflate(context, R.layout.message_demo_push,
					null);
			holder.message_longonclik = (LinearLayout) convertView
					.findViewById(R.id.message_longonclik);
			holder.message_agress = (LinearLayout) convertView
					.findViewById(R.id.message_agress);
			holder.message_record = (LinearLayout) convertView
					.findViewById(R.id.message_record);
			holder.record = (LinearLayout) convertView
					.findViewById(R.id.record);
			holder.message_type = (ImageView) convertView
					.findViewById(R.id.message_type);
			holder.message_time_one = (TextView) convertView
					.findViewById(R.id.message_time_one);
			holder.message_content = (TextView) convertView
					.findViewById(R.id.message_content);
			holder.message_isaccept_button = (Button) convertView
					.findViewById(R.id.message_isaccept_button);
			holder.message_ignore_button = (Button) convertView
					.findViewById(R.id.message_ignore_button);
			holder.message_record_to_play = (TextView) convertView
					.findViewById(R.id.message_record_to_play);
			holder.message_record_to_plays = (TextView) convertView
					.findViewById(R.id.message_record_to_plays);
			holder.message_delete = (ImageView) convertView
					.findViewById(R.id.message_delete);
			convertView.setTag(holder);
		}
		final Message message = list.get(position);
		if (message.getType() == 5 || message.getType() == 4) {
			holder.message_longonclik.setVisibility(View.VISIBLE);
			holder.message_agress.setVisibility(View.VISIBLE);
			holder.message_record.setVisibility(View.GONE);
			holder.record.setVisibility(View.GONE);
			switch (message.getAgre_stat()) {
			case 1:
				holder.message_ignore_button.setClickable(false);
				holder.message_ignore_button.setFocusable(false);
				holder.message_ignore_button.setText(R.string.is_agree);
				holder.message_isaccept_button.setVisibility(View.INVISIBLE);
				holder.message_ignore_button.setVisibility(View.VISIBLE);
				holder.message_ignore_button
						.setBackgroundResource(R.drawable.message_disagree);
				break;
			case -1:
				holder.message_ignore_button.setText(R.string.is_disagree);
				holder.message_ignore_button.setClickable(false);
				holder.message_ignore_button.setFocusable(false);
				holder.message_ignore_button
						.setBackgroundResource(R.drawable.message_disagree);
				holder.message_isaccept_button.setVisibility(View.GONE);
				holder.message_ignore_button.setVisibility(View.VISIBLE);
				break;
			case 0:
				holder.message_isaccept_button.setClickable(true);
				holder.message_ignore_button.setClickable(true);
				holder.message_isaccept_button.setText(R.string.agree);
				holder.message_ignore_button.setText(R.string.disagree);
				holder.message_isaccept_button.setVisibility(View.VISIBLE);
				holder.message_ignore_button.setVisibility(View.VISIBLE);
				break;
			}
			String[] info = message.getMsgData().split("_");
			switch (message.getType()) {
			case 4:
				holder.message_content.setText(context.getString(R.string.account) + info[1] + context.getString(R.string.ask_attation)
						+ info[0]);
				break;
			case 5:
				holder.message_content.setText(R.string.account + info[1] + context.getString(R.string.invite_attation)
						+ info[0]);
				break;
			}
			holder.message_type.setImageResource(R.drawable.bl_message_youjian);
			holder.message_time_one.setText(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(message.getAddDate()));
		} else if (message.getType() == 7) {
			holder.message_longonclik.setVisibility(View.GONE);
			holder.message_agress.setVisibility(View.GONE);
			holder.message_record.setVisibility(View.GONE);
			holder.record.setVisibility(View.VISIBLE);
			holder.message_time_one.setText(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(message.getAddDate()));
			holder.message_record_to_play.setText(message.getMsgData());
			holder.message_delete.setImageResource(R.drawable.icon_delete);
			holder.message_type.setImageResource(R.drawable.message_sos);
		} else {
			holder.message_longonclik.setVisibility(View.GONE);
			holder.message_agress.setVisibility(View.GONE);
			holder.message_record.setVisibility(View.VISIBLE);
			holder.record.setVisibility(View.GONE);
			switch (message.getType()) {
			case 0:
			
			case 1:
				holder.message_type.setImageResource(R.drawable.bl_message_zhuji);
				break;
			case 2:
				
			case 3:
				
			case 6:
				
				
			case 9:
				
			case 10:
			case 14:
			case 15:
				
			case 16:
				
			case 17:
				
			case 18:
			
			case 19:
				
			case 20:
				
			case 21:
				
			case 22:
			
			case 25:	
			
				holder.message_type
						.setImageResource(R.drawable.bl_message_youjian);
				break;
			case 11:
				holder.message_type.setImageResource(R.drawable.bl_message_sos);
				break;
			case 8:
				holder.message_type
						.setImageResource(R.drawable.bl_message_dindian);
				break;
			case 12:
				holder.message_type
						.setImageResource(R.drawable.bl_message_dianliang);
				break;
			case 13:
				holder.message_type.setImageResource(R.drawable.bl_dianzi_weilan);//待解决
				break;
			}
			holder.message_record_to_play.setText(message.getMsgData());
			holder.message_time_one.setText(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(message.getAddDate()));
		}
		// 同意
		holder.message_isaccept_button
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						messageType = message.getType();
						attentionId = message.getMsgData();
						userId = message.getId();
						new AgreeTask().execute();
					}
				});
		// 拒绝
		holder.message_ignore_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				messageType = message.getType();
				attentionId = message.getMsgData();
				userId = message.getId();
				new RefuseTask().execute();
			}
		});
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				int info = (Integer) msg.obj;
				switch (msg.what) {
				case AGREEATTION:
					if (info == -1) {
						Toast.makeText(context, context.getString(R.string.ask_attation_not_exit), 0).show();
					} else if (info == 0) {
						Toast.makeText(context, context.getString(R.string.operate_fasle), 0).show();
					} else if (info == 1) {
						Toast.makeText(context, context.getString(R.string.operate_true), 0).show();
						message.setAgre_stat(1);
						list.set(position, message);
						notifyDataSetChanged();
					}
					break;
				case AGREEDEVICEATTION:
					if (info == -1) {
						Toast.makeText(context, context.getString(R.string.operate_fasle), 0).show();
					} else if (info == 0) {
						Toast.makeText(context, context.getString(R.string.not_the_id), 0)
								.show();
					} else if (info == 1) {
						Toast.makeText(context, context.getString(R.string.operate_true), 0).show();
						message.setAgre_stat(1);
						list.set(position, message);
						notifyDataSetChanged();
					}
					break;
				case REFUSERAGREEATTION:
					if (info == -1) {
						Toast.makeText(context, R.string.ask_attation_not_exit, 0).show();
					} else if (info == 0) {
						Toast.makeText(context, context.getString(R.string.operate_fasle), 0).show();
					} else if (info == 1) {
						Toast.makeText(context, R.string.operate_true, 0).show();
						message.setAgre_stat(-1);
						list.set(position, message);
						notifyDataSetChanged();
					}
					break;
				case REFUSERAGREEDEVICEATTION:
					if (info == -1) {
						Toast.makeText(context, context.getString(R.string.operate_fasle), 0).show();
					} else if (info == 0) {
						Toast.makeText(context, R.string.not_the_id, 0)
								.show();
					} else if (info == 1) {
						Toast.makeText(context, R.string.operate_true, 0).show();
						holder.message_ignore_button.setClickable(false);
						message.setAgre_stat(-1);
						list.set(position, message);
						notifyDataSetChanged();
					}

					break;
				}
			};
		};
		return convertView;

	}

	// 同意关注设备
	class AgreeTask extends AsyncTask<Object, Void, Integer> {

		@Override
		protected void onPreExecute() {
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Object... arg0) {
			try {
				if (messageType == 4) {
					String[] userInfo = attentionId.split("_");
					return HttpHelperUtils.AgreeDeviceAttention1(
							userInfo[2], true, userId);
				} else if (messageType == 5) {
					String[] userInfo = attentionId.split("_");
					return HttpHelperUtils.AgreeAttion(loginName,
							userInfo[0], true);
				}

			} catch (ConnectTimeoutException e) {

			} catch (SocketTimeoutException e) {

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result != null) {
				android.os.Message msg = android.os.Message.obtain();
				if (messageType == 5) {
					msg.what = AGREEATTION;
					msg.obj = result;
					handler.sendMessage(msg);
				} else if (messageType == 4) {
					msg.what = AGREEDEVICEATTION;
					msg.obj = result;
					handler.sendMessage(msg);
				}
			} else {
//				ToastUtil.netConnectionException(context);
			}
//			new DeviceListUtils(loginName).execute();
			dialog.dismiss();
		}

	}

	// 拒绝关注设备
	class RefuseTask extends AsyncTask<Object, Void, Integer> {

		@Override
		protected void onPreExecute() {
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Object... arg0) {
			try {
				if (messageType == 4) {
					String[] userInfo = attentionId.split("_");
					return HttpHelperUtils.AgreeDeviceAttention1(
							userInfo[2], false, userId);
				} else if (messageType == 5) {
					String[] userInfo = attentionId.split("_");
					return HttpHelperUtils.AgreeAttion(loginName,
							userInfo[0], false);
				}

			} catch (ConnectTimeoutException e) {

			} catch (SocketTimeoutException e) {

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result != null) {
				android.os.Message msg = android.os.Message.obtain();
				if (messageType == 5) {
					msg.what = REFUSERAGREEATTION;
					msg.obj = result;
					handler.sendMessage(msg);
				} else if (messageType == 4) {
					msg.what = REFUSERAGREEDEVICEATTION;
					msg.obj = result;
					handler.sendMessage(msg);
				}
				MessageAdapter.this.notifyDataSetChanged();
			} else {
//				ToastUtil.netConnectionException(context);
			}
			dialog.dismiss();
		}

	}
}
