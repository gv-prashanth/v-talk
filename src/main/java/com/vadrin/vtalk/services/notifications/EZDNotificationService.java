package com.vadrin.vtalk.services.notifications;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.cloud.Timestamp;
import com.vadrin.vtalk.models.UserInfo;
import com.vadrin.vtalk.repositories.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EZDNotificationService implements NotificationService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserInfoRepository userInfoRepository;

	public void notify(String receiver) throws IOException, InterruptedException, ExecutionException {
		Optional<UserInfo> userInfo = userInfoRepository.findById(receiver);
		if (userInfo.isPresent()) {
			if (isRecentlyNotified(userInfo.get().getLastNotification())) {
				log.info("WBLabourNotificationService Recently notified. Not going to spam. "
						+ userInfo.get().getUsername());
				return;
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			Map<String, String> body = new HashMap<>();
			body.put("mobile", "+91" + userInfo.get().getPhone());
			HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
			String response = restTemplate.postForObject("https://force.eazydiner.com/web/otp", request, String.class);
			log.error("Response of EZDNotificationService" + response);
			UserInfo toSave = userInfo.get();
			toSave.setLastNotification(Timestamp.now());
			userInfoRepository.save(toSave);
		}
	}

	@Override
	public int getPriority() {
		return 1;
	}

}
