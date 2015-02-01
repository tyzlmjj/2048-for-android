package com.mjj.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.mjj.data.Const;
import com.mjj.data.util;
import com.mjj.main.R.id;
import com.mjj.model.Fangkuai_Y;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnTouchListener,OnClickListener{
	//���¿�ʼ��ť
	private Button restart;
	//�ٶȼӼ���ť
	private Button jia,jian;
	//���ð�ť
	private Button shezhi;
	//���ÿ�ť
	private Button moshi_5x5jd;
	//���ÿ�ť
	private Button moshi_4x4jd;
	//i��ť
	private Button main_i;
	//ȷ����ťyes
	private Button btn_yes;
	//ȷ����ťno
	private Button btn_no;
	//�ٶ���ֵ
	private TextView sd;
	//����
	private TextView fenshu;
	//��߷���
	private TextView zuigaofen;
	//��Ļ���
	private int width;
	//��Ļ�߶�
	private int height;
	//�����ȣ��߾�
	private int fwidth,fmargin; 
	//��Ϸ����
	private RelativeLayout huabu;
	//����ѡ���
	private RelativeLayout restart_background;
	//����ѡ���
	private LinearLayout shezhi_background;
	//i��
	private LinearLayout main_i_background;
	//��������ʹ��
	private RelativeLayout.LayoutParams rp;
	@SuppressLint("UseSparseArrays")
	//��ʽ
	private Map<Integer,Integer> map_drawable =new HashMap<Integer, Integer>();
	//�������صķ���
	private List<Integer> fn;
	//�����ƶ��ұ��ķ���
	private List<Integer> fm;
	//���صķ���
	private List<Integer> fnList;
	//�ո���
	private List<Integer> konggezi;
	//���Ӷ�Ӧ����
	@SuppressLint("UseSparseArrays")
	private Map<Integer,Fangkuai_Y> map;
	//�ж�����
	private float x_tmp1,y_tmp1,x_tmp2,y_tmp2;
	//���ƻ��������ж�
	private int xy;
	//�ƶ�����
	private AnimatorSet anim=new AnimatorSet();
	//���Ŷ���
	private AnimatorSet anim2=new AnimatorSet();
	//���������ж�
	private boolean panduan=false;
	//�����ٶ�
	private int sudu=1;
	//�����ؼ����
	private long exitTime = 0;
	//�ж��Ƿ������ÿ��
	private boolean panduan_shezhi=false;
	//�ж���Ϸ�򲼾�4x4��5x5
	private int daxiao=5;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//��ȡ��Ļ�ߴ�
		getwindows();
		//����ؼ�
		initView();
		//��ʼ���¼�
		initEvent();
		//��������
		bujuOnCreat();
		//���Ӵ���
		geziCreat(daxiao);
		//�������ַ���
		fangkuaionCreat(daxiao);
		//��ʼ����
		restart(daxiao);
		
		
	}
	/*
	 * ��2�η��ؼ��˳�
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		 //������߷�
		 util.saveZuigaofen(this, Integer.parseInt(zuigaofen.getText().toString()), Const.FILE_NAME[daxiao-4]);
		 if(panduan_shezhi){
			 shezhi_background.setVisibility(View.GONE);
			 restart_background.setVisibility(View.GONE);
			 main_i_background.setVisibility(View.GONE);
			 panduan_shezhi=false;
			 return true; 
		 }else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {  
	  
	            if ((System.currentTimeMillis() - exitTime) > 2000) {  
	                Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�2048",  
	                        Toast.LENGTH_SHORT).show();  
	                exitTime = System.currentTimeMillis();  
	            } else {  
	                finish();  
	                System.exit(0);  
	            }  
	            return true;  
	        }  
		return super.onKeyDown(keyCode, event);
	}
	
	/*
	 * ��ȡ��Ļ�ߴ�
	 */
	private void getwindows() {
		DisplayMetrics dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//��Ļ���
		width =dm.widthPixels;
		//��Ļ�߶�
		height = dm.heightPixels;
	}

	/*
	 * ��ʼ���¼�
	 */
	private void initEvent() {
		//��Ϸ�򻬶�����
		huabu.setOnTouchListener(this);
		//���ð�ť����¼�����
		restart.setOnClickListener(this);
		//ȷ����ťyes
		btn_yes.setOnClickListener(this);
		//ȷ����ťno
		btn_no.setOnClickListener(this);
		//�ٶȸı䰴ť����
		jia.setOnClickListener(this);
		jian.setOnClickListener(this);
		//���ð�ť����
		shezhi.setOnClickListener(this);
		//���ÿ�ť����
		moshi_5x5jd.setOnClickListener(this);
		//���ÿ�ť����
		moshi_4x4jd.setOnClickListener(this);
		//i��ť����
		main_i.setOnClickListener(this);
		//�ٶȳ�ʼ
		sudu=Integer.parseInt(sd.getText().toString());
		//���鱳����Ӧ
		for(int i=0,n=2;i<17;i++,n*=2){
			map_drawable.put(n, Const.fdrawable[i]);
		}
	}
	
	/*
	 * ����ؼ�
	 */
	private void initView() {
		wh(daxiao);
		//��Ϸ��
		huabu=(RelativeLayout) findViewById(id.huabu);
		//����ѡ���
		restart_background=(RelativeLayout)findViewById(R.id.restart_background);
		//����ѡ���
		shezhi_background=(LinearLayout) findViewById(R.id.shezhi_background);
		//iѡ���
		main_i_background=(LinearLayout) findViewById(R.id.main_i_background);
		//���ð�ť
		restart=(Button) findViewById(id.restart);
		//ȷ����ťyes
		btn_yes=(Button) findViewById(id.btn_restart_yes);
		//ȷ����ťno
		btn_no=(Button) findViewById(id.btn_restart_no);
		//�ٶȼӼ���ť
		jia=(Button)findViewById(R.id.jia);
		jian=(Button)findViewById(R.id.jian);
		//���ð�ť
		shezhi=(Button) findViewById(R.id.btn_shezhi);
		//���ÿ�ť
		moshi_4x4jd=(Button) findViewById(R.id.moshi_4x4jd);
		//���ÿ�ť
		moshi_5x5jd=(Button) findViewById(R.id.moshi_5x5jd);
		//i��ť
		main_i=(Button) findViewById(R.id.main_i);
		//�ٶ�����
		sd=(TextView) findViewById(id.sudu);
		//��������
		fenshu=(TextView) findViewById(id.fenshu);
		//��߷�����
		zuigaofen=(TextView) findViewById(id.zuigaofen);
	}
	/*
	 * �߳��ͱ߾�ȷ��
	 */
	private void wh(int daxiao){
		if(daxiao==4){
			//����߳�
			fwidth=(width-width/20)*2/9;
			//���
			fmargin=(width-width/20)/9/5;
		}else{
			//����߳�
			fwidth=(width-width/20)*2/9*4/5;
			//���
			fmargin=(width-width/20)/9/6;
		}
	}
	/*
	 * ����
	 */
	private void restart(int daxiao){
		//��߷ֶ�ȡ
		zuigaofen.setText(util.loadzuigaofen(this, Const.FILE_NAME[daxiao-4]));
		//�߳����߾�
		wh(daxiao);
		for(int i=1;i<=5;i++){
			for(int j=1;j<=5;j++){
				TextView t=(TextView) findViewById(i*10+j);
				t.setVisibility(View.GONE);
			}
		}
		//��������
		geziCreat(daxiao);
		//�÷�����
		fenshu.setText(0+"");
		//�����뷽���Ӧ���
		map=new HashMap<Integer, Fangkuai_Y>();
		
		fn=new ArrayList<Integer>();
		fm=new ArrayList<Integer>();
		//δ��ʾ�ĸ���id���
		fnList =new ArrayList<Integer>();
		//�ո�������Ϊ16����25��
		konggezi=new ArrayList<Integer>();
		for(int i=1;i<=daxiao*daxiao;i++){
			if(i<=daxiao){
				konggezi.add(10+i);
			}else if(i<=daxiao*2){
				konggezi.add(20+i-daxiao);
			}else if(i<=daxiao*3){
				konggezi.add(30+i-daxiao*2);
			}else if(daxiao==4){
				konggezi.add(40+i-daxiao*3);
			}else if(i<=daxiao*4){
				konggezi.add(40+i-daxiao*3);
			}else{
				konggezi.add(50+i-daxiao*4);
			}
			//����δ��ʾ����
			fnList.add(100+i);
		}
		//25����������
		for(int i=1;i<=25;i++){
			//����ʵ��
			TextView tx = (TextView) findViewById(100+i);
			if(i<=8||i>20){
				//����
				tx.setText("2");
				//������ʽ
				tx.setBackgroundResource(Const.fdrawable[0]);
			}
			else{
				//����
				tx.setText("4");
				//������ʽ
				tx.setBackgroundResource(Const.fdrawable[1]);
			}
			//��Բ��ֲ��� ���
			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
			//������Բ���λ��
			rp.addRule(RelativeLayout.ALIGN_LEFT,11);
			rp.addRule(RelativeLayout.ALIGN_BOTTOM,11);
			
			tx.setLayoutParams(rp);
			//������ɫ
			tx.setTextColor(0xff7c7268);
			//���ִ�С
			tx.setTextSize(Const.textsize[daxiao-4][0]);
			
			//����
			tx.setVisibility(View.INVISIBLE);
		}
		//��ʼ��ʾ2������
		suiji(2);
	}
	/*
	 * ����25���ո���,���Ʋ��ִ�С
	 */
	private void bujuOnCreat(){
		//��Ϸ���С�趨
		RelativeLayout.LayoutParams linearParams =  (RelativeLayout.LayoutParams)huabu.getLayoutParams();
	 	linearParams.height = width-width/20;
	 	linearParams.width = width-width/20;
	    huabu.setLayoutParams(linearParams);
	    //ȷ�����С����
	    FrameLayout.LayoutParams params=(LayoutParams) restart_background.getLayoutParams();
	    params.height=width-width/20;
	    params.width = width-width/20;
	    restart_background.setBackgroundColor(0xaaffffff);
	    restart_background.setLayoutParams(params);
	   //���ÿ��С����
	    FrameLayout.LayoutParams params1=(LayoutParams) shezhi_background.getLayoutParams();
	    params1.height=width-width/20;
	    params1.width = width-width/20;
	    shezhi_background.setBackgroundColor(0xaaffffff);
	    shezhi_background.setLayoutParams(params1);
	  //i���С����
	    FrameLayout.LayoutParams params2=(LayoutParams) main_i_background.getLayoutParams();
	    params2.height=width-width/20;
	    params2.width = width-width/20;
	    main_i_background.setBackgroundColor(0xaaffffff);
	    main_i_background.setLayoutParams(params2);
	    
		for(int i=1;i<=25;i++){
			TextView tx=new TextView(this);
			// id��ո���List��ʼ��
			if(i<=5){
				tx.setId(10+i);
			}else if(i<=10){
				tx.setId(20+i-5);
			}else if(i<=15){
				tx.setId(30+i-10);
			}else if(i<=20){
				tx.setId(40+i-15);
			}else{
				tx.setId(50+i-20);
			}
			tx.setVisibility(View.GONE);
			huabu.addView(tx);
		}
		
	}
	
	/*
	 * ���Ӵ���
	 */
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void geziCreat(int daxiao) {
		//��
        for(int i=1;i<=daxiao;i++){
        	//��
        	for(int j=1;j<=daxiao;j++){
        		TextView text=(TextView) findViewById(i*10+j);
        		//��Բ��ֲ��� ���
    			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
            	
    			//������߾�
            	if(i==daxiao&&j==1){
            		rp.setMargins(fmargin, fmargin, 0,fmargin);
            	}else if(j==1){
            		rp.setMargins(fmargin, fmargin, 0, 0);
            	}else if(j!=daxiao){
            		rp.setMargins(fmargin, 0, 0, 0);
            	}else {
            		rp.setMargins(fmargin, 0,fmargin, 0);
            	}
            	
            	//������Բ���λ��
            	if(j==1&&i!=1){
            		rp.addRule(RelativeLayout.BELOW,i*10-10+j);
            	}else{
            		rp.addRule(RelativeLayout.RIGHT_OF, i*10+j-1);
    				rp.addRule(RelativeLayout.ALIGN_TOP,i*10+j-1);
            	}
            	//������ʽ
    			text.setBackgroundResource(R.drawable.fangkuai_b);
    			text.setText("");
            	text.setLayoutParams(rp);
            	text.setVisibility(View.VISIBLE);
            }
        }
        
        
		
	}
	
	/*
	 * ���ַ��鴴��
	 */
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void fangkuaionCreat(int daxiao){
		
		for(int i=1;i<=25;i++){
			TextView tx = new TextView(this);
			
			//id
			tx.setId(100+i);
			if(i<=8||i>20){
				//����
				tx.setText("2");
				//������ʽ
				tx.setBackgroundResource(Const.fdrawable[0]);
			}
			else{
				//����
				tx.setText("4");
				//������ʽ
				tx.setBackgroundResource(Const.fdrawable[1]);
			}
			//������ɫ
			tx.setTextColor(0xff7c7268);
			//���־���
			tx.setGravity(Gravity.CENTER);
			//���ִ�С
			tx.setTextSize(Const.textsize[daxiao-4][0]);
			//������ʽ
			Typeface face = Typeface.MONOSPACE;
			tx.setTypeface(face);
			//�Ӵ�
			TextPaint tp = tx.getPaint(); 
			tp.setFakeBoldText(true); 
			
			//��Բ��ֲ��� ���
			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
			
			//������Բ���λ��
			rp.addRule(RelativeLayout.ALIGN_LEFT,11);
			rp.addRule(RelativeLayout.ALIGN_BOTTOM,11);
			
			tx.setLayoutParams(rp);

			//����
			tx.setVisibility(View.INVISIBLE);
			
			huabu.addView(tx);

		}
	}
	/*
	 * ��������µ�2/4����
	 */
	private void suiji(int n){
		
		for(int i=0;i<n;i++){
			if(konggezi.size()!=0&&konggezi!=null){
				//������Ӻͷ���
				int g=(int)(Math.random() * konggezi.size());
				int f=(int)(Math.random() * fnList.size());
				
				//��ø���id�ͷ���ID
				int gezi=konggezi.get(g);
				final int fid=fnList.get(f);
				
				//ɾ��
				konggezi.remove(g);
				fnList.remove(f);
				
				//��������ʵ��
				final TextView text=(TextView) findViewById(fid);
				//�󶨸��Ӻͷ���
				map.put(gezi, new Fangkuai_Y(fid,0,0,Integer.parseInt(text.getText().toString())));
				
				//�ƶ�����λ�õ��յĸ���
				ObjectAnimator p1 = ObjectAnimator.ofFloat(text,"translationX",map.get(gezi).getX(),(fwidth+fmargin)*(gezi%10-1));
				ObjectAnimator p2 = ObjectAnimator.ofFloat(text,"translationY",map.get(gezi).getY(),(fwidth+fmargin)*(gezi/10-1));
				AnimatorSet set=new AnimatorSet();
				set.playTogether(p1,p2);
				set.setDuration(100);
				set.start();
				//�����ƶ����֮����ʾ
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						text.setVisibility(View.VISIBLE);
						ObjectAnimator.ofFloat(text,"scaleX",0f,1f).setDuration(100).start();
						ObjectAnimator.ofFloat(text,"scaleY",0f,1f).setDuration(100).start();
					}
				});
				map.get(gezi).setX((fwidth+fmargin)*(gezi%10-1));
				map.get(gezi).setY((fwidth+fmargin)*(gezi/10-1));
				
				
				panduan=true;
			}else{
				//��������
			}

		}
	}
	/*
	 * ���������ж�
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//�ж��Ƿ������ÿ��
		if(!panduan_shezhi){
			//��ȡ��ǰ����  
	        float x = event.getX();  
	        float y = event.getY(); 
			switch(event.getAction()){
				case  MotionEvent.ACTION_DOWN:
				 	x_tmp1 = x;  
	                y_tmp1 = y;
	                break;  
		        case MotionEvent.ACTION_MOVE:  
	                x_tmp2 = x;  
	                y_tmp2 = y;
	                if(Math.abs(x_tmp1 - x_tmp2) > Math.abs(y_tmp1 - y_tmp2)){
	                	if(x_tmp1 - x_tmp2 > 8 ){  
	                    	xy++;
	                    	move("zuo");
	                    }
	                    else if(x_tmp2 - x_tmp1 > 8 ){
	                    	xy++;
	                    	move("you");
	                    }
	                }else{
		                if(y_tmp1 - y_tmp2 > 8 ){
		                	xy++;
		                	move("shang");
		                }
		                else if(y_tmp2 - y_tmp1 > 8 ){
		                	xy++;
		                	move("xia");
		                }
	                }
	                break;
		        case MotionEvent.ACTION_UP:
		        	xy=0;
		        	break;
			}
		}
		
		return true;
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.restart:
				if(!panduan_shezhi){
					restart_background.setVisibility(View.VISIBLE);
					panduan_shezhi=true;
				}
				break;
			case R.id.btn_restart_yes:
				restart_background.setVisibility(View.GONE);
				panduan_shezhi=false;
				//������߷�
				util.saveZuigaofen(this, Integer.parseInt(zuigaofen.getText().toString()), Const.FILE_NAME[daxiao-4]);
				restart(daxiao);
				break;
			case R.id.btn_restart_no:
				restart_background.setVisibility(View.GONE);
				panduan_shezhi=false;
				break;
			case R.id.jia:
				int a=Integer.parseInt(sd.getText().toString());
				if(a<10){
					sd.setText(a+1+"");
					fangda(sd);
					sudu=a+1;
				}
				break;
			case R.id.jian:
				int b=Integer.parseInt(sd.getText().toString());
				if(b>1){
					sd.setText(b-1+"");
					suoxiao(sd);
					sudu=b-1;
				}
				break;
			case R.id.btn_shezhi:
				if(!panduan_shezhi){
					shezhi_background.setVisibility(View.VISIBLE);
					panduan_shezhi=true;
				}
				break;
			case R.id.moshi_4x4jd:
				if(daxiao==5){
					util.saveZuigaofen(this, Integer.parseInt(zuigaofen.getText().toString()), Const.FILE_NAME[daxiao-4]);
					daxiao=4;
					restart(daxiao);
				}
				shezhi_background.setVisibility(View.GONE);
				panduan_shezhi=false;
				break;
			case R.id.moshi_5x5jd:
				if(daxiao==4){
					util.saveZuigaofen(this, Integer.parseInt(zuigaofen.getText().toString()), Const.FILE_NAME[daxiao-4]);
					daxiao=5;
					restart(daxiao);
				}
				shezhi_background.setVisibility(View.GONE);
				panduan_shezhi=false;
				break;
			case R.id.main_i:
				if(!panduan_shezhi){
					main_i_background.setVisibility(View.VISIBLE);
					panduan_shezhi=true;
				}
				break;
				
		}
		
	}
	
	
	/*
	 * ��������
	 */
	private void move(String fangxiang){
		if(xy==1 && panduan){
			panduan=false;
			int jishu=0;
			String str="shu";
			if(fangxiang.equals("zuo")||fangxiang.equals("you")){
				str="heng";
			}
			//��
			for(int i=1;i<=daxiao;i++){
				//��
				for(int j=2;j<=daxiao;j++){
					//�жϵ�j�������Ƿ�Ϊ��
					if(map.get(zhuanhuan(fangxiang, i, j))!=null){
						//��֮ǰ
						for(int k=j-1;k>0;k--){
							if(map.get(zhuanhuan(fangxiang, i, k))==null){
								if(k!=1)continue;
								else {
									before(str,zhuanhuan(fangxiang, i, k) ,zhuanhuan(fangxiang, i, j), 0);
									jishu++;
									break;
								}
									
							}else if(map.get(zhuanhuan(fangxiang, i, k)).getShuzi()==map.get(zhuanhuan(fangxiang, i, j)).getShuzi()){
								before(str,zhuanhuan(fangxiang, i, k) ,zhuanhuan(fangxiang, i, j) , map.get(zhuanhuan(fangxiang, i, k)).getId());
								jishu++;
								break;
							}else if(k+1!=j){
								int c=1;
								if(fangxiang.equals("you")){
									c=-1;
								}else if(fangxiang.equals("shang")){
									c=10;
								}else if(fangxiang.equals("xia")){
									c=-10;
								}
								before(str,zhuanhuan(fangxiang, i, k)+c ,zhuanhuan(fangxiang, i, j) , 0);
								jishu++;
								break;
							}else{
								break;
							}
						}
					}
				}
			}
			if(jishu!=0)zhixin();
			else panduan=true;
		}
		
	}
	
	/*
	 * ����idת��
	 */
	private int zhuanhuan(String fangxiang,int i,int n){
		
		if(fangxiang.equals("zuo")){
			return i*10+n;
		}else if(fangxiang.equals("you")){
			return i*10+(daxiao+1-n);
		}else if(fangxiang.equals("shang")){
			return i+n*10;
		}else{
			return i+((daxiao+1)*10-n*10);
		}
		
	}
	/*
	 * 
	 * first	��Ҫ�ƶ����ĸ���id
	 * second	��ʼ�ƶ��ĸ���id
	 * fid		Ҫ���صķ���id
	 */
	private void before(String str,int first,int second,int fid){
		
		//Ҫ�ƶ��ķ���ʵ��
		Fangkuai_Y fy =map.get(second);
		//ɾ���ƶ�����ĸ�������
		map.remove(second);
		map.remove(first);
		//�����µ�����
		map.put(first,fy);
		//�з���Ҫ����ʱ
		if(fid!=0){
			//����ɾ������
			fn.add(fid);
			//�ı����
			map.get(first).setShuzi(map.get(first).getShuzi()*2);
			//���뼴���ƶ�����ķ���id
			fm.add(map.get(first).getId());
			//�������Ŷ���
			fangkuaisuofang((TextView)findViewById(fy.getId()));
			
			
		}
		
		//�ж��ƶ����򣬸��·�������
		if(str.equals("heng")){
			//��������
			animatorOnCreater(str, fy, first%10,(50+(10-sudu)*10)*Math.abs(first-second));
			map.get(first).setX((fwidth+fmargin)*(first%10-1));
			
			
		}else{
			//��������
			animatorOnCreater(str, fy, first/10,(50+(10-sudu)*10)*(Math.abs(first-second)/10));
			map.get(first).setY((fwidth+fmargin)*(first/10-1));
			
			
		}
		//�ո������
		konggezi.add(second);
		//�ո���ɾ��
		Iterator<Integer> iter = konggezi.iterator();  
		while(iter.hasNext()){  
		    int s = iter.next();  
		    if(s==first){  
		        iter.remove();  
		    }  
		}  
	}
	
	
	
	/*
	 * ִ�ж����¼�
	 */
	private void zhixin(){
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				if(fn.size()!=0&&fn!=null){
					end();
				}
				suiji(1);
			}
		});
		anim.start();
		anim=new AnimatorSet();
		
	}
	/*
	 * �ƶ���������ִ��
	 */
	private void end(){
		for(int id:fn){
			TextView textn=(TextView) findViewById(id);
			//����
			textn.setVisibility(View.INVISIBLE);
			//������ɫ
			textn.setTextColor(0xff7c7268);
			textn.setTextSize(Const.textsize[daxiao-4][0]);
			//��ԭ��ʽ
			if(id<=108||id>120){
				//����
				textn.setText("2");
				//������ʽ
				textn.setBackgroundResource(Const.fdrawable[0]);
			}
			else{
				//����
				textn.setText("4");
				//������ʽ
				textn.setBackgroundResource(Const.fdrawable[1]);
			}
			fnList.add(id);
		}
		fn.clear();
		
		
		int defen=0;
		for(int j:fm){
			//��ȡ��ʾ����ʵ��
			TextView texty=(TextView)findViewById(j);
			//�����ʾ��������
			int shuzi=Integer.parseInt(texty.getText().toString());
			//�ı䱳��
			texty.setBackgroundResource((int)map_drawable.get(shuzi*2));
			//�ı�����
			texty.setText(shuzi*2+"");
			//���־���
			texty.setGravity(Gravity.CENTER);
			if(shuzi>=4)texty.setTextColor(0xffffffff);
			if(shuzi>=8)texty.setTextSize(Const.textsize[daxiao-4][1]);
			if(shuzi>=64)texty.setTextSize(Const.textsize[daxiao-4][2]);
			if(shuzi>=512)texty.setTextSize(Const.textsize[daxiao-4][3]);
			if(shuzi>=8192)texty.setTextSize(Const.textsize[daxiao-4][4]);
			if(shuzi>=65536)texty.setTextSize(Const.textsize[daxiao-4][5]);
			//�ۼӵ÷�
			defen+=shuzi*2;
		}
		//���ϵ÷�
		fenshu.setText(defen+Integer.parseInt(fenshu.getText().toString())+"");
		if(Integer.parseInt(zuigaofen.getText().toString())<Integer.parseInt(fenshu.getText().toString())){
			zuigaofen.setText(fenshu.getText());
			fangda(zuigaofen);
		}
		//��ʱ�������
		fm.clear();
		//���Ŷ���
		fangda(fenshu);
		anim2.setDuration((10-sudu)*10+50);
		anim2.start();
		anim2=new AnimatorSet();
	}

	/*
	 * �ƶ���������
	 * first �ƶ����ĸ�������1~4��
	 * n	�ٶ�
	 * 
	 */
	private void animatorOnCreater(String str,Fangkuai_Y fangkuai,int first,int n){
		TextView t=(TextView) findViewById(fangkuai.getId());
		ObjectAnimator p1;
		if(str.equals("heng")){
			 p1=ObjectAnimator.ofFloat(t, "translationX",fangkuai.getX(), (fwidth+fmargin)*(first-1)).setDuration(n);
		}else{
			 p1=ObjectAnimator.ofFloat(t, "translationY",fangkuai.getY(),(fwidth+fmargin)*(first-1)).setDuration(n);
		}
		anim.playTogether(p1);
	}
	
	/*
	 * ��ͨ���Ŷ���������ִ��(�Ŵ�)
	 */
	private void fangda(TextView view){
		ObjectAnimator f1=ObjectAnimator.ofFloat(view, "scaleX",1f,1.2f );
		ObjectAnimator f2=ObjectAnimator.ofFloat(view, "scaleY",1f,1.2f );
		ObjectAnimator f3=ObjectAnimator.ofFloat(view, "scaleX",1.2f,1f );
		ObjectAnimator f4=ObjectAnimator.ofFloat(view, "scaleY",1.2f,1f );
		AnimatorSet setf=new AnimatorSet();
		setf.play(f1).with(f2);
		setf.play(f3).with(f4).after(f1);
		setf.setDuration((10-sudu)*10+50);
		setf.start();
	}
	/*
	 * ��ͨ���Ŷ���������ִ��(��С)
	 */
	private void suoxiao(TextView view){
		ObjectAnimator f1=ObjectAnimator.ofFloat(view, "scaleX",1f,0.8f );
		ObjectAnimator f2=ObjectAnimator.ofFloat(view, "scaleY",1f,0.8f );
		ObjectAnimator f3=ObjectAnimator.ofFloat(view, "scaleX",0.8f,1f );
		ObjectAnimator f4=ObjectAnimator.ofFloat(view, "scaleY",0.8f,1f );
		AnimatorSet setf=new AnimatorSet();
		setf.play(f1).with(f2);
		setf.play(f3).with(f4).after(f1);
		setf.setDuration((10-sudu)*10+50);
		setf.start();
	}
	/*
	 * �ƶ���������Ŷ�������
	 */
	private void fangkuaisuofang(TextView view){
		ObjectAnimator p1 = ObjectAnimator.ofFloat(view, "scaleX",1f,1.15f );
		ObjectAnimator p2 = ObjectAnimator.ofFloat(view, "scaleY",1f,1.15f );
		ObjectAnimator p3 = ObjectAnimator.ofFloat(view, "scaleX",1.15f,1.0f );
		ObjectAnimator p4 = ObjectAnimator.ofFloat(view, "scaleY",1.15f,1.0f );
		anim2.play(p1).with(p2);
		anim2.play(p3).with(p4).after(p1);
	}

	
	

}
