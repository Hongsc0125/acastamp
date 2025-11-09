<template>
  <div class="px-4 sm:px-0">
    <div class="mb-4">
      <router-link to="/students" class="text-blue-600 hover:text-blue-900">
        ← 목록으로
      </router-link>
    </div>

    <div v-if="loading" class="text-center py-8">
      <p class="text-gray-500">로딩 중...</p>
    </div>

    <div v-else-if="student">
      <!-- 학생 정보 -->
      <div class="bg-white shadow rounded-lg p-6 mb-6">
        <div class="flex justify-between items-start mb-6">
          <h2 class="text-2xl font-bold text-gray-900">{{ student.name }}</h2>
          <span :class="getStatusClass(student.status)" class="px-3 py-1 text-sm font-semibold rounded-full">
            {{ getStatusText(student.status) }}
          </span>
        </div>

        <div class="grid grid-cols-1 gap-4 sm:grid-cols-2">
          <div>
            <p class="text-sm font-medium text-gray-500">전화번호</p>
            <p class="mt-1 text-sm text-gray-900">{{ student.phoneNumber || '-' }}</p>
          </div>
          <div>
            <p class="text-sm font-medium text-gray-500">생년월일</p>
            <p class="mt-1 text-sm text-gray-900">{{ formatDate(student.birthDate) }}</p>
          </div>
          <div>
            <p class="text-sm font-medium text-gray-500">등록일</p>
            <p class="mt-1 text-sm text-gray-900">{{ formatDate(student.enrollmentDate) }}</p>
          </div>
          <div>
            <p class="text-sm font-medium text-gray-500">주소</p>
            <p class="mt-1 text-sm text-gray-900">{{ student.address || '-' }}</p>
          </div>
          <div class="sm:col-span-2">
            <p class="text-sm font-medium text-gray-500">메모</p>
            <p class="mt-1 text-sm text-gray-900">{{ student.notes || '-' }}</p>
          </div>
        </div>
      </div>

      <!-- 수강 정보 -->
      <div class="bg-white shadow rounded-lg p-6 mb-6">
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-lg font-medium text-gray-900">수강 정보</h3>
          <button
            @click="showEnrollmentModal = true"
            class="inline-flex items-center px-3 py-1.5 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700"
          >
            수강 추가
          </button>
        </div>

        <div v-if="enrollments.length === 0" class="text-center py-4">
          <p class="text-gray-500">수강 정보가 없습니다.</p>
        </div>
        <div v-else class="space-y-4">
          <div
            v-for="enrollment in enrollments"
            :key="enrollment.id"
            class="border rounded-lg p-4"
          >
            <div class="flex justify-between items-start">
              <div class="flex-1">
                <h4 class="text-sm font-medium text-gray-900">{{ enrollment.lessonTypeName }}</h4>
                <div class="mt-2 grid grid-cols-2 gap-2">
                  <p class="text-xs text-gray-500">
                    전체 횟수: <span class="font-medium">{{ enrollment.totalCount }}회</span>
                  </p>
                  <p class="text-xs text-gray-500">
                    사용 횟수: <span class="font-medium">{{ enrollment.usedCount }}회</span>
                  </p>
                  <p class="text-xs text-gray-500">
                    남은 횟수: <span class="font-medium text-blue-600">{{ enrollment.remainingCount }}회</span>
                  </p>
                  <p class="text-xs text-gray-500">
                    기간: {{ formatDate(enrollment.startDate) }} ~ {{ formatDate(enrollment.endDate) }}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 최근 출석 기록 -->
      <div class="bg-white shadow rounded-lg p-6">
        <h3 class="text-lg font-medium text-gray-900 mb-4">최근 출석 기록</h3>
        <div v-if="attendances.length === 0" class="text-center py-4">
          <p class="text-gray-500">출석 기록이 없습니다.</p>
        </div>
        <ul v-else class="divide-y divide-gray-200">
          <li v-for="attendance in attendances" :key="attendance.id" class="py-3">
            <div class="flex justify-between">
              <div>
                <p class="text-sm font-medium text-gray-900">
                  {{ formatDate(attendance.attendanceDate) }}
                  <span class="text-gray-500">{{ attendance.attendanceTime }}</span>
                </p>
                <p class="text-xs text-gray-500">{{ attendance.lessonNumber }}회차</p>
              </div>
              <span
                :class="attendance.type === 'REGULAR' ? 'bg-blue-100 text-blue-800' : 'bg-purple-100 text-purple-800'"
                class="px-2 py-1 text-xs font-semibold rounded-full"
              >
                {{ attendance.type === 'REGULAR' ? '정규' : '보강' }}
              </span>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { studentApi, attendanceApi } from '../services/api'

const route = useRoute()
const student = ref(null)
const enrollments = ref([])
const attendances = ref([])
const loading = ref(false)
const showEnrollmentModal = ref(false)

const getStatusClass = (status) => {
  const classes = {
    ACTIVE: 'bg-green-100 text-green-800',
    DORMANT: 'bg-yellow-100 text-yellow-800',
    WITHDRAWN: 'bg-red-100 text-red-800',
  }
  return classes[status] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status) => {
  const texts = {
    ACTIVE: '활성',
    DORMANT: '휴원',
    WITHDRAWN: '탈퇴',
  }
  return texts[status] || status
}

const formatDate = (date) => {
  return date ? new Date(date).toLocaleDateString('ko-KR') : '-'
}

const loadStudent = async () => {
  loading.value = true
  try {
    const studentId = route.params.id
    student.value = await studentApi.getById(studentId)
    enrollments.value = await studentApi.getEnrollmentsByStudent(studentId)
    attendances.value = await attendanceApi.getByStudent(studentId)
  } catch (error) {
    console.error('Failed to load student:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadStudent()
})
</script>
