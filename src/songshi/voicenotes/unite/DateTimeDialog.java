package songshi.voicenotes.unite;

import java.util.Calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.example.voicenotes.R;


public class DateTimeDialog {

	private Context mContext;
	private LayoutInflater mInflater;
	private View dateTimeView;
	
	private TextView textViewDate, textViewTime;
	private DatePicker datePicker;
	private TimePicker timePicker;
	
	private Calendar mCalendar;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	public DateTimeDialog(Context context){
		this.mContext=context;
		mInflater=LayoutInflater.from(mContext);
		dateTimeView=mInflater.inflate(R.layout.datetime_dialog, null);
		
		textViewDate=(TextView) dateTimeView.findViewById(R.id.reminderDateText);
		textViewTime=(TextView) dateTimeView.findViewById(R.id.reminderTimeText);
		datePicker=(DatePicker) dateTimeView.findViewById(R.id.reminderDatePicker);
		timePicker=(TimePicker) dateTimeView.findViewById(R.id.reminderTimePicker);
	}
	
	public void DateTimeDialogShow() {
		
		//CustomDialog���Զ���Ի���
		CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
		//builder.setMessage("��������Զ����ʱ����ʾ��");
		
		builder.setContentView(dateTimeView);
		builder.setTitle("��������");
		
		initDateTime();
		setDateTime();
		
		builder.setPositiveButton("����", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// ������Ĳ�������
				// �������ڡ�ʱ���Text
				// �������ݿ�
			}
		});

		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// ����
						
					}
				});

		builder.create().show();
	}
	
	public void initDateTime(){
		mCalendar=Calendar.getInstance();
		
		year=mCalendar.get(Calendar.YEAR);
		month=mCalendar.get(Calendar.MONTH)+1;
		day=mCalendar.get(Calendar.DAY_OF_MONTH);
		hour=mCalendar.get(Calendar.HOUR_OF_DAY);
		minute=mCalendar.get(Calendar.MINUTE);
		
		textViewDate.setText(year+"��"+month+"��"+day+"��");
		textViewTime.setText(hour+"ʱ"+minute+"��");
	}
	
	public void setDateTime(){
		
		datePicker.init(year, mCalendar.get(Calendar.MONTH), day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				textViewDate.setText(year+"��"+(monthOfYear+1)+"��"+dayOfMonth+"��");
			}
		});
		
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				//mCalendar.set(Calendar.MINUTE, minute);
				textViewTime.setText(hourOfDay+"ʱ"+minute+"��");
			}
		});
	}
	
	
}
