package edu.sc.cec.cse.button.masher;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

	Random rnd;
	TextView timerView;
	TextView clicksView;
	Timer timer = new Timer();
	boolean go=false;
	float time=0;
	int clicks=0;

    class timerTask extends TimerTask {
        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
   };
	
	final Handler h = new Handler(new Callback() {
		public boolean handleMessage(Message msg) {
			timerView.setText(String.format("%1.1fs",time));
			time+=0.5;
			if(time>10) {
				stopTimer();
			}
            return false;
        }
    });
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        rnd = new Random();
        timerView = (TextView) findViewById(R.id.timerView);
        clicksView = (TextView) findViewById(R.id.clicksView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void buttonClicked(View view) {
    	RelativeLayout parent = (RelativeLayout) view.getParent();
    	int left = rnd.nextInt(parent.getWidth()-50);
    	int top = rnd.nextInt(parent.getHeight()-50);
    	RelativeLayout.LayoutParams param = (LayoutParams) view.getLayoutParams();  
    	param.leftMargin = left;
    	param.topMargin = top;
    	view.setLayoutParams(param);
    	if(!go) {
    		startTimer();
    	}
    	clicks++;
		clicksView.setText(String.format("%d",clicks)+" clicks");
    }
    
    public void startTimer() {
    	go=true;
        time=0;
        clicks=0;
    	timer = new Timer();
        timer.schedule(new timerTask(),0,500);
    }
    
    public void stopTimer() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Finished");
        alertDialog.setMessage("You managed "+String.valueOf(clicks)+" clicks");
        alertDialog.show();
    	go=false;
		timer.cancel();
        timer.purge();
    }

}
