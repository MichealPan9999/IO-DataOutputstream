package com.pan.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.pan.io.utils.ShowLog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity
{

	private EditText et_write;
	private TextView tv_content;
	private String writeContent;
	private String readContent;
	//private static final String ipConfigFile = Environment.getDataDirectory() + "/misc/wifi/ipconfig.txt";
	private static String ipConfigFile;// = Environment.getExternalStorageDirectory()+"/misc/wifi";
	private static final int IPCONFIG_FILE_VERSION = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_write = (EditText) findViewById(R.id.et_write);
		tv_content = (TextView) findViewById(R.id.tv_show);
		ipConfigFile = getFilesDir().getAbsolutePath();
		/*File file = new File(ipConfigFile);
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			} catch (IOException e)
			{
				ShowLog.E(file+"文件创建失败");
				e.printStackTrace();
			}
		}*/
	}

	public void WriteTo(View v)
	{
		if (!TextUtils.isEmpty(et_write.getText().toString()))
		{
			writeContent = et_write.getText().toString();
			ShowLog.D(writeContent);
			onWriteCalled(writeContent);
		}

	}

	public void ReadFrom(View v)
	{
		DataInputStream in =null;
		try
		{
			in = new DataInputStream(new BufferedInputStream(new FileInputStream(ipConfigFile+"/ipconfig.txt")));
			int version = in.readInt();
			if (version != IPCONFIG_FILE_VERSION)
			{
				ShowLog.E("获取到版本不对");
				return;
			}
			String content = in.readUTF();
			if (!TextUtils.isEmpty(content))
			{
				readContent = content;
				tv_content.setText(readContent);
			}
			else
			{
				ShowLog.E("获取到内容为空");
			}
		} catch (FileNotFoundException e)
		{
			ShowLog.E("未找到该文件");
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (in!=null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private static void onWriteCalled(String content)
	{
		DataOutputStream out = null;
		try
		{
			File filedir = new File(ipConfigFile);
			if (!filedir.exists())
			{
				filedir.mkdir();
			}
			File file = new File(ipConfigFile,"ipconfig.txt");
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			out.writeInt(IPCONFIG_FILE_VERSION);
			if (!TextUtils.isEmpty(content))
			{
				out.writeUTF(content);
				out.flush();
			} else
			{
				ShowLog.E("内容不能为空");
			}
		} catch (FileNotFoundException e)
		{
			ShowLog.E("未找到文件 :" + ipConfigFile);
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
