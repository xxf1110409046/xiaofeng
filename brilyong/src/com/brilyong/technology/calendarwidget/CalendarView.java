package com.brilyong.technology.calendarwidget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.brilyong.technology.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by Mrper on 2015/5/30. ������ͼ�ؼ�
 */
public class CalendarView extends LinearLayout {
	private int time = Calendar.getInstance().get(Calendar.MONTH);

	/** ������Ϣʵ���� **/
	public class DayInfo {
		public int day;
		public DayType dayType;
		private boolean isClick;

		@Override
		public String toString() {
			return String.valueOf(day);
		}
	}

	/** �������� **/
	public enum DayType {
		DAY_TYPE_NONE(0), DAY_TYPE_FORE(1), DAY_TYPE_NOW(2), DAY_TYPE_NEXT(3);
		private int value;

		DayType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private Context context;// �����Ķ���
	private TextView txtTitle;// ��������
	private GridView dateGrid;// ���ڱ��
	private final Calendar calendar = Calendar.getInstance();
	private static final int MAX_DAY_COUNT = 42;// ����������
	private DayInfo[] dayInfos = new DayInfo[MAX_DAY_COUNT];// ÿ��Ӧ���е�������36Ϊ��������

	public CalendarView(Context context) {
		super(context);
		init(context);// ��ʼ������
		showCalendar(calendar);// ��ʾ��������
	}

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);// ��ʼ������
		showCalendar(calendar);// ��ʾ��������
	}

	/** ��ʼ������ **/
	private void init(Context context) {
		this.context = context;
		View rootView = View.inflate(context, R.layout.widget_calendar, null);
		dateGrid = (GridView) rootView
				.findViewById(R.id.widgetCalendar_calendar);
		txtTitle = (TextView) rootView
				.findViewById(R.id.widgetCalendar_txtTitle);
		rootView.findViewById(R.id.widgetCalendar_imgForeMonth)
				.setOnClickListener(navigatorClickListener);
		iimg = (ImageView) rootView
				.findViewById(R.id.widgetCalendar_imgNextMonth);
		iimg.setOnClickListener(navigatorClickListener);
		this.setOrientation(VERTICAL);// ���ò��ַ���
		this.addView(rootView);// ��Ӹ���ͼ

	}

	/** ��ʾ�������� **/
	private void showCalendar(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);// ������
		int month = calendar.get(Calendar.MONTH) + 1;// ��ȡ�·�
		// int day = calendar.get(Calendar.DATE);//��ȡ����
		int centry = Integer.valueOf(String.valueOf(year).substring(0, 2));// ȡ���ǰ��λ��Ϊ������,������-1
		int tmpYear = Integer.valueOf(String.valueOf(year).substring(2, 4));// ȡ��ݺ���λ
		if (month == 1 || month == 2) {// �����1��2�¿���Ϊǰһ���13�£�14��
			tmpYear -= 1;
			month += 12;
		}
		// ������µĵ�һ�������ڼ�
		int firstOfWeek = (tmpYear + (tmpYear / 4) + centry / 4 - 2 * centry + 26 * (month + 1) / 10) % 7;
		if (firstOfWeek <= 0)
			firstOfWeek = 7 + firstOfWeek;// �������ڵ���ʾ
		// �����һ�����ڵ�����ֵ,�������Ϊ����һ���������д���
		final int firstDayIndex = firstOfWeek == 1 ? 1 : firstOfWeek;
		final int dayCount = getDayCount(year, month);// ��ȡ���µ�����
		// �����µ�����
		for (int i = firstDayIndex; i < firstDayIndex + dayCount; i++) {
			if (dayInfos[i] == null)
				dayInfos[i] = new DayInfo();
			dayInfos[i].day = i - firstDayIndex + 1;
			dayInfos[i].dayType = DayType.DAY_TYPE_NOW;
		}
		// ����ǰһ���µ�����
		calendar.add(Calendar.MONTH, -1);// ǰһ����
		year = calendar.get(Calendar.YEAR);// ������
		month = calendar.get(Calendar.MONTH) + 1;// ��ȡ�·�
		final int foreDayCount = getDayCount(year, month);// ���ǰһ���µ�����
		for (int i = 0; i < firstDayIndex; i++) {
			if (dayInfos[i] == null)
				dayInfos[i] = new DayInfo();
			dayInfos[i].day = foreDayCount - firstDayIndex + i + 1;
			dayInfos[i].dayType = DayType.DAY_TYPE_FORE;
		}
		// ������һ���µ�����
		for (int i = 0; i < MAX_DAY_COUNT - dayCount - firstDayIndex; i++) {
			if (dayInfos[firstDayIndex + dayCount + i] == null)
				dayInfos[firstDayIndex + dayCount + i] = new DayInfo();
			dayInfos[firstDayIndex + dayCount + i].day = i + 1;
			dayInfos[firstDayIndex + dayCount + i].dayType = DayType.DAY_TYPE_NEXT;
		}
		calendar.add(Calendar.MONTH, 1);
		// ��ԭ�·�����
		txtTitle.setText(new SimpleDateFormat("yyyy��MM��").format(calendar
				.getTime()));
		// ����������ʾ�ı���
		dateGrid.setAdapter(new CalendarAdapter(context, dayInfos));
	}

	/** ������ť����¼� **/
	private View.OnClickListener navigatorClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.widgetCalendar_imgForeMonth:// ��һ��
				calendar.add(Calendar.MONTH, -1);
				break;
			case R.id.widgetCalendar_imgNextMonth:// ��һ��
				calendar.add(Calendar.MONTH, 1);
				break;
			}
			showCalendar(calendar);// ��ʾ��������
			if (calendar.get(Calendar.MONTH) == time) {
				iimg.setVisibility(View.INVISIBLE);
			} else {
				iimg.setVisibility(View.VISIBLE);
			}
		}
	};
	private ImageView iimg;

	/** �Ƿ���ƽ�� **/
	private boolean isLeapYear(int year) {
		return !((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
	}

	/**
	 * ��ȡĳ���ĳ���ж�����
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDayCount(int year, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
		case 13:// ��ʵ��1�£�������һ���13�¿���
			return 31;
		case 2:
		case 14:// ��ʵ��2�£�������һ���14�¿�
			return isLeapYear(year) ? 28 : 29;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		}
		return 0;
	}

	/**
	 * �ж�����Calendar�е������Ƿ����
	 * 
	 * @param calendar
	 * @param calendar1
	 * @return
	 */
	private boolean isDateEqual(Calendar calendar, Calendar calendar1) {
		return (calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)
				&& calendar.get(Calendar.MONTH) == calendar1
						.get(Calendar.MONTH) && calendar.get(Calendar.DATE) == calendar1
				.get(Calendar.DATE));
	}

	/**
	 * �ж��Ƿ�ѡ����������ǰ��ʱ��
	 * 
	 * @param calendar
	 * @param calendar1
	 * @return
	 */
	private boolean isDateEquals(Calendar calendar, Calendar calendar1) {
		return (calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)
				&& calendar.get(Calendar.MONTH) == calendar1
						.get(Calendar.MONTH) && calendar.get(Calendar.DATE) > calendar1
				.get(Calendar.DATE));
	}

	/** �������������� **/
	public class CalendarAdapter extends BaseAdapter {

		private Context context;
		private List<DayInfo> dayInfos = new ArrayList<DayInfo>();

		public CalendarAdapter(Context context, DayInfo[] dayInfos) {
			this.context = context;
			if (dayInfos != null && dayInfos.length > 0) {
				this.dayInfos.addAll(Arrays.asList(dayInfos));
			}
		}

		@Override
		public int getCount() {
			return dayInfos == null ? 0 : dayInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return dayInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final DayInfo item = dayInfos.get(position);
			if (convertView == null) {
				convertView = new TextView(context);
				AbsListView.LayoutParams cellLayoutParams = new AbsListView.LayoutParams(
						android.view.ViewGroup.LayoutParams.MATCH_PARENT,
						android.view.ViewGroup.LayoutParams.MATCH_PARENT);
				convertView.setLayoutParams(cellLayoutParams);
				TextView txtCell = ((TextView) convertView);
				txtCell.setGravity(Gravity.CENTER);
				txtCell.setPadding(10, 15, 10, 15);
				txtCell.getPaint().setFakeBoldText(true);
				txtCell.setTextSize(16);
			}
			final TextView txtItem = ((TextView) convertView);
			txtItem.setText(item.toString());
			if (item.dayType == DayType.DAY_TYPE_FORE
					|| item.dayType == DayType.DAY_TYPE_NEXT) {// ��ʶ������ɫ
				txtItem.setTextColor(getResources().getColor(R.color.Sameday));
			} else {
				txtItem.setTextColor(Color.BLACK);
			}
			final Calendar tmpCalendar = Calendar.getInstance();
			tmpCalendar.setTimeInMillis(calendar.getTimeInMillis());
			tmpCalendar.set(Calendar.DAY_OF_MONTH, item.day);
			if (isDateEqual(Calendar.getInstance(), tmpCalendar)
					&& item.dayType == DayType.DAY_TYPE_NOW) {// �ж��ǲ��ǽ���
				iimg.setVisibility(View.INVISIBLE);
				txtItem.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
				txtItem.setTextColor(Color.RED);
			} else if (item.dayType == DayType.DAY_TYPE_NOW) {
				txtItem.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
			} else {
				txtItem.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
			}
			View.OnClickListener listener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					txtItem.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
					switch (item.dayType) {// ���������ж�Ӧ�ô�����¼�
					case DAY_TYPE_FORE:// ��ת��ǰһ����
						txtItem.setBackgroundResource(R.color.now);
						Toast.makeText(context, item.toString(), 1000).show();
						break;
					case DAY_TYPE_NOW:
						if (isDateEquals(Calendar.getInstance(), tmpCalendar)
								&& item.dayType == DayType.DAY_TYPE_NOW) {
							txtItem.setBackgroundResource(R.color.now);
							Toast.makeText(context, item.toString(), 1000)
									.show();
						} else {

							Toast.makeText(context, "��ѡ�������߽�����ǰ��ʱ��", 1000)
									.show();
						}
						break;
					case DAY_TYPE_NEXT:
						Toast.makeText(context, "��ѡ�������߽�����ǰ��ʱ��", 1000).show();
						break;
					}
				}
			};
			txtItem.setOnClickListener(listener);// �������ڵ���¼�
			return convertView;
		}
	}

}
