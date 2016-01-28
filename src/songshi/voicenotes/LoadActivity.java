package songshi.voicenotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.example.voicenotes.R;



/**
 * ����ҳ�� 
 * @author songshi
 *
 */
public class LoadActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.load_image);
		
		ImageView loadImage = (ImageView) findViewById(R.id.loadImage);

		// ͸���Ƚ��䶯��
		AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);// ��΢����ʾ����ȫ��ʾ
		// ����ʱ��
		alphaAnimation.setDuration(1500);
		// ������Ͷ������й���
		loadImage.setAnimation(alphaAnimation);

		alphaAnimation.setAnimationListener(new AnimationListener() {

			// ������ʼ
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				//Toast.makeText(LoadActivity.this, "��ӭʹ������", Toast.LENGTH_SHORT).show();
				//NetWork.isNetworkAvailable(LoadActivity.this);
				
				//NetWork.setNetWork(LoadActivity.this);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			// ��������
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
				Intent mainIntent = new Intent(LoadActivity.this,
						MainActivity.class);
				startActivity(mainIntent);
				LoadActivity.this.finish();
			}
		});
	}
}
