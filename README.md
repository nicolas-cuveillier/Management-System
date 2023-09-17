# Management System

> The goals of this project for me were to re-activate the skills of programming in Java while experimenting with new tools such as the mongoDB database and Docker. The project has been done in a short period of time, meaning that many aspects of the code need to be revisited, improved or simply developped. Therefore, many POO concepts haven't been used in this project.

## Description

The project aims to replicate the simplest version of a school management system where student can register an account, add and drop courses. Student and courses informations are AES encrypted (except for password that are hashed for security reason) in the mongoDB database while th UI is simply from the `javax.swing.*` library. 

Of course, the functionalities will be improved in order to add a better description for the courses, a better display and the possibility to modify personnal information.

## Classes

- Student (class)
- Course (class)
- CourseType (enum)
- ManagementSystem (gui)
- StudentDashboard (gui)  
- DBConnector (class)
- Main (main)

## Issues

This section provides a declaration of the actual and unresolved issues with this code. 

> First, the docker image created from the docker file won't run the app and won't display the gui beceause some librairies seems to be missing. Here is the stack trace I get when I try to run the image in a container. 

<details>
  <summary>
    `Exception in thread "main" java.lang.UnsatisfiedLinkError: /usr/local/openjdk-11/lib/libawt_xawt.so: libXext.so.6: cannot open shared object file: No such file or directory`
  </summary>
  `at java.base/java.lang.ClassLoader$NativeLibrary.load0(Native Method)
	at java.base/java.lang.ClassLoader$NativeLibrary.load(ClassLoader.java:2445)
	at java.base/java.lang.ClassLoader$NativeLibrary.loadLibrary(ClassLoader.java:2501)
	at java.base/java.lang.ClassLoader.loadLibrary0(ClassLoader.java:2700)
	at java.base/java.lang.ClassLoader.loadLibrary(ClassLoader.java:2630)
	at java.base/java.lang.Runtime.load0(Runtime.java:768)
	at java.base/java.lang.System.load(System.java:1837)
	at java.base/java.lang.ClassLoader$NativeLibrary.load0(Native Method)
	at java.base/java.lang.ClassLoader$NativeLibrary.load(ClassLoader.java:2445)
	at java.base/java.lang.ClassLoader$NativeLibrary.loadLibrary(ClassLoader.java:2501)
	at java.base/java.lang.ClassLoader.loadLibrary0(ClassLoader.java:2700)
	at java.base/java.lang.ClassLoader.loadLibrary(ClassLoader.java:2651)
	at java.base/java.lang.Runtime.loadLibrary0(Runtime.java:830)
	at java.base/java.lang.System.loadLibrary(System.java:1873)
	at java.desktop/java.awt.Toolkit$3.run(Toolkit.java:1395)
	at java.desktop/java.awt.Toolkit$3.run(Toolkit.java:1393)
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at java.desktop/java.awt.Toolkit.loadLibraries(Toolkit.java:1392)
	at java.desktop/java.awt.Toolkit.<clinit>(Toolkit.java:1425)
	at java.desktop/java.awt.Component.<clinit>(Component.java:621)
	at ManagementSystem.<init>(ManagementSystem.java:30)
	at Main.main(Main.java:9)`
</details>
    
> Secondly, the other unresolved issue is when I try to upsert a `List<Course` for the student. I get an error about the codecRegistery which seems not to understand how to proceed with the list. This pretty understandable since I didn't specify the structure of the nested array courses but I struggle to figure out how handle this.