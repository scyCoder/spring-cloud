提供者微服务 也是eureka client 
两个xml配文件，是为了实现负载均衡，当消费者消费时，会轮训的调用这两个不同端口的提供者
EurekaProducerClientApplication1 与配置文件application-producer1.yml绑定  启动第一个提供者  端口号为：2583
EurekaProducerClientApplication2 与配置文件application-producer2.yml绑定  启动第二个提供者  端口号位：2584