# CarrierPigeon - SocialMediaApplication

This project was developed using Java 11 and Springboot 2.7.1.

# Description

This is the back end portion of the carrier-pigeon ultra social media web application for the final project at Revature, the front end can be found at https://github.com/carrier-pigeon-ultra/P3-front-end

# Software versions
<ul>
  <li> apache-maven 3.8.5 </li>
  <li> Java 11
  <li> Springboot 2.7.1
</ul>

# Contributors
<ul>
  <li> Ryan Knight - RyanReedKnight
  <li> Robert Lei - SleepingGlaceon
  <li> Nuru Hussein - Kankoo1
  <li> Blake Rhynes - blaker859
</ul>

# Features developed in this sprint
<ul>
  <li> Auth Service: Users can log in and will get an exception response if not registered or if providing incorrect credentials
  <li> View Post Service: Users can query all posts, top posts, individual users posts, and delete their own posts
  <li> Post Service: Users can make posts, and create comments. Comments are posts themselves and carry a post ID as well
  <li> User Service: Users must set a strong password, they are able to create a personal page that includes their hometown, birthday, current residence, and biography
  <li> User Search: Users can search for other users by name, id, or email. Returns a list of users matching criteria
  <li> Profanity Filter: Using Purgomalum API, swear words are "purged" or censored from view
  <li> Forgot Password: Using gmail smtp a user can reset their password via a link sent to their email. User is assigned a temporary token and that token is then emailed to a registered email. Once clicking the link and using another strong password, then the token will be set back to null.
</ul>

# Link to deployed application
http://codepipeline-us-west-2-791209503483.s3-website-us-west-2.amazonaws.com/
