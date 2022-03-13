package IS296.CRMApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import config.CRMConfig;


public class CrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CRMConfig.class);
	}

}
