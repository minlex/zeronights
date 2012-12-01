package ru.zeronights.app;

import java.util.ArrayList;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import android.content.Context;
import android.text.format.Time;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final List<Talk> values;

  public MyArrayAdapter(Context context, List<Talk> values, List<String> values2) {   
    super(context, R.layout.rowlayout, values2);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.Time);
    textView.setText(values.get(position).getTime());
    textView = (TextView) rowView.findViewById(R.id.Topic);
    textView.setText(values.get(position).getHeader());
    textView = (TextView) rowView.findViewById(R.id.Speaker);
    textView.setText(values.get(position).getSpeakers());
    
   /*Calendar c = Calendar.getInstance();
   int hour  = c.get(Calendar.HOUR);
   int minute = c.get(Calendar.SECOND);
   String time = values.get(position).getTime();
   int talk_hour = Integer.parseInt(time.split(":")[0]);
   int talk_min = Integer.parseInt(time.split(":")[1]);
   if (position != values.size()){
	   String next_time = values.get(position+1).getTime();
	   int next_hour = Integer.parseInt(next_time.split(":")[0]);
	   int next_min = Integer.parseInt(next_time.split(":")[1]);
	   if (hour == talk_hour){
		   if ((minute >= talk_min) && (minute<=next_min))
			   rowView.setBackgroundColor(0xC70B00);
		   textView = (TextView) rowView.findViewById(R.id.Topic);
		   		textView.setTextColor(0xC70B00);
	   }}
	   else	{
		   if (hour == talk_hour){
			   if ((minute >= talk_min))
				   rowView.setBackgroundColor(0xC70B00);
		   }
		   }
  
   */
   
    
    return rowView;
  }
} 