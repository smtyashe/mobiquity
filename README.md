## Mobiquity Assessment Project

This is a spring boot application built using Java 17.
### Tech used :
* Cucumber-spring
* Cucumber-reporting
* RestAssured
* Maven
* Intellij
* Circle-Ci

### Build Status
[![CircleCI](https://dl.circleci.com/status-badge/img/gh/smtyashe/mobiquity/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/smtyashe/mobiquity/tree/master)

### How To run
#### If you are running the API service on localhost
`mvn test -Dspring.profiles.active=local`

#### If you are connect to the API service (https://jsonplaceholder.typicode.com)
`mvn test -Dspring.profiles.active=prod`

### Where to find the generated reports
* JSON - target/cucumber-report.json
* XML - target/cucumber-report.xml
* HTML - target/cucumber-report.html
