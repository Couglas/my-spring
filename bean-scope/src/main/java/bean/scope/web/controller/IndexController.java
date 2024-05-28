package bean.scope.web.controller;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页controller
 *
 * @author couglas
 * @since 2024/5/28
 */
@Controller
public class IndexController {

    // requestscope，生成的对象叫scopedTarget.user，在AbstractRequestAttributesScope#get方法中，scopedObject每次都不一样。每次生成的都不一样。
    // 但是autowired被cglib代理的user对象都是同一个的
    @Autowired
    private User user;

    @GetMapping("/index.html")
    public String index(Model model) {
        model.addAttribute("user", user);
        return "index";
    }
}
