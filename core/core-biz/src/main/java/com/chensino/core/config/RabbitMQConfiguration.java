//package com.chensino.core.config;
//
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitMQConfiguration {
//
//
////    @Bean
////    FanoutExchange serverExchange() {
////        return new FanoutExchange("suc.addUser", true, false);
////    }
//
//    @Bean
//    MessageConverter createMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @RabbitListener(queues = "addUserQueue")
//    public void myListener1(SysUser message){
//        System.out.println("addUserQueue接收到的消息为：" + message);
//    }
//
//    @RabbitListener(queues = "payQueue")
//    public void myListener2(Object message){
//        System.out.println("payQueue接收到的消息为：" + message);
//    }
//}
