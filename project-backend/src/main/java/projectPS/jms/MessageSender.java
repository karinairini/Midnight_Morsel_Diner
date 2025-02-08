package projectPS.jms;

import projectPS.dto.mail.SendingStatus;

public interface MessageSender<Request> {

    SendingStatus sendMessage(Request request);
}
