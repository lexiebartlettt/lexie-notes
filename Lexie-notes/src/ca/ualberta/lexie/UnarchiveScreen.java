package ca.ualberta.lexie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class UnarchiveScreen extends ViewArchive{
	private ListView mainListView ;
	private ArrayAdapter<ToDo> listAdapter;
	public static ArrayList<ToDo> viewarchived=new ArrayList<ToDo>();
	private String ARCHIVEFILENAME="archive1.sav";
	private String FILENAME="saves8.sav";
	private ArrayList<ToDo> todolist;
	private ArrayList<ToDo> moveBackList;
	private ArrayList<Integer> positions=new ArrayList<Integer>();
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unarchive_screen);
		moveBackList=new ArrayList<ToDo>();
		//when a list item is clicked it is added to the movebackList
		mainListView = (ListView) findViewById( R.id.unarchiveListView);
		mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> listAdapter,View v, int position, long id)
			{
				moveBackList.add(viewarchived.get(position));
				positions.add(position);
			}
		});
		//this button calls function MoveBack();
		Button move_back=(Button) findViewById(R.id.moveSelect);
		move_back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				MoveBack();
			}
		});
		//this button calls delete
		Button deleteButton=(Button) findViewById(R.id.deleteSelect);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				Delete();
			}
		});
	}
	//moves the items from moveBackList to todolist
	public void MoveBack(){
		for(int i=0;i<moveBackList.size();i++){
			todolist.add(moveBackList.get(i));
			viewarchived.remove(positions.get(i));
		}
		saveInFile();
		saveArchiveInFile();
		listAdapter.notifyDataSetChanged();
	}
	//deletes the selected items
	public void Delete(){
		for(int i=0;i<moveBackList.size();i++){
			viewarchived.remove(positions.get(i));
		}
		saveArchiveInFile();
		listAdapter.notifyDataSetChanged();
	}
	//this does the same thing all the other onStarts do...Go read those if you're curious
	public void onStart(){
		super.onStart();
		loadArchiveFromFile();
		loadFromFile();
		mainListView = (ListView) findViewById( R.id.unarchiveListView );
		listAdapter = new ArrayAdapter<ToDo>(this,android.R.layout.simple_list_item_checked, viewarchived);
		mainListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mainListView.setAdapter( listAdapter );
	}
	//loads list viewarchived from file ARCHIVEFILENAME
	private void loadArchiveFromFile() {
		try {
			FileInputStream fis = openFileInput(ARCHIVEFILENAME);
			InputStreamReader isr = new InputStreamReader(fis);
			//from http://www.javacreed.com/simple-gson-example/
			Gson gson= new GsonBuilder().create();
			viewarchived=gson.fromJson(isr, new TypeToken <ArrayList<ToDo>>(){}.getType());
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
}
//loads to do list from file FILENAME
private void loadFromFile() {
try {
FileInputStream fis = openFileInput(FILENAME);
InputStreamReader isr = new InputStreamReader(fis);
//from http://www.javacreed.com/simple-gson-example/
Gson gson= new GsonBuilder().create();
todolist=gson.fromJson(isr, new TypeToken <ArrayList<ToDo>>(){}.getType());
}catch (FileNotFoundException e){
e.printStackTrace();
}
}
//saves todolist to file FILENAME
private void saveInFile() {
try {
FileOutputStream fos = openFileOutput(FILENAME,0);
OutputStreamWriter osw = new OutputStreamWriter(fos);
Gson gson=new GsonBuilder().create();
gson.toJson(todolist,osw);
osw.flush();
osw.close();
} catch (FileNotFoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}
//saves viewarchived to ARCHIVEFILENAME
private void saveArchiveInFile() {
try {
FileOutputStream fos = openFileOutput(ARCHIVEFILENAME,0);
OutputStreamWriter osw = new OutputStreamWriter(fos);
Gson gson=new GsonBuilder().create();
gson.toJson(viewarchived,osw);
osw.flush();
osw.close();
} catch (FileNotFoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}
}