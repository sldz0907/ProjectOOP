Game Nhập Vai Platform - OOP Project
Bài tập lớn môn Lập trình hướng đối tượng – Trường Đại học Bách Khoa Hà Nội  
Nhóm 8 – Mã lớp: IT3103_20242

# Giới thiệu

Dự án xây dựng một trò chơi nhập vai dạng platform sử dụng ngôn ngữ Java theo mô hình lập trình hướng đối tượng. Trò chơi cho phép người chơi điều khiển nhân vật vượt qua các bản đồ, chiến đấu với quái vật và sử dụng kỹ năng để sinh tồn.

# Tính năng chính

-  Giao diện chọn chế độ chơi (Easy / Hard)
-  Người chơi có thể di chuyển, tấn công gần / xa và sử dụng kỹ năng
-  Nhiều loại quái vật với hành vi khác nhau (tấn công gần / xa, boss,…)
-  Chuyển map khi hoàn thành màn chơi
-  Hệ thống dừng / tiếp tục trò chơi
-  Tuỳ chỉnh âm thanh
-  Lưu / tải trạng thái game
-  Đã kiểm thử đầy đủ các chức năng cơ bản

# Công nghệ sử dụng

- Ngôn ngữ: Java (thuần hướng đối tượng)
- IDE: Eclipse
- Quản lý mã nguồn: Git + GitHub
- Mô hình: OOP – đóng gói, kế thừa, đa hình, interface,…

# Cấu trúc chương trình

src/
 main/ # Điểm khởi đầu game
 gameStates/ # Quản lý trạng thái game (Menu, Playing, Option,…)
 ui/ # Giao diện người dùng
 entities/ # Định nghĩa Player, Enemy, Manager
 input/ # Xử lý phím / chuột
 audio/ # Âm thanh game
 physics/ # Xử lý va chạm
 loadSave/ # Quản lý lưu / tải game
 assets/ # Hình ảnh, âm thanh, font,...

# Cài đặt & chạy thử

bash
git clone https://github.com/yourusername/oop-platform-game.git
cd oop-platform-game
# Mở bằng Eclipse / IDE Java bất kỳ và chạy file `Main.java`

# Tài liệu tham khảo
Think in Java – Bruce Eckel
Java Performance – Charlie Hunt & Binu John
StackOverflow, GitHub & Tài liệu giảng viên cung cấp

# Thành viên nhóm
1 Nguyễn Danh Khải	20235750
2 Lê Thái Bảo	20235663	
3	Lê Minh Hiếu	20235710	
4	Nguyễn Xuân Hưng	20235742	
5	Nguyễn Anh Tuấn	20235860	
6	Đinh Đức Mạnh	20235774	
7	Nguyễn Sỹ Lộc	20235766
