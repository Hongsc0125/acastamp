<template>
  <div class="px-4 sm:px-0">
    <div class="sm:flex sm:items-center sm:justify-between mb-6">
      <h2 class="text-2xl font-bold text-gray-900">학생 관리</h2>
      <button
        @click="showCreateModal = true"
        class="mt-4 sm:mt-0 inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700"
      >
        학생 추가
      </button>
    </div>

    <!-- 필터 -->
    <div class="bg-white shadow rounded-lg p-4 mb-6">
      <div class="grid grid-cols-1 gap-4 sm:grid-cols-3">
        <div>
          <label class="block text-sm font-medium text-gray-700">상태</label>
          <select
            v-model="filter.status"
            @change="loadStudents"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
          >
            <option value="">전체</option>
            <option value="ACTIVE">활성</option>
            <option value="DORMANT">휴원</option>
            <option value="WITHDRAWN">탈퇴</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700">검색</label>
          <input
            v-model="filter.search"
            type="text"
            placeholder="이름, 전화번호 검색..."
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
          />
        </div>
      </div>
    </div>

    <!-- 학생 목록 -->
    <div class="bg-white shadow rounded-lg overflow-hidden">
      <div v-if="loading" class="p-8 text-center">
        <p class="text-gray-500">로딩 중...</p>
      </div>
      <div v-else-if="filteredStudents.length === 0" class="p-8 text-center">
        <p class="text-gray-500">학생이 없습니다.</p>
      </div>
      <table v-else class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">이름</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">전화번호</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">상태</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">등록일</th>
            <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">액션</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="student in filteredStudents" :key="student.id" class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium text-gray-900">{{ student.name }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm text-gray-500">{{ student.phoneNumber }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                :class="getStatusClass(student.status)"
                class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full"
              >
                {{ getStatusText(student.status) }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ formatDate(student.enrollmentDate) }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
              <router-link
                :to="`/students/${student.id}`"
                class="text-blue-600 hover:text-blue-900 mr-4"
              >
                상세
              </router-link>
              <button
                @click="deleteStudent(student.id)"
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
import { ref, computed, onMounted } from 'vue'
import { studentApi } from '../services/api'
import { useAcademyStore } from '../stores/academy'

const academyStore = useAcademyStore()
const students = ref([])
const loading = ref(false)
const showCreateModal = ref(false)

const filter = ref({
  status: '',
  search: '',
})

const filteredStudents = computed(() => {
  return students.value.filter((student) => {
    if (filter.value.search) {
      const searchLower = filter.value.search.toLowerCase()
      return (
        student.name.toLowerCase().includes(searchLower) ||
        student.phoneNumber?.includes(searchLower)
      )
    }
    return true
  })
})

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

const loadStudents = async () => {
  loading.value = true
  try {
    const academyId = academyStore.academyId || 1
    if (filter.value.status) {
      students.value = await studentApi.getByStatus(academyId, filter.value.status)
    } else {
      students.value = await studentApi.getAllByAcademy(academyId)
    }
  } catch (error) {
    console.error('Failed to load students:', error)
  } finally {
    loading.value = false
  }
}

const deleteStudent = async (id) => {
  if (!confirm('정말로 이 학생을 삭제하시겠습니까?')) return

  try {
    await studentApi.delete(id)
    await loadStudents()
  } catch (error) {
    console.error('Failed to delete student:', error)
    alert('학생 삭제에 실패했습니다.')
  }
}

onMounted(() => {
  loadStudents()
})
</script>
