# STRUCTURE

> The goal of this file is to define the structure of the project.
> The project is a management system for student courses enrollment

## Requirements

- Student should be able to register or login
- Student should have access to a dashboard and must be able to enroll and drop courses as well as 
  in final exams.
- Dashboard should contain courses, description, price.
- Student should have access to their personal infos and should be able to 
  modify them.
  
## Class

- Student (class)
    - private constructor
    - static construction methods to instantiate for register or db
- Course (class)
- CourseType (enum)
- ManagementSystem (gui)
- StudentDashboard (gui)  
- DBConnector
- ObservableStudent

    