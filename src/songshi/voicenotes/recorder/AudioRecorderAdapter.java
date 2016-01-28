package songshi.voicenotes.recorder;

import java.util.List;

import songshi.voicenotes.MainActivity.Recorder;
import songshi.voicenotes.unite.DateTimeDialog;
import songshi.voicenotes.unite.ReminderDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voicenotes.R;


public class AudioRecorderAdapter extends ArrayAdapter<Recorder> {

	private List<Recorder> mDatas;  //���ݼ�
	private Context mContext;
	
	private int mMinItemWidth;
	private int mMaxItemWidth;

	private LayoutInflater mInflater;

	private DateTimeDialog mDateTimeDialog;
	private ReminderDialog mReminderDialog;
	
	//���캯��
	public AudioRecorderAdapter(Context context, List<Recorder> datas) {
		super(context, -1, datas);
		mContext=context;
		mDatas=datas;
		
		mInflater=LayoutInflater.from(context);
		
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);

		mMaxItemWidth = (int) (outMetrics.widthPixels * 0.8f);    //���������
		mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);    //������С���
		
		mDateTimeDialog=new DateTimeDialog(getContext());
		mReminderDialog=new ReminderDialog(getContext());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		AlarmclockBtnListener alarmclockBtnListener=null;
		ReminderBtnListener reminderBtnListener=null;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.voicenotes_recorder_item, parent, false);
			
			//�ؼ��ĳ�ʼ��,������������Ч��
			holder=new ViewHolder();
			holder.currentTime=(TextView) convertView.findViewById(R.id.currentTime_text);
			holder.seconds=(TextView) convertView.findViewById(R.id.recorder_time);
			holder.mLength=convertView.findViewById(R.id.recorder_length);
			holder.alarmclockBtn=(ImageView) convertView.findViewById(R.id.alarmclockBtn);
			holder.reminderBtn=(ImageView) convertView.findViewById(R.id.reminderBtn);
			
			convertView.setTag(holder);   //���ñ�ǩ����ʶconvertView
			
			//Recorder recorder = mDatas.get(position);
			//convertView.setTag(recorder.getmCurrentTime());  //��¼����CurrentTime����ʶconvertView�������Ժ���
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		//Item�ϵġ�ͼƬ��ť���ļ�������ʼ��
		alarmclockBtnListener=new AlarmclockBtnListener(position);
		reminderBtnListener=new ReminderBtnListener(position);
		
		//���ÿؼ�����ʾ����
		holder.currentTime.setText(getItem(position).getmCurrentTime());
		holder.seconds.setText(Math.round(getItem(position).getTime())+"\"");   //¼����ʱ�����ʾ
		ViewGroup.LayoutParams lp=holder.mLength.getLayoutParams();
		lp.width=(int) (mMinItemWidth+(mMaxItemWidth/60f * getItem(position).getTime()));
		
		holder.alarmclockBtn.setTag(position);
		holder.alarmclockBtn.setFocusable(false);
		holder.alarmclockBtn.setOnClickListener(alarmclockBtnListener);
		
		holder.reminderBtn.setTag(position);
		holder.reminderBtn.setFocusable(false);
		holder.reminderBtn.setOnClickListener(reminderBtnListener);
		
		return convertView;
	}

	//Item�ؼ�
	private class ViewHolder {
		public TextView currentTime;  //��ʾϵͳ��ǰ��ʱ��
		public TextView seconds;      //¼����ʱ�䳤��
		public View mLength;          //��ʾ����
		public ImageView alarmclockBtn;  //���ѡ���ť��
		public ImageView reminderBtn;    //��������ť��
		
	}
	
	//���Ӱ�ť�ĵ�������ӿ�
	public class AlarmclockBtnListener implements OnClickListener{

		int mPosition;

		public AlarmclockBtnListener(int position) {
			mPosition = position;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "��������ӣ�"+mPosition, Toast.LENGTH_SHORT).show();
			mDateTimeDialog.DateTimeDialogShow();
		}
		
	}
	
	//���Ӱ�ť�ĵ�������ӿ�
	public class ReminderBtnListener implements OnClickListener {

		int mPosition;

		public ReminderBtnListener(int position) {
			mPosition = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "����˱�����" + mPosition, Toast.LENGTH_SHORT).show();
			mReminderDialog.DateTimeDialogShow();
		}

	}
	
}
