package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TelegramBotScheduler {

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private NotificationTaskRepository repository;

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendTasksByCurrentMinute() {
        LocalDateTime currentMinute = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> notificationTasks = repository.findByNotificationTime(currentMinute);
        notificationTasks.forEach(notificationTask -> {
            if (notificationTask.getNotificationTime().equals(currentMinute)) {
                telegramBot.execute(new SendMessage(notificationTask.getChatId(), notificationTask.toString()));
            }
        });
    }
}
