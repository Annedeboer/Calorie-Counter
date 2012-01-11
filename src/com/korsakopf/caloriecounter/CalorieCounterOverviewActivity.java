package com.korsakopf.caloriecounter;

import java.util.Arrays;

import com.androidplot.xy.XYPlot;
import android.graphics.Color;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import com.korsakopf.caloriecounter.database.CalorieCounterDbAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

public class CalorieCounterOverviewActivity extends Activity{

	CalorieCounterDbAdapter db = new CalorieCounterDbAdapter(this);
	
	private XYPlot mySimpleXYPlot;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview);
		
		db.open();
		
		/*
		// POGING EEN GRAFIEK TE MAKEN
	      
        // Initialize our XYPlot reference:
        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
 
        // Create two arrays of y-values to plot:
        Number[] caloriesNumbers = db.getCalForGraph();
        Number[] protienNumbers = db.getProtForGraph();
        
        String[] date = { "dag1", "dag2", "dag3" };
        
        int[] date2 = {1, 2, 3};
        
        Number[] date3 = {1, 2, 3};
 
        // Turn the above arrays into XYSeries:
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(caloriesNumbers), Arrays.asList(caloriesNumbers),          // SimpleXYSeries takes a List so turn our array into a List
                "Series1");                             // Set the display title of the series
 
        // Same as abovwele, for series2
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(protienNumbers), SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, 
                "Series2");
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                Color.rgb(150, 190, 150));              // fill color (optional)
 
        // Add series1 to the xyplot:
        mySimpleXYPlot.addSeries(series1, series1Format);
 
        // Same as above, with series2:
        mySimpleXYPlot.addSeries(series2, new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 100),
                Color.rgb(150, 150, 190)));
 
 
        // Reduce the number of range labels
        mySimpleXYPlot.setTicksPerRangeLabel(3);
 
        // By default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():
        mySimpleXYPlot.disableAllMarkup();
        
        // EINDE GRAFIEK POGING 
		*/
		
		
		
		// Oude weergave kcal en eiwitten in kolommen
	
	

	     
		
	      ListView listContent = (ListView)findViewById(R.id.contentlist);
			
	      Cursor cursor = db.getOverviewDate();
	      startManagingCursor(cursor);
	      
	      final String[] columns = { CalorieCounterDbAdapter.C_DATE, CalorieCounterDbAdapter.C_CAL, CalorieCounterDbAdapter.C_PROT};
	      int[] to = new int[]{R.id.date, R.id.calories, R.id.protien};
	     
	      SimpleCursorAdapter cursorAdapter =
	       new SimpleCursorAdapter(this, R.layout.row, cursor, columns, to);

	      listContent.setAdapter(cursorAdapter);
	    
	      db.close();
	      
	}
}
