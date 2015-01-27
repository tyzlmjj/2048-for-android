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
	
	//测试用
	private Button btn;
	//屏幕宽度
	private int width;
	//屏幕高度
	private int height;
	//方块宽度，边距
	private int fwidth,fmargin; 
	//游戏画布
	private RelativeLayout huabu;
	//创建布局使用
	private RelativeLayout.LayoutParams rp;
	//方块背景
	private int[] fdrawable={R.drawable.fangkuai_2,R.drawable.fangkuai_4,
								R.drawable.fangkuai_8,R.drawable.fangkuai_16,
								R.drawable.fangkuai_32,R.drawable.fangkuai_64,
								R.drawable.fangkuai_128,R.drawable.fangkuai_256,
								R.drawable.fangkuai_512,R.drawable.fangkuai_1024,
								R.drawable.fangkuai_2048};
	@SuppressLint("UseSparseArrays")
	//样式
	private Map<Integer,Integer> map_drawable =new HashMap<Integer, Integer>();
	//即将隐藏的方块
	private List<Integer> fn= new ArrayList<Integer>();
	//即将移动的方块
	private List<Integer> fm= new ArrayList<Integer>();
	//隐藏的方块
	private List<Integer> fnList= new ArrayList<Integer>();
	//空格子
	private List<Integer> konggezi=new ArrayList<Integer>();
	//格子对应方块
	@SuppressLint("UseSparseArrays")
	private Map<Integer,Fangkuai_Y> map=new HashMap<Integer, Fangkuai_Y>();
	//判断手势
	private float x_tmp1,y_tmp1,x_tmp2,y_tmp2;
	
	private int xy;
	//移动动画
	private AnimatorSet anim=new AnimatorSet();
	//缩放动画
	private AnimatorSet anim2=new AnimatorSet();
	//动画结束判断
	private boolean panduan=false;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//获取屏幕尺寸
		getwindows();
		//导入控件
		initView();
		//初始化事件
		initEvent();
		//画布创建
		huabuCreat();
		//创建数字方块
		fangkuaionCreat();
		//开始显示2个格子
		suiji(2);
		
		
		
	}
	
	
	/*
	 * 获取屏幕尺寸
	 */
	private void getwindows() {
		
		DisplayMetrics dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//屏幕宽度
		width =dm.widthPixels;
		//屏幕高度
		height = dm.heightPixels;
		
	}

	/*
	 * 初始化事件
	 */
	private void initEvent() {
		//方块边长
		fwidth=(width-width/20)/5;
		//间距
		fmargin=fwidth/5;
		//游戏框滑动监听
		huabu.setOnTouchListener(this);
		//方块背景对应
		for(int i=0,n=2;i<11;i++,n*=2){
			map_drawable.put(n, fdrawable[i]);
		}
		
	}


	/*
	 * 导入控件
	 */
	private void initView() {
		
		//游戏框
		huabu=(RelativeLayout) findViewById(id.huabu);
	}
	
	/*
	 * 画布创建
	 */
	@SuppressLint("NewApi")
	private void huabuCreat() {
		
		RelativeLayout.LayoutParams linearParams =  (RelativeLayout.LayoutParams)huabu.getLayoutParams();
	 	linearParams.height = width-width/20;
	 	linearParams.width = width-width/20;
        huabu.setLayoutParams(linearParams);
	        
		for(int i=1;i<=16;i++){
			TextView tx = new TextView(this);
			
			// id与空格子List初始化
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
			
			//背景样式
			tx.setBackgroundResource(R.drawable.fangkuai_b);
			
			//相对布局参数 宽高
			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
			
			//设置外边距
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
			
			//设置相对布局位置
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
	 * 数字方块创建
	 */
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void fangkuaionCreat(){
		
		for(int i=1;i<=16;i++){
			TextView tx = new TextView(this);
			
			//id
			tx.setId(100+i);
			if(i<=8){
				//数字
				tx.setText("2");
				//背景样式
				tx.setBackgroundResource(fdrawable[0]);
			}
			else{
				//数字
				tx.setText("4");
				//背景样式
				tx.setBackgroundResource(fdrawable[1]);
			}
			//数字颜色
			tx.setTextColor(0xff7c7268);
			//数字居中
			tx.setGravity(Gravity.CENTER);
			//数字大小
			tx.setTextSize(fwidth/5);
			//相对布局参数 宽高
			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
			//设置相对布局位置
			rp.addRule(RelativeLayout.ALIGN_LEFT,11);
			rp.addRule(RelativeLayout.ALIGN_BOTTOM,11);
			
			tx.setLayoutParams(rp);

			//隐藏
			tx.setVisibility(View.INVISIBLE);
			
			huabu.addView(tx);
			
			//导入未显示集合
			fnList.add(100+i);
		}
			
		
	}
	/*
	 * 滑动方向判定
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//获取当前坐标  
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
	 * 随机生成新的2/4方块
	 */
	private void suiji(int n){
		
		for(int i=0;i<n;i++){
			if(konggezi.size()!=0&&konggezi!=null){
				//随机格子和方块
				int g=(int)(Math.random() * konggezi.size());
				int f=(int)(Math.random() * fnList.size());
				
				//获得格子号码和方块ID
				int gezi=konggezi.get(g);
				final int fid=fnList.get(f);
				
				//删除
				konggezi.remove(g);
				fnList.remove(f);
				
				//创建方块实例
				final TextView text=(TextView) findViewById(fid);
				
				//绑定格子和方块
				map.put(gezi, new Fangkuai_Y(fid,0,0,Integer.parseInt(text.getText().toString())));
				
				//移动方块位置到空的格子
				ObjectAnimator p1 = ObjectAnimator.ofFloat(text,"translationX",map.get(gezi).getX(),(fwidth+fmargin)*(gezi%10-1));
				ObjectAnimator p2 = ObjectAnimator.ofFloat(text,"translationY",map.get(gezi).getY(),(fwidth+fmargin)*(gezi/10-1));
				AnimatorSet set=new AnimatorSet();
				set.playTogether(p1,p2);
				set.setDuration(100);
				set.start();
				//方块移动完成之后显示
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
				//格子满了判断
			}

		}
	}
	/*
	 * 向左滑动
	 */
	public void zuo(){
		if(xy==1 && panduan){
			panduan=false;
			int jishu=0;
			for(int i=1;i<=4;i++){
				
				//判断第二个格子
				if(map.get(i*10+2)!=null){
					//第一个格子为空
					if(map.get(i*10+1)==null){
						before("heng",i*10+1 ,i*10+2 , 0);
						jishu++;
					}
					//第一个格子不为空且数字与第二个相同
					else if(map.get(i*10+1).getShuzi()==map.get(i*10+2).getShuzi()){
						before("heng",i*10+1 ,i*10+2 , map.get(i*10+1).getId());
						jishu++;
					}
				}
				
				//判断第三个格子
				if(map.get(i*10+3)!=null){
					//第二个格子为空
					if(map.get(i*10+2)==null){
						//第一个格子为空
						if(map.get(i*10+1)==null){
							before("heng",i*10+1 ,i*10+3 , 0);
							jishu++;
						}
						//第一个格子不为空且数字与第三个相同
						else if(map.get(i*10+1).getShuzi()==map.get(i*10+3).getShuzi()){
							before("heng",i*10+1 ,i*10+3 , map.get(i*10+1).getId());
							jishu++;
						}else{
							before("heng",i*10+2 ,i*10+3 , 0);
							jishu++;
						}
					}
					//第二个格子不为空且数字与第三个相同
					else if(map.get(i*10+2).getShuzi()==map.get(i*10+3).getShuzi()){
						before("heng",i*10+2 ,i*10+3 , map.get(i*10+2).getId());
						jishu++;
					}
				}
				//判断第四个格子
				if(map.get(i*10+4)!=null){
					//第三个格子为空
					if(map.get(i*10+3)==null){
						//第二个格子为空
						if(map.get(i*10+2)==null){
							//第一个格子为空
							if(map.get(i*10+1)==null){
								before("heng",i*10+1 ,i*10+4 , 0);
								jishu++;
							}
							//第一个格子不为空且数字与第四个相同
							else if(map.get(i*10+1).getShuzi()==map.get(i*10+4).getShuzi()){
								before("heng",i*10+1 ,i*10+4 , map.get(i*10+1).getId());
								jishu++;
							}else{
								before("heng",i*10+2 ,i*10+4 , 0);
								jishu++;
							}
						}
						//第二个格子不为空且数字与第四个相同
						else if(map.get(i*10+2).getShuzi()==map.get(i*10+4).getShuzi()){
							before("heng",i*10+2 ,i*10+4 , map.get(i*10+2).getId());
							jishu++;
						}else{
							before("heng",i*10+3 ,i*10+4 , 0);
							jishu++;
						}
					}
					//第三个格子不为空且数字与第四个相同
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
	 * 向右滑动
	 */
	public void you(){
		if(xy==1&& panduan){
			panduan=false;
			int jishu=0;
			for(int i=1;i<=4;i++){
				
				//判断第二个格子
				if(map.get(i*10+3)!=null){
					//第一个格子为空
					if(map.get(i*10+4)==null){
						before("heng",i*10+4 ,i*10+3 , 0);
						jishu++;
					}
					//第一个格子不为空且数字与第二个相同
					else if(map.get(i*10+4).getShuzi()==map.get(i*10+3).getShuzi()){
						before("heng",i*10+4 ,i*10+3 , map.get(i*10+4).getId());
						jishu++;
					}
				}
				
				//判断第三个格子
				if(map.get(i*10+2)!=null){
					//第二个格子为空
					if(map.get(i*10+3)==null){
						//第一个格子为空
						if(map.get(i*10+4)==null){
							before("heng",i*10+4 ,i*10+2 , 0);
							jishu++;
						}
						//第一个格子不为空且数字与第三个相同
						else if(map.get(i*10+4).getShuzi()==map.get(i*10+2).getShuzi()){
							before("heng",i*10+4 ,i*10+2 , map.get(i*10+4).getId());
							jishu++;
						}else{
							before("heng",i*10+3 ,i*10+2 , 0);
							jishu++;
						}
					}
					//第二个格子不为空且数字与第三个相同
					else if(map.get(i*10+3).getShuzi()==map.get(i*10+2).getShuzi()){
						before("heng",i*10+3 ,i*10+2 , map.get(i*10+3).getId());
						jishu++;
					}
				}
				//判断第四个格子
				if(map.get(i*10+1)!=null){
					//第三个格子为空
					if(map.get(i*10+2)==null){
						//第二个格子为空
						if(map.get(i*10+3)==null){
							//第一个格子为空
							if(map.get(i*10+4)==null){
								before("heng",i*10+4 ,i*10+1 , 0);
								jishu++;
							}
							//第一个格子不为空且数字与第四个相同
							else if(map.get(i*10+4).getShuzi()==map.get(i*10+1).getShuzi()){
								before("heng",i*10+4 ,i*10+1 , map.get(i*10+4).getId());
								jishu++;
							}else{
								before("heng",i*10+3 ,i*10+1 , 0);
								jishu++;
							}
						}
						//第二个格子不为空且数字与第四个相同
						else if(map.get(i*10+3).getShuzi()==map.get(i*10+1).getShuzi()){
							before("heng",i*10+3 ,i*10+1 , map.get(i*10+3).getId());
							jishu++;
						}else{
							before("heng",i*10+2 ,i*10+1 , 0);
							jishu++;
						}
					}
					//第三个格子不为空且数字与第四个相同
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
	 * 向上滑动
	 */
	public void shang(){
		if(xy==1&& panduan){
			panduan=false;
			int jishu=0;
			for(int i=1;i<=4;i++){
				
				//判断第二个格子
				if(map.get(i+20)!=null){
					//第一个格子为空
					if(map.get(i+10)==null){
						before("shu",i+10 ,i+20 , 0);
						jishu++;
					}
					//第一个格子不为空且数字与第二个相同
					else if(map.get(i+10).getShuzi()==map.get(i+20).getShuzi()){
						before("shu",i+10 ,i+20 , map.get(i+10).getId());
						jishu++;
					}
				}
				
				//判断第三个格子
				if(map.get(i+30)!=null){
					//第二个格子为空
					if(map.get(i+20)==null){
						//第一个格子为空
						if(map.get(i+10)==null){
							before("shu",i+10 ,i+30 , 0);
							jishu++;
						}
						//第一个格子不为空且数字与第三个相同
						else if(map.get(i+10).getShuzi()==map.get(i+30).getShuzi()){
							before("shu",i+10 ,i+30 , map.get(i+10).getId());
							jishu++;
						}else{
							before("shu",i+20 ,i+30 , 0);
							jishu++;
						}
					}
					//第二个格子不为空且数字与第三个相同
					else if(map.get(i+20).getShuzi()==map.get(i+30).getShuzi()){
						before("shu",i+20 ,i+30 , map.get(i+20).getId());
						jishu++;
					}
				}
				//判断第四个格子
				if(map.get(i+40)!=null){
					//第三个格子为空
					if(map.get(i+30)==null){
						//第二个格子为空
						if(map.get(i+20)==null){
							//第一个格子为空
							if(map.get(i+10)==null){
								before("shu",i+10 ,i+40 , 0);
								jishu++;
							}
							//第一个格子不为空且数字与第四个相同
							else if(map.get(i+10).getShuzi()==map.get(i+40).getShuzi()){
								before("shu",i+10 ,i+40 , map.get(i+10).getId());
								jishu++;
							}else{
								before("shu",i+20 ,i+40 , 0);
								jishu++;
							}
						}
						//第二个格子不为空且数字与第四个相同
						else if(map.get(i+20).getShuzi()==map.get(i+40).getShuzi()){
							before("shu",i+20 ,i+40 , map.get(i+20).getId());
							jishu++;
						}else{
							before("shu",i+30 ,i+40 , 0);
							jishu++;
						}
					}
					//第三个格子不为空且数字与第四个相同
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
	 * 向下滑动
	 */
	public void xia(){
		if(xy==1&& panduan){
			panduan=false;
			int jishu=0;
			for(int i=1;i<=4;i++){
				
				//判断第二个格子
				if(map.get(i+30)!=null){
					//第一个格子为空
					if(map.get(i+40)==null){
						before("shu",i+40 ,i+30 , 0);
						jishu++;
					}
					//第一个格子不为空且数字与第二个相同
					else if(map.get(i+40).getShuzi()==map.get(i+30).getShuzi()){
						before("shu",i+40 ,i+30 , map.get(i+40).getId());
						jishu++;
					}
				}
				
				//判断第三个格子
				if(map.get(i+20)!=null){
					//第二个格子为空
					if(map.get(i+30)==null){
						//第一个格子为空
						if(map.get(i+40)==null){
							before("shu",i+40 ,i+20 , 0);
							jishu++;
						}
						//第一个格子不为空且数字与第三个相同
						else if(map.get(i+40).getShuzi()==map.get(i+20).getShuzi()){
							before("shu",i+40 ,i+20 , map.get(i+40).getId());
							jishu++;
						}else{
							before("shu",i+30 ,i+20 , 0);
							jishu++;
						}
					}
					//第二个格子不为空且数字与第三个相同
					else if(map.get(i+30).getShuzi()==map.get(i+20).getShuzi()){
						before("shu",i+30 ,i+20 , map.get(i+30).getId());
						jishu++;
					}
				}
				//判断第四个格子
				if(map.get(i+10)!=null){
					//第三个格子为空
					if(map.get(i+20)==null){
						//第二个格子为空
						if(map.get(i+30)==null){
							//第一个格子为空
							if(map.get(i+40)==null){
								before("shu",i+40 ,i+10 , 0);
								jishu++;
							}
							//第一个格子不为空且数字与第四个相同
							else if(map.get(i+40).getShuzi()==map.get(i+10).getShuzi()){
								before("shu",i+40 ,i+10 , map.get(i+40).getId());
								jishu++;
							}else{
								before("shu",i+30 ,i+10 , 0);
								jishu++;
							}
						}
						//第二个格子不为空且数字与第四个相同
						else if(map.get(i+30).getShuzi()==map.get(i+10).getShuzi()){
							before("shu",i+30 ,i+10 , map.get(i+30).getId());
							jishu++;
						}else{
							before("shu",i+20 ,i+10 , 0);
							jishu++;
						}
					}
					//第三个格子不为空且数字与第四个相同
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
	 * first	将要移动到的格子id
	 * second	开始移动的格子id
	 * fid		要隐藏的方块id
	 */
	private void before(String str,int first,int second,int fid){
		
		//要移动的方块实例
		Fangkuai_Y fy =map.get(second);
		//删除移动方块的格子连接
		map.remove(second);
		map.remove(first);
		//创建新的连接
		map.put(first,fy);
		//有方块要隐藏时
		if(fid!=0){
			//即将删除格子
			fn.add(fid);
			//改变计数
			map.get(first).setShuzi(map.get(first).getShuzi()*2);
			//导入即将移动增大的方块id
			fm.add(map.get(first).getId());
			//创建缩放动画
			ObjectAnimator p1 = ObjectAnimator.ofFloat(findViewById(fy.getId()), "scaleX",1f,1.1f );
			ObjectAnimator p2 = ObjectAnimator.ofFloat(findViewById(fy.getId()), "scaleY",1f,1.1f );
			ObjectAnimator p3 = ObjectAnimator.ofFloat(findViewById(fy.getId()), "scaleX",1.1f,1.0f );
			ObjectAnimator p4 = ObjectAnimator.ofFloat(findViewById(fy.getId()), "scaleY",1.1f,1.0f );
			anim2.play(p1).with(p2);
			anim2.play(p3).with(p4).after(p1);
			
		}
		
		//判断移动方向，更新方块坐标
		if(str.equals("heng")){
			//动画创建
			animatorOnCreater(str, fy, first%10,120*Math.abs(first-second));
			map.get(first).setX((fwidth+fmargin)*(first%10-1));
			
			
		}else{
			//动画创建
			animatorOnCreater(str, fy, first/10,120*(Math.abs(first-second)/10));
			map.get(first).setY((fwidth+fmargin)*(first/10-1));
			
			
		}
		//空格子添加
		konggezi.add(second);
		//空格子删除
		Iterator<Integer> iter = konggezi.iterator();  
		while(iter.hasNext()){  
		    int s = iter.next();  
		    if(s==first){  
		        iter.remove();  
		    }  
		}  
	}
	
	
	
	/*
	 * 执行动画事件
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
	 * 移动动画结束执行
	 */
	private void end(){
		for(int id:fn){
			TextView textn=(TextView) findViewById(id);
			//隐藏
			textn.setVisibility(View.INVISIBLE);
			//数字颜色
			textn.setTextColor(0xff7c7268);
			textn.setTextSize(fwidth/5);
			//还原样式
			if(id<=108){
				//数字
				textn.setText("2");
				//背景样式
				textn.setBackgroundResource(fdrawable[0]);
			}
			else{
				//数字
				textn.setText("4");
				//背景样式
				textn.setBackgroundResource(fdrawable[1]);
			}
			fnList.add(id);
		}
		fn.clear();
		
		for(int j:fm){
			//获取显示方块实例
			TextView texty=(TextView)findViewById(j);
			//获得显示方块数字
			int shuzi=Integer.parseInt(texty.getText().toString());
			//改变背景
			texty.setBackgroundResource((int)map_drawable.get(shuzi*2));
			//改变数字
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
	 * 动画创建
	 * first 移动到的格子数（1~4）
	 * n	速度
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
