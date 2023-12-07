package com.tigon.config;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import com.tigon.model.TaiKhoan;
import com.tigon.service.TaiKhoanService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	TaiKhoanService taiKhoanService;
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Autowired
	HttpSession session;


	// Cung cấp nguồn dữ liệu đăng nhập
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {

				System.out.println("Username :" + username);
				TaiKhoan taikhoan = taiKhoanService.findIdByEmailOrPhone(username);
				String password = null;
				String roles = null;
				String hoten = null;
				 	
				// Nhập đúng thông tin dăng nhập
				if (taikhoan.getIDTAIKHOAN() != null) {
					TaiKhoan user = taiKhoanService.findById(taikhoan.getIDTAIKHOAN());
					System.out.println(user.getHOVATEN());
					password = pe.encode(user.getMATKHAU());
					roles = user.getVAITRO();
					hoten = user.getEMAIL();
					hoten = user.getHOVATEN();
					session.setAttribute("user", user.getIDTAIKHOAN());
					session.setAttribute("role", roles);
				}


				
				return User.withUsername(hoten).password(password).roles(roles).build();
			} catch (NoSuchElementException e) {
				throw new UsernameNotFoundException(username + "không tìm thấy!");
			}
		});
	}

	/*--Phân quyền sử dụng và hình thức đăng nhập--*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CSRF, CORS
		http.csrf().disable();
		// Phân quyền sử dụng
		http.authorizeRequests()
		.antMatchers("/admin").hasAnyRole("ADMIN","STAFF")
		.anyRequest().permitAll(); // anonymous
		
//		http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400);
		// Giao diện đăng nhập
		http.formLogin()
				.loginPage("/security/login/form")
				.loginProcessingUrl("/security/login") // [/login]
				.defaultSuccessUrl("/security/login/success", false)
				.failureUrl("/security/login/error");

		// Oauth2Login
		http.oauth2Login().loginPage("/oauth2/login/form").defaultSuccessUrl("/oauth2/login/success", true)
				.failureUrl("/oauth2/login/error").authorizationEndpoint().baseUri("/oauth2/authorization")
				.authorizationRequestRepository(getRepository()).and().tokenEndpoint()
				.accessTokenResponseClient(getToken());
		
		//set session hết hạn 
		http.rememberMe().tokenValiditySeconds(86400);

		// Điều khiển lỗi truy cập không đúng vai trò
		http.exceptionHandling().accessDeniedPage("/security/unauthoritied"); // [/error]
		// Đăng xuất
		http.logout().logoutUrl("/security/logoff") // [/logout]
				.logoutSuccessUrl("/security/logoff/success");
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken() {
		return new DefaultAuthorizationCodeTokenResponseClient();
	}

	// cơ chế mã hóa mật khẩu
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Cho phép truy xuất REST API từ bên ngoài(domain khác)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
