# How to import Master into Papyrus for running ##
    step 1: Download our project from github (or assignment submission)
    step 2: Unzip your file to a desired location
    step 3: open papyrus and create a workspace that contains your unzipped file location
    step 4: import an existing project into your workspace through papyrus' file tab.
    step 5: set the workspace as your root directory and select the project in the projects window.
    step 6: right click the project folder.
    step 7: configure the project's buildpath
    step 8: click the source tab, select the folder that should be in there and remove.
    step 9: add folder and check t he project folder and hit ok.
    step 10: select the libraries tab and classpath.
    step 11: add the ocsf and the mysql connector jar files located within the project.
    step 12: select add library, select JUnit then next. then make sure JUnit 4 is selected. hit finish. apply and close.

# The project should now be fully set up into papyrus, but now you have some options to run it #

    You can use the .bat files to run the application (the easiest way), or you can make a run configuration in papyrus for GameGUI.java that has a commandline argument of "localhost". Once that is set up, run ServerGUI, then GameGUI. Now the program is running!

    Always make sure to run the ServerGUI before any GameGUIs.