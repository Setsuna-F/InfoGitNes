
#!/bin/bash
mvn clean package

# For Docker tomcat:
docker cp target/sentiment-analysis-webpage.war sentiment_analysis_payara:/opt/payara41/glassfish/domains/payaradomain/autodeploy
