package ca.ualberta.lexie;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends Activity {
	private ListView listView ;
	private ArrayAdapter<ToDo> listAdapter;
	public static ArrayList<ToDo> toDoList= new ArrayList<ToDo>();;
	private static final String FILENAME="saves8.sav";
	private static final String FILENAME2 = "archive1.sav";
	int max = 0;
	public static ArrayList<ToDo> mainarchived;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		//Add Button: Adds new task to To Do List
		Button add_button=(Button) findViewById(R.id.addBtn);
		add_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				addClick();
			}
		});
		//When item in list is checked/unchecked this bit is called
		listView = (ListView) findViewById( R.id.listView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> listAdapter,View v, int position, long id)
			{
				if(toDoList.get(position).getCheck()==true){
					toDoList.get(position).setFalse();
				}
				else {
					toDoList.get(position).setTrue();
				}
				saveInFile();
			}
		});
	}
	//Creates a context menu that helps you delete To Do List items
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		String title = "Delete?";
		menu.setHeaderTitle(title);
		menu.add(Menu.NONE,R.menu.delete_menu, Menu.NONE, "Delete");
	}
	//This is where it actually deletes when you press delete
	@Override
	public boolean onContextItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.menu.delete_menu:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			toDoList.remove(info.position);
			listAdapter.notifyDataSetChanged();
			saveInFile();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	//the following two functions was taken from http://developer.android.com/guide/topics/ui/menus.html
	//September 18 2014
	//changes from the original have been made
	//Creates an option menu that appears either as 3 dots in a corner or when you press corner
	//button on newer devices
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}
	//	This is what happens when you select an item from the options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.emailItm:
			//	This case sends you to EmailScreen1
			Intent emailIntent= new Intent(getApplicationContext(),EmailScreen1.class);
			startActivity(emailIntent);
			return true;
		case R.id.archiveItm:
			//This item sends you to ArchiveScreen
			Intent archiveIntent= new Intent(getApplicationContext(),ArchiveScreen.class);
			startActivity(archiveIntent);
			return true;	
		case R.id.summary:
			//This item sends you to Summary
			Intent summaryIntent = new Intent(getApplicationContext(),Summary.class);
			startActivity(summaryIntent);
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	//This function is called when you press the add button
	//It actually adds what you typed to list
	void addClick(){
		EditText editText=(EditText)findViewById(R.id.newToDo);
		String toDo=editText.getText().toString();
		toDoList.add(new ToDo(toDo));
		listAdapter.notifyDataSetChanged();
		editText.setText("");
		setResult(RESULT_OK);
		saveInFile();
	}
	//Loads toDoList from file called FILENAME
	private void loadFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fis);
			//	from http://www.javacreed.com/simple-gson-example/
			Gson gson= new GsonBuilder().create();
			toDoList=gson.fromJson(isr, new TypeToken <ArrayList<ToDo>>(){}.getType());
		}
		//this catch catches if the file isn't found
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	//Saves todoList to file called FILENAME
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			Gson gson=new GsonBuilder().create();
			gson.toJson(toDoList,osw);
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
	//this is called when the app starts
	//it initializes the to do list and the screen
	@Override
	public void onStart(){
		super.onStart();
		loadFromFile();
		if(toDoList==null){
			toDoList= new ArrayList<ToDo>();
		}
		listAdapter= new ArrayAdapter<ToDo>(this,android.R.layout.simple_list_item_multiple_choice, toDoList);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setAdapter( listAdapter );
		registerForContextMenu(listView);
		checkChecked();
		loadArchiveFromFile();
		listAdapter.notifyDataSetChanged();
	}
	//onResume is for when you come back to this screen
	//this one loads the edited to do list and changes the screen
	//accordingly
	public void onResume(){
		super.onResume();
		loadFromFile();
		listAdapter.notifyDataSetChanged();
	}
	//loads the archive list from file FILENAME2
	private void loadArchiveFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME2);
			InputStreamReader isr = new InputStreamReader(fis);
			//from http://www.javacreed.com/simple-gson-example/
			Gson gson= new GsonBuilder().create();
			mainarchived=gson.fromJson(isr, new TypeToken <ArrayList<ToDo>>(){}.getType());
		}
		//catch catches when the file can't be found
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	//When this is called is makes every ToDo where isChecked == true appears checked
	public void checkChecked(){
		for (int i=0;i<toDoList.size();i++){
			if(toDoList.get(i).getCheck()==true){
				listView.setItemChecked(i, true);
			}
		}
	}
}
