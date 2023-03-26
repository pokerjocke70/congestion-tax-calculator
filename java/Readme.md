# Congestion Tax Calculator


Calculate the congestion tax for a vehicle.



## Getting Started


###

Start the application by running the following command:

Prerequisites: Java 17

```shell
./mvnw spring-boot:run
```

or with docker

```shell
./mvnw verify && docker build -t congestion-tax-calculator .
docker run -p 8080:8080 congestion-tax-calculator
```

### Call the application

```shell

curl "http://localhost:8080/taxes/calculate?vehicle=car&dates=2013-12-02T14:23:00,2013-12-02T17:23:00"

```


## Next steps

- [ ] Add more tests
- [ ] Jdbc implementation
- [ ] Integration testing with testcontainers and rest-assured
- [ ] Database migration with flywaydb
- [ ] Review api design
- [ ] Api docs with OAS
- [ ] Logging
- [ ] Robustness (timeouts, retries)
