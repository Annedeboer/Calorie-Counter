package com.korsakopf.caloriecounter;

import java.sql.Date;
import java.util.Calendar;

import com.androidplot.xy.XYPlot;
import android.graphics.Color;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import com.korsakopf.caloriecounter.database.CalorieCounterDbAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalorieCounterMainActivity extends Activity {
	long calories;
	String caloriesString;

	long protien;
	String protienString;

	Date todaysDate;
	int mYear;
	int mMonth;
	int mDay;
	
	

	CalorieCounterDbAdapter db = new CalorieCounterDbAdapter(this);

	// Maken van de eerste activity en de layout xml eraan koppelen
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// database openen
				db.open();
		
		setContentView(R.layout.main);

		// Button resources verbinden aan Button object en listener toevoegen
		Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
		buttonAdd.setOnClickListener(buttonListener);

		Button buttonOverview = (Button) findViewById(R.id.buttonOverview);
		buttonOverview.setOnClickListener(buttonListener);
		
	
	
		     	
	}

	// Als men teruggaat van een andere activity naar de MainActity kcal en prot
	// updaten
	@Override
	public void onResume() {
		super.onResume();
		db.open();
		// datum van vandaag achterhalen in er een string van maken
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		todaysDate = new Date((mYear - 1900), mMonth, mDay);
		String stringDate = todaysDate.toString();

		// test voor weergeven van calorien uit de database
		calories = db.getCalories(stringDate);
		TextView numberCalories = (TextView) findViewById(R.id.numberSummaryCal);
		caloriesString = String.valueOf(calories);
		numberCalories.setText(caloriesString);

		// test voor weergeven van proteinen uit de database
		protien = db.getProtien(stringDate);
		TextView numberProtien = (TextView) findViewById(R.id.numberSummaryProt);
		protienString = String.valueOf(protien);
		numberProtien.setText(protienString);
		
		if (db != null) {
			db.close();
		}

	}

	// Button listener click class, wat te doen als er op een button wordt
	// geklikt
	private OnClickListener buttonListener = new OnClickListener() {
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.buttonAdd:
				Intent myIntent = new Intent(CalorieCounterMainActivity.this,
						CalorieCounterAddActivity.class);
				CalorieCounterMainActivity.this.startActivity(myIntent);
				
				break;
			case R.id.buttonOverview:
				Intent myIntent2 = new Intent(CalorieCounterMainActivity.this,
						CalorieCounterOverviewActivity.class);
				CalorieCounterMainActivity.this.startActivity(myIntent2);
				break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (db != null) {
			db.close();
		}
	
	
		
	
	
	}
	
	
    
}