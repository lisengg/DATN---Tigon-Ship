package com.tigon.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PhoneNumberVerificationController {

	public String TWILIO_ACCOUNT_SID = "AC78cd4c1efbf2296951f59f809abbacd5";
	public String TWILIO_AUTH_TOKEN = "4557bb203078a30966c30d566b8e8304";
	public String oldPhone;

	@GetMapping(value = "/sendSMS")
	public ResponseEntity<String> sendSMS() {

		Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

		Message.creator(new PhoneNumber("+84794953695"), new PhoneNumber("+17076201485"), "Hello from Twilio").create();

		return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
	}

	@GetMapping(value = "/generateOTP")
	public ResponseEntity<String> generateOTP() {

		Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

		Verification verification = Verification.creator("VA75de71c7e520563528427d730d7cb3cb", // this is your
																								// verification sid
				"+84794953695", // this is your Twilio verified recipient phone number
				"sms") // this is your channel type
				.create();

		System.out.println(verification.getStatus());

		log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());

		return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
	}
//	@GetMapping("/verifyOTP")
//	public ResponseEntity<?> verifyUserOTP() throws Exception {
//		Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
//
//		try {
//
//			VerificationCheck verificationCheck = VerificationCheck.creator("VA75de71c7e520563528427d730d7cb3cb","68123")
//					.setTo("+84794953695").create();
//
//			System.out.println(verificationCheck.getStatus());
//
//		} catch (Exception e) {
//			return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
//		}
//		return new ResponseEntity<>("This user's verification has been completed successfully", HttpStatus.OK);
//	}
}
