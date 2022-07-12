package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberController {
    // @Controller 가 있는 객체를 만나게 되면 스프링 컨테이너 안에 해당 memberController 라는 객체를 생성해서 스프링이 관리한다.

//    private final MemberService memberService = new MemberService();
    // new MemberService를 할 경우, 다른 곳에서 사용하는 memberService 와 다른 객체가 되게 된다. 하나의 인스턴스를 생성하여 공유하여야 하므로 위와 같이 쓰지 않는다.

    private final MemberService memberService;

    @Autowired //생성자에 Autowired 가 되어있으면 생성자의 파라미터 스프링컨테이너에서 가져와 매핑해준다.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass() );
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
