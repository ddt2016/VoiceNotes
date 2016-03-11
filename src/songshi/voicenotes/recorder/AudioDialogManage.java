package songshi.voicenotes.recorder;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.voicenotes.R;

/**
 * ����¼����ʾ�Ի���
 * ��ͬ��ʾ�Ի�����ʾԭ����ͬ״̬����ʾ����View�����ز���View��Ȼ�������ʾ
 * @author songshi
 *
 */
public class AudioDialogManage {
	private Dialog mDialog;

	private ImageView mIcon;    //���ͼ�� 
	private ImageView mVoice;   //����չʾ

	private TextView mLabel;

	private Context mContext;

	public AudioDialogManage(Context context) {
		this.mContext = context;
	}

	/**
	 * Ĭ�ϵĶԻ������ʾ
	 */
	public void showRecorderingDialog() {
		mDialog = new Dialog(mContext, R.style.Theme_AudioDialog);

		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(
				R.layout.voicenotes_recorder_dialog, null);
		mDialog.setContentView(view);

		mIcon = (ImageView) mDialog.findViewById(R.id.recorder_dialog_icon);
		mVoice = (ImageView) mDialog.findViewById(R.id.recorder_dialog_voice);
		mLabel = (TextView) mDialog.findViewById(R.id.recorder_dialog_label);

		mDialog.show();
	}

	//��������ʾ���ֶԻ���ʱ��mDialog�Ѿ������죬ֻ��Ҫ����ImageView��TextView����ʾ����
	/**
	 * ����¼��ʱ��Dialog����ʾ
	 */
	public void recording() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.VISIBLE);
			mLabel.setVisibility(View.VISIBLE);

			mIcon.setImageResource(R.drawable.recorder);
			mLabel.setText("��ָ������ȡ��¼��");
		}
	}

	/**
	 * ȡ��¼����ʾ�Ի���
	 */
	public void wantToCancel() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLabel.setVisibility(View.VISIBLE);

			mIcon.setImageResource(R.drawable.cancel);
			mLabel.setText("�ɿ���ָ��ȡ��¼��");
		}
	}

	/**
	 * ¼��ʱ�����
	 */
	public void tooShort() {
		if (mDialog != null && mDialog.isShowing()) {
			
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLabel.setVisibility(View.VISIBLE);
			
			mIcon.setImageResource(R.drawable.voice_to_short);
			mLabel.setText("¼��ʱ�����");
		}
	}

	/**
	 * mDialog.dismiss();
	 */
	public void dimissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;
		}	
	}

	/**
	 * ͨ��Level����Voice��ͼƬ��V1����V7
	 * @param level
	 */
	public void updateVoiceLevel(int level) {
		if (mDialog != null && mDialog.isShowing()) {
			
			int voiceResId=mContext.getResources().getIdentifier("v"+level, "drawable", mContext.getPackageName());  //getIdentifier()��ȡӦ�ð���ָ����Դ��ID
			mVoice.setImageResource(voiceResId);
		}
	}
}
