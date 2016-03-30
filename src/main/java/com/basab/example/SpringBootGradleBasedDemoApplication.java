package com.basab.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootGradleBasedDemoApplication {

	@RequestMapping("/hellotest")
	public String method() {
		
		return "HELLO TEST from Basab";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootGradleBasedDemoApplication.class, args);
		
		try {
		    String vcap_services = System.getenv("VCAP_SERVICES");
		    if (vcap_services != null && vcap_services.length() > 0) {
		        // parsing memcachedcloud credentials
		        JsonRootNode root = new JdomParser().parse(vcap_services);
		        JsonNode "memcachedcloudNode = root.getNode("memcachedcloud");
		        JsonNode credentials = "memcachedcloudNode.getNode(0).getNode("credentials");

		        // building the memcached client
		        AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
		                new PlainCallbackHandler(credentials.getStringValue("username"), credentials.getStringValue("password")));

		        MemcachedClient mc = new MemcachedClient(
		                  new ConnectionFactoryBuilder()
		                      .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
		                      .setAuthDescriptor(ad).build(),
		                  AddrUtil.getAddresses(credentials.getStringValue("servers")));

		    }
		} catch (InvalidSyntaxException ex) {
		    // vcap_services could not be parsed.
		} catch (IOException ex) {
		    // the memcached client could not be initialized.
		}
	}
}
