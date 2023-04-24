
# NBP Api

An application that queries data from the public APIs of the National Bank of Poland and returns relevant information from them.


## Deployment

To deploy this project run in main directory

```bash
  docker build -t nbp-api:0.0.1 .
```

```bash
  docker run -p 8080:8080 nbp-api:0.0.1 
```


## Api Endpoints

- This endpoint returns the average exchange rate for a currency - /exchanges/{currencyCode}/{date}
- This endpoint returns the maximum and minimum value of the latest quotations - /exchanges/{currencyCode}/last/{numberOfQuotations}
- This endpoint returns the major difference between the buy and ask rate of the latest quotations - /exchanges/{currencyCode}/last/{numberOfQuotations}/difference



## Usage/Examples

1. To query operation 1, run this command (which should have the value 5.2768 as the returning information):
```javascript
curl localhost:8080/exchanges/GBP/2023-01-02
```

2. To query operation 2, run this command (which should have {"max" : 5.3369, "min" : 5.2086} as the returning information):
```javascript
curl localhost:8080/exchanges/GBP/last/10
```

3. To query operation 3, run this command (which should have the value 0.10679999999999978 as the returning information):
```javascript
curl localhost:8080/exchanges/GBP/last/10/difference
```
## Documentation



To generate the API documentation, run the application and open a web browser at 'http://localhost:8080/swagger-ui/index.html'.

## Authors

- [@MaciejDani](https://github.com/MaciejDani)
