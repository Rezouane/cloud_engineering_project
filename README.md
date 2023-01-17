# Description of our project
We decided to do a project with **two mirco-services**. The first micro-service is used to
manage employees of an enterprise and affect them to teams. The second one is used to
create projects and affect some teams to it and also add some extra employees (not in the
team). Our two micro-services are registered on the **eureka** registry of Netflix. So the todolist
micro-service communicates to the enterprise micro-service throughout the registry to get
the API url. Then we added **kafka** to our project to be more realistic (asynchronous
exchange), because when there is an action on a project, everybody who is affected by it
might be alerted. So when an action is done on a project with the todolist micro-service a
message is sended to kafka and on the consumer side (the enterprise micro-service) there is
a permanent thread which polls every message from kafka and then saves it to the user that
is concerned by it. We use **MongoDB** to store our Datas. All the services are coded in java
spring.

Finally we put every service on separate docker containers. In the configuration of our
containers, we pass some environment variables, so if an IP address or an url changes we
don’t have to change and recompile our code, we just have to change the value of the
variable and restart the container. To manage all these containers we used
**docker-compose**. Everything works perfectly.

Also in another version we used **kubernetes** to orchestrate our containerized application on
a cluster, unfortunately in that second version we had some problems to get kafka working
however if we remove kafka all works fine.

# Architecture of our project
