package songshi.voicenotes.recorder;


import songshi.voicenotes.recorder.AudioManage.AudioStateListenter;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.voicenotes.R;


/**
 * ����¼��Button
 * @author songshi
 * 1����дonTouchEvent����changeState������wantToCancel������reset��������
 * 2����дAudioDialogManage���������AudioRecorderButton�������ϣ�
 * 3����дAudioManage���������AudioRecorderButton�������ϣ�
 */
public class AudioRecorderButton extends Button implements AudioStateListenter {

	/**
	 * AudioRecorderButton������״̬
	 */
	private static final int STATE_NORMAL = 1;           //Ĭ��״̬
	private static final int STATE_RECORDERING = 2;      //¼��״̬
	private static final int STATE_WANT_TO_CALCEL = 3;   //ȡ��״̬

	private int mCurState = STATE_NORMAL;    // ��ǰ¼��״̬
	private boolean isRecordering = false;   // �Ƿ��Ѿ���ʼ¼��
	private boolean mReady;    // �Ƿ񴥷�onLongClick

	private static final int DISTANCE_Y_CANCEL = 50;

	private AudioDialogManage audioDialogManage;

	private AudioManage mAudioManage;
	
	/**
	 * ����¼����ɺ�Ļص�
	 * @author songshi
	 *
	 */
	public interface AudioFinishRecorderListenter{
		void onFinish(float seconds, String FilePath);
	}
	
	private AudioFinishRecorderListenter mListenter;
	
	public void setAudioFinishRecorderListenter(AudioFinishRecorderListenter listenter){
		this.mListenter=listenter;
	}
	
	//���췽��
	public AudioRecorderButton(Context context) {
		super(context, null);
		// TODO Auto-generated constructor stub
	}
	public AudioRecorderButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		audioDialogManage = new AudioDialogManage(getContext());

		String dir = Environment.getExternalStorageDirectory()
				+ "/VoiceRecorder";                             // �˴���Ҫ�ж��Ƿ��д洢��(���)
		mAudioManage = AudioManage.getInstance(dir);
		mAudioManage.setOnAudioStateListenter(this);

		setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				mReady = true;
				// ������ʾӦ����audio end prepared�Ժ�
				mAudioManage.prepareAudio();
				//return true;
				return false;
			}
		});
		// TODO Auto-generated constructor stub
	}

	/* 
	 * ��дonTouchEvent
	 * @see android.widget.TextView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();   //��ȡ��ǰAction
		
		int x = (int) event.getX();       //��ȡ��ǰ������
		int y = (int) event.getY();

		switch (action) {
		
		case MotionEvent.ACTION_DOWN:
			changeState(STATE_RECORDERING);
			break;

		case MotionEvent.ACTION_MOVE:

			// �Ѿ���ʼ¼��״̬ʱ������X��Y�����꣬�ж��Ƿ���Ҫȡ��
			if (isRecordering) {
				if (wantToCancel(x, y)) {
					changeState(STATE_WANT_TO_CALCEL);
				} else {
					changeState(STATE_RECORDERING);
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			if (!mReady) {   //û�д���onLongClick
				reset();
				return super.onTouchEvent(event);
			}

			if (!isRecordering || mTime < 0.7f) {  //¼��ʱ�����
				audioDialogManage.tooShort();
				mAudioManage.cancel();
				mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);// �ӳ٣�1.3���Ժ�رա�ʱ����̶Ի���
			} 
			
			else if (mCurState == STATE_RECORDERING) { //����¼�ƽ���
				audioDialogManage.dimissDialog();
				// release
				mAudioManage.release();
				// callbackToAct
				// ����¼�ƽ������ص�¼��ʱ���¼���ļ�����·�������ڲ��ŵ�ʱ����Ҫʹ��
				if(mListenter!=null){
					mListenter.onFinish(mTime, mAudioManage.getCurrentFilePath());
				}				
				
			} else if (mCurState == STATE_WANT_TO_CALCEL) {
				// cancel
				audioDialogManage.dimissDialog();
				mAudioManage.cancel();
			}

			reset();
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * �ָ�״̬�Լ�һЩ��־λ
	 */
	private void reset() {
		isRecordering = false;
		mReady = false;                 //�Ƿ񴥷�onLongClick
		mTime = 0;
		changeState(STATE_NORMAL);
	}

	private boolean wantToCancel(int x, int y) {
		// �ж���ָ�Ļ����Ƿ񳬳���Χ
		if (x < 0 || x > getWidth()) {
			return true;
		}
		if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
			return true;
		}
		return false;
	}

	/**
	 * �ı�Button�ı������ı���չʾ��ͬ״̬��¼����ʾ�Ի���
	 * @param state
	 */
	private void changeState(int state) {
		if (mCurState != state) {
			mCurState = state;
			switch (state) {
			case STATE_NORMAL:
				setBackgroundResource(R.drawable.btn_recorder_normal);
				setText(R.string.str_recorder_normal);
				break;

			case STATE_RECORDERING:
				setBackgroundResource(R.drawable.btn_recorder_recordering);
				setText(R.string.str_recorder_recording);
				if (isRecordering) {
					// ����Dialog.recording()
					audioDialogManage.recording();
				}
				break;

			case STATE_WANT_TO_CALCEL:
				setBackgroundResource(R.drawable.btn_recorder_recordering);
				setText(R.string.str_recorder_want_cancel);
				// ����Dialog.wantCancel()
				audioDialogManage.wantToCancel();
				break;
			}
		}
	}

	/* 
	 * ʵ�֡�׼����ϡ��ӿ�
	 * (non-Javadoc)
	 * @see songshi.voicenotes.recorder.AudioManage.AudioStateListenter#wellPrepared()
	 */
	@Override
	public void wellPrepared() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
	}
	
	private static final int MSG_AUDIO_PREPARED = 0x110;   //׼����ȫ
	private static final int MSG_VOICE_CHANGE = 0x111;     //�����ı�
	private static final int MSG_DIALOG_DIMISS = 0x112;    //���ٶԻ���
	
	/**
	 * �������߳����ݣ����ô�����������̸߳���UI
	 * Handler���������̣߳�UI�̣߳��У��������߳�ͨ��Message���󴫵����ݡ�
	 * Handler�������̴߳�������(���߳���sedMessage()��������)Message���󣬰���Щ��Ϣ�������̶߳����У�������߳̽��и���UI��
	 */
	private Handler mHandler = new Handler() {
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_AUDIO_PREPARED:        //216:mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
				audioDialogManage.showRecorderingDialog();
				isRecordering = true;
				//�Ѿ���¼�ƣ�ͬʱ����һ����ȡ���������Ҽ�ʱ���߳�
				new Thread(mGetVoiceLevelRunnable).start();
				break;

			case MSG_VOICE_CHANGE:          //265:mHandler.sendEmptyMessage(MSG_VOICE_CHANGE);
				audioDialogManage.updateVoiceLevel(mAudioManage
						.getVoiceLevel(7));
				break;

			//������Handler���洦��DIALOG_DIMISS������Ϊ���øöԻ�����ʾһ��ʱ�䣬�ӳٹرգ��������125��
			case MSG_DIALOG_DIMISS:         //125:mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
				audioDialogManage.dimissDialog();
				break;
			}
		};
	};
	
	private float mTime;  //��ʼ¼��ʱ����ʱ������reset()���ÿգ�
	/**
	 * ��ȡ������С��Runnable
	 */
	private Runnable mGetVoiceLevelRunnable = new Runnable() {

		@Override
		public void run() {
			
			while (isRecordering) {
				
				try {
					Thread.sleep(100);
					mTime += 0.1f;
					mHandler.sendEmptyMessage(MSG_VOICE_CHANGE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	};
	
}
