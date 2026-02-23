package com.ogap.gymapp.modules.notifications.service;

import com.ogap.gymapp.modules.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

   public void sendPhaseAdvancedNotification(User user, String newPhaseName) {
      log.info("🔔 PUSH NOTIFICATION STUB: User {} (Email: {}) advanced to phase '{}'",
            user.getId(), user.getEmail(), newPhaseName);
   }

   public void sendCourseCompletedNotification(User user, String courseName) {
      log.info("🏆 PUSH NOTIFICATION STUB: User {} (Email: {}) completed course '{}'",
            user.getId(), user.getEmail(), courseName);
   }
}
