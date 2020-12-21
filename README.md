# spring-boot-admin-and-client
PoC: launch Spring-boot-admin and a client on different ports in a single application

Answer to StackOverflow question: [How to migrate spring boot admin server and client into one spring boot application](https://stackoverflow.com/questions/65211628/how-to-migrate-spring-boot-admin-server-and-client-into-one-spring-boot-applicat/65328951#65328951)

Problems with this approche:
- application with should launch 2 servers: the `client` on port `8080` and the admin-server on port `8081`
- the client and the server should have separate configurations

### Lets start with the configuration
We could separate the multiple configuration by using a `profile` so we can take advantage of [Profile Specific Files](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-files-profile-specific)

[application-client.properties]()
```
server.address=localhost
spring.boot.admin.client.url = http://localhost:8081/
management.endpoints.web.exposure.include = *
management.endpoint.health.show-details= always

spring.application.name=client-app
spring.boot.admin.client.instance.name=client-app
```

[application-server.properties]()
```
server.address=localhost
server.port=8081
```

### Launch the server and the client with the correct profile

(DemoApplication)[]
```
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication admin = new SpringApplication(DemoAdminServer.class);
		admin.setAdditionalProfiles("admin");
		admin.run(args);

		SpringApplication client = new SpringApplication(DemoClient.class);
		client.setAdditionalProfiles("client");
		client.run(args);
	}
}

@EnableAdminServer
@Configuration
@EnableAutoConfiguration
@Profile("admin")
class DemoAdminServer {

}

@SpringBootApplication
@RestController
@Profile("client")
class DemoClient {

	@GetMapping
	public String hello(){
		return "Hello world!";
	}

}
```

Launch the Application and we should be good... [client](http://localhost:8080) and [admin-server](http://localhost:8081/wallboard)
