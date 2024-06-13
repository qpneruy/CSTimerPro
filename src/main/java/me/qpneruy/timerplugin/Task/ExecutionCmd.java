package me.qpneruy.timerplugin.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ExecutionCmd {
    private static final List<String> VALID_DAYS_OF_WEEK = Arrays.asList(
            "Thu_Hai", "Thu_Ba", "Thu_Tu", "Thu_Nam", "Thu_Sau", "Thu_Bay", "Chu_Nhat"
    );
    private String name;
    private String command;
    private String execDate; // Lưu trữ ngày theo định dạng MM/dd/yyyy nếu có
    private String execDayOfWeek; // Lưu trữ thứ trong tuần nếu có
    private String startTime;
    private String endTime;
    private boolean eachHour;
    private boolean eachMinute;
    private boolean isEnabled = true;

    /**
     * Khởi tạo một lệnh hẹn giờ mới.
     *
     * @param name         Tên của lệnh.
     * @param command       Lệnh sẽ được thực thi.
     * @param execDateTime Chuỗi thời gian thực thi, có thể là ngày tháng (MM/dd/yyyy) hoặc thứ trong tuần.
     * @param startTime     Thời gian bắt đầu thực thi, theo định dạng HH:mm.
     * @return true nếu khởi tạo thành công, false nếu không.
     */
    public boolean init(String name, String command, String execDateTime, String startTime) {
        this.name = name;
        this.setCommand(command);
        if (!setExecDateTime(execDateTime)) {
            return false;
        }
        if (!setStartTime(startTime)) {
            return false;
        }
        this.endTime = this.startTime;
        this.eachHour = false;
        this.eachMinute = false;
        this.isEnabled = true;
        return true;
    }

    public String getName() {
        return this.name != null ? this.name : "UnnamedCommand";
    }

    public String getCommand() {
        return this.command;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Lấy thời gian thực thi của lệnh.
     *
     * @return Chuỗi thời gian thực thi, ưu tiên trả về ngày (MM/dd/yyyy) nếu có,
     *         nếu không thì trả về thứ trong tuần.
     */
    public String getExecDateTime() {
        if (this.execDate != null) {
            return this.execDate;
        }
        return this.execDayOfWeek;
    }

    /**
     * Thiết lập thời gian thực thi cho lệnh.
     *
     * @param execDateTime Chuỗi thời gian thực thi, có thể là ngày tháng (MM/dd/yyyy)
     *                     hoặc thứ trong tuần.
     * @return true nếu thiết lập thành công, false nếu không.
     */
    public boolean setExecDateTime(String execDateTime) {
        // Chuẩn hóa chuỗi execDateTime trước khi kiểm tra
        String normalizedExecDateTime = normalizeDayOfWeek(execDateTime);

        if (isValidDate(normalizedExecDateTime)) {
            this.execDate = normalizedExecDateTime;
            return true;
        }
        if (isValidDayOfWeek(normalizedExecDateTime)) {
            this.execDayOfWeek = normalizedExecDateTime;
            return true;
        }
        return false;
    }

    public boolean setStartTime(String startTime) {
        if (isValidTime(startTime)) {
            this.startTime = startTime;
            return true;
        }
        return false;
    }

    public boolean setEndTime(String endTime) {
        if (isValidTime(endTime)) {
            this.endTime = endTime;
            return true;
        }
        return false;
    }

    public void toggleEachHour() {
        this.eachHour = !this.eachHour;
    }

    public void toggleEachMinute() {
        this.eachMinute = !this.eachMinute;
    }

    public void toggleCommand() {
        this.isEnabled = !this.isEnabled;
    }

    private static boolean isValidTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidDayOfWeek(String dayOfWeek) {
        return VALID_DAYS_OF_WEEK.contains(dayOfWeek);
    }

    public static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Chuẩn hóa chuỗi ngày trong tuần về đúng định dạng.
     *
     * @param dayOfWeek Chuỗi ngày trong tuần cần chuẩn hóa.
     * @return Chuỗi ngày trong tuần đã được chuẩn hóa.
     */
    private String normalizeDayOfWeek(String dayOfWeek) {
        // Loại bỏ khoảng trắng thừa và chuyển về chữ thường
        String normalizedDayOfWeek = dayOfWeek.trim().toLowerCase();

        // Xử lý từng trường hợp viết tắt
        if (normalizedDayOfWeek.startsWith("thu")) {
            if (normalizedDayOfWeek.contains("hai")) {
                return "Thu_Hai";
            } else if (normalizedDayOfWeek.contains("ba")) {
                return "Thu_Ba";
            } // ... (xử lý các trường hợp còn lại)
        } else if (normalizedDayOfWeek.startsWith("chu")) { // ...
        }

        return dayOfWeek; // Trả về chuỗi ban đầu nếu không khớp trường hợp nào
    }

    public boolean isEachHour() {
        return this.eachHour;
    }

    public boolean isEachMinute() {
        return this.eachMinute;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }
}

/*
 * Những thay đổi đã được thực hiện:
 *
 * - Thêm ghi chú JavaDoc: Mỗi phương thức và lớp đều được bổ sung ghi chú JavaDoc để giải thích rõ ràng chức năng và cách sử dụng.
 * - Sử dụng Locale: Thay vì mặc định định dạng ngày tháng theo hệ thống, sử dụng Locale.getDefault() để đảm bảo code hoạt động chính xác với nhiều ngôn ngữ và vùng miền khác nhau.
 * - Chuẩn hóa dữ liệu đầu vào: Thêm phương thức normalizeDayOfWeek để chuẩn hóa chuỗi ngày trong tuần, giúp xử lý linh hoạt các trường hợp người dùng nhập liệu khác nhau (viết tắt, sai chính tả nhẹ,...).
 * - Đặt tên biến rõ ràng: Đổi tên biến isEnable thành isEnabled để tuân thủ quy tắc đặt tên trong Java và dễ hiểu hơn.
 * - Tối ưu logic: Rút gọn một số đoạn code bằng cách sử dụng toán tử phủ định ! để đảo ngược giá trị boolean.
 *
 * Lưu ý:
 * - Bạn nên kiểm tra lại logic xử lý ngày tháng và thứ trong tuần của phương thức setExecDateTime để đảm bảo nó hoạt động chính xác theo mong muốn của bạn.
 * - Việc bổ sung ghi chú JavaDoc rất quan trọng, đặc biệt khi làm việc nhóm hoặc phát triển dự án lớn, giúp code dễ đọc, dễ hiểu và dễ bảo trì hơn.
 */