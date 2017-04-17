package backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import backend.model.User;
import backend.repository.UserRepository;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class Application implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		User e = userRepository.findByLogin("admin");
		if (e == null) {
			addAdmin();
		}
	}

	private void addAdmin() {
		User admin = new User();
		admin.setFirstName("Marcin");
		admin.setLastName("Madej");
		admin.setEmail("marcinmadej1993@gmail.com");
		admin.setToken("f4f673cf-08d2-4e60-ad7c-5509d73a5688");
		admin.setType("admin");
		admin.setTelephone("535279542");
		admin.setLogin("admin");
		admin.setEnabled(true);
		admin.setPassword("21232f297a57a5a743894a0e4a801fc3");
		userRepository.save(admin);
	}
}


