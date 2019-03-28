package com.targas.erp.utilities;

import com.targas.erp.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyWebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor()).addPathPatterns(new String[]{ "/","/profil","/affectation","/etablissements","/filieres","/groupes","/salles","/utilisateurs","/emploi","/emploi/**","/niveaux","/affectation/**","/cours/**"});
    }

    @Override
    public void addViewControllers (ViewControllerRegistry registry) {
//        ViewControllerRegistration r = registry.addViewController("/");
//        r.setViewName("accueil");
//        r.setStatusCode(HttpStatus.GONE);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/assets/**",
                "/images/**"
                )
                .addResourceLocations(
                        "classpath:/static/assets/",
                        "classpath:/static/images/");
    }
}
