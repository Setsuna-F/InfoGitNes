
## Used technologies

* Java 8
* Payara41
* Postgres PSQL DB

## Deployed on Advanse

### How to run it on Advanse server

#### How to update the war on Advanse Server

* `git pull` the project
* `mvn clean package` to package the poroject in a war with maven
* Go to http://advanse.lirmm.fr:4848, log with credentials, go to application and deploy the new war
* Go to http://advanse.lirmm.fr:8081/sentiment-analysis-webpage/index

#### Restart the container

`docker ps` to check if the container is running (the payaraserver image with the name "sentiment_analysis_payara"). If not running:
```
docker start sentiment_analysis_payara

# Run start script in container
docker exec sentiment_analysis_payara /opt/payara41/glassfish/bin/asadmin start-domain payaradomain

# Attach to the container to start payara:
docker exec -i -t sentiment_analysis_payara bash
# In container run the following to start payara
./asadmin start-domain payaradomain
```

#### Things to check if not working properly and known bugs

To set those things right refer to the following sections.

* Check that resources files (from resource_on_server) has been added to the Payara docker container (with proper permissions)

* You need to have a Postgres DB well defined and created. Setup address in https://gite.lirmm.fr/advanse/sentiment-analysis-webpage/blob/master/src/main/java/fr/lirmm/db/BaseDeDonnee.java#L29 (For advanse it is 193.49.110.38:5432)

* Connection pool created in Payara admin webpage

* *Port already in use*: port used by payara (8081, 4848) can be already used (it can come from a bad shutdown of the app) and preventing from running the app
To set free those port:

```
# Display port information (with PID of process running on each port)
sudo netstat -tulpn

# Get port 4848 process PID
kill -9 process_pid
```



## Deploy it on your computer

We are using "sentiment_analysis_payara" as the container name, change it according to your container name

#### Install pre-requisites

* Install docker

* If you are not using the Advanse Postgres server (only available from LIRMM network), then you will have to create a new postgres database. See the `Create PSQL database and user` part below for more details.
You can change the Postgres DB address here: https://gite.lirmm.fr/advanse/sentiment-analysis-webpage/blob/master/src/main/java/fr/lirmm/db/BaseDeDonnee.java#L29 (For advanse it is 193.49.110.38:5432)


#### Create the payara docker container

Exposing ports on localhost: 4848 and 8081 (for traditional 8080)

```
docker run -t -p 4848:4848 -p 8081:8080 -p 8181:8181 --name sentiment_analysis_payara -d payaradocker/payaraserver:4.1.1.161 /bin/bash
```

#### Add resources files to the Payara container

* Copy the files from the git project (in resources_on_server) to the container

```
cd resources_on_server
docker cp resources sentiment_analysis_payara:/opt/payara41/glassfish/domains/payaradomain/config
docker cp models sentiment_analysis_payara:/opt/payara41/glassfish/domains/payaradomain/config
docker cp TreeTagger sentiment_analysis_payara:/opt/payara41/glassfish/domains/payaradomain/config
```

* Then change owner and permission for files added in the containers (it needs to be payara)

```
docker exec -i -t --user root sentiment_analysis_payara bash
cd /opt/payara41/glassfish/domains/payaradomain/config

chown -R payara:payara *
chmod -R 755 resources/
chmod -R 755 models/
chmod -R 755 TreeTagger/
```

#### Run payara

```
# Attach to the container to start payara:
docker exec -i -t sentiment_analysis_payara bash
# In container run the following to start payara
./asadmin start-domain payaradomain

# Or directly run start script in container:
docker exec sentiment_analysis_payara /opt/payara41/glassfish/bin/asadmin start-domain payaradomain
```

#### Create the JDBC Connection Pool in Payara

* Open Payara admin UI ( https://advanse.lirmm.fr:4848 )
* Resources > JDBC > JDBC Connection Pools
* Click on "New..."
* Fill the fiels with the following :
    * Pool Name: SentimentAnalysisWebpagePool
    * Resource Type: javax.sql.DataSource
    * Database Driver Vendor: Postgresql
    * Cliquer sur Next

* Fill some *Additional Properties*:
    * User: sentiment_analysis_webpage_user
    * Password: admin
    * ServerName: 193.49.110.38 ou localhost
    * PortNumber: 5432
    * DatabaseName: sentiment_analysis_webpage_users_db


* Create the JDBC Resources in Payara
    * Resources > JDBC > JDBC Resources
    * JNDI Name: jdbc/sentimentanalysiswebpage
    * Pool Name: SentimentAnalysisWebpagePool



#### Get to the application

* Admin: https://advanse.lirmm.fr:4848
Login: admin / Password: glassfish
And deploy the new war: Applications > Deploy... or use the **compile.sh** script to mvn package and then put the war on payara

* Webpage: http://advanse.lirmm.fr:8081/sentiment-analysis-webpage-0.1/index

* Check the logs
`cat /opt/payara41/glassfish/domains/payaradomain/logs/server.log`


## Postgres DB

### PSQL on Advanse server

Postgres DB on Advanse server postgres, at adress: 193.49.110.38

`sudo -u postgres psql postgres`

```
\c sentiment_analysis_webpage_users_db
SHOW search_path;  (should be "lirmm")
\dt

# Check if something in User table
select * FROM lirmm."User";
```


## Create PSQL database and user

Create the user and the DB in the PSQL database

Open postgresql as command line with `sudo -u postgres psql postgres`. And run the following script to create the users and tables.

```sql
-- Role: sentiment_analysis_webpage_user

-- DROP ROLE sentiment_analysis_webpage_user;

CREATE ROLE sentiment_analysis_webpage_user WITH LOGIN PASSWORD 'admin' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

-- Database: sentiment_analysis_webpage_users_db

-- DROP DATABASE sentiment_analysis_webpage_users_db;

CREATE DATABASE sentiment_analysis_webpage_users_db WITH OWNER = sentiment_analysis_webpage_user ENCODING = 'UTF8' TABLESPACE = pg_default CONNECTION LIMIT = -1;


-- Il vaudrait mieux changer le nom du schema, pour qu'il soit plus parlant
-- DROP SCHEMA lirmm;

CREATE SCHEMA "lirmm" AUTHORIZATION "sentiment_analysis_webpage_user";

SET search_path= lirmm;

SHOW search_path;

-- Table: lirmm."User"

-- DROP TABLE lirmm."User";

CREATE TABLE lirmm."User"
(
  "Id" serial NOT NULL,
  "Fname" character varying(50),
  "Lname" character varying(50),
  "Mail" character varying(50),
  "Password" character varying(50),
  "Mod" boolean,
  "IsUpload" boolean,
  "IsTraining" boolean,
  CONSTRAINT "PK" PRIMARY KEY ("Id"),
  CONSTRAINT mail UNIQUE ("Mail")
)
WITH (
  OIDS=FALSE
);

ALTER TABLE lirmm."User" OWNER TO sentiment_analysis_webpage_user;

-- Table: lirmm."File"

-- DROP TABLE lirmm."File";

CREATE TABLE lirmm."File"
(
  "Id_file" serial NOT NULL,
  "Id_user" serial NOT NULL,
  "Name" character varying(50),
  "Info" character varying(100),
  "Date_create" date,
  "Date_update" date,
  CONSTRAINT "PK-file" PRIMARY KEY ("Id_file"),
  CONSTRAINT "FK-file-user" FOREIGN KEY ("Id_user")
      REFERENCES lirmm."User" ("Id") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

ALTER TABLE lirmm."File" OWNER TO sentiment_analysis_webpage_user;
```
