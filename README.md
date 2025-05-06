# 实现IoC容器
1. 新增xml应用上下文
    从xml解析beanDefinition，根据情况实例化bean，然后存储到一个容器中，以供使用。
2. 拆分xml应用上下文
    applicationContext使用reader解析resource得到beanDefinition，其中reader通过注入beanFactory，得到加载beanDefinition的能力。解析即是将resource中配置的属性读取出来放到beanDefinition中，加载是将beanDefinition放到beanFactory中。加载到beanFactory中之后，将beanDefinition实例化成真正的bean对象，并存放到singletonBeanRegistry里。加载完毕就可以通过applicationContext.getBean()获取加载好的bean对象进行使用。
3. 完善beanDefinition和setter注入等
    此容器支持setter注入和构造器注入，这两个能力在reader解析resource的过程中，存放到beanDefinition中的argumentValues和propertyValues中，然后在simpleBeanFactory中依次遍历两个列表，通过反射创建对应的bean对象并注入bean对象的属性。
4. 添加bean依赖
    bean可能嵌套依赖bean，此时，为propertyValue新增isRef字段，用于判断该属性是否是一个依赖，如果是则通过createBean方法创建它，形成一个调用链，最终创建出所有依赖的bean。
5. 解决循环依赖
    如果一个beanA依赖beanB，在解析resource时没有先解析出beanB，创建beanA的beanB属性时则会抛出没有这个bean的错误。在这个过程中，beanA除了没有设置beanB属性，它基本已经实例化好了，因此，在这个环节增加一个逻辑，就可以解决循环依赖的问题。具体来说，在beanA实例化到最后一步时（即还没有beanB属性），将beanA对象先存放到一个地方earlySingletonObjects，然后开始实例化beanB，由于beanB没有依赖其他bean，所以可以实例化成功，最后再把存储未完成的beanA拿出来设置beanB，这样就实例化完成了。这个例子还比较简单，更复杂一点的场景，是a、b相互依赖，a依赖b、b依赖c、c依赖a等等，同理，也可以使用这个逻辑进行处理。总的来说，解决bean循环依赖是把相互依赖的所有bean的实例化作为一个整体来创建。
6. 新增注解注入
    在xml中配置ref注入bean不够方便，注解方便了bean的注入和使用，但作为程序来说它还是在看不见的地方完成了xml的ref所做的事情。注解作用在实例变量上时，想要注解生效，则对象必须创建好，因此，做这个事情就在createBean之后。refresh方法就是遍历所有的beanDefinitaion创建所有的bean，在这个方法中就可以在bean初始化前、中、后分别对bean进行处理，也就是postProcessBeforeInitialization、init-method、postProcessAfterInitialization。具体做法应该是通过反射获取标注了注解的成员变量，把它初始化成bean然后注入属性
7. 新增refresh方法
    将以上所述内容整体包装一下，对外提供一个refresh方法，封装所有细节，作为容器启动的入口。这个方法包含容器启动的各个步骤，从bean工厂的创建，到bean对象的实例化、初始化，最后到容器加载，一切的bean处理都在这里完成。
8. 扩展beanFactory
    - ListableBeanFactory：将容器的bean作为一个集合来管理的工厂
    - ConfigurableBeanFactory：维护bean依赖管理、支持bean处理器的工厂
    - AutowireCapableBeanFactory：处理注解注入的工厂
    - ConfigurableListableBeanFactory：结合以上三者的工厂
    - DefaultListableBeanFactory：容器默认工厂，实现ConfigurableListableBeanFactory继承 AbstractAutowireCapableBeanFactory
9. 完善applicationContext
    - ApplicationContext：继承EnvironmentCapable、ListableBeanFactory、ConfigurableBeanFactory、ApplicationEventPublisher，定义getBeanFactory、refresh等核心方法
    - AbstractApplicationContenxt：实现ApplicationContext，实现接口中的继承得来的所有方法，其中有关bean的管理全部委托给子类中注入的BeanFactory处理。核心的refresh方法定义好流程，交给子类实现对应的抽象方法
    - ClassPathXmlApplicationContenxt：继承AbstractApplicationContext，注入DefaultListableBeanFactory，调用refresh方法
