-- Insert roles
INSERT INTO roles (role_id, role_name)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (role_id, role_name)
VALUES (2, 'ROLE_USER');

-- Insert users
INSERT INTO users (user_id, username, email, password, full_name, phone_number, created_at, updated_at)
VALUES (1, 'admin', 'admin@gmail.com', '$2y$10$mnzNEItO4CsWuyR6iH/r.eHrlbgK67Ith2WF9UtBQXFSzNHFPQgli', 'Dao Huy Hoang',
        '0123456789', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (user_id, username, email, password, full_name, phone_number, created_at, updated_at)
VALUES (2, 'user', 'user1@gmail.com', '$2y$10$RuAs7hgVw92KT0jGKcDRCuiqdTlYvagmvzUElFU8BOp1KIOMMxo4m', 'Thanh Nguyet',
        '0987654321', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (user_id, username, email, password, full_name, phone_number, created_at, updated_at)
VALUES (3, 'user2', 'user2@gmail.com', '$2y$10$RuAs7hgVw92KT0jGKcDRCuiqdTlYvagmvzUElFU8BOp1KIOMMxo4m', 'User Two',
        '0912345678', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert user_roles mapping
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1); -- Admin user with ROLE_ADMIN
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 2); -- Admin with ROLE_USER
INSERT INTO user_roles (user_id, role_id)
VALUES (2, 2); -- User with ROLE_USER

INSERT INTO cities (name)
VALUES ('Hà Nội'),
       ('Thành phố Hồ Chí Minh'),
       ('Đà Nẵng'),
       ('Nha Trang'),
       ('Hội An');

INSERT INTO hotels (name, address, phone_number, email, city_id)
VALUES ('Khách sạn Mặt Trời', 'Số 1, Đường Phạm Văn Đồng, Hà Nội', '0123456789', 'mattroi@hotel.vn', 1),
       ('Khách sạn Biển Xanh', 'Số 10, Đường Trần Phú, Nha Trang', '0987654321', 'bienxanh@hotel.vn', 4),
       ('Khách sạn Hoa Sen', 'Số 15, Đường Nguyễn Văn Linh, Đà Nẵng', '0912345678', 'hoasen@hotel.vn', 3),
       ('Khách sạn Sài Gòn', 'Số 99, Đường Lý Thường Kiệt, TP. Hồ Chí Minh', '0934567890', 'saigon@hotel.vn', 2),
       ('Khách sạn Hội An Xưa', 'Số 20, Đường Trần Phú, Hội An', '0923456789', 'hoianxua@hotel.vn', 5);
INSERT INTO rooms (room_number, type, price, capacity, availability_status, description, hotel_id)
VALUES ('A101', 'Phòng Đơn', 500000, 1, 'AVAILABLE', 'Phòng đơn tiện nghi với giường đơn và bàn làm việc.', 1),
       ('B202', 'Phòng Đôi', 800000, 2, 'BOOKED', 'Phòng đôi với giường đôi và ban công nhìn ra biển.', 1),
       ('C303', 'Phòng Gia Đình', 1200000, 4, 'OUT_OF_SERVICE',
        'Phòng rộng rãi dành cho gia đình, có khu vực tiếp khách riêng.', 1),
       ('D404', 'Phòng Vip', 2000000, 2, 'AVAILABLE',
        'Phòng Vip với nội thất cao cấp và tầm nhìn toàn cảnh thành phố.', 1),
       ('E505', 'Phòng Deluxe', 1500000, 3, 'AVAILABLE',
        'Phòng Deluxe với phòng tắm riêng và không gian nghỉ ngơi thư giãn.', 1);


INSERT INTO bookings (user_id, room_id, check_in_date, check_out_date, status, payment_status, estimated_check_out_date,
                      note, deposit_amount, create_at, updated_at)
VALUES (1, 1, '2024-10-01 14:00:00', '2024-10-05 12:00:00', 'CONFIRMED', 'PAID', '2024-10-05',
        'Khách yêu cầu dọn phòng hàng ngày', 500000, '2024-10-01 09:00:00', '2024-10-01 09:00:00'),
       (2, 2, '2024-10-02 15:00:00', '2024-10-07 11:00:00', 'PENDING', 'NOT_PAID', '2024-10-07',
        'Khách đi du lịch cùng gia đình', 800000, '2024-10-02 10:00:00', '2024-10-02 10:00:00'),
       (3, 3, '2024-10-03 13:00:00', '2024-10-04 10:00:00', 'CANCELLED', 'NOT_PAID', '2024-10-04',
        'Khách hủy đặt phòng do bận công việc', 0, '2024-10-03 08:00:00', '2024-10-03 08:00:00'),
       (1, 4, '2024-10-05 12:00:00', '2024-10-10 14:00:00', 'COMPLETED', 'PAID', '2024-10-10',
        'Khách hàng rất hài lòng về dịch vụ', 2000000, '2024-10-05 09:00:00', '2024-10-10 09:00:00'),
       (2, 5, '2024-10-06 16:00:00', '2024-10-08 11:00:00', 'CONFIRMED', 'PARTIALLY_PAID', '2024-10-08',
        'Khách đã thanh toán trước một phần', 1000000, '2024-10-06 11:00:00', '2024-10-06 11:00:00');
