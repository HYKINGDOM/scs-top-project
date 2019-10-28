//package com.scs.top.project.framework.redis;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericToStringSerializer;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//import java.util.Objects;
//
///**
// * RedisConfig
// *
// * @author yihur
// **/
//@Slf4j
//@Configuration
//@AllArgsConstructor
//@AutoConfigureBefore(RedisAutoConfiguration.class)
//public class RedisConfig extends CachingConfigurerSupport {
//
//
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        //  设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
//        // 使用：进行分割，可以很多显示出层级关系
//        // 这里其实就是new了一个KeyGenerator对象，只是这是lambda表达式的写法，我感觉很好用，大家感兴趣可以去了解下
//        return (target, method, params) -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append(target.getClass().getName());
//            sb.append(":");
//            sb.append(method.getName());
//            for (Object obj : params) {
//                sb.append(":").append(obj);
//            }
//            String rsToUse = String.valueOf(sb);
//            log.info("自动生成Redis Key -> [{}]", rsToUse);
//            return rsToUse;
//        };
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // key采用String的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        // hash的key也采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
//        template.setKeySerializer(new GenericToStringSerializer<>(Object.class));
//        template.setHashValueSerializer(new GenericToStringSerializer<>(Object.class));
//        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
//        template.afterPropertiesSet();
//
//        return template;
//    }
//
////    /**
////     * 自定义CacheManager
////     */
////    @Bean
////    public CacheManager cacheManager(RedisTemplate redisTemplate) {
////        //全局redis缓存过期时间,一天
////        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1));
////        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
////        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
////    }
//}
