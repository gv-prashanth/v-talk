package com.vadrin.vtalk.services.notifications;

import java.io.IOException;

public interface NotificationService {

  public void notify(String receiver) throws IOException;

  public int getPriority();

}
