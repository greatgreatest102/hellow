## Framework details
- This is a Maven Java project
- TestNG as the test runner and MAVEN as the build tool.
- This framework should be able to run with Java 8 and 11.
- Rest Assured Library as client for API testing
- Hamcrest library for assertions
    
## Framework test structure
 - You will find the test classes under 
  `src/test/java/test/`
 - You will find the utilities and test data under
  `src/test/java/TestData/`
  `src/test/java/TestData/RequestSpec.java` is the spec configuration for all requests and will handle the authorization. 
    
## How to run the tests ***Using the IDEA***
 1. Right-click on a single test file and click Run. You can as well run a single test method
 2. Right-click on the src/test/java/test/{testfiles.java} and click Run
    
## How to run the tests ***Using Maven***    
 1. to run all use: mvn clean test 
 2. to run a specific test, use: mvn test -Dtest={testfiles.java}
