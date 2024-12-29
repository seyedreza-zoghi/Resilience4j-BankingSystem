# Resilience4jInBankingSystem
use all features resilience4j in banking system
---------------------------------------------------------
To use all the features of Resilience4j in a microservice scenario, you can design a scenario for a banking system that includes communication with several external services to manage banking transactions. This system should use Circuit Breaker, Rate Limiter, Retry, Bulkhead and Time Limiter features to improve the stability and performance of services.

Scenario:
The banking system that manages customer deposit and withdrawal transactions must connect to several external services:

Authentication service: Checking the user's credentials before each transaction.
Exchange rate service: to convert currency units if needed.
Foreign payment service: money transfer to foreign bank accounts.
Notification service: sending SMS or email after completing the transaction.
Features of Resilience4j:
Circuit breaker:

For external services such as external payment service, if there are a number of failed requests in a row, the Circuit Breaker will activate and block further requests to allow the system to recover.
Rate Limiter:

For the notification service that may have limits on the number of SMS sent in a time period, Rate Limiter is used so that the requests do not exceed a certain limit.
Retry:

For the exchange rate service, if the request fails due to temporary problems such as timeout, Retry is activated and several retries are made to receive the information.
Bulkheads:

Bulkhead is used to prevent excessive resource consumption by a particular service. For example, requests to the authentication service are made within a certain limit in terms of the number of dedicated threads so that other services are not affected.
Time limiter:

For services such as external payment service where the response time may be long, Time Limiter is set so that if the response is not received within a certain time period, the request is canceled and the system does not wait.
How it works:
When a user requests a deposit, the authentication service first checks whether the user is authentic or not. If the service is slow or inefficient for any reason, Time Limiter and Circuit Breaker can prevent the service from stopping.
Then, the exchange rate service is called for currency conversion. If the service becomes unresponsive or crashes, Retry will try to retrieve the data a few more times.
Finally, the external payment service is responsible for the transfer of funds. This service may become unavailable due to high request pressure or external problems; Therefore, Circuit Breaker and Bulkhead prevent additional load on the service and resources.
After the successful transaction, the notification service sends a confirmation message to the user. Rate Limiter is used for this service to manage requests if the number of messages exceeds a certain limit.
Advantages:
Preventing additional load on the system and services.
Improving responsiveness and stability of microservices.
Manage errors and retries intelligently.
Control and limit the rate of requests for external services.
