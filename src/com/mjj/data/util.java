package com.mjj.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class util {
	
	/**
	 * 最高分保存
	 * @param context
	 * @param zuigaofen
	 */
	public static void saveZuigaofen(Context context,int zuigaofen,String name){
		FileOutputStream file=null;
		try {
			file=context.openFileOutput(name, Context.MODE_PRIVATE);
			DataOutputStream dos=new DataOutputStream(file);
			
			dos.writeInt(zuigaofen);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 读取最高分
	 * @param context
	 * @param name
	 * @return
	 */
	public static String loadzuigaofen(Context context,String name){
		FileInputStream file=null;
		int zuigaofen=0;
		try {
			file=context.openFileInput(name);
			DataInputStream dis=new DataInputStream(file);
			
			zuigaofen=dis.readInt();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return zuigaofen+"";
	}
}
