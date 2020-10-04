public class Main {
    public static void main(String[] args) {
        Calendar calendar = new Calendar();
        System.out.println(calendar.getDateTime());
        calendar.advanceDay(3);
        System.out.println(calendar.getDateTime());
    }
}
