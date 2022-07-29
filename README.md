#Tracking Web Server
A webserver application based in Java EE 8 providing file based APIs.
##API List:
/api/tracking-web-server/ping 
•	Checks if OK file exists in location and returns result
/api/tracking-web-server/img
•	Returns image
;;Additional APIs:
/api/tracking-web-server/ping-temp-file
•	Checks if OK file exists in temporary location and returns result
/api/tracking-web-server/create-temp-file
•	Creates OK  file in temporary location
/api/tracking-web-server/delete-temp-file
•	Deletes OK file in temporary location

##Testing:
Testing was manual in this instance using a REST Client and the browser

##Future Improvements:
* Test harness for APIs
* Better logging (more information) and store to different medium such as a database. Interceptors, Web Filters or annotations could also be used for cleaner logging.
* Implement methods of scaling and handling concurrent users such as cacheing or load balancing
* Add API documentation such as Swagger
