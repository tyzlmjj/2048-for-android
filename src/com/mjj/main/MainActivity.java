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
	//重新开始按钮
	private Button restart;
	//速度加减按钮
	private Button jia,jian;
	//设置按钮
	private Button shezhi;
	//设置框按钮
	private Button moshi_5x5jd;
	//设置框按钮
	private Button moshi_4x4jd;
	//i按钮
	private Button main_i;
	//确定框按钮yes
	private Button btn_yes;
	//确定框按钮no
	private Button btn_no;
	//速度数值
	private TextView sd;
	//分数
	private TextView fenshu;
	//最高分数
	private TextView zuigaofen;
	//屏幕宽度
	private int width;
	//屏幕高度
	private int height;
	//方块宽度，边距
	private int fwidth,fmargin; 
	//游戏画布
	private RelativeLayout huabu;
	//重置选项框
	private RelativeLayout restart_background;
	//设置选项框
	private LinearLayout shezhi_background;
	//i框
	private LinearLayout main_i_background;
	//创建布局使用
	private RelativeLayout.LayoutParams rp;
	@SuppressLint("UseSparseArrays")
	//样式
	private Map<Integer,Integer> map_drawable =new HashMap<Integer, Integer>();
	//即将隐藏的方块
	private List<Integer> fn;
	//即将移动且变大的方块
	private List<Integer> fm;
	//隐藏的方块
	private List<Integer> fnList;
	//空格子
	private List<Integer> konggezi;
	//格子对应方块
	@SuppressLint("UseSparseArrays")
	private Map<Integer,Fangkuai_Y> map;
	//判断手势
	private float x_tmp1,y_tmp1,x_tmp2,y_tmp2;
	//手势滑动结束判断
	private int xy;
	//移动动画
	private AnimatorSet anim=new AnimatorSet();
	//缩放动画
	private AnimatorSet anim2=new AnimatorSet();
	//动画结束判断
	private boolean panduan=false;
	//滑动速度
	private int sudu=1;
	//按返回键间隔
	private long exitTime = 0;
	//判断是否有设置框打开
	private boolean panduan_shezhi=false;
	//判断游戏框布局4x4或5x5
	private int daxiao=5;
	
	
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
		bujuOnCreat();
		//格子创建
		geziCreat(daxiao);
		//创建数字方块
		fangkuaionCreat(daxiao);
		//开始重置
		restart(daxiao);
		
		
	}
	/*
	 * 按2次返回键退出
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		 //保存最高分
		 util.saveZuigaofen(this, Integer.parseInt(zuigaofen.getText().toString()), Const.FILE_NAME[daxiao-4]);
		 if(panduan_shezhi){
			 shezhi_background.setVisibility(View.GONE);
			 restart_background.setVisibility(View.GONE);
			 main_i_background.setVisibility(View.GONE);
			 panduan_shezhi=false;
			 return true; 
		 }else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {  
	  
	            if ((System.currentTimeMillis() - exitTime) > 2000) {  
	                Toast.makeText(getApplicationContext(), "再按一次退出2048",  
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
		//游戏框滑动监听
		huabu.setOnTouchListener(this);
		//重置按钮点击事件监听
		restart.setOnClickListener(this);
		//确定框按钮yes
		btn_yes.setOnClickListener(this);
		//确定框按钮no
		btn_no.setOnClickListener(this);
		//速度改变按钮监听
		jia.setOnClickListener(this);
		jian.setOnClickListener(this);
		//设置按钮监听
		shezhi.setOnClickListener(this);
		//设置框按钮监听
		moshi_5x5jd.setOnClickListener(this);
		//设置框按钮监听
		moshi_4x4jd.setOnClickListener(this);
		//i按钮监听
		main_i.setOnClickListener(this);
		//速度初始
		sudu=Integer.parseInt(sd.getText().toString());
		//方块背景对应
		for(int i=0,n=2;i<17;i++,n*=2){
			map_drawable.put(n, Const.fdrawable[i]);
		}
	}
	
	/*
	 * 导入控件
	 */
	private void initView() {
		wh(daxiao);
		//游戏框
		huabu=(RelativeLayout) findViewById(id.huabu);
		//重置选项框
		restart_background=(RelativeLayout)findViewById(R.id.restart_background);
		//设置选项框
		shezhi_background=(LinearLayout) findViewById(R.id.shezhi_background);
		//i选项框
		main_i_background=(LinearLayout) findViewById(R.id.main_i_background);
		//重置按钮
		restart=(Button) findViewById(id.restart);
		//确定框按钮yes
		btn_yes=(Button) findViewById(id.btn_restart_yes);
		//确定框按钮no
		btn_no=(Button) findViewById(id.btn_restart_no);
		//速度加减按钮
		jia=(Button)findViewById(R.id.jia);
		jian=(Button)findViewById(R.id.jian);
		//设置按钮
		shezhi=(Button) findViewById(R.id.btn_shezhi);
		//设置框按钮
		moshi_4x4jd=(Button) findViewById(R.id.moshi_4x4jd);
		//设置框按钮
		moshi_5x5jd=(Button) findViewById(R.id.moshi_5x5jd);
		//i按钮
		main_i=(Button) findViewById(R.id.main_i);
		//速度数字
		sd=(TextView) findViewById(id.sudu);
		//分数数字
		fenshu=(TextView) findViewById(id.fenshu);
		//最高分数字
		zuigaofen=(TextView) findViewById(id.zuigaofen);
	}
	/*
	 * 边长和边距确定
	 */
	private void wh(int daxiao){
		if(daxiao==4){
			//方块边长
			fwidth=(width-width/20)*2/9;
			//间距
			fmargin=(width-width/20)/9/5;
		}else{
			//方块边长
			fwidth=(width-width/20)*2/9*4/5;
			//间距
			fmargin=(width-width/20)/9/6;
		}
	}
	/*
	 * 重置
	 */
	private void restart(int daxiao){
		//最高分读取
		zuigaofen.setText(util.loadzuigaofen(this, Const.FILE_NAME[daxiao-4]));
		//边长，边距
		wh(daxiao);
		for(int i=1;i<=5;i++){
			for(int j=1;j<=5;j++){
				TextView t=(TextView) findViewById(i*10+j);
				t.setVisibility(View.GONE);
			}
		}
		//格子重置
		geziCreat(daxiao);
		//得分清零
		fenshu.setText(0+"");
		//格子与方块对应清除
		map=new HashMap<Integer, Fangkuai_Y>();
		
		fn=new ArrayList<Integer>();
		fm=new ArrayList<Integer>();
		//未显示的格子id清除
		fnList =new ArrayList<Integer>();
		//空格子重置为16个或25个
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
			//导入未显示集合
			fnList.add(100+i);
		}
		//25个方块隐藏
		for(int i=1;i<=25;i++){
			//方块实例
			TextView tx = (TextView) findViewById(100+i);
			if(i<=8||i>20){
				//数字
				tx.setText("2");
				//背景样式
				tx.setBackgroundResource(Const.fdrawable[0]);
			}
			else{
				//数字
				tx.setText("4");
				//背景样式
				tx.setBackgroundResource(Const.fdrawable[1]);
			}
			//相对布局参数 宽高
			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
			//设置相对布局位置
			rp.addRule(RelativeLayout.ALIGN_LEFT,11);
			rp.addRule(RelativeLayout.ALIGN_BOTTOM,11);
			
			tx.setLayoutParams(rp);
			//数字颜色
			tx.setTextColor(0xff7c7268);
			//数字大小
			tx.setTextSize(Const.textsize[daxiao-4][0]);
			
			//隐藏
			tx.setVisibility(View.INVISIBLE);
		}
		//开始显示2个格子
		suiji(2);
	}
	/*
	 * 创建25个空格子,控制布局大小
	 */
	private void bujuOnCreat(){
		//游戏框大小设定
		RelativeLayout.LayoutParams linearParams =  (RelativeLayout.LayoutParams)huabu.getLayoutParams();
	 	linearParams.height = width-width/20;
	 	linearParams.width = width-width/20;
	    huabu.setLayoutParams(linearParams);
	    //确定框大小设置
	    FrameLayout.LayoutParams params=(LayoutParams) restart_background.getLayoutParams();
	    params.height=width-width/20;
	    params.width = width-width/20;
	    restart_background.setBackgroundColor(0xaaffffff);
	    restart_background.setLayoutParams(params);
	   //设置框大小设置
	    FrameLayout.LayoutParams params1=(LayoutParams) shezhi_background.getLayoutParams();
	    params1.height=width-width/20;
	    params1.width = width-width/20;
	    shezhi_background.setBackgroundColor(0xaaffffff);
	    shezhi_background.setLayoutParams(params1);
	  //i框大小设置
	    FrameLayout.LayoutParams params2=(LayoutParams) main_i_background.getLayoutParams();
	    params2.height=width-width/20;
	    params2.width = width-width/20;
	    main_i_background.setBackgroundColor(0xaaffffff);
	    main_i_background.setLayoutParams(params2);
	    
		for(int i=1;i<=25;i++){
			TextView tx=new TextView(this);
			// id与空格子List初始化
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
	 * 格子创建
	 */
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void geziCreat(int daxiao) {
		//行
        for(int i=1;i<=daxiao;i++){
        	//列
        	for(int j=1;j<=daxiao;j++){
        		TextView text=(TextView) findViewById(i*10+j);
        		//相对布局参数 宽高
    			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
            	
    			//设置外边距
            	if(i==daxiao&&j==1){
            		rp.setMargins(fmargin, fmargin, 0,fmargin);
            	}else if(j==1){
            		rp.setMargins(fmargin, fmargin, 0, 0);
            	}else if(j!=daxiao){
            		rp.setMargins(fmargin, 0, 0, 0);
            	}else {
            		rp.setMargins(fmargin, 0,fmargin, 0);
            	}
            	
            	//设置相对布局位置
            	if(j==1&&i!=1){
            		rp.addRule(RelativeLayout.BELOW,i*10-10+j);
            	}else{
            		rp.addRule(RelativeLayout.RIGHT_OF, i*10+j-1);
    				rp.addRule(RelativeLayout.ALIGN_TOP,i*10+j-1);
            	}
            	//背景样式
    			text.setBackgroundResource(R.drawable.fangkuai_b);
    			text.setText("");
            	text.setLayoutParams(rp);
            	text.setVisibility(View.VISIBLE);
            }
        }
        
        
		
	}
	
	/*
	 * 数字方块创建
	 */
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void fangkuaionCreat(int daxiao){
		
		for(int i=1;i<=25;i++){
			TextView tx = new TextView(this);
			
			//id
			tx.setId(100+i);
			if(i<=8||i>20){
				//数字
				tx.setText("2");
				//背景样式
				tx.setBackgroundResource(Const.fdrawable[0]);
			}
			else{
				//数字
				tx.setText("4");
				//背景样式
				tx.setBackgroundResource(Const.fdrawable[1]);
			}
			//数字颜色
			tx.setTextColor(0xff7c7268);
			//数字居中
			tx.setGravity(Gravity.CENTER);
			//数字大小
			tx.setTextSize(Const.textsize[daxiao-4][0]);
			//字体样式
			Typeface face = Typeface.MONOSPACE;
			tx.setTypeface(face);
			//加粗
			TextPaint tp = tx.getPaint(); 
			tp.setFakeBoldText(true); 
			
			//相对布局参数 宽高
			rp = new RelativeLayout.LayoutParams(fwidth,fwidth);
			
			//设置相对布局位置
			rp.addRule(RelativeLayout.ALIGN_LEFT,11);
			rp.addRule(RelativeLayout.ALIGN_BOTTOM,11);
			
			tx.setLayoutParams(rp);

			//隐藏
			tx.setVisibility(View.INVISIBLE);
			
			huabu.addView(tx);

		}
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
				
				//获得格子id和方块ID
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
				//格子满了
			}

		}
	}
	/*
	 * 滑动方向判定
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//判断是否有设置框打开
		if(!panduan_shezhi){
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
				//保存最高分
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
	 * 滑动解析
	 */
	private void move(String fangxiang){
		if(xy==1 && panduan){
			panduan=false;
			int jishu=0;
			String str="shu";
			if(fangxiang.equals("zuo")||fangxiang.equals("you")){
				str="heng";
			}
			//行
			for(int i=1;i<=daxiao;i++){
				//列
				for(int j=2;j<=daxiao;j++){
					//判断第j个格子是否为空
					if(map.get(zhuanhuan(fangxiang, i, j))!=null){
						//列之前
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
	 * 格子id转换
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
			fangkuaisuofang((TextView)findViewById(fy.getId()));
			
			
		}
		
		//判断移动方向，更新方块坐标
		if(str.equals("heng")){
			//动画创建
			animatorOnCreater(str, fy, first%10,(50+(10-sudu)*10)*Math.abs(first-second));
			map.get(first).setX((fwidth+fmargin)*(first%10-1));
			
			
		}else{
			//动画创建
			animatorOnCreater(str, fy, first/10,(50+(10-sudu)*10)*(Math.abs(first-second)/10));
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
			textn.setTextSize(Const.textsize[daxiao-4][0]);
			//还原样式
			if(id<=108||id>120){
				//数字
				textn.setText("2");
				//背景样式
				textn.setBackgroundResource(Const.fdrawable[0]);
			}
			else{
				//数字
				textn.setText("4");
				//背景样式
				textn.setBackgroundResource(Const.fdrawable[1]);
			}
			fnList.add(id);
		}
		fn.clear();
		
		
		int defen=0;
		for(int j:fm){
			//获取显示方块实例
			TextView texty=(TextView)findViewById(j);
			//获得显示方块数字
			int shuzi=Integer.parseInt(texty.getText().toString());
			//改变背景
			texty.setBackgroundResource((int)map_drawable.get(shuzi*2));
			//改变数字
			texty.setText(shuzi*2+"");
			//数字居中
			texty.setGravity(Gravity.CENTER);
			if(shuzi>=4)texty.setTextColor(0xffffffff);
			if(shuzi>=8)texty.setTextSize(Const.textsize[daxiao-4][1]);
			if(shuzi>=64)texty.setTextSize(Const.textsize[daxiao-4][2]);
			if(shuzi>=512)texty.setTextSize(Const.textsize[daxiao-4][3]);
			if(shuzi>=8192)texty.setTextSize(Const.textsize[daxiao-4][4]);
			if(shuzi>=65536)texty.setTextSize(Const.textsize[daxiao-4][5]);
			//累加得分
			defen+=shuzi*2;
		}
		//加上得分
		fenshu.setText(defen+Integer.parseInt(fenshu.getText().toString())+"");
		if(Integer.parseInt(zuigaofen.getText().toString())<Integer.parseInt(fenshu.getText().toString())){
			zuigaofen.setText(fenshu.getText());
			fangda(zuigaofen);
		}
		//临时集合清楚
		fm.clear();
		//缩放动画
		fangda(fenshu);
		anim2.setDuration((10-sudu)*10+50);
		anim2.start();
		anim2=new AnimatorSet();
	}

	/*
	 * 移动动画创建
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
	
	/*
	 * 普通缩放动画创建并执行(放大)
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
	 * 普通缩放动画创建并执行(缩小)
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
	 * 移动方块的缩放动画创建
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