10. 添加容器事件
    - ApplicationEvent：应用事件，继承EventObject，维护事件消息
    - ApplicationListener：应用监听器，实现EventListener，实现事件处理
    - ApplicationEventPublisher：应用事件发布器，包含发布事件和添加监听器的方法
    - SimpleApplicationEventPublisher：简单应用事件发布器，实现应用事件发布器，里面维护一个监听器列表，实现发布事件和添加监听器
    - ContextRefreshEvent：继承ApplicationEvent，实现具体的方法
11. 添加环境
     - PropertyResolver：属性解析器，提供一堆解析属性的方法
     - Environment：继承PropertyResolver，提供获取文件的方法
     - EnvironmentCapable：提供获取Environment的方法

# 实现MVC
MVC的基本流程是：前端发送请求到控制器，控制器寻找对应模型，然后返回结果，视图返给前端生成页面。Servlet规范可以通过Filter或Servlet拦截，Spring MVC选择通过Servlet拦截所有请求，处理映射关系，调用业务逻辑代码，处理返回值给浏览器。开发人员只关注业务逻辑代码，也就是Bean。
1. 实现原始MVC
    web.xml配置servlet-class，init-param，load-on-startup、url-pattern等属性。当Tomcat的Servlet容器启动时，先读取web.xml配置，加载配置中的servlet，按规定拦截所有http请求。同实现IoC容器一样，将init-param中的配置xml中的bean通过reader加载到配置的servlet中。当请求来的时候，实现的doGet方法执行，根据url匹配获取相应的service，然后反射调用它的相应方法。

2. 扩展原始MVC
    通过xml配置bean比较繁琐，引入注解@RequestMapping。xml文件通过components、component-scan标签直接配置业务类所在的包。新增解析类读新标签将配置加载到servlet中。然后递归遍历所有配置的包中的所有类，通过反射创建后加载到servlet的成员变量中。最后找出这些类中被@RequestMapping注解的类，将注解里对应的url和类，url和方法加载到servlet中。请求来的时候，通过url获取对应的类和方法，再利用反射invoke调用该方法。

3. 结合IoC容器
    在Servlet规范中，服务器启动的时候，会根据web.xml文件来配置。因此，每个Java Web应用都必须包含一个web.xml文件，用来配置应用的全部配置信息，且它必须放在WEB-INF路径下。具体元素如下：

    ```xml
    <?xml version="1.0" encoding="UTF-8">
    <web-app>
    <display-name></display-name>  // 声明web应用的名字
    <description></description>  // 声明web应用的描述信息
    <context-param></context-param>  // 声明web应用的全局的初始化参数
    <listener></listener>  // 声明监听器
    <filter></filter>  // 声明过滤器
    <serlvet></servlet>  // 声明servlet
    <serlvet-mapping></servlet-mapping>  // 声明servlet访问路径
    <session-config></session-config>  // 声明session有关配置
    <error-page></error-page>  // 声明错误页
    </web-app>
    ```
    Servlet服务器（Tomcat）启动时，大概顺序如下：
    1. 读取web.xml的context-param，获取全局参数
    2. 创建一个ServletContext实例，全局有效
    3. 将全局参数存储在ServletContext中
    4. 创建listener中定义的监听器，调用contextInitialized方法完成初始化
    5. Tomcat完成启动后，再初始化filter
    6. 初始化servlet。参数load-on-startup（越小优先级越高）若配置了，自动启动servlet。未配置则等servlet调用时再初始化。参数init-param配置需要扫描的包。

    由这个顺序可以看出，想要把IoC容器结合到MVC中，可以在创建监听器的地方做处理。具体来说就是实现一个监听器来加载自己的Ioc容器，绑定到ServletContext中，然后实现一个Servlet来解析请求并处理请求。Controller由DispatcherServlet启动，Service由Listener启动，然后解析请求并根据对应的url找到对应的service处理请求。
3. 拆分DispatcherServlet
    将所有功能实现都放到DispatcherServlet中，使这个类比较臃肿。因此通过进一步分解，让它只负责解析请求，各种bean则通过专门的context来管理。
    1. service都通过XmlWebApplicationContext来加载管理
    2. controller都通过AnnotationConfgWebApplicationContext来加载管理
    3. 请求的解析通过HandlerMapping来管理映射关系
    4. 请求的处理通过HandlerAdapter来处理请求
    5. DispatcherServlet通过依赖以上四个组件做相应的处理
    

    
    


    























