package ynu.edu.controller;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ynu.edu.entity.CommonResult;
import ynu.edu.entity.User;
import ynu.edu.feign.ServiceProviderService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private ServiceProviderService serviceProviderService;
    @GetMapping("/getCartById/{userId}")
    @CircuitBreaker(name = "backendA" , fallbackMethod = "getCartByIdDown1")
//    @Retry(name="retry1", fallbackMethod = "getCartByIdDown")

    public CommonResult<User> getCartById(@PathVariable("userId") Integer userId){
        CommonResult<User> result = serviceProviderService.getUserById(userId);
        return result;
    }


    @GetMapping("/getCartByName/{userName}")
    @CircuitBreaker(name = "backendB" , fallbackMethod = "getCartByIdDown2")
    public CommonResult<User> getCartByName(@PathVariable("userName") Integer userName){
        CommonResult<User> result = serviceProviderService.getUserById(userName);
        return result;
    }

    @GetMapping("/getCartByNum/{userNum}")

    @RateLimiter(name = "rate1", fallbackMethod = "getCartByIdDown3")
    public CommonResult<User> getCartByNum(@PathVariable("userNum") Integer userNum){
        CommonResult<User> result = serviceProviderService.getUserById(userNum);
        return result;
    }
    public CommonResult<User> getCartByIdDown1(Integer userId, Throwable e){
        e.printStackTrace();
        String message="获取用户"+userId+"信息的服务当前被熔断，因此方法降级1";
        System.out.println(message);
        CommonResult<User> result = new CommonResult<>(400,message,new User());
        return result;
    }
    public CommonResult<User> getCartByIdDown2(Integer userName, Throwable e){
        e.printStackTrace();
        String message="获取用户"+userName+"信息的服务当前被熔断，因此方法降级2";
        System.out.println(message);
        CommonResult<User> result = new CommonResult<>(400,message,new User());
        return result;
    }

    public CommonResult<User> getCartByIdDown3(Integer userNum, Throwable e){
        e.printStackTrace();
        String message="获取用户"+userNum+"信息的服务当前被限流，因此方法降级1";
        System.out.println(message);
        CommonResult<User> result = new CommonResult<>(400,message,new User());
        return result;
    }

    @GetMapping("/getCartByid/{userId}")
    @LoadBalanced
    @Bulkhead(name="bulkhead1",type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "getCartByIdDown")
    public CompletableFuture<User> getCartByid(@PathVariable("userId") Integer userId){
        CompletableFuture<User> result = CompletableFuture.supplyAsync(() ->{return serviceProviderService.getUserById(userId).getResult();});
        System.out.println("运行正常");
        return result;
    }
    public  CompletableFuture<User> getCartByIdDown(Integer userId, Throwable e){
        e.printStackTrace();
        String message="当前服务火爆";
        System.out.println(message);
        CompletableFuture<User> result = CompletableFuture.supplyAsync(() ->{return new CommonResult<>(400,message,new User()).getResult();});
        return result;
    }
}
