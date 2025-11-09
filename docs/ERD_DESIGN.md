# 학원 관리 시스템 ERD 설계

## 시스템 아키텍처
- **멀티테넌시(Multi-tenancy)**: 각 학원별로 독립적인 데이터 관리
- **모든 테이블에 `academy_id` FK 포함**

---

## 📋 테이블 목록 및 관계

### 1. 핵심 테이블 (Core Tables)

#### 1.1 academy (학원)
```
id (PK)
name (학원명)
business_number (사업자등록번호)
representative_name (대표자명)
phone (대표 연락처)
address (주소)
bank_name (은행명)
account_number (계좌번호)
account_holder (예금주)
created_at
updated_at
```

#### 1.2 academy_settings (학원별 시스템 설정)
```
id (PK)
academy_id (FK -> academy)
attendance_method (출결 방식: KEYPAD, APP_PUSH)
keypad_auth_type (키패드 인증: PASSWORD, PHONE_LAST_DIGITS)
default_makeup_limit (기본 보강 횟수 제한)
created_at
updated_at
```

---

### 2. 레슨 및 가격 정책 (Lesson & Pricing)

#### 2.1 lesson_type (레슨 유형)
```
id (PK)
academy_id (FK -> academy)
name (레슨명: 예: 개인레슨 4회, 그룹레슨 8회)
type (레슨 형태: INDIVIDUAL, GROUP)
billing_type (과금 방식: COUNT_BASED, PERIOD_BASED)
lesson_count (레슨 횟수, COUNT_BASED인 경우)
period_days (기간 일수, PERIOD_BASED인 경우)
is_active (활성 여부)
created_at
updated_at
```

#### 2.2 lesson_price_policy (가격 정책)
```
id (PK)
academy_id (FK -> academy)
lesson_type_id (FK -> lesson_type)
base_price (기본 수강료)
discount_amount (할인 금액)
discount_type (할인 유형: FAMILY, SIBLING, MULTI_ENROLLMENT 등)
discount_condition (할인 조건 JSON)
created_at
updated_at
```

#### 2.3 payment_method (결제 수단)
```
id (PK)
academy_id (FK -> academy)
name (결제 수단명: 카드, 현금, 계좌이체, 지역화폐 등)
is_active (활성 여부)
created_at
```

---

### 3. 메시지 관리 (Messaging)

#### 3.1 message_template (문자 템플릿)
```
id (PK)
academy_id (FK -> academy)
template_type (유형: ATTENDANCE, MAKEUP, OVERDUE, NOTICE)
subject (제목)
content (본문, Placeholder 포함: [학생명], [교육비], [회차] 등)
is_active (활성 여부)
created_at
updated_at
```

---

### 4. 시설 및 인력 (Facility & Staff)

#### 4.1 classroom (강의실)
```
id (PK)
academy_id (FK -> academy)
name (강의실명)
capacity (수용 인원)
is_active (활성 여부)
created_at
```

#### 4.2 instructor (강사)
```
id (PK)
academy_id (FK -> academy)
name (강사명)
phone (연락처)
email (이메일)
specialization (전문 분야)
is_active (활성 여부)
created_at
updated_at
```

---

### 5. 학생 관리 (Student Management)

#### 5.1 student (학생)
```
id (PK)
academy_id (FK -> academy)
name (학생명)
birth_date (생년월일)
phone (학생 연락처)
parent_phone (학부모 연락처)
school (최종 학교)
grade (학년)
status (상태: ACTIVE, DORMANT, WITHDRAWN)
enrollment_date (등록일)
dormant_date (휴원일)
withdrawal_date (퇴원일)
notes (비고)
created_at
updated_at
```

#### 5.2 enrollment (수강 등록)
```
id (PK)
academy_id (FK -> academy)
student_id (FK -> student)
lesson_type_id (FK -> lesson_type)
instructor_id (FK -> instructor)
classroom_id (FK -> classroom)
total_count (총 회차)
used_count (사용 회차)
remaining_count (잔여 회차)
monthly_fee (월 수강료, 할인 적용 후)
start_date (수강 시작일)
end_date (수강 종료일)
is_active (활성 여부)
created_at
updated_at
```

---

### 6. 출석 관리 (Attendance Management)

#### 6.1 attendance (출석)
```
id (PK)
academy_id (FK -> academy)
student_id (FK -> student)
enrollment_id (FK -> enrollment)
attendance_date (출석일)
attendance_time (출석 시간)
lesson_number (해당 회차: 1/4, 2/4 등)
type (유형: REGULAR, MAKEUP)
created_at
```

#### 6.2 makeup_lesson (보강 예약)
```
id (PK)
academy_id (FK -> academy)
student_id (FK -> student)
enrollment_id (FK -> enrollment)
original_date (원래 레슨 날짜)
makeup_date (보강 예약 날짜)
makeup_time (보강 시간)
status (상태: SCHEDULED, COMPLETED, CANCELLED)
created_at
updated_at
```

---

### 7. 수납 및 결제 (Payment Management)

#### 7.1 payment (결제)
```
id (PK)
academy_id (FK -> academy)
student_id (FK -> student)
enrollment_id (FK -> enrollment)
payment_method_id (FK -> payment_method)
amount (결제 금액)
payment_date (결제일)
billing_month (청구 월: YYYY-MM)
status (상태: PAID, OVERDUE, CANCELLED)
notes (비고)
created_at
updated_at
```

#### 7.2 receipt (영수증)
```
id (PK)
academy_id (FK -> academy)
payment_id (FK -> payment)
receipt_number (영수증 번호)
issue_date (발급일)
recipient_name (수령인)
amount (금액)
cash_receipt_issued (현금영수증 발급 여부)
cash_receipt_number (현금영수증 번호)
pdf_path (PDF 파일 경로)
created_at
```

---

## 🔗 주요 관계 (Relationships)

1. **academy** 1:N **student**
2. **academy** 1:N **lesson_type**
3. **academy** 1:N **instructor**
4. **student** 1:N **enrollment**
5. **enrollment** 1:N **attendance**
6. **enrollment** 1:N **payment**
7. **lesson_type** 1:N **enrollment**
8. **instructor** 1:N **enrollment**

---

## 📊 인덱스 전략

### 성능 최적화를 위한 인덱스
- `academy_id` (모든 테이블)
- `student.status` + `academy_id` (재원생/휴원생 필터링)
- `payment.status` + `academy_id` (미납자 조회)
- `attendance.attendance_date` + `academy_id` (출결 조회)
- `enrollment.is_active` + `academy_id` (활성 수강 조회)

---

## 🔐 보안 및 데이터 격리

- 모든 쿼리에 `academy_id` 조건 필수
- Row-Level Security 고려 (PostgreSQL RLS)
- 학원별 데이터 완전 격리
