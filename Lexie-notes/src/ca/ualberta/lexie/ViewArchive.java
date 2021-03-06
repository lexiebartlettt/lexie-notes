package ca.ualberta.lexie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class ViewArchive extends ArchiveScreen{
	private ListView listView ;
	private ArrayAdapter<ToDo> listAdapter;
	public static ArrayList<ToDo> viewarchived;
	public static ArrayList<ToDo> todolist;
	private String FILENAME="archive1.sav";
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_archive);

		
		//checks and save isChecked when item in list is clicked
		listView = (ListView) findViewById( R.id.archiveListView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> listAdapter,View v, int position, long id)
			{
				if(viewarchived.get(position).getCheck()==true){
					viewarchived.get(position).setFalse();
				}
				else {
					viewarchived.get(position).setTrue();
				}
				saveInFile();
			}
		});
	}
	//loads archived list from file on resume
	public void onResume(){
		super.onResume();
		loadFromFile();
		listAdapter.notifyDataSetChanged();
	}
	//loads items from file and initializes layout
	@Override
	public void onStart(){
		super.onStart();
		loadFromFile();
		loadToDoFromFile();
		listView = (ListView) findViewById( R.id.archiveListView );
		listAdapter = new ArrayAdapter<ToDo>(this,android.R.layout.simple_list_item_multiple_choice, viewarchived);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setAdapter( listAdapter );
		registerForContextMenu(listView);
		checkChecked();
	}
	//loads archived item from file FILENAME
	private void loadFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fis);
			//	from http://www.javacreed.com/simple-gson-example/
			Gson gson= new GsonBuilder().create();
			viewarchived=gson.fromJson(isr, new TypeToken <ArrayList<ToDo>>(){}.getType());
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	//saves archived items to file FILENAME
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,0);
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
	//checks the items who's boolean isChecked == true
	public void checkChecked(){
		for (int i=0;i<viewarchived.size();i++){
			if(viewarchived.get(i).getCheck()==true){
				listView.setItemChecked(i, true);
			}
		}
	}
	//Creates a context menu that helps you delete To Do List items
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		String title = "Delete?";
		menu.setHeaderTitle(title);
		menu.add(Menu.NONE,R.menu.archive_delete, Menu.NONE, "Delete");
		menu.add(Menu.NONE,R.menu.delete_menu, Menu.NONE,"Unarchive");
	}
	//This is where it actually deletes when you press delete
	@Override
	public boolean onContextItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.menu.archive_delete:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			viewarchived.remove(info.position);
			saveInFile();
			listAdapter.notifyDataSetChanged();
			return true;
		case R.menu.delete_menu:
			info = (AdapterContextMenuInfo) item.getMenuInfo();
			todolist.add(viewarchived.get(info.position));
			viewarchived.remove(info.position);
			saveInFile();
			saveToDoInFile();
			listAdapter.notifyDataSetChanged();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	//loads toDo item from file FILENAME
	private void loadToDoFromFile() {
		try {
			FileInputStream fis = openFileInput("saves8.sav");
			InputStreamReader isr = new InputStreamReader(fis);
			//	from http://www.javacreed.com/simple-gson-example/
			Gson gson= new GsonBuilder().create();
			todolist=gson.fromJson(isr, new TypeToken <ArrayList<ToDo>>(){}.getType());
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	//saves toDo items to file FILENAME
	private void saveToDoInFile() {
		try {
			FileOutputStream fos = openFileOutput("saves8.sav",0);
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
}