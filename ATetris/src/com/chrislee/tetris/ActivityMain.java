package com.chrislee.tetris;
// Author: ChrisLee
// 2010.3

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ActivityMain extends Activity {
	
	public static final int FLAG_NEW_GAME = 0;
	public static final int FLAG_CONTINUE_LAST_GAME = 1;
	
	public static final String FILENAME = "settingInfo";
	public static final String LEVEL = "level";
	public static final String VOICE = "voice";
	
	
	private int mLevel = 1;
	
	private Button btNewgame = null;
	private Button btContinue = null;
	private Button btHelp = null;
	private Button btRank = null;
	private Button btPre = null;
	private Button btNext = null;
	private Button btExit = null;
	
	private TextView tvLevel = null;
	private CheckBox cbVoice = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        btNewgame = (Button)findViewById(R.id.bt_new);
        btContinue = (Button)findViewById(R.id.bt_continue);
        btHelp = (Button)findViewById(R.id.bt_help);
        btRank = (Button)findViewById(R.id.bt_rank);
        btPre = (Button)findViewById(R.id.bt_pre);
        btNext = (Button)findViewById(R.id.bt_next);
        btExit = (Button)findViewById(R.id.bt_exit);
        
        tvLevel = (TextView)findViewById(R.id.tv_speed);
       
        cbVoice = (CheckBox)findViewById(R.id.cb_voice);
        
        btNewgame.setOnClickListener(buttonListener);
        btContinue.setOnClickListener(buttonListener);
        btHelp.setOnClickListener(buttonListener);
        btRank.setOnClickListener(buttonListener);
        btPre.setOnClickListener(buttonListener);
        btNext.setOnClickListener(buttonListener);
        btExit.setOnClickListener(buttonListener);
        restoreSettings();
    }
    
    private Button.OnClickListener buttonListener = new Button.OnClickListener()
    {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_new:
				Intent intent1 = new Intent(ActivityMain.this,ActivityGame.class);
				intent1.setFlags(FLAG_NEW_GAME);
				intent1.putExtra(VOICE,cbVoice.isChecked());
				intent1.putExtra(LEVEL,mLevel);
				startActivity(intent1);
				break;
			case R.id.bt_continue:
				Intent intent2 = new Intent(ActivityMain.this,ActivityGame.class);
				intent2.setFlags(FLAG_CONTINUE_LAST_GAME);
				intent2.putExtra(VOICE,cbVoice.isChecked());
				startActivity(intent2);
				break;
			case R.id.bt_help:
				Intent intent3 = new Intent(ActivityMain.this,ActivityHelp.class);
				startActivity(intent3);
				break;
			case R.id.bt_next:
				btNext.setBackgroundColor(0xffc0c0c0);
				String s1 = tvLevel.getText().toString();
				int level1 = Integer.parseInt(s1);
				--level1;
				level1 = (level1+1) % TetrisView.MAX_LEVEL;
				++level1;
				s1 = String.valueOf(level1);
				tvLevel.setText(s1);
				mLevel = level1;
				btNext.setBackgroundColor(0x80cfcfcf);
				break;
			case R.id.bt_pre:
				btPre.setBackgroundColor(0xffc0c0c0);
				String s2 = tvLevel.getText().toString();
				int level2 = Integer.parseInt(s2);
				--level2;
				level2 = (level2-1+TetrisView.MAX_LEVEL) % TetrisView.MAX_LEVEL;
				++level2;
				s2 = String.valueOf(level2);
				tvLevel.setText(s2);
				mLevel = level2;
				btPre.setBackgroundColor(0x80cfcfcf);
				break;
			case R.id.bt_exit:
				ActivityMain.this.finish();
				break;
			case R.id.bt_rank:
				Intent intent4 = new Intent(ActivityMain.this,ActivityRank.class);
				startActivity(intent4);
				break;
			}
		}
    };
    
    private void saveSettings()
    {
    	SharedPreferences settings = getSharedPreferences(FILENAME,0);
    	settings.edit()
    	.putInt(LEVEL,mLevel)
    	.putBoolean(VOICE,cbVoice.isChecked())
    	.commit();
    }
    
    private void restoreSettings()
    {
    	SharedPreferences settings = getSharedPreferences(FILENAME,0);
    	mLevel = settings.getInt(LEVEL,1);
    	boolean hasVoice = settings.getBoolean(VOICE,true);
    	tvLevel.setText(String.valueOf(mLevel));
    	cbVoice.setChecked(hasVoice);
    }
    
    public void onStop()
    {
    	super.onStop();
    	saveSettings();
    }
}