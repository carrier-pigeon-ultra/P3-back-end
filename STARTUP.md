# Dependencies
<ul>
  <li>java 11</li>
  <li>Maven(3.8.5)</li>
  <li>A PostgreSQL database</li>
  <li>An email account through which password reset links will be sent</li>
  <li>The front end of the application, which can be found and cloned from https://github.com/carrier-pigeon-ultra/P3-front-end
</ul>
All other dependencies, which include SrpingBoot 2.7.1, lombok, and a PostgrSQL driver among other things will be downloaded automatically via the
pom.xml file.

# Environment variables that need to be set.
<ul>
  <li>RDS_URL: The url of the PostgreSQL database being used</li>
  <li>RDS_USERNAME: The username associated with the database being used</li>
  <li>RDS_PASSWORD: The password associted with the database being used</li>
  <li>MAIL_HOST: The transfer protocol service</li>
  <li>MAIL_PASSWORD: The password associated with the mail account being used to reset passwords</li>
  <li>MAIL_USERNAME: The email that will send users a password reset link.</li>
  <li>JWT_SECRET: The secret for jwt tokens, must be greater than 256 bytes.</li> 
</ul>

# Instructions to run
<ul>
  <li>Star instance of </li>
</ul>
