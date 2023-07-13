# HomeVision test
Exercise for HomeVision.

## Configure local environment
- Requisites
```
Java >= 11
Maven >= 3.8
```

- Installation using `mvn`
```
mvn install
```

- Run (run mvn install previously)
```
./bin/run.sh
```

- Tests
```
mvn test
```

- Clean (Clean project and remove pictures output folder)
```
./bin/clean.sh
```

- Hardcoded definitions

It always downloads files in `pictures` folder in the project. Also, it has fixed pages to process up to 10.