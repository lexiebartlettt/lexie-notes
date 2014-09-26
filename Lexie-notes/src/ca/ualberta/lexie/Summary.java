package ca.ualberta.lexie;


import android.os.Bundle;
import android.widget.TextView;


public class Summary extends MainActivity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary_view);
	}
	public void onStart(){
		super.onStart();
		//This sets up and prints total to dos
		TextView totalToDos=new TextView(this);
		totalToDos=(TextView)findViewById(R.id.totalTodos);
		int totalToDoNumber = MainActivity.toDoList.size();
		StringBuilder totalStringBuilder =new StringBuilder();
		totalStringBuilder.append("Total ToDos: ");
		totalStringBuilder.append(totalToDoNumber);
		String totalToDoString=totalStringBuilder.toString();
		totalToDos.setText(totalToDoString);
		int totalChecked=0;
		int totalUnchecked=0;
		for (int i=0; i<MainActivity.toDoList.size();i++){
			if (MainActivity.toDoList.get(i).getCheck() == true){
				totalChecked+=1;
			}
			else{
				totalUnchecked+=1;
			}
		}
		//this sets up and prints total checked to dos
		TextView totalCheck=new TextView(this);
		totalCheck=(TextView)findViewById(R.id.totalCheck);
		StringBuilder totalCheckStringBuilder =new StringBuilder();
		totalCheckStringBuilder.append("Total Checked ToDos: ");
		totalCheckStringBuilder.append(totalChecked);
		String totalCheckToDoString=totalCheckStringBuilder.toString();
		totalCheck.setText(totalCheckToDoString);
		//	this sets up and prints total unchecked to dos
		TextView totalUncheck=new TextView(this);
		totalUncheck=(TextView)findViewById(R.id.totalUncheck);
		StringBuilder totalUncheckStringBuilder =new StringBuilder();
		totalUncheckStringBuilder.append("Total Unchecked ToDos: ");
		totalUncheckStringBuilder.append(totalUnchecked);
		String totalUncheckToDoString=totalUncheckStringBuilder.toString();
		totalUncheck.setText(totalUncheckToDoString);
		//This sets up and prints total Archive count
		TextView totalArchive=new TextView(this);
		totalArchive=(TextView)findViewById(R.id.totalArchive);
		int totalArchiveNumber = MainActivity.mainarchived.size();
		StringBuilder totalArchiveStringBuilder =new StringBuilder();
		totalArchiveStringBuilder.append("Total Archived ToDos: ");
		totalArchiveStringBuilder.append(totalArchiveNumber);
		String totalArchiveString=totalArchiveStringBuilder.toString();
		totalArchive.setText(totalArchiveString);
		int totalArchiveChecked=0;
		int totalArchiveUnchecked=0;
		for (int i=0; i<MainActivity.mainarchived.size();i++){
			if (MainActivity.mainarchived.get(i).getCheck() == true){
				totalArchiveChecked+=1;
			}
			else{
				totalArchiveUnchecked+=1;
			}
		}
		//	this sets up and prints total checked archived counts
		TextView totalArchiveCheck=new TextView(this);
		totalArchiveCheck=(TextView)findViewById(R.id.totalArchiveCheck);
		StringBuilder totalArchiveCheckStringBuilder =new StringBuilder();
		totalArchiveCheckStringBuilder.append("Total Archived Checked ToDos: ");
		totalArchiveCheckStringBuilder.append(totalArchiveChecked);
		String totalArchiveCheckToDoString=totalArchiveCheckStringBuilder.toString();
		totalArchiveCheck.setText(totalArchiveCheckToDoString);
		//this sets up and prints total unchecked archived counts
		TextView totalArchiveUncheck=new TextView(this);
		totalArchiveUncheck=(TextView)findViewById(R.id.totalArchiveUncheck);
		StringBuilder totalArchiveUncheckStringBuilder =new StringBuilder();
		totalArchiveUncheckStringBuilder.append("Total Archived Unchecked ToDos: ");
		totalArchiveUncheckStringBuilder.append(totalArchiveUnchecked);
		String totalArchiveUncheckToDoString=totalArchiveUncheckStringBuilder.toString();
		totalArchiveUncheck.setText(totalArchiveUncheckToDoString);
	}
}
