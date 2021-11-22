package socialnetwork.domain.constants;

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
    public static final String TEXTMSG = "text_message";
    public static final String TIMESTAMP = "timestamp";
    public static final String FROM = "id_from";
    public static final String TO = "id_group_to";
    public static final String BASEMSG = "base_message";

    public static DateTimeFormatter DATEFORMATTER =
            new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy[ [HH][:mm][:ss]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();
    public static LocalDateTime NULLDATE = LocalDateTime.parse("01/01/0001 00:00:00",DATEFORMATTER);

}
