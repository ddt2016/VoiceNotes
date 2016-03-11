package songshi.voicenotes.recorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.media.MediaRecorder;

public class AudioManage {

	private MediaRecorder mMediaRecorder;  //MediaRecorder����ʵ��¼����¼����Ҫ�ϸ�����API˵���еĺ��������Ⱥ�˳��.
	private String mDir;             // �ļ��е�����
	private String mCurrentFilePath;

	private static AudioManage mInstance;

	private boolean isPrepared; // ��ʶMediaRecorder׼�����

	private AudioManage(String dir) {
		mDir = dir;
	}

	/**
	 * �ص���׼����ϡ�
	 * @author songshi
	 *
	 */
	public interface AudioStateListenter {
		void wellPrepared();    // prepared���
	}

	public AudioStateListenter mListenter;

	public void setOnAudioStateListenter(AudioStateListenter audioStateListenter) {
		mListenter = audioStateListenter;
	}
	
	/**
	 * ʹ�õ���ʵ�� AudioManage
	 * @param dir
	 * @return
	 */
	//DialogManage��Ҫ����Dialog��Dialog��Ҫ����Context�����Ҵ�Context������Activity��Context��
	//���DialogManageд�ɵ���ʵ�֣�����Application����ģ����޷��ͷţ���������ڴ�й¶���������´���
	public static AudioManage getInstance(String dir) {
		if (mInstance == null) {
			synchronized (AudioManage.class) {   // ͬ��
				if (mInstance == null) {
					mInstance = new AudioManage(dir);
				}
			}
		}

		return mInstance;
	}

	/**
	 * ׼��¼��
	 */
	public void prepareAudio() {

		try {
			isPrepared = false;

			File dir = new File(mDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String fileName = GenerateFileName(); // �ļ�����
			File file = new File(dir, fileName);  // ·��+�ļ�����

			//MediaRecorder����ʵ��¼����¼����Ҫ�ϸ�����API˵���еĺ��������Ⱥ�˳��.
			mMediaRecorder = new MediaRecorder();
			mCurrentFilePath = file.getAbsolutePath();
			mMediaRecorder.setOutputFile(file.getAbsolutePath());    // ��������ļ�
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);    // ����MediaRecorder����ƵԴΪ��˷�
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);    // ������Ƶ�ĸ�ʽ
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);    // ������Ƶ�ı���ΪAMR_NB

			mMediaRecorder.prepare();

			mMediaRecorder.start();
			
			isPrepared = true; // ׼������

			if (mListenter != null) {
				mListenter.wellPrepared();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��������ļ�����
	 * @return
	 */
	private String GenerateFileName() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString() + ".amr"; // ��Ƶ�ļ���ʽ
	}

	/**
	 * ��������ȼ�����ͨ��mMediaRecorder��������Ȼ���������Level
	 * maxLevel���Ϊ7��
	 * mMediaRecorder.getMaxAmplitude() / 32768��0����1��
	 * maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1�� 1����7��
	 * @param maxLevel
	 * @return
	 */
	public int getVoiceLevel(int maxLevel) {
		if (isPrepared) {
			try {
				//mMediaRecorder.getMaxAmplitude()�������������:1-32767
				return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return 1;
	}

	/**
	 * �ͷ���Դ
	 */
	public void release() {
		mMediaRecorder.stop();
		mMediaRecorder.release();
		mMediaRecorder = null;
	}

	/**
	 * ȡ�����ͷ���Դ+ɾ���ļ���
	 */
	public void cancel() {

		release();

		if (mCurrentFilePath != null) {
			File file = new File(mCurrentFilePath);
			file.delete();    //ɾ��¼���ļ�
			mCurrentFilePath = null;
		}
	}

	public String getCurrentFilePath() {
		// TODO Auto-generated method stub
		return mCurrentFilePath;
	}
}
