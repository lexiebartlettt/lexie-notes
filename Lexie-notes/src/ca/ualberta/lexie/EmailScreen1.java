package ca.ualberta.lexie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
public class EmailScreen1 extends MainActivity {
private ListView mainListView ;
private ArrayAdapter<ToDo> listAdapter;
private ArrayList<ToDo> emailStrings = new ArrayList <ToDo>();
private String FILENAME="archive1.sav";
private ArrayList <ToDo> archived = new ArrayList <ToDo>();
@Override
public void onCreate(Bundle savedInstanceState){
super.onCreate(savedInstanceState);
setContentView(R.layout.email_screen);
//Adds selected item to emailStrings
mainListView = (ListView) findViewById( R.id.emailListView);
mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
public void onItemClick(AdapterView<?> listAdapter,View v, int position, long id)
{
emailStrings.add(MainActivity.toDoList.get(position));
}
});
//Email the toDos in emailStrings
Button email_select_button=(Button) findViewById(R.id.emailSelected);
email_select_button.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v){
emailToDos(emailStrings);
}
});
//Email All ToDos
Button email_all_button=(Button) findViewById(R.id.emailAll);
email_all_button.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v){
emailToDos(ca.ualberta.lexie.MainActivity.toDoList);
}
});
//Email All Archived and ToDos
Button email_everything=(Button) findViewById(R.id.emailEverything);
email_everything.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v){
emailEverything();
}
});
}
//email the entire to do list
public void emailToDos(ArrayList<ToDo> emailText){
EditText fromText=(EditText)findViewById(R.id.emailFrom);
String from =fromText.getText().toString();
EditText ccText=(EditText)findViewById(R.id.emailcc);
String cc=ccText.getText().toString();
if (from=="" || cc==""){
Toast.makeText(EmailScreen1.this,"Enter Email Addresses",Toast.LENGTH_SHORT).show();
return;
}
StringBuilder email=new StringBuilder();
Intent emailIntent = new Intent(Intent.ACTION_SEND);
emailIntent.setData(Uri.parse("mailto:"));
emailIntent.setType("text/plain");
emailIntent.putExtra(Intent.EXTRA_EMAIL, cc);
emailIntent.putExtra(Intent.EXTRA_CC, from);
emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
for(int i=0;i<emailText.size();i++){
email.append(emailText.get(i).getString()+"\n");
}
emailIntent.putExtra(Intent.EXTRA_TEXT, email.toString()) ;
try{
startActivity(Intent.createChooser(emailIntent, "send mail..."));
finish();
Log.i("Finished sending email...","");
}
catch(android.content.ActivityNotFoundException ex){
Toast.makeText(EmailScreen1.this, "There is no email client installed", Toast.LENGTH_SHORT).show();
}
}
//email archived list and to do list
public void emailEverything(){
EditText fromText=(EditText)findViewById(R.id.emailFrom);
String from =fromText.getText().toString();
EditText ccText=(EditText)findViewById(R.id.emailcc);
String cc=ccText.getText().toString();
if (from=="" || cc=="" || from==null || cc==null){
Toast.makeText(EmailScreen1.this,"Enter Email Addresses",Toast.LENGTH_SHORT).show();
return;
}
StringBuilder email=new StringBuilder();
Intent emailIntent = new Intent(Intent.ACTION_SEND);
emailIntent.setData(Uri.parse("mailto:"));
emailIntent.setType("text/plain");
emailIntent.putExtra(Intent.EXTRA_EMAIL, cc);
emailIntent.putExtra(Intent.EXTRA_CC, from);
emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
for(int i=0;i<archived.size();i++){
email.append(archived.get(i).getString()+"\n");
}
for(int i=0;i<archived.size();i++){
email.append(MainActivity.toDoList.get(i).getString()+"\n");
}
emailIntent.putExtra(Intent.EXTRA_TEXT, email.toString()) ;
try{
startActivity(Intent.createChooser(emailIntent, "send mail..."));
finish();
Log.i("Finished sending email...","");
}
catch(android.content.ActivityNotFoundException ex){
Toast.makeText(EmailScreen1.this, "There is no email client installed", Toast.LENGTH_SHORT).show();
}
}
//load the archived list from file FILENAME
private void loadArchiveFromFile() {
try {
FileInputStream fis = openFileInput(FILENAME);
InputStreamReader isr = new InputStreamReader(fis);
//from http://www.javacreed.com/simple-gson-example/
Gson gson= new GsonBuilder().create();
archived=gson.fromJson(isr, new TypeToken <ArrayList<ToDo>>(){}.getType());
}catch (FileNotFoundException e){
e.printStackTrace();
}
}
//on start formats screen and loads archived list
@Override
public void onStart(){
super.onStart();
loadArchiveFromFile();
mainListView = (ListView) findViewById( R.id.emailListView );
listAdapter = new ArrayAdapter<ToDo>(this,android.R.layout.simple_list_item_checked, ca.ualberta.lexie.MainActivity.toDoList);
mainListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
mainListView.setAdapter( listAdapter );
}
}
