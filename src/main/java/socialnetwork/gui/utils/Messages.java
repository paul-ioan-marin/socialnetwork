package socialnetwork.gui.utils;

import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.GroupMessage;
import socialnetwork.service.AbstractService;

import java.util.List;
import java.util.stream.Collectors;

import static socialnetwork.domain.constants.Constants.DATEFORMATTER;


public class Messages {
    public static List<MessageChat> messagesInGroup(AbstractService service, GroupMessage groupMessage, User user) throws Exception {
        return service.messagesInGroup(groupMessage).stream()
                .map( message -> new MessageChat(message, user))
                .collect(Collectors.toList());
    }

    public static String messageForm(ReplyMessage message, User user) {
        StringBuilder toShow = new StringBuilder();
        toShow.append("At ")
                .append(message.getDate().format(DATEFORMATTER))
                .append(" ");
        if (!message.getFrom().equals(user))
            toShow.append(message.getFrom().getFirstName())
                    .append(" ")
                    .append(message.getFrom().getLastName());
        else toShow.append("you");
        toShow.append(" ");
        if (message.getReply() != null)
            toShow.append("replied to ")
                    .append(replyForm(message.getReply().getMessage()));
        else toShow.append("said");
        toShow.append(":")
                .append("\n")
                .append(message.getMessage());
        return toShow.toString();
    }

    public static String replyForm(String message) {
        if (message.length() > 15)
            return message.substring(0,10) + "...";
        else return message;
    }

    public static String chatTitle(GroupMessage groupMessage, User user) {
        String result = "";
        if (groupMessage.getMembers().size() > 3) {
            result = groupMessage.everyoneBut(user).get(0).getFirstName() + ", " + groupMessage.everyoneBut(user).get(1).getFirstName()
                    + " and other " + (groupMessage.getMembers().size() - 3);
        } else if (groupMessage.getMembers().size() == 3) {
            result = groupMessage.everyoneBut(user).get(0).getFirstName() + " and " + groupMessage.everyoneBut(user).get(1).getFirstName();
        } else if (groupMessage.getMembers().size() == 2) {
            result = groupMessage.everyoneBut(user).get(0).getFirstName() + " " + groupMessage.everyoneBut(user).get(0).getLastName();
        }
        return result;
    }
}
