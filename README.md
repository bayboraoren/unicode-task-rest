[![Build Status](https://travis-ci.org/bayboraoren/unicode-task-rest.svg?branch=develop)](https://travis-ci.org/bayboraoren/unicode-task-rest)

[![Coverage Status](https://coveralls.io/repos/github/bayboraoren/unicode-task-rest/badge.svg?branch=develop)](https://coveralls.io/github/bayboraoren/unicode-task-rest?branch=develop)

**UNICO TASK**
-
...

***Test Concurrency*** 
-
**with apache ab**

*rest-concurrency-test.json* contains the json you want to post\
*-p* means to POST it\
*-H* adds an Auth header (could be Basic or Token)\
*-T* sets the Content-Type\
*-c* is concurrent clients\
*-n* is the number of requests to run in the test\


ab -u rest-concurrency-test.json -T application/json -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNTE4NTEwODUxLCJpYXQiOjE1MTc5MDYwNTF9.3h4YrjHc_JrFpPPN_Pl-kKiAP1Sn3R7VqHA82L1ElEBQ9t0rr2w-ovd20kAWHBvdVU42oSgHK0whwvNVUdVsmg' -c 20 -n 100 http://localhost:8080/api/v1/parameters

**with jmeter**
1. go to root folder 
2. *cmd* mvn verify

*jmeter graph report*
1. go to business-service module
2. *cmd* mvn jmeter-graph:create-graph
3. check target/jmeter

*other jmeter graph types*

    edit or add plugin type, defaults are (in pom);
    - ResponseTimesOverTime*
    - TransactionsPerSecond*
    - HitsPerSecond*

- ThreadsStateOverTime = Active Threads Over Time\
- BytesThroughputOverTime\
- HitsPerSecond\
- LatenciesOverTime\
- ResponseCodesPerSecond\
- ResponseTimesDistribution\
- ResponseTimesOverTime\
- ResponseTimesPercentiles\
- ThroughputVsThreads\
- TimesVsThreads = Response Times VS Threads\
- TransactionsPerSecond
