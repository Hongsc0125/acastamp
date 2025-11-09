<template>
  <div class="px-4 sm:px-0">
    <div class="sm:flex sm:items-center sm:justify-between mb-6">
      <h2 class="text-2xl font-bold text-gray-900">출석 관리</h2>
      <button
        @click="showCheckInModal = true"
        class="mt-4 sm:mt-0 inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700"
      >
        출석 체크
      </button>
    </div>

    <!-- 필터 -->
    <div class="bg-white shadow rounded-lg p-4 mb-6">
      <div class="grid grid-cols-1 gap-4 sm:grid-cols-3">
        <div>
          <label class="block text-sm font-medium text-gray-700">시작일</label>
          <input
            v-model="filter.startDate"
            type="date"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700">종료일</label>
          <input
            v-model="filter.endDate"
            type="date"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
          />
        </div>
        <div class="flex items-end">
          <button
            @click="loadAttendances"
            class="w-full px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-gray-600 hover:bg-gray-700"
          >
            조회
          </button>
        </div>
      </div>
    </div>

    <!-- 출석 목록 -->
    <div class="bg-white shadow rounded-lg overflow-hidden">
      <div v-if="loading" class="p-8 text-center">
        <p class="text-gray-500">로딩 중...</p>
      </div>
      <div v-else-if="attendances.length === 0" class="p-8 text-center">
        <p class="text-gray-500">출석 기록이 없습니다.</p>
      </div>
      <table v-else class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">학생명</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">날짜</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">시간</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">회차</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">유형</th>
            <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">액션</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="attendance in attendances" :key="attendance.id" class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium text-gray-900">{{ attendance.studentName || '-' }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ formatDate(attendance.attendanceDate) }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ attendance.attendanceTime }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ attendance.lessonNumber }}회
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                :class="attendance.type === 'REGULAR' ? 'bg-blue-100 text-blue-800' : 'bg-purple-100 text-purple-800'"
                class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full"
              >
                {{ attendance.type === 'REGULAR' ? '정규' : '보강' }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
              <button
                @click="deleteAttendance(attendance.id)"
                class="text-red-600 hover:text-red-900"
              >
                삭제
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { attendanceApi } from '../services/api'
import { useAcademyStore } from '../stores/academy'

const academyStore = useAcademyStore()
const attendances = ref([])
const loading = ref(false)
const showCheckInModal = ref(false)

const today = new Date()
const firstDayOfMonth = new Date(today.getFullYear(), today.getMonth(), 1)

const filter = ref({
  startDate: firstDayOfMonth.toISOString().split('T')[0],
  endDate: today.toISOString().split('T')[0],
})

const formatDate = (date) => {
  return date ? new Date(date).toLocaleDateString('ko-KR') : '-'
}

const loadAttendances = async () => {
  loading.value = true
  try {
    const academyId = academyStore.academyId || 1
    attendances.value = await attendanceApi.getByDateRange(
      academyId,
      filter.value.startDate,
      filter.value.endDate
    )
  } catch (error) {
    console.error('Failed to load attendances:', error)
  } finally {
    loading.value = false
  }
}

const deleteAttendance = async (id) => {
  if (!confirm('정말로 이 출석 기록을 삭제하시겠습니까?')) return

  try {
    await attendanceApi.delete(id)
    await loadAttendances()
  } catch (error) {
    console.error('Failed to delete attendance:', error)
    alert('출석 기록 삭제에 실패했습니다.')
  }
}

onMounted(() => {
  loadAttendances()
})
</script>
