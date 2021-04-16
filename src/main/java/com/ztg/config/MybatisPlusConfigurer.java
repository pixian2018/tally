package com.ztg.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @Description: MybatisPlus配置类
 * @author: zhoutg
 * @time: 2018/8/2 13:39
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfigurer {

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作,true调回到首页,false继续请求,默认false
        //paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量,默认500条,-1不受限制
        paginationInterceptor.setLimit(-1L);
        return paginationInterceptor;
    }

}
