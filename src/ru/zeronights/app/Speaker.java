package ru.zeronights.app;

public class Speaker {

	int _id;
	String _name;
	String _bio;
	int _talk;
	String _photo;
	
	public Speaker(int id, String name, String bio, int talk, String photo){
		this._id = id;
		this._name = name;
		this._bio = bio;
		this._talk = talk;
		
	 if (photo == null )
	        	this._photo = "zalipukha.jpg";
else
			 this._photo = photo;
			 
		this._photo = photo;
	}
	
	public String getName(){
		return this._name;
	}
	public void setName(String name){
		this._name = name;
	}
	
	public String getBio(){
		return this._bio;
	}
	
	public void setBio(String bio){
		this._bio = bio;
	}
		
	public int getTalk(){
		return this._talk;
	}
	
	public String getPhoto(){
		return this._photo;
	}
	
}
