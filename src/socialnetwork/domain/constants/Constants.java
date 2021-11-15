package socialnetwork.domain.constants;

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
}
