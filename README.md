# Exam App Creator

## Table of Contents

- [Purpose of the Project](#Purpose-of-the-Project)
- [Features of the Project](#Features-of-the-Project)
- [Modules of the Project](#Modules-of-the-Project)
- [Screenshots](#Screenshots)
- [Requirements](#Requirements)
- [Installation](#Installation)

## Purpose of the Project:
Aim of this project is make easy to conduct exam. Helping teachers to create dynamic exams with no efforts giving them the abilty to modify exams as they like.
Not only that but also devoloping an convenient exam system to help students during exams.
It is built with help of JAVAFX and NetBeans.
## Features of the Project:
- Dark and Light mode
- Customization of test taking options.
- Creating multiple exams with no limits.
- Efficiency to create a question bank.
- On screen evaluation for instant results.
## Modules of the Project:
In this project there are two major modules:
- Admin Module
- Student Module
### Admin Module:
In admin module there are five sub modules:
- Login Submodule
- Adding Exams Submodule
- Editing Exams Submodule
- Deleting Exams Submodule
- Showing results of the Exams Submodule
#### Features of Admin Module:
- Admin can login.
- Admin can add Exams.
- Admin can add questions.
- Admin can search/update/delete questions.
- Admin can view questions lists.
- Admin can view results.
- Admin can logout.
### Student Module:
In this project there are two sub modules:
- Login Submodule
- Exam Submodule

#### Features of Student Module:
- Students can login.
- Students can have an exam.
- Students cannot have exams they attemped before 
- Students can see their score and their rank among their friends
- Studends can see the their answers and correct answers 
- Students can logout

---
## Requirements
- Java 8 or JDK 20
- JAVAFX

---
## Installation
1. Clone the repo  ```git clone https://github.com/nourhanHesham77/javaProject.git```

2. Open the project in your preferred Java IDE (such as IntelliJ , Eclipse or Netbeans (recommended)).
3. Configure the project to use JavaFX. This can be done by adding the following lines to your build.gradle file:   
dependencies {
    implementation 'org.openjfx:javafx-controls:11.0.2'
}
```
Note: If you are using a different build tool such as [Maven](poe://www.poe.com/_api/key_phrase?phrase=Maven&prompt=Tell%20me%20more%20about%20Maven.), the [configuration steps](poe://www.poe.com/_api/key_phrase?phrase=configuration%20steps&prompt=Tell%20me%20more%20about%20configuration%20steps.) will be different.
```


4. Build the project in your IDE to generate the necessary Java class files.

5. Run the project by executing the main class of the project.
If you are using an IDE, you can typically run the project by right-clicking on the main class and selecting "Run".

6. If the project runs successfully, you should see the JavaFX window appear on your screen.
  Congratulations, you have successfully installed and run the JavaFX project!


Note: If you are using NetBeans and JDK20 Download the FX20 Library and add the path of it to the vm Options
```
EX:
--module-path "Path to 20 Library" --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics
```

---


## Screenshots

### Home Page
| light                                                                                                                     | Dark                                |
| -----------------------------------                                                                                       | ----------------------------------- |
| ![Screenshot (130)](https://github.com/nourhanHesham77/javaProject/assets/118073597/d15d9365-296e-4758-9cf7-07606d5ca807) | ![Screenshot (135)](https://github.com/nourhanHesham77/javaProject/assets/118073597/7ef99505-3d1a-4a59-92a7-ca18c7f6cc4e)|

| light                                                                                                                     | Dark                                |
| -----------------------------------                                                                                       | ----------------------------------- |
| ![Screenshot (131)](https://github.com/nourhanHesham77/javaProject/assets/118073597/b32de1ce-d8a3-45d5-97a1-f28ef3be233b) | ![Screenshot (132)](https://github.com/nourhanHesham77/javaProject/assets/118073597/6e6623c6-03df-4ea1-a100-e23d66389e14)|




### Login Page
| light                                                                                                                     | Dark                                |
| -----------------------------------                                                                                       | ----------------------------------- |
| ![Screenshot (133)](https://github.com/nourhanHesham77/javaProject/assets/118073597/ef11185c-bb10-47bd-91e6-56f0e0303295) | ![Screenshot (134)](https://github.com/nourhanHesham77/javaProject/assets/118073597/4c3c556e-8081-4415-a9a1-88012720f8f3)|


### Admin Modifications Page
| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot 2023-06-04 224730](https://github.com/nourhanHesham77/javaProject/assets/118073597/361cdbaa-b32f-48ae-8db7-13b5f6f901bf) | ![Screenshot 2023-06-04 224004](https://github.com/nourhanHesham77/javaProject/assets/118073597/37cb45d4-5fbe-411d-8e7f-5559428f52db)|

### Admin Add Question Page
| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot (145)](https://github.com/nourhanHesham77/javaProject/assets/118073597/f4a62469-e171-4c49-8a6d-4fc21edd606c) | ![Screenshot 2023-06-04 223806](https://github.com/nourhanHesham77/javaProject/assets/118073597/d88b25ae-8363-4f54-84f9-c87b81765b62)|

| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot 2023-06-04 224834](https://github.com/nourhanHesham77/javaProject/assets/118073597/67976034-d6aa-477f-ba8b-d41a92dd0fd6) | ![Screenshot 2023-06-04 223756](https://github.com/nourhanHesham77/javaProject/assets/118073597/7e67998a-a23a-4db5-95b8-bc4a82e9462c)|

### Admin Update Question Page
| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot (148)](https://github.com/nourhanHesham77/javaProject/assets/118073597/205c9742-e087-447d-a074-ad8d89cc10b4) | ![Screenshot 2023-06-04 224131](https://github.com/nourhanHesham77/javaProject/assets/118073597/f1ae3d89-fe87-448c-a05f-b90447a31bfc)|

### Admin Show Student Results Page
| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot 2023-06-04 225210](https://github.com/nourhanHesham77/javaProject/assets/118073597/a8f06900-bda5-46e2-b362-a62ac7637283) | ![Screenshot 2023-06-04 223954](https://github.com/nourhanHesham77/javaProject/assets/118073597/d8b84f7e-64f5-49bf-bfb3-9514e46e44b3)|


### Student Choosing Exam Page
| light                                                                                                                     | Dark                                |
| -----------------------------------                                                                                       | ----------------------------------- |
| ![Screenshot 2023-06-04 221002](https://github.com/nourhanHesham77/javaProject/assets/118073597/d4f7e353-057d-4ac0-a04d-6003599b44f3) | ![Screenshot (136)](https://github.com/nourhanHesham77/javaProject/assets/118073597/e0d7ef77-5cf5-4008-b1b6-2ff6f36b3816)|

| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot 2023-06-04 220935](https://github.com/nourhanHesham77/javaProject/assets/118073597/1e5b6d96-b792-4609-a296-220052e376da) | ![Screenshot (137)](https://github.com/nourhanHesham77/javaProject/assets/118073597/6d419e61-6a37-461b-a03c-207b12b6d189)|

### Student Instrutions Page
| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot 2023-06-04 221143](https://github.com/nourhanHesham77/javaProject/assets/118073597/74dce867-bb17-47ab-87df-d340c6f626aa) | ![Screenshot (138)](https://github.com/nourhanHesham77/javaProject/assets/118073597/1e9993d3-2692-4805-bb99-05e026358c31)|


### Student Exam Page
| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot 2023-06-04 222021](https://github.com/nourhanHesham77/javaProject/assets/118073597/952a79c0-8353-4f15-89a9-b5e782379fdd) | ![Screenshot (139)](https://github.com/nourhanHesham77/javaProject/assets/118073597/6783da91-3dee-4a30-915c-175208c79a4c)|

### Student Result Page
| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot 2023-06-04 222233](https://github.com/nourhanHesham77/javaProject/assets/118073597/94e34479-90eb-4d9d-ae71-63f7498789ca) | ![Screenshot 2023-06-04 223022](https://github.com/nourhanHesham77/javaProject/assets/118073597/e213b978-2339-452f-97ba-f3732a3e8ce8)|


### Student Rank Page
| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot 2023-06-04 222502](https://github.com/nourhanHesham77/javaProject/assets/118073597/b9b9995d-2f30-43c9-9fc3-261ad0ac232b) | ![Screenshot 2023-06-04 223031](https://github.com/nourhanHesham77/javaProject/assets/118073597/cd9f18b7-e1ec-4375-82bd-5f019e86a309)|


### Student Order Page
| light                                                                                                                                 | Dark                                |
| -----------------------------------                                                                                                   | ----------------------------------- |
| ![Screenshot 2023-06-04 222302](https://github.com/nourhanHesham77/javaProject/assets/118073597/b28216b2-aa8d-4de5-af4f-19ca0ef2dd13) | ![Screenshot 2023-06-04 223057](https://github.com/nourhanHesham77/javaProject/assets/118073597/63107a82-183f-47e7-92b9-7f7bc8537c76)|



## Authors
[@Nourhan Hesham](https://github.com/nourhanHesham77)

[@Ahmed Maher](https://github.com/AhmedMaherTohmay)


## 🔗 Links
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/nourhan-hesham-216863252/)

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/ahmed-maher-tohamy/)

