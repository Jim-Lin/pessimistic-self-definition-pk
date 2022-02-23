# pessimistic-self-definition-pk

It was inspired by DATABASECHANGELOGLOCK table of Liquibase.

## issue

self-definition PK (numeric) increment

## how

- A request get the lock.
  - others retry or finally cannot get lock!!!
- The request is the only one executor of inserting.
- Release lock.

default isolation level: **REPEATABLE-READ**

### test

30 requests / 1 second => 5s 150 requests  
![](image/test01.png)
  
150 requests success, 9.x second avg time  
![](image/test02.png)
  
88 cannot get lock!!!  
![](image/test03.png)
  
150 - 88 = 62 insert rows  
![](image/test04.png)

---

### DeferredResult

150 requests success, 3.x second avg time  
![](image/testDeferredResult01.png)
  
4 cannot get lock!!!  
![](image/testDeferredResult02.png)
  
150 - 4 = 146 insert rows  
![](image/testDeferredResult03.png)