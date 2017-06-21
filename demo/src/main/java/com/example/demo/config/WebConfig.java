package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.example.demo.util.inteceptor.Inteceptor;

@Configuration
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Value("${server.port}")
    private String serverport;
    @Value("${spring.profiles.active}")
    private String springprofilesactive;
    @Value("${local.storage.root}")
    private String locationTmp;
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
		final MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("200MB");
		factory.setMaxRequestSize("200MB");
		factory.setLocation(this.locationTmp);
		return factory.createMultipartConfig();
	}
    
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		WebConfig.log.info("*******************************************************************");
		WebConfig.log.info("  profiles active is: " + this.springprofilesactive+", server port is: " + this.serverport);
		WebConfig.log.info("*******************************************************************");
    		
	}

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //// 以下为Excel数据导出配置
    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Bean
    public ViewResolver contentNegotiatingViewResolver(final ContentNegotiationManager manager) {
        final ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        // Define all possible view resolvers
        final List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
        resolvers.add(this.excelViewResolver());
        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    @Bean
    public ViewResolver excelViewResolver() {
        return new ViewResolver() {
            @Override
            public View resolveViewName(final String viewName, final Locale locale) throws Exception {
                if (!WebConfig.this.applicationContext.containsBean(viewName)) {
                    return null;
                }
                final Object bean = WebConfig.this.applicationContext.getBean(viewName);
                if (!(bean instanceof AbstractXlsView)) {
                    return null;
                }
                return (View) bean;
            }
        };
    }
    
	/**
	 * 注册拦截器的
	 * 注册拦截器，方法是不能改名字的，因为是重写的
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new Inteceptor());
		System.out.println("拦截器注册完成.....");
	}
}
