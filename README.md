K_Means
=======
*)  Thuật toán k-means bao gồm các bước cơ bản sau:

Input: Số cụm k và các trọng tâm cụm {mj}kj=1.
Output: Các cụm C[i] (1  ≤  i  ≤  k) và hàm tiêu chuẩn E đạt giá trị tối thiểu.
Begin
- Bước 1: Khởi tạo
Chọn k trọng tâm {mj}kj=1 ban đầu trong không gian Rd (d là số chiều của dữ liệu). Việc lựa chọn này có thể là ngẫu nhiên hoặc theo kinh nghiệm.
- Bước 2: Tính toán khoảng cách
Đối với mỗi điểm Xi  (1 ≤ i ≤ n), tính toán khoảng cách của nó tới mỗi trọng tâm mj (1 ≤ j ≤  k). Sau đó tìm trọng tâm gần nhất đối với mỗi điểm.
- Bước 3: Cập nhật lại trọng tâm
Đối với mỗi 1 ≤ j ≤ k, cập nhật trọng tâm cụm mj  bằng cách xác định trung bình cộng các vectơ đối tượng dữ liệu.
Điều kiện dừng:
Lặp lại các bước 2 và 3 cho đến khi các trọng tâm của cụm không thay đổi.
End.

*)  Cài đặt:

- Tách lấy 100 danh từ hay xuất hiện nhất làm từ điển. Mỗi từ là 1 chiều của vector biểu diễn văn bản.
- Khởi tạo các trọng tâm ban đầu là các vector lần lượt ở đầu.
- Trong thực tế thì vì các thông số rất lẻ nên việc các trọng tâm không đổi rất khó xảy ra. Do đó phải giới hạn số lần lặp.

*)  Ưu điểm:

- Thuật toán không quá khó.

*)  Nhược điểm:

- Việc khởi tạo phần tử trung tâm của nhóm ban đầu ảnh hưởng đến sự phân chia đối tượng vào nhóm trong trường hợp dữ liệu không lớn.
- Số nhóm k luôn phải được xác định trước.
- Không xác định được rõ ràng vùng của nhóm, cùng 1 đối tượng, nó có thể được đưa vào nhóm này hoặc nhóm khác khi dung lượng dữ liệu thay đổi.
- Điều kiện khởi tạo có ảnh hưởng lớn đến kết quả. Điều kiện khởi tạo khác nhau có thể cho ra kết quả phân vùng nhóm khác nhau. 
- Không xác định được mức độ ảnh hưởng của thuộc tính đến quá trình tạo nhóm.
