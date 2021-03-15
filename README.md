# Squizward
## Preview
<p align="center"><img src="https://im4.ezgif.com/tmp/ezgif-4-34c3b759c4bc.gif" alt="animated"/></p>

## Developed with tools and libraries:
IDE: Android Studio</br>
Gradle Tools: 4.1.2 (gradle-6.5-all.zip)</br>
Language: Kotlin</br>
Layouting: XML</br>
Architechture: Model-View-ViewModel (MVVM)</br>
Asynchronous: Coroutines</br>
Local Database: Room</br>
Supported Library:</br>
<a href="https://github.com/LouisCAD/Splitties">Splitties (Kotlin extensions for Intent)</a></br>
<a href="https://github.com/coil-kt/coil">Coil (Image Loader)</a>

## Features:</br>
- SplashScreen</br>
a screen that displays the application logo for a few seconds. usually to show the identity of the company, organization, or the application.</br>
Build it with Handler to hold screen for 3 seconds and then Intent with splitties.</br>
- Category</br>
a list of quiz categories that can be selected by the user in order to answer questions in that quiz category.</br>
Build it with Recyclerview GridLayout and get categories from local database in asset (Room).</br>
- Quiz</br>
a list of questions and answers with several answers that can be selected only one or more depending on the question and has a picture that also depends on the question.</br>
Build it with TabLayoutMediator and fragment bundle to pass each question and answer according to local database. And have CountDownTimer for 10 minutes, if already 10 minutes it will be auto intent to Achievement. Have floating action button for finish to see achievement, before finish it will show alert bottomsheet to convince the user.</br>
- Achievement</br>
achievement of user-answered quizzes containing all questions in the category, quiz score, number of correct user answers, number of incorrect user answers. And there are corrections to user answers for incorrect or unanswered answers.</br>
Build it with RecyclerView LinearLayout and calculate correct and incorrect with MutableMap by comparing user answers and answer keys. And calculate score with number of correct user answers divided by number of total questions. </br>

## Download app
<image src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/78/Google_Play_Store_badge_EN.svg/512px-Google_Play_Store_badge_EN.svg.png" width="150" height="50"/>
<a href="https://play.google.com/store/apps/details?id=com.suy.squizwardapp">Squizward App</a>
