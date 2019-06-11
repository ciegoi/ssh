package com.itheima.ssm.controller;

import com.cy.domain.SysLog;
import com.cy.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Controller
@Aspect
public class LogAop {
    private Date visitTime;
    private Class clazz;
    private Method method;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService sysAopService;

    /**
     * 前置通知,获取开始时间,执行类是哪一个,使用的那个方法
     * @param jp
     */
    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws Exception {
        visitTime = new Date();    //当前时间
        clazz = jp.getTarget().getClass();  //具体访问的类
        String methodName = jp.getSignature().getName();    //获取方法名称
        Object[] args = jp.getArgs();   //获取访问参数

        if (args == null || args.length == 0){
            method = clazz.getMethod(methodName);   //只能获取无参
        } else {
            Class[] classAsgs = new Class[args.length];
            for (int i = 0; i <args.length ; i++) {
                classAsgs[i] = args[i].getClass();
            }
            clazz.getMethod(methodName,classAsgs);
        }
    }

    /**
     * 后置通知
     * @param jp
     */
    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception{
        long time = new Date().getTime() - visitTime.getTime(); //访问时长

        String url ="";
        //获取URL
        if (clazz != null && method != null && clazz != LogAop.class){
            //1.获取类上的@RequestMapping("/orders")
            RequestMapping clazzAnnotation = (RequestMapping)clazz.getAnnotation(RequestMapping.class);
            if (clazzAnnotation != null){
                String[] clazzValue = clazzAnnotation.value();
                //1.获取方法上的@RequestMapping("/")
                RequestMapping methodAnnotation = (RequestMapping) method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null){
                    String[] methodValue = methodAnnotation.value();
                    url = clazzValue[0] + methodValue[0];

                    //获取ip
                    String ip = request.getRemoteAddr();

                    //获取当前操作用户名
                    SecurityContext context = SecurityContextHolder.getContext();
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();
                    System.out.println("当前用户" + username);

                    //将日志相关信息封装到LogAop中
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time);  //执行时长
                    sysLog.setIp(ip);
                    sysLog.setMethod("类名" + clazz.getName() + "方法名" +method.getName());
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(visitTime);
                    sysLog.setUrl(url);

                    //调用service完成操作
                   sysAopService.save(sysLog);
                }
            }
        }

    }
}
