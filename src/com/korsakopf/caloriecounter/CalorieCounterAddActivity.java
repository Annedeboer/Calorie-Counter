package com.korsakopf.caloriecounter;

import java.sql.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.korsakopf.caloriecounter.database.CalorieCounterDbAdapter;

public class CalorieCounterAddActivity extends Activity {

	CalorieCounterDbAdapter db = new CalorieCounterDbAdapter(this);

	EditText textNutName;
	EditText textNutGram;
	EditText textNutCal;
	EditText textNutProt;

	DatePicker DatePicker;
	Date date;
	int year;
	int month;
	int day;
	String stringDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);

		// Button resources verbinden aan Button object en listener toevoegen
		Button buttonAddNutValues = (Button) findViewById(R.id.buttonAddNutValues);
		buttonAddNutValues.setOnClickListener(buttonAddNutValuesListener);

		Button buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonBack.setOnClickListener(buttonBackListener);
	}

	// Voeg aan de tabel toe als op de 'buttonAdd' wordt geklikt
	private OnClickListener buttonAddNutValuesListener = new OnClickListener() {
		public void onClick(View v) {
			db.open();
			long id = 0;
			try {
				// Datum van de datePicker widget omzetten naar een String
				DatePicker = (DatePicker) findViewById(R.id.datePicker);
				year = DatePicker.getYear();
				month = DatePicker.getMonth() + 1;
				day = DatePicker.getDayOfMonth();
				Date date = new Date((year - 1900), month, day);
				String stringDate = date.toString();
				long longDate = date.getTime();
				String stringDate2 = "" + (day) + (month);
				int intDate = Integer.parseInt(stringDate2);

				// Edittext fields verbinden aan hun resource
				textNutName = (EditText) findViewById(R.id.textNutName);
				textNutGram = (EditText) findViewById(R.id.textNutGram);
				textNutCal = (EditText) findViewById(R.id.textNutCal);
				textNutProt = (EditText) findViewById(R.id.textNutProt);

				// Controle of alle velden zijn ingevuld, zo niet dan een Toast melding
				if (textNutName.getText().toString().equals("")
						|| textNutGram.getText().equals("")
						|| textNutCal.getText().equals("")
						|| textNutProt.getText().equals("")) {
					CharSequence ToastFieldsEmpty = "Vul alle velden in";
					Context context = getApplicationContext();
					int duur = Toast.LENGTH_LONG;
					Toast toast2 = Toast.makeText(context, ToastFieldsEmpty, duur);
					toast2.show();
				} else {

					// Roep de 'insertMultiple' method uit de DbAdapter aan
					db.insertMultiple(intDate,
							textNutName.getText().toString(), Double
									.parseDouble(textNutGram.getText()
											.toString()), Double
									.parseDouble(textNutCal.getText()
											.toString()), Double
									.parseDouble(textNutProt.getText()
											.toString()));

					// Toast message die aangeeft wat en hoeveel is toegevoegd
					id = db.getAllEntries();
					Context context = getApplicationContext();
					CharSequence text = "Het etenswaar '"
							+ textNutName.getText()
							+ "' is succesvol toegevoegd\nTotaal aantal rijen in de database is nu: "
							+ id;
					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();

					// De textvelden weer leegmaken na het toevoegen aan de
					// database
					textNutName.setText("");
					textNutGram.setText("");
					textNutCal.setText("");
					textNutProt.setText("");
				}
			} catch (Exception ex) {
			}
			db.close();
		}

	};

	// Terug naar de MainActivity als op de 'buttonBack' wordt gedrukt
	private OnClickListener buttonBackListener = new OnClickListener() {
		public void onClick(View v) {
			Intent myIntent = new Intent(CalorieCounterAddActivity.this,
					CalorieCounterMainActivity.class);
			CalorieCounterAddActivity.this.startActivity(myIntent);
		}
	};

}
