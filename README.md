Demo crawler spawner with jsoup

Usage guide

```
git clone https://github.com/qza/gft-crawler.git
cd gft-crawler
mvn clean package
mvn exec:exec
```

Find configuration, results and reports in resources folder.

Example report for 1000 links

```
 	   Crawling report 	 
 **************************************** 

 Parameters: 	 
 	 Runner count: 		 	100000 
 	 Runner init pause: 	250 
 	 Wait 4 queue: 		 	true 
 	 Pool initialSize: 	 	50 
 	 Pool maxSize: 		 	50 
 	 Release time: 		 	10 

 Results: 	 
 	 Duration: 		 		0 hours 1 minutes 44 seconds 
 	 Visited: 		 		1128 
 	 Visited / Second: 	 	10 
 	 Remained in queue: 	747 
 	 Executor task count: 	382 
 	 Completed task count: 	361 
 	 Remained task count: 	21 

****************************************
```

Example report for 50000 links

```
	 Crawling report 	 
 ************************************** 

 Parameters: 	 
 	 Runner count: 		 	100000 
 	 Runner init pause: 	300 
 	 Wait 4 queue: 		 	true 
 	 Pool initialSize: 	 	50 
 	 Pool maxSize: 		 	50 
 	 Release time: 		 	10 

 Results: 	 
 	 Duration: 		 		1 hours 53 minutes 49 seconds 
 	 Visited: 		 		50063 
 	 Visited / Second: 	 	7 
 	 Remained in queue: 	28701 
 	 Executor task count: 	22219 
 	 Completed task count: 	21313 
 	 Remained task count: 	50 

 ************************************** 
```
