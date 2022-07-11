package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // 매핑 url이 존재하면 return 된 home.html 을 먼저 찾아가고, 없을 경우 index.html 호출
    public String home() {
        return "home";
    }
}
