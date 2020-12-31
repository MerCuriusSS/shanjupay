package com.shanjupay.uaa.config;


import com.shanjupay.uaa.integration.RestOAuth2WebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private AuthorizationCodeServices authorizationCodeServices;

	@Autowired
	private AuthenticationManager authenticationManager;


	/**
	 * 1.客户端详情相关配置
	 */

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 *
	 * @param dataSource
	 * @return
	 */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
		ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
		((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder());//passwordEncoder: clientDetail 信息里的client_secret字段加解密器。
        return clientDetailsService;
    }


	/**
	 * 配置从哪里获取ClientDetails信息。
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {
		clients.withClientDetails(clientDetailsService);//ClientDetails信息加载实现类。
	}

	/**
	 * 2.配置令牌服务(token services)
	 */
    @Bean
   	public AuthorizationServerTokenServices tokenService() {
       	DefaultTokenServices service=new DefaultTokenServices();
       	service.setClientDetailsService(clientDetailsService);
       	service.setSupportRefreshToken(true);
   		service.setTokenStore(tokenStore);

		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(/*tokenEnhancer(),*/ accessTokenConverter));
   		service.setTokenEnhancer(tokenEnhancerChain);

   		service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
   		service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
   		return service;
    }

	/**
	 * 3.配置令牌（token）的访问端点
	 */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

	/**
	 * 注入相关配置：
	 * 1. 密码模式下配置认证管理器 AuthenticationManager
	 * 2. 设置 AccessToken的存储介质tokenStore， 默认使用内存当做存储介质。
	 * 3. userDetailsService注入
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authenticationManager)//密码模式下配置认证管理器 AuthenticationManager
				.authorizationCodeServices(authorizationCodeServices)
				.tokenServices(tokenService())
				.pathMapping("/oauth/confirm_access", "/confirm_access")
				.pathMapping("/oauth/error", "/oauth_error")
				.allowedTokenEndpointRequestMethods(HttpMethod.POST)
				.exceptionTranslator(new RestOAuth2WebResponseExceptionTranslator());
	}


	/**
	 *  配置：安全检查流程,用来配置令牌端点（Token Endpoint）的安全与权限访问
	 *  默认过滤器：BasicAuthenticationFilter
	 *  1、oauth_client_details表中clientSecret字段加密【ClientDetails属性secret】
	 *  2、CheckEndpoint类的接口 oauth/check_token 无需经过过滤器过滤，默认值：denyAll()
	 * 对以下的几个端点进行权限配置：
	 * /oauth/authorize：授权端点
	 * /oauth/token：令牌端点
	 * /oauth/confirm_access：用户确认授权提交端点
	 * /oauth/error：授权服务错误信息端点
	 * /oauth/check_token：用于资源服务访问的令牌解析端点
	 * /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话
	 **/
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security){
		security
				.tokenKeyAccess("permitAll()")//spring security认证方法：允许无条件访问
				.checkTokenAccess("permitAll()")//spring security认证方法：允许无条件访问
				.allowFormAuthenticationForClients()//允许表单认证
		;
	}

}
