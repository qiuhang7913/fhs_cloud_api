框架整体使用springboot

1.fhs个人javaee框架cloud api版本
2.微服务使用spring cloud Greenwich.M3
3.目前体系当中使用到了cloud_security|cloud_oath2.0|eureka 注册中心|cloud-hystrix熔断|cloud-ribbon负载均|cloud-zuul做网关中心


4.项目体系
fhs_common ===== 公共模块
fhs_eureka_server ===== 注册中心服务器
fhs_gateway ===== 网关
fhs_generator ===== 代码生成器
fhs_manager ===== 用户认证中心
(....)
备注:资源服务中心略