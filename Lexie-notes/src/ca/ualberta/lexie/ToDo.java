package ca.ualberta.lexie;


public class ToDo {
	//this class is loosely based off of the lonely twitter class
	//LonelyTweetModel.java
	//https://github.com/lexiebartlettt/lonelyTwitter/blob/f14io/src/ca/ualberta/cs/lonelytwitter/LonelyTweetModel.java
	//taken Monday September 22 2014
	private String toDo;
	private Boolean isChecked;
	//returns the toDo string
	public String getString(){
		return toDo;
	}
	//creates a new toDo with the string given
	//since no isChecked is given it automatically sets it to false
	public ToDo(String text){
		super();
		this.toDo=text;
		this.isChecked=false;
	}
	//does the same as the previous ToDo except a boolean is passed
	//to the function and set for isChecked
	public ToDo(String text, Boolean isChecked){
		super();
		this.toDo=text;
		this.isChecked=isChecked;
	}
	//sets isChecked as true
	public void setTrue(){
		this.isChecked=true;
	}
	//sets isChecked as false
	public void setFalse(){
		this.isChecked=false;
	}
	//sets isChecked to the boolean past to the function
	public void Check(Boolean check){
		this.isChecked=check;
	}
	//does the same as getString
	//Used for the arrayAdapter
	public String toString(){
		return toDo;
	}
	//returns isChecked
	public Boolean getCheck(){
		return isChecked;
	}
}