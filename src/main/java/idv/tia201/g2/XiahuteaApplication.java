package idv.tia201.g2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@ServletComponentScan
public class XiahuteaApplication {


	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(XiahuteaApplication.class);
		builder.application().setAdditionalProfiles("xiahutea");
		builder.run( args);

	}

}
