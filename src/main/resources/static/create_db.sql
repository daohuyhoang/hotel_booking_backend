-- Tạo cơ sở dữ liệu mới
CREATE DATABASE hotel_booking_system;
USE hotel_booking_system;

-- Bảng users: Lưu thông tin tất cả người dùng trong hệ thống (khách hàng, nhân viên, quản trị viên)
CREATE TABLE users
(
    user_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    username     VARCHAR(100) UNIQUE NOT NULL,
    email        VARCHAR(100) UNIQUE NOT NULL,
    password     VARCHAR(255)        NOT NULL,
    full_name    VARCHAR(100),
    phone_number VARCHAR(10),
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_tokens
(
    token_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id    BIGINT              NOT NULL, -- Khóa ngoại liên kết với bảng users
    token      VARCHAR(500) UNIQUE NOT NULL, -- Token xác thực JWT
    expires_at TIMESTAMP           NOT NULL, -- Thời gian hết hạn của token
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- Bảng password_reset_requests: Lưu trữ các yêu cầu đặt lại mật khẩu
CREATE TABLE password_reset
(
    request_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id    BIGINT              NOT NULL, -- Khóa ngoại liên kết với bảng users
    token      VARCHAR(500) UNIQUE NOT NULL, -- Token xác minh yêu cầu đặt lại mật khẩu
    expires_at TIMESTAMP           NOT NULL, -- Thời gian hết hạn của token đặt lại mật khẩu (thường là vài giờ)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- Bảng roles: Lưu thông tin các vai trò (CUSTOMER, ADMIN, EMPLOYEE)
CREATE TABLE roles
(
    role_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

-- Bảng user_roles: Liên kết user với các role trong hệ thống (many-to-many)
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

-- Bảng employees: Lưu thông tin nhân viên, liên kết với bảng users
CREATE TABLE employees
(
    employee_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id     BIGINT       NOT NULL,
    position    VARCHAR(100) NOT NULL,      -- Chức vụ (Receptionist, Manager, Staff, etc.)
    salary      DECIMAL(10, 2) DEFAULT 0.0, -- Lương nhân viên
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- Bảng cities: Lưu thông tin các thành phố
CREATE TABLE cities
(
    city_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL UNIQUE
);

-- Bảng hotels: Thay đổi để liên kết với bảng cities
CREATE TABLE hotels
(
    hotel_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL,
    address   VARCHAR(255),
    city_id   BIGINT,
    phone_number VARCHAR(10),
    email     VARCHAR(100),
    FOREIGN KEY (city_id) REFERENCES cities (city_id)
);

-- Bảng hotel_images: Lưu thông tin hình ảnh liên kết với khách sạn
CREATE TABLE hotel_images
(
    image_id  BIGINT PRIMARY KEY AUTO_INCREMENT,        -- ID của hình ảnh
    hotel_id  BIGINT       NOT NULL,                    -- Khóa ngoại liên kết với bảng hotels
    image_url VARCHAR(255) NOT NULL,                    -- Đường dẫn đến ảnh
    FOREIGN KEY (hotel_id) REFERENCES hotels (hotel_id) -- Liên kết với bảng hotels
);

CREATE TABLE reviews
(
    review_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    hotel_id   BIGINT       NOT NULL,                                           -- Khóa ngoại liên kết với bảng hotels
    user_id    BIGINT       NOT NULL,                                           -- Khóa ngoại liên kết với bảng users
    rating     INT          NOT NULL CHECK (rating >= 1 AND rating <= 5),       -- Điểm đánh giá từ 1 đến 5
    image_url  VARCHAR(255) NOT NULL,
    comment    TEXT,                                                            -- Nội dung đánh giá
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                             -- Ngày tạo
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Ngày cập nhật
    FOREIGN KEY (hotel_id) REFERENCES hotels (hotel_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- Bảng rooms: Lưu thông tin các phòng trong từng khách sạn
CREATE TABLE rooms
(
    room_id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    hotel_id            BIGINT UNIQUE      NOT NULL,                                        -- Khóa ngoại liên kết với bảng hotels
    room_number         VARCHAR(20) UNIQUE NOT NULL,                                        -- Số phòng
    type                VARCHAR(100)       NOT NULL,                                        -- Loại phòng (Standard, Deluxe, Suite)
    price               DECIMAL(10, 2)     NOT NULL,                                        -- Giá phòng
    capacity            INT                                            DEFAULT 1,           -- Sức chứa (số người)
    availability_status ENUM ('AVAILABLE', 'BOOKED', 'OUT_OF_SERVICE') DEFAULT 'AVAILABLE', -- Trạng thái phòng
    description         TEXT,
    FOREIGN KEY (hotel_id) REFERENCES hotels (hotel_id)
);

CREATE TABLE room_images
(
    image_id  BIGINT PRIMARY KEY AUTO_INCREMENT,     -- ID của hình ảnh
    room_id   BIGINT       NOT NULL,                 -- Khóa ngoại liên kết với bảng rooms
    image_url VARCHAR(255) NOT NULL,                 -- Đường dẫn đến ảnh trên Firebase
    FOREIGN KEY (room_id) REFERENCES rooms (room_id) -- Liên kết với bảng rooms
);

-- Bảng bookings: Lưu thông tin đơn đặt phòng của khách hàng, sử dụng ENUM cho cột `status`
CREATE TABLE bookings
(
    booking_id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id                  BIGINT   NOT NULL,                                                         -- Khách hàng đặt phòng
    room_id                  BIGINT   NOT NULL,                                                         -- Phòng được đặt
    check_in_date            DATETIME NOT NULL,                                                         -- Ngày nhận phòng
    check_out_date           DATETIME,                                                                  -- Ngày trả phòng (có thể null nếu chưa xác định)
    status                   ENUM ('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING', -- Trạng thái đơn đặt phòng
    payment_status           ENUM ('NOT_PAID', 'PARTIALLY_PAID', 'PAID')             DEFAULT 'NOT_PAID',
    estimated_check_out_date DATE,                                                                      -- Ngày trả phòng dự kiến (dành cho những khách lưu trú dài hạn)
    note                     TEXT,
    deposit_amount           DECIMAL(10, 2)                                          DEFAULT 0.0,
    created_at               TIMESTAMP                                               DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP                                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (room_id) REFERENCES rooms (room_id)
);

CREATE TABLE booking_details
(
    detail_id  BIGINT PRIMARY KEY AUTO_INCREMENT, -- ID của từng dòng chi tiết trong đơn đặt phòng
    booking_id BIGINT NOT NULL,                   -- Khóa ngoại liên kết với bảng bookings
    room_id    BIGINT DEFAULT NULL,               -- Khóa ngoại liên kết với bảng rooms (có thể NULL nếu đây là dịch vụ)
    quantity   INT    DEFAULT 1,                  -- Số lượng phòng hoặc dịch vụ trong dòng chi tiết này
    FOREIGN KEY (booking_id) REFERENCES bookings (booking_id),
    FOREIGN KEY (room_id) REFERENCES rooms (room_id)
);

CREATE TABLE payment_methods
(
    method_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    method_name VARCHAR(50) UNIQUE NOT NULL -- Tên phương thức (Credit Card, Debit Card, PayPal, etc.)
);

CREATE TABLE payments
(
    payment_id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id       BIGINT NOT NULL,                                                                             -- Liên kết với đơn đặt phòng
    user_id          BIGINT NOT NULL,                                                                             -- Liên kết với người dùng
    method_id        BIGINT NOT NULL,                                                                             -- Liên kết với phương thức thanh toán
    payment_status   ENUM ('PENDING', 'SUCCESS', 'FAILED') DEFAULT 'PENDING',                                     -- Trạng thái thanh toán
    transaction_date TIMESTAMP                             DEFAULT CURRENT_TIMESTAMP,                             -- Ngày giao dịch
    updated_at       TIMESTAMP                             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Ngày cập nhật cuối
    FOREIGN KEY (booking_id) REFERENCES bookings (booking_id),                                                    -- Khóa ngoại liên kết với bảng bookings
    FOREIGN KEY (user_id) REFERENCES users (user_id),                                                             -- Khóa ngoại liên kết với bảng users
    FOREIGN KEY (method_id) REFERENCES payment_methods (method_id)                                                -- Khóa ngoại liên kết với bảng phương thức thanh toán
);

-- Bảng blogs: Lưu thông tin bài viết
CREATE TABLE blogs
(
    blog_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(200) NOT NULL,
    content      TEXT         NOT NULL,
    publish_date DATETIME     NOT NULL,
    creator_id   BIGINT,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (creator_id) REFERENCES users (user_id)
);

-- Bảng blog_images: Lưu thông tin hình ảnh liên kết với bài viết
CREATE TABLE blog_images
(
    image_id  BIGINT PRIMARY KEY AUTO_INCREMENT,     -- ID của hình ảnh
    blog_id   BIGINT       NOT NULL,                 -- Khóa ngoại liên kết với bảng blogs
    image_url VARCHAR(255) NOT NULL,                 -- Đường dẫn đến ảnh
    FOREIGN KEY (blog_id) REFERENCES blogs (blog_id) -- Liên kết với bảng blogs
);
