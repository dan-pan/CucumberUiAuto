## Run tests
### Indicate test, driver and platform
`mvn clean test -Dcucumber.options="classpath:features/search.feature" -DdriverType="chrome" -Dplatform="stage"`
### Run all tests with default setting
`mvn clean test`