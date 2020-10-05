package data_classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeBetween
{
    /**
     * find the number of days between two dates
     * @param start beginning end point
     * @param end ending end point
     * @return number of days between the two end points. A negative number means end came before start, a
     * positive number means end cam after start, and zero means they're the same day
     */
    public static int numDays(LocalDate start, LocalDate end)
    {
        return (end.getYear() - start.getYear()) * 365 + end.getDayOfYear() - start.getDayOfYear() + 1;
    }

    /**
     * find the time to the second between two times
     * @param start beginning end point
     * @param end ending end point
     * @return time between the two end points in the form [hour,minute,second]
     */
    public static int[] hourMinuteSecond(LocalTime start, LocalTime end)
    {
        int[] time = new int[3];
        time[0] = end.getHour() - start.getHour();
        time[1] = end.getMinute() - start.getMinute();
        time[2] = end.getSecond() - start.getSecond();
        for (int i = 2; i > 0; i--)
            if (time[i] < 0) {
                time[i - 1]--;
                time[i] += 60;
            }
        return time;
    }

    /**
     * find the time between between two times on dates
     * @param start beginning end point
     * @param end ending end point
     * @return time between the two end points in the form [day,hour,minute,second]
     */
    public static int[] dayAndTime(LocalDateTime start, LocalDateTime end)
    {
        int[] time = new int[4], temp = hourMinuteSecond(start.toLocalTime(), end.toLocalTime());
        time[0] = numDays(start.toLocalDate(), end.toLocalDate());
        System.arraycopy(temp, 0, time, 1, 3);
        if (time[1] < 0) {
            time[0]--;
            time[1] += 24;
        }
        return time;
    }
}
