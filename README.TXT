Craft Application : This project covers two applications

     a. Craft
     b. Craft-Rest Client
	 
Rest Client Application is client which directly interacts with Craft main Application.


Application is already loaded with Users (U1 ,U2 ,U3 ,U4)   

Both Application are created on Spring Boot Framework. 
Craft application runs on port 8080 .
Client application runs on port 8081.

Rest Client is exposing Services using below URL's

*********************************************** Tweet *********************************************

##  Posting a new Tweet ##

    http://localhost:8081/Craft/tweets/postTweet
    
	Request Type : POST	
    Add Authorization Header in Request with any users (as mentioned above ) and pwd.
	Sample Input : {"tweetText":"hello from U3"}	
	
##  Delete the old Tweet

    Request Type : DELETE
    http://localhost:8081/Craft/tweets/1 (in case Tweet id is 1)	


##  Get the list of tweets posted by LoggedIn Users

    Request Type : GET
    http://localhost:8081/Craft/tweets


##  Get the feed for loggged-in User

    Request Type : GET
    http://localhost:8081/Craft/tweets/feed


******************************************** Follower ***********************************************	


## Adding the Follwing ( If user U1 starts following User U2)

   Request Type : POST
   http://localhost:8081/Craft/follow
   
   Sample JSON Input : 
   {
	
    "id":"",
    "follower":{
    	
    	"userId":"U1"
    },
    "followed":
    {
    	"userId":"U2"
    }
	
	
   }
   
## Get the Follower List for the logged in User

   Request Type : GET
   http://localhost:8081/Craft/follower
   
   

      
   
   

   
  
  

	

 
 
 
 
 
 
 
 