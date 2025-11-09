-- ========================================
-- 학원 관리 시스템 데이터베이스 스키마
-- Multi-tenancy 기반 설계
-- ========================================

-- 1. 학원 (Academy)
CREATE TABLE academy (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    business_number VARCHAR(50),
    representative_name VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    bank_name VARCHAR(100),
    account_number VARCHAR(100),
    account_holder VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. 학원 시스템 설정
CREATE TABLE academy_settings (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    attendance_method VARCHAR(20) DEFAULT 'KEYPAD', -- KEYPAD, APP_PUSH
    keypad_auth_type VARCHAR(30) DEFAULT 'PASSWORD', -- PASSWORD, PHONE_LAST_DIGITS
    default_makeup_limit INT DEFAULT 2,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(academy_id)
);

-- 3. 레슨 유형
CREATE TABLE lesson_type (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL, -- INDIVIDUAL, GROUP
    billing_type VARCHAR(20) NOT NULL, -- COUNT_BASED, PERIOD_BASED
    lesson_count INT, -- COUNT_BASED인 경우
    period_days INT, -- PERIOD_BASED인 경우
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. 가격 정책
CREATE TABLE lesson_price_policy (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    lesson_type_id BIGINT NOT NULL REFERENCES lesson_type(id) ON DELETE CASCADE,
    base_price DECIMAL(10, 2) NOT NULL,
    discount_amount DECIMAL(10, 2) DEFAULT 0,
    discount_type VARCHAR(50), -- FAMILY, SIBLING, MULTI_ENROLLMENT
    discount_condition JSONB, -- 할인 조건 (유연한 구조)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. 결제 수단
CREATE TABLE payment_method (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. 문자 템플릿
CREATE TABLE message_template (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    template_type VARCHAR(50) NOT NULL, -- ATTENDANCE, MAKEUP, OVERDUE, NOTICE
    subject VARCHAR(255),
    content TEXT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. 강의실
CREATE TABLE classroom (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    capacity INT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8. 강사
CREATE TABLE instructor (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255),
    specialization VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 9. 학생
CREATE TABLE student (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    birth_date DATE,
    phone VARCHAR(20),
    parent_phone VARCHAR(20) NOT NULL,
    school VARCHAR(255),
    grade VARCHAR(50),
    status VARCHAR(20) DEFAULT 'ACTIVE', -- ACTIVE, DORMANT, WITHDRAWN
    enrollment_date DATE,
    dormant_date DATE,
    withdrawal_date DATE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 10. 수강 등록
CREATE TABLE enrollment (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    student_id BIGINT NOT NULL REFERENCES student(id) ON DELETE CASCADE,
    lesson_type_id BIGINT NOT NULL REFERENCES lesson_type(id) ON DELETE RESTRICT,
    instructor_id BIGINT REFERENCES instructor(id) ON DELETE SET NULL,
    classroom_id BIGINT REFERENCES classroom(id) ON DELETE SET NULL,
    total_count INT NOT NULL,
    used_count INT DEFAULT 0,
    remaining_count INT NOT NULL,
    monthly_fee DECIMAL(10, 2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 11. 출석
CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    student_id BIGINT NOT NULL REFERENCES student(id) ON DELETE CASCADE,
    enrollment_id BIGINT NOT NULL REFERENCES enrollment(id) ON DELETE CASCADE,
    attendance_date DATE NOT NULL,
    attendance_time TIME NOT NULL,
    lesson_number INT NOT NULL, -- 1, 2, 3, 4 (해당 회차)
    type VARCHAR(20) DEFAULT 'REGULAR', -- REGULAR, MAKEUP
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 12. 보강 예약
CREATE TABLE makeup_lesson (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    student_id BIGINT NOT NULL REFERENCES student(id) ON DELETE CASCADE,
    enrollment_id BIGINT NOT NULL REFERENCES enrollment(id) ON DELETE CASCADE,
    original_date DATE,
    makeup_date DATE NOT NULL,
    makeup_time TIME,
    status VARCHAR(20) DEFAULT 'SCHEDULED', -- SCHEDULED, COMPLETED, CANCELLED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 13. 결제
CREATE TABLE payment (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    student_id BIGINT NOT NULL REFERENCES student(id) ON DELETE CASCADE,
    enrollment_id BIGINT REFERENCES enrollment(id) ON DELETE SET NULL,
    payment_method_id BIGINT REFERENCES payment_method(id) ON DELETE SET NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date DATE NOT NULL,
    billing_month VARCHAR(7) NOT NULL, -- YYYY-MM
    status VARCHAR(20) DEFAULT 'PAID', -- PAID, OVERDUE, CANCELLED
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 14. 영수증
CREATE TABLE receipt (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL REFERENCES academy(id) ON DELETE CASCADE,
    payment_id BIGINT NOT NULL REFERENCES payment(id) ON DELETE CASCADE,
    receipt_number VARCHAR(100) UNIQUE NOT NULL,
    issue_date DATE NOT NULL,
    recipient_name VARCHAR(100),
    amount DECIMAL(10, 2) NOT NULL,
    cash_receipt_issued BOOLEAN DEFAULT FALSE,
    cash_receipt_number VARCHAR(100),
    pdf_path TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- 인덱스 생성 (성능 최적화)
-- ========================================

-- academy_id 인덱스 (모든 테이블)
CREATE INDEX idx_academy_settings_academy ON academy_settings(academy_id);
CREATE INDEX idx_lesson_type_academy ON lesson_type(academy_id);
CREATE INDEX idx_lesson_price_policy_academy ON lesson_price_policy(academy_id);
CREATE INDEX idx_payment_method_academy ON payment_method(academy_id);
CREATE INDEX idx_message_template_academy ON message_template(academy_id);
CREATE INDEX idx_classroom_academy ON classroom(academy_id);
CREATE INDEX idx_instructor_academy ON instructor(academy_id);
CREATE INDEX idx_student_academy ON student(academy_id);
CREATE INDEX idx_enrollment_academy ON enrollment(academy_id);
CREATE INDEX idx_attendance_academy ON attendance(academy_id);
CREATE INDEX idx_makeup_lesson_academy ON makeup_lesson(academy_id);
CREATE INDEX idx_payment_academy ON payment(academy_id);
CREATE INDEX idx_receipt_academy ON receipt(academy_id);

-- 복합 인덱스 (조회 성능 최적화)
CREATE INDEX idx_student_status_academy ON student(status, academy_id);
CREATE INDEX idx_payment_status_academy ON payment(status, academy_id);
CREATE INDEX idx_attendance_date_academy ON attendance(attendance_date, academy_id);
CREATE INDEX idx_enrollment_active_academy ON enrollment(is_active, academy_id);
CREATE INDEX idx_enrollment_student ON enrollment(student_id);
CREATE INDEX idx_attendance_student ON attendance(student_id);
CREATE INDEX idx_payment_student ON payment(student_id);

-- ========================================
-- 초기 데이터 (Optional)
-- ========================================

-- 예시: 테스트용 학원 데이터
INSERT INTO academy (name, business_number, representative_name, phone, bank_name, account_number, account_holder)
VALUES ('테스트 음악학원', '123-45-67890', '홍길동', '02-1234-5678', '국민은행', '123456-78-901234', '홍길동');

-- 예시: 기본 시스템 설정
INSERT INTO academy_settings (academy_id, attendance_method, keypad_auth_type, default_makeup_limit)
VALUES (1, 'KEYPAD', 'PASSWORD', 2);
