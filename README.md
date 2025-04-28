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





























