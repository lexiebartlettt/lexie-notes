lexie-notes
===========

To Do List App created by Lexie Bartlett

Under apached license 2.0 (See license file)
copyright of Lexie Bartlett 2014 

To Use:
- add tasks by typing in textbox and clicking the add button 
- to delete todos click and hold on list items on the main screen 
- clicking and holding archived list items gives you the option to delete or unarchive
- To access archive,email and summary there are three buttons in the top right hand corner that open a menu
  In newer device versions it shows up by pressing the left button at the bottom at the device 

Sources:
 Used lonelyTwitter app for saving and loading implementations and the ToDo class.
 Can be found https://github.com/lexiebartlettt/lonelyTwitter
 accessed September 23 2014
 
 Used http://developer.android.com/guide/topics/ui/menus.html for onCreateOptionsMenu and onOptionsItemSelected
 Accessed on September 18 2014
 this was also used as a template for the functions onContextItemelected and onCreateContextMenu
 
 Used http://windrealm.org/tutorials/android/android-listview.php for arrayAdapter implementation and creating listviews
 Accessed on September 15 2014
 this is used throughout the code but it is particularly found on each of the onStart() functions in all of the classes
 
 Used http://www.tktutorials.com/2013/06/android-context-menu-example.html for context menus 
 accessed on September 26 2014
 
 Dayna Lacoursiere helped me through discussion although no direct code was exchanged 
 
 Problems:
 
  The EmailArchived class stopped opening, the class seems to work fine it just refuses to open. The button to enter this class was removed for neatness. The class is still there and in the UML diagram. 
 
  Also when you delete an item on either lists you have to refresh the screen for the change to show notifydatasetchanged() didn't work in this case.
  
 
 
 
