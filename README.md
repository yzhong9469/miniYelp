# Mini-Yelp

## Overview
A Mini-Yelp for Penn students with login function, using Yelp API to collect restaurant data and JMapviewer to display map in Philadelphia University City area. Users will get customized recommendations based on their previous ratings.

## Motivation
This java application is the final project for CIT 594 at University of Pennsylvania, Fall 2016. It is inspired by common conversations between students on complaining it is hard to find a good place to eat in University City, and this application is designed to help students simplify the decision process. 

## Example Usage
* After import the entire project into Eclipse, please configure your external library build path in Eclipse to add **myMap.jar**, which is adapted from JMapViewer.
* To use the application, please run "miniYelp.java". You can choose to register if this is your first time using this application. You can also choose to login if you've already registered. 
* The "main" page is the Search panel. You can search restaurants by select your desired filtering criteria on top of the page and press "Search". Then red markers will show up on the map and detailed information will be shown in the left text panel. 
* You can click on "Get Recommendations" to get a list of 10 recommended restaurants. To get customized recommendations, please go to "My Profile" and add some ratings for restaurants in the system. 
* To add rating, you first enter the partial/full restaurant name in the top text field and press "Find". Possible matches will show up in the drop down selecter below. You can then choose a restaurant with the correct name and address, choose a rating, and press "Add". 
* To exit the program, please click "Exit" on the main page, otherwise your personalize information might not be properly stored in our system.

## Requirements fullfilled
1. We used Interfaces and Design Patterns in this project. 
  * Design patterns used are MVC, Singleton. 
2. Data structures used are ArrayList, HashMap, PriorityQueue(Heap). 
3. We also used Java Graphics (swing) and external .jar library (JMapViewer).


## Authors
Man Hu, Ziyi Yan, Yan Zhong
