import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:7000/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor
api.interceptors.request.use(
  (config) => {
    // 여기서 인증 토큰 등을 추가할 수 있습니다
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor
api.interceptors.response.use(
  (response) => {
    return response.data
  },
  (error) => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

// Academy API
export const academyApi = {
  getById: (id) => api.get(`/academy/${id}`),
  getByOwner: (ownerId) => api.get(`/academy/owner/${ownerId}`),
  create: (data) => api.post('/academy', data),
  update: (id, data) => api.put(`/academy/${id}`, data),
  delete: (id) => api.delete(`/academy/${id}`),

  // Settings
  getSettings: (academyId) => api.get(`/academy/${academyId}/settings`),
  updateSettings: (academyId, data) => api.put(`/academy/${academyId}/settings`, data),
}

// Lesson API
export const lessonApi = {
  // Lesson Types
  getLessonType: (id) => api.get(`/lesson/type/${id}`),
  getLessonTypesByAcademy: (academyId) => api.get(`/lesson/type/academy/${academyId}`),
  createLessonType: (data) => api.post('/lesson/type', data),
  updateLessonType: (id, data) => api.put(`/lesson/type/${id}`, data),
  deleteLessonType: (id) => api.delete(`/lesson/type/${id}`),

  // Payment Methods
  getPaymentMethod: (id) => api.get(`/lesson/payment-method/${id}`),
  getPaymentMethodsByAcademy: (academyId) => api.get(`/lesson/payment-method/academy/${academyId}`),
  createPaymentMethod: (data) => api.post('/lesson/payment-method', data),
  updatePaymentMethod: (id, data) => api.put(`/lesson/payment-method/${id}`, data),
  deletePaymentMethod: (id) => api.delete(`/lesson/payment-method/${id}`),
}

// Student API
export const studentApi = {
  getById: (id) => api.get(`/student/${id}`),
  getByStatus: (academyId, status) => api.get(`/student/status/${status}`, { params: { academyId } }),
  getAllByAcademy: (academyId) => api.get(`/student/academy/${academyId}`),
  create: (data) => api.post('/student', data),
  update: (id, data) => api.put(`/student/${id}`, data),
  updateStatus: (id, status) => api.patch(`/student/${id}/status`, null, { params: { status } }),
  delete: (id) => api.delete(`/student/${id}`),

  // Enrollments
  getEnrollment: (id) => api.get(`/student/enrollment/${id}`),
  getEnrollmentsByStudent: (studentId) => api.get(`/student/${studentId}/enrollments`),
  getActiveEnrollments: (academyId) => api.get(`/student/enrollment/active/${academyId}`),
  createEnrollment: (data) => api.post('/student/enrollment', data),
  updateEnrollment: (id, data) => api.put(`/student/enrollment/${id}`, data),
  updateEnrollmentCount: (id, params) => api.patch(`/student/enrollment/${id}/count`, null, { params }),
  deleteEnrollment: (id) => api.delete(`/student/enrollment/${id}`),
}

// Attendance API
export const attendanceApi = {
  getById: (id) => api.get(`/attendance/${id}`),
  getByStudent: (studentId) => api.get(`/attendance/student/${studentId}`),
  getByEnrollment: (enrollmentId) => api.get(`/attendance/enrollment/${enrollmentId}`),
  getByDateRange: (academyId, startDate, endDate) =>
    api.get(`/attendance/academy/${academyId}/range`, { params: { startDate, endDate } }),
  create: (data) => api.post('/attendance', data),
  delete: (id) => api.delete(`/attendance/${id}`),

  // Makeup Lessons
  getMakeup: (id) => api.get(`/attendance/makeup/${id}`),
  getMakeupsByStudent: (studentId) => api.get(`/attendance/makeup/student/${studentId}`),
  getMakeupsByStatus: (academyId, status) => api.get(`/attendance/makeup/academy/${academyId}/status/${status}`),
  createMakeup: (data) => api.post('/attendance/makeup', data),
  updateMakeupStatus: (id, status) => api.patch(`/attendance/makeup/${id}/status`, null, { params: { status } }),
  deleteMakeup: (id) => api.delete(`/attendance/makeup/${id}`),
}

// Payment API
export const paymentApi = {
  getById: (id) => api.get(`/payment/${id}`),
  getByStudent: (studentId) => api.get(`/payment/student/${studentId}`),
  getByStatus: (academyId, status) => api.get(`/payment/academy/${academyId}/status/${status}`),
  getOverdue: (academyId) => api.get(`/payment/academy/${academyId}/overdue`),
  create: (data) => api.post('/payment', data),
  update: (id, data) => api.put(`/payment/${id}`, data),
  updateStatus: (id, status) => api.patch(`/payment/${id}/status`, null, { params: { status } }),
  delete: (id) => api.delete(`/payment/${id}`),

  // Receipts
  getReceipt: (id) => api.get(`/payment/receipt/${id}`),
  getReceiptByPayment: (paymentId) => api.get(`/payment/receipt/payment/${paymentId}`),
  getReceiptsByAcademy: (academyId) => api.get(`/payment/receipt/academy/${academyId}`),
  createReceipt: (data) => api.post('/payment/receipt', data),
  deleteReceipt: (id) => api.delete(`/payment/receipt/${id}`),
}

export default api
