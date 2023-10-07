package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String notificationText;
    private LocalDateTime notificationTime;

    public NotificationTask() {
    }

    public NotificationTask(Long chatId, String notificationText, LocalDateTime notificationTime) {
        this.chatId = chatId;
        this.notificationText = notificationText;
        this.notificationTime = notificationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) &&
                Objects.equals(notificationText, that.notificationText)
                && Objects.equals(notificationTime, that.notificationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, notificationText, notificationTime);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", notificationText='" + notificationText + '\'' +
                ", notificationTime=" + notificationTime +
                '}';
    }
}
