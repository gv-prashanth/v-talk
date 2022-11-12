package com.vadrin.vtalk.services.notifications;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public interface NotificationService {

  public void notify(String receiver) throws IOException;

  public int getPriority();
  
  public default boolean isRecentlyNotified(Timestamp lastNotifiedTime) {
	  System.out.println("NotificationService lastNotifiedTime " + lastNotifiedTime.getTime());
	  System.out.println("NotificationService InstantTime " + Timestamp.from(Instant.now()).getTime());
	  return (Timestamp.from(Instant.now()).getTime() - lastNotifiedTime.getTime() < 300000);
  }

}
