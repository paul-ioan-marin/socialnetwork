package socialnetwork.domain.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Constants {
    public static final String ALPHABET_VALIDATOR = "[a-zA-Z]+";
    public static final String EMAIL_VALIDATOR = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    public static final int ID_COLUMN = 1;
    public static final int EMAIL_COLUMN = 2;
    public static final int FIRSTNAME_COLUMN = 3;
    public static final int LASTNAME_COLUMN = 4;

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String FNAME = "first_name";
    public static final String LNAME = "last_name";
    public static final String FRIEND1 = "friend_1";
    public static final String FRIEND2 = "friend_2";
    public static final String STATUS = "status";
    public static final String FRIENDDATE = "date";

    public enum Status {
        ACCEPTED("accepted"), PENDING("pending"), DECLINED("declined");
        private final String text;
        Status(String status) { text = status; }
        public String getValue() { return text; }
        public static Status fromString(String text) {
            return switch (text) {
                case "accepted" -> Status.ACCEPTED;
                case "pending" -> Status.PENDING;
                case "declined" -> Status.DECLINED;
                default -> throw new IllegalArgumentException("invalid status");
            };
        }
    }

    public static DateTimeFormatter DATEFORMATTER =
            new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy[ [HH][:mm][:ss]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();
    public static LocalDateTime NULLDATE = LocalDateTime.parse("01/01/0001 00:00:00",DATEFORMATTER);
}
