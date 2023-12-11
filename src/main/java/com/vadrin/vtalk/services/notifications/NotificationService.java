package com.vadrin.vtalk.services.notifications;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.cloud.Timestamp;

public interface NotificationService {

  public void notify(String receiver) throws IOException, InterruptedException, ExecutionException;

  public int getPriority();
  
  public default boolean isRecentlyNotified(Timestamp lastNotifiedTime) {
	  return (Timestamp.now().getSeconds() - lastNotifiedTime.getSeconds() < 1200);
  }

}
