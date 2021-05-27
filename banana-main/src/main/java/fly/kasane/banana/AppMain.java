package fly.kasane.banana;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;




@MapperScan(value = "fly.kasane.banana.mapper")
@SpringBootApplication
public class AppMain {
    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }

    @GetMapping("/")
    public String indexString(){
        return "Welcome to System";
    }
}
