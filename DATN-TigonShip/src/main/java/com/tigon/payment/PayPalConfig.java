package com.tigon.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class PayPalConfig {

	public static String clientId = "AfmEnbmDELCNJRAKggm2MVwLjsrI5vBXc0kl_R4LIl1i_Pc3p52ZRmG01D3ZNfFCSKcZvnG6hlMm_3_K";

	public static String clientSecret = "EIo0AVxQVV2JNGo2PFC5gA56Hym9obkHv4nkAaWnLa8b9EX5bqf5ahI-GEihaJ3g-VKGliZKR5Wvw_iM";

	public static String mode = "sandbox";
 
	@Bean
	public Map<String, String> paypalSdkConfig(){
		Map<String, String> sdkConfig = new HashMap<>();
		sdkConfig.put("mode", mode);
		return sdkConfig;
	}
	
	@Bean
	public OAuthTokenCredential authTokenCredential() {
		return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
	}
		
	@Bean
	public APIContext apiContext() throws PayPalRESTException{
		APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
		apiContext.setConfigurationMap(paypalSdkConfig());
		return apiContext;
	}

}