package com.mjj.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




import com.mjj.main.R.id;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnTouchListener{
	
	//������
	private Button btn;
	//��Ļ���
	private int width;
	//��Ļ�߶�
	private int height;
	//�����ȣ��߾�
	private int fwidth,fmargin; 
	//��Ϸ����
	private RelativeLayout huabu;
	//��������ʹ��
	private RelativeLayout.LayoutParams rp;
	//���鱳��
	private int[] fdrawable={R.drawable.fangkuai_2,R.drawable.fangkuai_4,
								R.drawable.fangkuai_8,R.drawable.fangkuai_16,
								R.drawable.fangkuai_32,R.drawable.fangkuai_64,
								R.drawable.fangkuai_128,R.drawable.fangkuai_256,
								R.drawable.fangkuai_512,R.drawable.fangkuai_1024,
								R.drawable.fangkuai_2048};
	@SuppressLint("UseSparseArrays")
	//��ʽ
	private Map<Integer,Integer> map_drawable =new HashMap<Integer, Integer>();
	//�������صķ���
	private List<Integer> fn= new ArrayList<Integer>();
	//�����ƶ��ķ���
	private List<Integer> fm= new ArrayList<Integer>();
	//���صķ���
	private List<Integer> fnList= new ArrayList<Integer>();
	//�ո���
	private List<Integer> konggezi=new ArrayList<Integer>();
	//���Ӷ�Ӧ����
	@SuppressLint("UseSparseArrays")
	private Map<Integer,Fangkuai_Y> map=new HashMap<Integer, Fangkuai_Y>();
	//�ж�����
	private float x_tmp1,y_tmp1,x_tmp2,y_tmp2;
	
	private int xy;
	//�ƶ�����
	private AnimatorSet anim=new AnimatorSet();
	//���Ŷ���
	private AnimatorSet anim2=new AnimatorSet();
	//���������ж�
	private boolean panduan=false;
	
	
	
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
		huabuCreat();
		//�������ַ���
		fangkuaionCreat();
		//��ʼ��ʾ2������
		suiji(2);
		
		
		
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
		//����߳�
		fwidth=(width-width/20)/5;
		//���
		fmargin=fwidth/5;
		//��Ϸ�򻬶�����
		huabu.setOnTouchListener(this);
		//���鱳����Ӧ
		for(int i=0,n=2;i<11;i++,n*=2){
			map_drawable.put(n, fdrawable[i]);
		}
		
	}


	/*
	 * ����ؼ�
	 */
	private void initView() {
		
		//��Ϸ��
		huabu=(RelativeLayout) findViewById(id.huabu);
	}
	
	/*
	 * ��������
	 */
	@SuppressLint("NewApi")
	private void huabuCreat() {
		
		RelativeLayout.LayoutParams linearParams =  (RelativeLayout.LayoutParams)huabu.getLayoutParams();
	 	linearParams.height = width-width/20;
	 	linearParams.width = width-width/20;
        huabu.setLayoutParams(linearParams);
	        
		for(int i=1;i<=16;i++){
			TextView tx = new TextView(this);
			
			// id��ո���List��ʼ��
			if(i<=4){
				tx.setId(10+i);
				konggezi.add(10+i);
			}else if(i<=8){
				tx.setId(20+i-4);
				konggezi.add(20+i-4);
			}else if(i<=12){
				tx.setId(30+i-8);
				konggezi.add(30+i-8);
			}else{
				tx.setId(40+i-12);
				konggezi.add(40+i-12);
			}
			
			//������ʽ
			tx.setBackgroundResource(R.drawable.fangkuai_b);
			
			//��Բ��ֲ��� ���
			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
			
			//������߾�
			if(i==1||i==5||i==9||i==13){
				rp.setMargins(fmargin,fmargin, 0, 0);
			}
			else if(i%4==0 && i!=16){
				rp.setMargins(fmargin,0,fmargin, 0);
			}
			else if(i<12){
				rp.setMargins(fmargin,0, 0, 0);
			}
			else if(i==16){
				rp.setMargins(fmargin,0, fmargin, fmargin);
			}
			else{
				rp.setMargins(fmargin,0, 0, fmargin);
			}
			
			//������Բ���λ��
			if(i==1){
				
			}else if(i==5){
				rp.addRule(RelativeLayout.BELOW,11);
			}else if(i==9){
				rp.addRule(RelativeLayout.BELOW,21);
			}else if(i==13){
				rp.addRule(RelativeLayout.BELOW,31);
			}else if(i<=4){
				rp.addRule(RelativeLayout.RIGHT_OF, 9+i);
				rp.addRule(RelativeLayout.ALIGN_TOP, 9+i);
			}else if(i<=8){
				rp.addRule(RelativeLayout.RIGHT_OF, 15+i);
				rp.addRule(RelativeLayout.ALIGN_TOP, 15+i);
			}
			else if(i<=12){
				rp.addRule(RelativeLayout.RIGHT_OF, 21+i);
				rp.addRule(RelativeLayout.ALIGN_TOP, 21+i);
			}
			else{
				rp.addRule(RelativeLayout.RIGHT_OF, 27+i);
				rp.addRule(RelativeLayout.ALIGN_TOP, 27+i);
			}
			tx.setText("");
			tx.setLayoutParams(rp);
			huabu.addView(tx);
		}
		
	}
	
	/*
	 * ���ַ��鴴��
	 */
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void fangkuaionCreat(){
		
		for(int i=1;i<=16;i++){
			TextView tx = new TextView(this);
			
			//id
			tx.setId(100+i);
			if(i<=8){
				//����
				tx.setText("2");
				//������ʽ
				tx.setBackgroundResource(fdrawable[0]);
			}
			else{
				//����
				tx.setText("4");
				//������ʽ
				tx.setBackgroundResource(fdrawable[1]);
			}
			//������ɫ
			tx.setTextColor(0xff7c7268);
			//���־���
			tx.setGravity(Gravity.CENTER);
			//���ִ�С
			tx.setTextSize(fwidth/5);
			//��Բ��ֲ��� ���
			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
			//������Բ���λ��
			rp.addRule(RelativeLayout.ALIGN_LEFT,11);
			rp.addRule(RelativeLayout.ALIGN_BOTTOM,11);
			
			tx.setLayoutParams(rp);

			//����
			tx.setVisibility(View.INVISIBLE);
			
			huabu.addView(tx);
			
			//����δ��ʾ����
			fnList.add(100+i);
		}
			
		
	}
	/*
	 * ���������ж�
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
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
                    	zuo();
                    }
                    else if(x_tmp2 - x_tmp1 > 8 ){
                    	xy++;
                    	you();
                    }
                }else{
	                if(y_tmp1 - y_tmp2 > 8 ){
	                	xy++;
	                	shang();
	                }
	                else if(y_tmp2 - y_tmp1 > 8 ){
	                	xy++;
	                	xia();
	                }
                }
                break;
	        case MotionEvent.ACTION_UP:
	        	xy=0;
	        	break;
		}
		return true;
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
				
				//��ø��Ӻ���ͷ���ID
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
				//���������ж�
			}

		}
	}
	/*
	 * ���󻬶�
	 */
	public void zuo(){
		if(xy==1 && panduan){
			panduan=false;
			int jishu=0;
			for(int i=1;i<=4;i++){
				
				//�жϵڶ�������
				if(map.get(i*10+2)!=null){
					//��һ������Ϊ��
					if(map.get(i*10+1)==null){
						before("heng",i*10+1 ,i*10+2 , 0);
						jishu++;
					}
					//��һ�����Ӳ�Ϊ����������ڶ�����ͬ
					else if(map.get(i*10+1).getShuzi()==map.get(i*10+2).getShuzi()){
						before("heng",i*10+1 ,i*10+2 , map.get(i*10+1).getId());
						jishu++;
					}
				}
				
				//�жϵ���������
				if(map.get(i*10+3)!=null){
					//�ڶ�������Ϊ��
					if(map.get(i*10+2)==null){
						//��һ������Ϊ��
						if(map.get(i*10+1)==null){
							before("heng",i*10+1 ,i*10+3 , 0);
							jishu++;
						}
						//��һ�����Ӳ�Ϊ�����������������ͬ
						else if(map.get(i*10+1).getShuzi()==map.get(i*10+3).getShuzi()){
							before("heng",i*10+1 ,i*10+3 , map.get(i*10+1).getId());
							jishu++;
						}else{
							before("heng",i*10+2 ,i*10+3 , 0);
							jishu++;
						}
					}
					//�ڶ������Ӳ�Ϊ�����������������ͬ
					else if(map.get(i*10+2).getShuzi()==map.get(i*10+3).getShuzi()){
						before("heng",i*10+2 ,i*10+3 , map.get(i*10+2).getId());
						jishu++;
					}
				}
				//�жϵ��ĸ�����
				if(map.get(i*10+4)!=null){
					//����������Ϊ��
					if(map.get(i*10+3)==null){
						//�ڶ�������Ϊ��
						if(map.get(i*10+2)==null){
							//��һ������Ϊ��
							if(map.get(i*10+1)==null){
								before("heng",i*10+1 ,i*10+4 , 0);
								jishu++;
							}
							//��һ�����Ӳ�Ϊ������������ĸ���ͬ
							else if(map.get(i*10+1).getShuzi()==map.get(i*10+4).getShuzi()){
								before("heng",i*10+1 ,i*10+4 , map.get(i*10+1).getId());
								jishu++;
							}else{
								before("heng",i*10+2 ,i*10+4 , 0);
								jishu++;
							}
						}
						//�ڶ������Ӳ�Ϊ������������ĸ���ͬ
						else if(map.get(i*10+2).getShuzi()==map.get(i*10+4).getShuzi()){
							before("heng",i*10+2 ,i*10+4 , map.get(i*10+2).getId());
							jishu++;
						}else{
							before("heng",i*10+3 ,i*10+4 , 0);
							jishu++;
						}
					}
					//���������Ӳ�Ϊ������������ĸ���ͬ
					else if(map.get(i*10+3).getShuzi()==map.get(i*10+4).getShuzi()){
						before("heng",i*10+3 ,i*10+4 , map.get(i*10+3).getId());
						jishu++;
					}
				}
				
			}
			if(jishu!=0)zhixin();
			else panduan=true;
		}
	}
	/*
	 * ���һ���
	 */
	public void you(){
		if(xy==1&& panduan){
			panduan=false;
			int jishu=0;
			for(int i=1;i<=4;i++){
				
				//�жϵڶ�������
				if(map.get(i*10+3)!=null){
					//��һ������Ϊ��
					if(map.get(i*10+4)==null){
						before("heng",i*10+4 ,i*10+3 , 0);
						jishu++;
					}
					//��һ�����Ӳ�Ϊ����������ڶ�����ͬ
					else if(map.get(i*10+4).getShuzi()==map.get(i*10+3).getShuzi()){
						before("heng",i*10+4 ,i*10+3 , map.get(i*10+4).getId());
						jishu++;
					}
				}
				
				//�жϵ���������
				if(map.get(i*10+2)!=null){
					//�ڶ�������Ϊ��
					if(map.get(i*10+3)==null){
						//��һ������Ϊ��
						if(map.get(i*10+4)==null){
							before("heng",i*10+4 ,i*10+2 , 0);
							jishu++;
						}
						//��һ�����Ӳ�Ϊ�����������������ͬ
						else if(map.get(i*10+4).getShuzi()==map.get(i*10+2).getShuzi()){
							before("heng",i*10+4 ,i*10+2 , map.get(i*10+4).getId());
							jishu++;
						}else{
							before("heng",i*10+3 ,i*10+2 , 0);
							jishu++;
						}
					}
					//�ڶ������Ӳ�Ϊ�����������������ͬ
					else if(map.get(i*10+3).getShuzi()==map.get(i*10+2).getShuzi()){
						before("heng",i*10+3 ,i*10+2 , map.get(i*10+3).getId());
						jishu++;
					}
				}
				//�жϵ��ĸ�����
				if(map.get(i*10+1)!=null){
					//����������Ϊ��
					if(map.get(i*10+2)==null){
						//�ڶ�������Ϊ��
						if(map.get(i*10+3)==null){
							//��һ������Ϊ��
							if(map.get(i*10+4)==null){
								before("heng",i*10+4 ,i*10+1 , 0);
								jishu++;
							}
							//��һ�����Ӳ�Ϊ������������ĸ���ͬ
							else if(map.get(i*10+4).getShuzi()==map.get(i*10+1).getShuzi()){
								before("heng",i*10+4 ,i*10+1 , map.get(i*10+4).getId());
								jishu++;
							}else{
								before("heng",i*10+3 ,i*10+1 , 0);
								jishu++;
							}
						}
						//�ڶ������Ӳ�Ϊ������������ĸ���ͬ
						else if(map.get(i*10+3).getShuzi()==map.get(i*10+1).getShuzi()){
							before("heng",i*10+3 ,i*10+1 , map.get(i*10+3).getId());
							jishu++;
						}else{
							before("heng",i*10+2 ,i*10+1 , 0);
							jishu++;
						}
					}
					//���������Ӳ�Ϊ������������ĸ���ͬ
					else if(map.get(i*10+2).getShuzi()==map.get(i*10+1).getShuzi()){
						before("heng",i*10+2 ,i*10+1 , map.get(i*10+2).getId());
						jishu++;
					}
				}
				
			}
			if(jishu!=0)zhixin();
			else panduan=true;
		}
	}
	/*
	 * ���ϻ���
	 */
	public void shang(){
		if(xy==1&& panduan){
			panduan=false;
			int jishu=0;
			for(int i=1;i<=4;i++){
				
				//�жϵڶ�������
				if(map.get(i+20)!=null){
					//��һ������Ϊ��
					if(map.get(i+10)==null){
						before("shu",i+10 ,i+20 , 0);
						jishu++;
					}
					//��һ�����Ӳ�Ϊ����������ڶ�����ͬ
					else if(map.get(i+10).getShuzi()==map.get(i+20).getShuzi()){
						before("shu",i+10 ,i+20 , map.get(i+10).getId());
						jishu++;
					}
				}
				
				//�жϵ���������
				if(map.get(i+30)!=null){
					//�ڶ�������Ϊ��
					if(map.get(i+20)==null){
						//��һ������Ϊ��
						if(map.get(i+10)==null){
							before("shu",i+10 ,i+30 , 0);
							jishu++;
						}
						//��һ�����Ӳ�Ϊ�����������������ͬ
						else if(map.get(i+10).getShuzi()==map.get(i+30).getShuzi()){
							before("shu",i+10 ,i+30 , map.get(i+10).getId());
							jishu++;
						}else{
							before("shu",i+20 ,i+30 , 0);
							jishu++;
						}
					}
					//�ڶ������Ӳ�Ϊ�����������������ͬ
					else if(map.get(i+20).getShuzi()==map.get(i+30).getShuzi()){
						before("shu",i+20 ,i+30 , map.get(i+20).getId());
						jishu++;
					}
				}
				//�жϵ��ĸ�����
				if(map.get(i+40)!=null){
					//����������Ϊ��
					if(map.get(i+30)==null){
						//�ڶ�������Ϊ��
						if(map.get(i+20)==null){
							//��һ������Ϊ��
							if(map.get(i+10)==null){
								before("shu",i+10 ,i+40 , 0);
								jishu++;
							}
							//��һ�����Ӳ�Ϊ������������ĸ���ͬ
							else if(map.get(i+10).getShuzi()==map.get(i+40).getShuzi()){
								before("shu",i+10 ,i+40 , map.get(i+10).getId());
								jishu++;
							}else{
								before("shu",i+20 ,i+40 , 0);
								jishu++;
							}
						}
						//�ڶ������Ӳ�Ϊ������������ĸ���ͬ
						else if(map.get(i+20).getShuzi()==map.get(i+40).getShuzi()){
							before("shu",i+20 ,i+40 , map.get(i+20).getId());
							jishu++;
						}else{
							before("shu",i+30 ,i+40 , 0);
							jishu++;
						}
					}
					//���������Ӳ�Ϊ������������ĸ���ͬ
					else if(map.get(i+30).getShuzi()==map.get(i+40).getShuzi()){
						before("shu",i+30 ,i+40 , map.get(i+30).getId());
						jishu++;
					}
				}
				
			}
			if(jishu!=0)zhixin();
			else panduan=true;
		}
	}
	/*
	 * ���»���
	 */
	public void xia(){
		if(xy==1&& panduan){
			panduan=false;
			int jishu=0;
			for(int i=1;i<=4;i++){
				
				//�жϵڶ�������
				if(map.get(i+30)!=null){
					//��һ������Ϊ��
					if(map.get(i+40)==null){
						before("shu",i+40 ,i+30 , 0);
						jishu++;
					}
					//��һ�����Ӳ�Ϊ����������ڶ�����ͬ
					else if(map.get(i+40).getShuzi()==map.get(i+30).getShuzi()){
						before("shu",i+40 ,i+30 , map.get(i+40).getId());
						jishu++;
					}
				}
				
				//�жϵ���������
				if(map.get(i+20)!=null){
					//�ڶ�������Ϊ��
					if(map.get(i+30)==null){
						//��һ������Ϊ��
						if(map.get(i+40)==null){
							before("shu",i+40 ,i+20 , 0);
							jishu++;
						}
						//��һ�����Ӳ�Ϊ�����������������ͬ
						else if(map.get(i+40).getShuzi()==map.get(i+20).getShuzi()){
							before("shu",i+40 ,i+20 , map.get(i+40).getId());
							jishu++;
						}else{
							before("shu",i+30 ,i+20 , 0);
							jishu++;
						}
					}
					//�ڶ������Ӳ�Ϊ�����������������ͬ
					else if(map.get(i+30).getShuzi()==map.get(i+20).getShuzi()){
						before("shu",i+30 ,i+20 , map.get(i+30).getId());
						jishu++;
					}
				}
				//�жϵ��ĸ�����
				if(map.get(i+10)!=null){
					//����������Ϊ��
					if(map.get(i+20)==null){
						//�ڶ�������Ϊ��
						if(map.get(i+30)==null){
							//��һ������Ϊ��
							if(map.get(i+40)==null){
								before("shu",i+40 ,i+10 , 0);
								jishu++;
							}
							//��һ�����Ӳ�Ϊ������������ĸ���ͬ
							else if(map.get(i+40).getShuzi()==map.get(i+10).getShuzi()){
								before("shu",i+40 ,i+10 , map.get(i+40).getId());
								jishu++;
							}else{
								before("shu",i+30 ,i+10 , 0);
								jishu++;
							}
						}
						//�ڶ������Ӳ�Ϊ������������ĸ���ͬ
						else if(map.get(i+30).getShuzi()==map.get(i+10).getShuzi()){
							before("shu",i+30 ,i+10 , map.get(i+30).getId());
							jishu++;
						}else{
							before("shu",i+20 ,i+10 , 0);
							jishu++;
						}
					}
					//���������Ӳ�Ϊ������������ĸ���ͬ
					else if(map.get(i+20).getShuzi()==map.get(i+10).getShuzi()){
						before("shu",i+20 ,i+10 , map.get(i+20).getId());
						jishu++;
					}
				}
				
			}
			if(jishu!=0)zhixin();
			else panduan=true;
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
			ObjectAnimator p1 = ObjectAnimator.ofFloat(findViewById(fy.getId()), "scaleX",1f,1.1f );
			ObjectAnimator p2 = ObjectAnimator.ofFloat(findViewById(fy.getId()), "scaleY",1f,1.1f );
			ObjectAnimator p3 = ObjectAnimator.ofFloat(findViewById(fy.getId()), "scaleX",1.1f,1.0f );
			ObjectAnimator p4 = ObjectAnimator.ofFloat(findViewById(fy.getId()), "scaleY",1.1f,1.0f );
			anim2.play(p1).with(p2);
			anim2.play(p3).with(p4).after(p1);
			
		}
		
		//�ж��ƶ����򣬸��·�������
		if(str.equals("heng")){
			//��������
			animatorOnCreater(str, fy, first%10,120*Math.abs(first-second));
			map.get(first).setX((fwidth+fmargin)*(first%10-1));
			
			
		}else{
			//��������
			animatorOnCreater(str, fy, first/10,120*(Math.abs(first-second)/10));
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
			textn.setTextSize(fwidth/5);
			//��ԭ��ʽ
			if(id<=108){
				//����
				textn.setText("2");
				//������ʽ
				textn.setBackgroundResource(fdrawable[0]);
			}
			else{
				//����
				textn.setText("4");
				//������ʽ
				textn.setBackgroundResource(fdrawable[1]);
			}
			fnList.add(id);
		}
		fn.clear();
		
		for(int j:fm){
			//��ȡ��ʾ����ʵ��
			TextView texty=(TextView)findViewById(j);
			//�����ʾ��������
			int shuzi=Integer.parseInt(texty.getText().toString());
			//�ı䱳��
			texty.setBackgroundResource((int)map_drawable.get(shuzi*2));
			//�ı�����
			texty.setText(shuzi*2+"");
			if(shuzi>=4)texty.setTextColor(0xffffffff);
			if(shuzi>=8)texty.setTextSize(fwidth/6);
			if(shuzi>=64)texty.setTextSize(fwidth/7);
			if(shuzi>=512)texty.setTextSize(fwidth/8);
		}
		fm.clear();
		anim2.setDuration(150);
		anim2.start();
		anim2=new AnimatorSet();
	}

	/*
	 * ��������
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
	

}
