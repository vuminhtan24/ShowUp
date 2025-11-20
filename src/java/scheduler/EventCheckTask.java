package scheduler;

import dal.EventDAO;
import java.util.List;
import model.Event;

public class EventCheckTask implements Runnable {

    @Override
    public void run() {
        try {
            EventDAO dao = new EventDAO();
            List<Event> list = dao.getExpiredEventsNotNotified();

            for (Event e : list) {
                // Gửi email thông báo hết hạn
                MailService.sendExpiredEmail(e.getGmail(), e.getEventName());

                // Đánh dấu đã thông báo
                dao.markAsNotified(e.getEventId());

                System.out.println("Sent expired email for event: " + e.getEventName());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        EventCheckTask e = new EventCheckTask();
        e.run();
        
    }
}
