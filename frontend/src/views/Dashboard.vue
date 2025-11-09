<template>
  <div class="px-4 sm:px-0">
    <h2 class="text-2xl font-bold text-gray-900 mb-6">대시보드</h2>

    <!-- 통계 카드 -->
    <div class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4 mb-8">
      <div class="bg-white overflow-hidden shadow rounded-lg">
        <div class="p-5">
          <div class="flex items-center">
            <div class="flex-1">
              <dt class="text-sm font-medium text-gray-500 truncate">전체 학생</dt>
              <dd class="mt-1 text-3xl font-semibold text-gray-900">{{ stats.totalStudents }}</dd>
            </div>
          </div>
        </div>
      </div>

      <div class="bg-white overflow-hidden shadow rounded-lg">
        <div class="p-5">
          <div class="flex items-center">
            <div class="flex-1">
              <dt class="text-sm font-medium text-gray-500 truncate">활성 학생</dt>
              <dd class="mt-1 text-3xl font-semibold text-green-600">{{ stats.activeStudents }}</dd>
            </div>
          </div>
        </div>
      </div>

      <div class="bg-white overflow-hidden shadow rounded-lg">
        <div class="p-5">
          <div class="flex items-center">
            <div class="flex-1">
              <dt class="text-sm font-medium text-gray-500 truncate">이번 달 출석</dt>
              <dd class="mt-1 text-3xl font-semibold text-blue-600">{{ stats.monthlyAttendance }}</dd>
            </div>
          </div>
        </div>
      </div>

      <div class="bg-white overflow-hidden shadow rounded-lg">
        <div class="p-5">
          <div class="flex items-center">
            <div class="flex-1">
              <dt class="text-sm font-medium text-gray-500 truncate">미수금</dt>
              <dd class="mt-1 text-3xl font-semibold text-red-600">{{ stats.overduePayments }}</dd>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 최근 활동 -->
    <div class="bg-white shadow rounded-lg p-6">
      <h3 class="text-lg font-medium text-gray-900 mb-4">최근 활동</h3>
      <div v-if="loading" class="text-center py-4">
        <p class="text-gray-500">로딩 중...</p>
      </div>
      <div v-else-if="recentActivities.length === 0" class="text-center py-4">
        <p class="text-gray-500">최근 활동이 없습니다.</p>
      </div>
      <ul v-else class="divide-y divide-gray-200">
        <li v-for="activity in recentActivities" :key="activity.id" class="py-4">
          <div class="flex space-x-3">
            <div class="flex-1 space-y-1">
              <div class="flex items-center justify-between">
                <h4 class="text-sm font-medium">{{ activity.title }}</h4>
                <p class="text-sm text-gray-500">{{ formatDate(activity.date) }}</p>
              </div>
              <p class="text-sm text-gray-500">{{ activity.description }}</p>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useAcademyStore } from '../stores/academy'

const academyStore = useAcademyStore()
const loading = ref(false)

const stats = ref({
  totalStudents: 0,
  activeStudents: 0,
  monthlyAttendance: 0,
  overduePayments: 0,
})

const recentActivities = ref([])

const formatDate = (date) => {
  return new Date(date).toLocaleDateString('ko-KR')
}

onMounted(async () => {
  loading.value = true
  try {
    // TODO: API에서 통계 데이터 가져오기
    // 임시 데이터
    stats.value = {
      totalStudents: 42,
      activeStudents: 38,
      monthlyAttendance: 156,
      overduePayments: 3,
    }

    recentActivities.value = [
      { id: 1, title: '새 학생 등록', description: '김철수 학생이 등록되었습니다.', date: new Date() },
      { id: 2, title: '출석 체크', description: '오늘 총 12명의 학생이 출석했습니다.', date: new Date() },
    ]
  } catch (error) {
    console.error('Failed to load dashboard data:', error)
  } finally {
    loading.value = false
  }
})
</script>
