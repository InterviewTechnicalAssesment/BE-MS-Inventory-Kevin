package com.showcase.interview.msinventory.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Util {

	@Value("${timezone:Asia/Jakarta}")
	private String timezone;

	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	public static void logInfo(String message) {
		logger.info(message);
	}

	public static void logDebug(String message) {
		logger.debug(message);
	}

	public static void logError(String message) {
		logger.error(message);
	}

	public ZonedDateTime getTimeNow() {

		Instant nowUtc = Instant.now();
		ZoneId zoneID = ZoneId.of(timezone);
		ZonedDateTime now = ZonedDateTime.ofInstant(nowUtc, zoneID);

		return now;

	}

	public String shaEncryption(String data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < encodedhash.length; i++) {
				String hex = Integer.toHexString(0xff & encodedhash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			return e.toString();
		}
	}

}
