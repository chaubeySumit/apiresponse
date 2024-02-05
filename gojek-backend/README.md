# gojek-backend

# Introduction 
    - gojek backend is a comparator which compares the response of apis.

# SetUp
    - shared a downloadable link from where we can directly download the zip file
    - Extract the zip file in system
    - Import/Open the project using Intellij or Eclipse
    - Use "./gradlew build" to build the project
    
# Run
    - Run the project in terminal using command './graldew clean build' in home directory(/../gojek-backend)
    - It will start running the testcases present in CompareTest file
    - Further output is printted on terminal in this way "https://reqres.in/api/users/2 equals https://reqres.in/api/users/2"
    or https://reqres.in/api/users/2 not equals https://reqres.in/api/users/1
    
# How it works
    - Pass the url files( containing urls) using the runParallelComparatorWithStreams method which will get 
    the response from client by taking urls and validate the corresponding responses are same or not.
    if same then it will print https://reqres.in/api/users/2 equals https://reqres.in/api/users/2
    else https://reqres.in/api/users/2 not equals https://reqres.in/api/users/1
    
    - Http Client uses the default classes of Jersey Client which basically invoke each request 
    and generate the response
    - JsonMapper file is the mapping of response to Json Object which further converts it into its respective Object
    - ResponseExecutor is generally a response fetcher from client and also compare the responses
    - Pilot is the driving class for comparing the results.
    - ComparatorImplement is a class which implements the compare method of IComparator interface.
    
# Technology
    - Used Java as a programming language
    - Gradle as a build tool
    - TestNg as a testing framework
    - Jax-rs client for invoking the apis
