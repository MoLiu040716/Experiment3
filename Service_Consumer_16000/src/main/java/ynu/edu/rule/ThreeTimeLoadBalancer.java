//package ynu.edu.rule;
//
//import org.apache.coyote.Request;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.DefaultResponse;
//import org.springframework.cloud.client.loadbalancer.EmptyResponse;
//import org.springframework.cloud.client.loadbalancer.Response;
//import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//public class ThreeTimeLoadBalancer implements ReactorServiceInstanceLoadBalancer {
//    private int total=0;//以及被调用的次数
//    private int instance_index=0;//当前提供服务的实例
//    private final String serviceId;
//    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierObjectProvider;
//
//    public ThreeTimeLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierObjectProvider, String serviceId) {
//        this.serviceInstanceListSupplierObjectProvider = serviceInstanceListSupplierObjectProvider;
//        this.serviceId=serviceId;
//    }
//
//    @Override
//    public Mono<Response<ServiceInstance>> choose(Request request) {
//        ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierObjectProvider.getIfAvailable();
//        return ReactorServiceInstanceLoadBalancer.super.choose();
//    }
//
//
//    //每个服务调用三次
//    public Response<ServiceInstance> getInstanseResponse(List<ServiceInstance> instanceList){
//        if (instanceList.isEmpty()){
//            return new EmptyResponse();
//        }
//        int size = instanceList.size();
//        ServiceInstance serviceInstance=null;
//        while (serviceInstance==null){
//            if (this.total<3){
//                serviceInstance=instanceList.get(this.instance_index);
//                this.total++;
//            }
//            else {
//                this.instance_index++;
//                this.total=0;
//                if (this.instance_index>=size){
//                    this.instance_index=0;
//                }
//            }
//        }
//        return new DefaultResponse(serviceInstance);
//    }
//}
