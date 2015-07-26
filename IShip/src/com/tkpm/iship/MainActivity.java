package com.tkpm.iship;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.drawable.*;
import android.view.View.*;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
		init();
		runner();
    }
	
	public void finishme(){
		this.finish();
	}
	
	//SETTINGS
	public static final int MAX_REPS = 3;
	public static final int MAX_WORK = 2;
	
	private class cards
	{
		TextView mymessage;
		TextView[] myreps = new TextView[MAX_REPS];
		String[] acts = new String[MAX_REPS];
		String chapter = "";
		private Handler mHandler = new Handler();
		
		//funcs simpliest
		boolean showmessage(){
			int i = getResources().getIdentifier("msg" + chapter,"string", getPackageName());
			if(i==0) return false;	
			mymessage.setText(i);
			mymessage.setVisibility(View.VISIBLE);
			return true;
		}
		
		boolean showreps(){
			int i = getResources().getIdentifier("rep" + chapter, "string", getPackageName());
			if(i==0) return false;
			String[] work = new String[MAX_WORK];
			String[] reps = getString(i).split("@@", MAX_REPS);
			for (i=0; i<MAX_REPS && i<reps.length; i++){
				work=reps[i].split("@",MAX_WORK);
				myreps[i].setText(work[0]); //TODO link to function drawreps
				myreps[i].setVisibility(View.VISIBLE); //
				if(work.length>1) acts[i]=work[1];
			}
			return true;
		}
		
		boolean showreps(int[] where){
			int i = getResources().getIdentifier("rep" + chapter, "string", getPackageName());
			if(i==0) return false;
			String[] work = new String[MAX_WORK];
			String[] reps = getString(i).split("@@", MAX_REPS);
			for (i=0; i<MAX_REPS && i<where.length && i<reps.length; i++){
				work=reps[i].split("@",MAX_WORK);
				myreps[where[i]].setText(work[0]); //TODO link to function drawreps
				myreps[where[i]].setVisibility(View.VISIBLE); //
				if(work.length>1) acts[where[i]]=work[1];
			}
			return true;
		}

		boolean action(){ //wait timer and next chapter
			int i = getResources().getIdentifier("act" + chapter, "string", getPackageName());
			if(i==0) return false;
			String[] work = new String[MAX_WORK];
			work = getString(i).split("@",MAX_WORK);
			//myreps[0].setText(work[1].replaceAll("[\\D]",""));
			chapter = work[0];
			//if(work.length<2) return true;
			i=Integer.parseInt(work[1].replaceAll("[\\D]","")); //wait time in seconds
			try {
				Thread.sleep(1000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			return true;
		}
		
		//more options
		boolean showmessage(String id){
			int i = getResources().getIdentifier("msg" + id,"string", getPackageName());
			if(i==0) return false;	
			mymessage.setText(i);
			mymessage.setVisibility(View.VISIBLE);
			return true;
		}

		boolean showreps(String id){
			int i = getResources().getIdentifier("rep" + id, "string", getPackageName());
			if(i==0) return false;
			String[] work = new String[MAX_WORK];
			String[] reps = getString(i).split("@@", MAX_REPS);
			for (i=0; i<MAX_REPS && i<reps.length; i++){
				work=reps[i].split("@",MAX_WORK);
				myreps[i].setText(work[0]); //TODO link to function drawreps
				myreps[i].setVisibility(View.VISIBLE); //
				if(work.length>1) acts[i]=work[1];
			}
			return true;
		}
		
		boolean showreps(String id, int[] where){
			int i = getResources().getIdentifier("rep" + id, "string", getPackageName());
			if(i==0) return false;
			String[] work = new String[MAX_WORK];
			String[] reps = getString(i).split("@@", MAX_REPS);
			for (i=0; i<MAX_REPS && i<where.length && i<reps.length; i++){
				work=reps[i].split("@",MAX_WORK);
				myreps[where[i]].setText(work[0]); //TODO link to function drawreps
				myreps[where[i]].setVisibility(View.VISIBLE); //
				if(work.length>1) acts[where[i]]=work[1];
			}
			return true;
		}
		
		boolean action(String id){ //wait timer and next chapter
			int i = getResources().getIdentifier("act" + id, "string", getPackageName());
			if(i==0) return false;
			String[] work = new String[MAX_WORK];
			//myreps[0].setText(getString(i));
			//myreps[0].setVisibility(View.VISIBLE);
			work = getString(i).split("@",MAX_WORK);
			chapter="";
			chapter = work[0];
			if(work.length<2) return true;
			i=Integer.parseInt(work[1].replaceAll("[\\D]","")); //wait time in seconds
			//blankall();
			/*try
			{
				wait(1000);
			}
			catch (InterruptedException e)
			{
				finish();
			}*/
			
			/*try
 			{
				Thread.sleep(i*1000);                 //1000 milliseconds is one second.
				
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}//*/
			mHandler.postDelayed(new Runnable() {
					public void run() {
						runner();
					}
				}, i*1000);

			return false;
		}
	}
	
	//flow
	public cards mycard = new cards(); //this is were we keep current stuff
	
	private void init (){
		mycard.chapter="A1";
		mycard.mymessage = (TextView) findViewById(R.id.mainMessage);
		mycard.myreps[0] = (TextView) findViewById(R.id.FirstRep);
		mycard.myreps[1] = (TextView) findViewById(R.id.SecondRep);
		mycard.myreps[2] = (TextView) findViewById(R.id.ThirdRep);
		/*OnClickListener mOCL = new OnClickListener(){
			@Override
			public void onClick(View V) {
				if(!mycard.acts[0].isEmpty()) mycard.chapter=mycard.acts[0];
				Runnable mr = new Runnable() {public void run () {runner();}};
				runOnUiThread(mr);
			}
		};
	
		mycard.myreps[0].setOnClickListener(mOCL);*/
	}
	
	private void blankall(){
		mycard.mymessage.setVisibility(View.INVISIBLE);
		for (int i=0; i<MAX_REPS; i++){
			mycard.myreps[i].setVisibility(View.INVISIBLE);
			mycard.acts[i]="";
		}
	}
	
	public void onRepClick(View v){
		int i = 0;
		switch(v.getId()){
			case R.id.FirstRep:
				i = 0;
				break;
			case R.id.SecondRep:
				i = 1;
				break;
			case R.id.ThirdRep:
				i = 2;
				break;
		}
		if(!mycard.acts[i].isEmpty()) mycard.chapter=mycard.acts[i];
		runner();
	}
	
	private void runner(){ //return: repeat?
		//while(mycard.chapter != "" && mycard.chapter != "end"){
		if(mycard.chapter.equals("end")) finish();
		else {
			blankall();
			mycard.showmessage();
			if(mycard.showreps()) return;
			if(!mycard.action(mycard.chapter)) return;
		} 
	}
};//MainActivity


