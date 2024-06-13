package me.qpneruy.timerplugin.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
/*
 * Những thay đổi đã được thực hiện:
 *
 * - Thêm ghi chú JavaDoc: Mỗi phương thức đều được bổ sung ghi chú JavaDoc để giải thích rõ ràng chức năng và cách sử dụng.
 * - Sử dụng Locale: Thay vì mặc định định dạng ngày tháng theo hệ thống, sử dụng Locale.getDefault() để đảm bảo code hoạt động chính xác với nhiều ngôn ngữ và vùng miền khác nhau.
 * - Chuẩn hóa tên biến: Đổi tên biến Isdisparity thành isDisparity để tuân thủ quy tắc đặt tên trong Java (camelCase cho tên phương thức).
 * - Tối ưu code: Sử dụng switch expression (Java 14+) để code ngắn gọn và dễ đọc hơn.
 * - Bổ sung comment: Bổ sung comment giải thích chi tiết cho phương thức getDayOfWeek.
 */
/**
 * Lớp cung cấp các tiện ích liên quan đến thời gian.
 */
public class TimeCalibarate {

    /**
     * Lấy thời gian hiện tại theo định dạng được chỉ định.
     *
     * @param format Định dạng thời gian (ví dụ: "HH:mm", "dd/MM/yyyy").
     * @return Chuỗi thời gian theo định dạng đã cho.
     */
    public static String getTime(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault()); // Sử dụng Locale.getDefault()
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        Date currentTime = new Date();
        return dateFormat.format(currentTime);
    }

    /**
     * Kiểm tra xem có sự chênh lệch thời gian hay không.
     *
     * @param time Thời gian ở dạng chuỗi (ví dụ: "00").
     * @return true nếu có sự chênh lệch, ngược lại là false.
     */
    public static boolean isDisparity(String time) {
        return time.equals("00");
    }

    /**
     * Lấy tên thứ trong tuần hiện tại.
     *
     * @return Tên thứ trong tuần (ví dụ: "Chủ Nhật", "Thứ Hai").
     */
    public static String getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Sử dụng switch expression (Java 14+) để code gọn hơn.
        return switch (dayOfWeek) {
            case Calendar.SUNDAY -> "Chu_Nhat";
            case Calendar.MONDAY -> "Thu_Hai";
            case Calendar.TUESDAY -> "Thu_Ba";
            case Calendar.WEDNESDAY -> "Thu_Tu";
            case Calendar.THURSDAY -> "Thu_Nam";
            case Calendar.FRIDAY -> "Thu_Sau";
            case Calendar.SATURDAY -> "Thu_Bay";
            default -> "";
        };
    }
}
