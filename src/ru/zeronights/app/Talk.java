package ru.zeronights.app;

public class Talk implements Comparable<Talk>{
	
	int _id;
	String _name;
	String _description;
	String _speakers;
	int _type;
	int _track;
	String _time;
	int _day;

	public Talk(int id,String name, String desc,String speakers,int type,int track, int day, String time){
		this._id = id;
		this._name = name;
		this._description = desc;
		this._speakers = speakers;
		this._type = type;
		this._track = track;
		this._day = day;
		this._time = time;
	}
	
	public int getId(){
		return this._id;
	}

	public String getHeader(){
		return this._name;
	}
	
	public String getBody(){
		return this._description;
	}
	
	public String getSpeakers(){
		return this._speakers;
	}
	
	public String getTime(){
		return this._time;
	}

	public int compareTo(Talk another) {
		
		String[]  time_now = this.getTime().split(":");
		String[]  time_another =  ((Talk) another).getTime().split(":");
		int hour_now = Integer.parseInt(time_now[0]);
		int min_now = Integer.parseInt(time_now[1]);
		int hour_another = Integer.parseInt(time_another[0]);
		int min_another = Integer.parseInt(time_another[1]);
		
		if (hour_now > hour_another)
			return 1;
		else if (hour_now < hour_another)
			return -1;
		else if (hour_now == hour_another)
			if (min_now > min_another)
				return 1;
			else if (min_now < min_another)
				return -1;
			else	
				return 0;
		return 0;
	}
}
