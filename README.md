# My Personal Project

## My Project Idea: Birthday Tracker Application

The project I want to build is a **birthday tracker application** that stores birthdays of people that the
user knows. The app will let the user know when it is someone's birthday. *Ideally* this would be through a
desktop or email notification, but it could also just be a message that displays when the user opens the
app. It will also be able to calculate how many days there are until someone's birthday, and how old they are
turning. The app will store information about the person whose birthday it is 
such as their interests and hobbies, which could help the user choose a gift for them when their birthday is 
coming up.

I've tried a few birthday reminder apps but *none* of them had any functionality to store gift ideas or information
about people like their interests that would help when thinking of gift ideas. These apps also tended to have
features that were behind a pay-wall. My motivation for this project is to make a birthday tracking app that
addresses these issues I have encountered. The target audience for the app will be any person who has birthdays they want to keep track of. It would
be most useful for people who are forgetful with dates and/or people who care about giving well-thought-out
gifts that other will actually like.

## User Stories
- As a user, I want to be able to create a new birthday and add it to my calendar
- As a user, I want to be able to view a list of all the birthdays in my calendar
- As a user, I want to be able to search for a birthday and view information about the person
whose birthday it is, such as their age and some gift ideas for them
- As a user, I want to be able to delete a birthday

Phase 2 additions:
- As a user, when I select 'x' to exit the application, I want to be provided with the option to save any unsaved
  changes to my calendar
- As a user, I want to be provided with the option to load my calendar from file when I open the application

# Instructions for Grader
- You can locate my visual component by running Main.main(). There is a birthday cat image and also
  a cake icon in the top left corner.
- You can reload the state of my application by running Main.main() and clicking "Load from file"
- You can generate the first required action related to adding Xs to a Y by clicking "Add Birthday"
  - Enter a name and birthdate (replace "mm/dd" with a date like 01/01)
  - Optional: enter a year by replacing yyyy with a year
  - Optional: enter a list of interests separated by commas
  - Optional: enter a list of gift ideas separated by commas
  - Then click "Add birthday". You should be taken back to the main window with the list of birthdays and be able 
  to see the birthday you added.
- You can generate the second required action related to adding Xs to a Y (which is to delete a birthday) by...
  - Click once on any birthday in the list shown. A window should open with information about this birthday.
  - Click "Delete birthday" and then click "Yes" to confirm the deletion
  - YOu should be taken back to the main window with the list of birthdays
- You can save the state of my application by clicking the red X button to exit. You should be asked whether 
 you want to save your calendar to file. Click "Yes" to save.

## Phase 4 - Task 2:
Sun Apr 09 17:22:08 PDT 2023 <br />
New calendar created <br />
Sun Apr 09 17:22:08 PDT 2023 <br />
Birthday with name "Snoopy" added to calendar. <br />
Sun Apr 09 17:22:08 PDT 2023 <br />
Birthday with name "Sage" added to calendar. <br />
Sun Apr 09 17:22:19 PDT 2023 <br />
Birthday with name "Sage Drake" added to calendar. <br />
Sun Apr 09 17:22:27 PDT 2023 <br />
Birthday with name "Sage" deleted from calendar. <br />
Sun Apr 09 17:22:38 PDT 2023 <br />
Birthday with name "Madi" added to calendar. <br />

## Sources:
- https://github.students.cs.ubc.ca/CPSC210/TellerApp
- https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
- Cake icon: https://brandeps.com/icon/C/Cake-01
- Cat image: https://pin.it/7Coc7Lf

For learning GUIs:
- https://www.youtube.com/watch?v=5o3fMLPY7qY
- https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
- https://www.youtube.com/watch?v=iE8tZ0hn2Ws
- https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
- https://www.youtube.com/watch?v=OI-TFbHQhtA
- https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#overview


## Refactoring ideas:
The first refactoring I would so is to extract common behaviour from all the classes I used to represent
different GUI windows. These classes all have things in common such  as
their size and position on the screen, their default close operation, their icon, etc.
I would make an abstract super class that implements this common behaviour when constructed,
and then had each window extend that superclass. This would reduce repetition in my code.

The second refactoring I would do is to use the observer pattern with my GUI windows. When a birthday
is added in AddBirthdayWindow, or deleted in DisplayBirthdayWindow, the main ViewCalendarWindow needs
to become visible and also reflect these changes. I dealt with this by making many of ViewCalendarWindow's
methods and fields static, similar to the Singleton pattern. I could use the observer pattern instead by
making a subject interface and an observer interface (they would have to be interfaces and not classes
since the ui window classes will already each be extending a class after the first refactoring). The
ViewCalendarWindow would implement the Observer interface and the AddBirthdayWindow and DisplayBirthdayWindow
classes would implement the Subject interface. When a method in ViewCalendarWindow creates a new AddBirthdayWindow 
or DisplayBirthdayWindow, it would also call an add observer method with itself as a parameter. Then
when a birthday is added in AddBirthdayWindow, or deleted in DisplayBirthdayWindow, the update method can 
be called on observers so that ViewCalendarWindow will update. This would eliminate the need to use static
methods and fields. It would also mean that AddBirthdayWindow and DisplayBirthdayWindow would need less information
about ViewCalendarWindow since they would not need to know exactly *how* to update ViewCalendarWindow when
a change is made.