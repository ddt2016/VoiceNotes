package songshi.voicenotes.unite;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.example.voicenotes.R;

public class ReminderDialog {

	private Context mContext;
	private LayoutInflater mInflater;
	private View remiderView;
	
	public ReminderDialog(Context context){
		this.mContext=context;
		
		mInflater=LayoutInflater.from(mContext);
		remiderView=mInflater.inflate(R.layout.reminder_dialog, null);
	}
	
	public void DateTimeDialogShow() {
		
		//CustomDialog���Զ���Ի���
		CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
		//builder.setMessage("��������Զ���ı�ע��ʾ��");
		builder.setTitle("��ע����");
		
		builder.setContentView(remiderView);
		
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
						//����
					}
				});

		builder.create().show();
	}
}
