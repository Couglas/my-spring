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

    // sessionscope，在浏览器中请求，携带cookie，cookie中包含jseesioid保持用户和服务器之间的连接。有cookie的时候，scopedObject每次都一样
    // 和requestscope不同的是，实现上加了锁，避免同一个用户使用同一个浏览器，打开不同的tab，做多个请求造成数据不一致。requestscope每次不一样都是隔开的，所以没有加锁
    @Autowired
    private User user;

    @GetMapping("/index.html")
    public String index(Model model) {
        model.addAttribute("user", user);
        return "index";
    }
}
