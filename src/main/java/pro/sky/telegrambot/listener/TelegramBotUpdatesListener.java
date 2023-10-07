package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Pattern PATTERN = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final NotificationTaskRepository repository;

    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationTaskRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            Message message = update.message();
            logger.info("Processing update: {}", update);
            if ("/start".equalsIgnoreCase(message.text())) {
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Hello!"));
            } else {
                String text = message.text();
                Long chatId = message.chat().id();
                Matcher matcher = PATTERN.matcher(text);
                if (matcher.matches()) {
                    try {
                        String date = matcher.group(1);
                        String item = matcher.group(3);
                        LocalDateTime messageTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                        NotificationTask notificationTask = new NotificationTask(chatId, item, messageTime);
                        telegramBot.execute(new SendMessage(update.message().chat().id(), notificationTask.toString()));
                        repository.save(notificationTask);
                    } catch (DateTimeParseException e) {
                        logger.error("failed to parse date/time");
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Incorrect date"));
                    }
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
