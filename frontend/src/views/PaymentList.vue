<template>
  <div class="px-4 sm:px-0">
    <div class="sm:flex sm:items-center sm:justify-between mb-6">
      <h2 class="text-2xl font-bold text-gray-900">결제 관리</h2>
      <button
        @click="showCreateModal = true"
        class="mt-4 sm:mt-0 inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700"
      >
        결제 등록
      </button>
    </div>

    <!-- 필터 -->
    <div class="bg-white shadow rounded-lg p-4 mb-6">
      <div class="grid grid-cols-1 gap-4 sm:grid-cols-3">
        <div>
          <label class="block text-sm font-medium text-gray-700">결제 상태</label>
          <select
            v-model="filter.status"
            @change="loadPayments"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
          >
            <option value="">전체</option>
            <option value="PENDING">대기</option>
            <option value="COMPLETED">완료</option>
            <option value="OVERDUE">연체</option>
          </select>
        </div>
      </div>
    </div>

    <!-- 연체 알림 -->
    <div v-if="overdueCount > 0" class="bg-red-50 border-l-4 border-red-400 p-4 mb-6">
      <div class="flex">
        <div class="flex-shrink-0">
          <svg class="h-5 w-5 text-red-400" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/>
          </svg>
        </div>
        <div class="ml-3">
          <p class="text-sm text-red-700">
            연체된 결제가 <strong>{{ overdueCount }}건</strong> 있습니다.
          </p>
        </div>
      </div>
    </div>

    <!-- 결제 목록 -->
    <div class="bg-white shadow rounded-lg overflow-hidden">
      <div v-if="loading" class="p-8 text-center">
        <p class="text-gray-500">로딩 중...</p>
      </div>
      <div v-else-if="payments.length === 0" class="p-8 text-center">
        <p class="text-gray-500">결제 내역이 없습니다.</p>
      </div>
      <table v-else class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">학생명</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">금액</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">결제일</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">청구월</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">상태</th>
            <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">액션</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="payment in payments" :key="payment.id" class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium text-gray-900">{{ payment.studentName || '-' }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm text-gray-900">{{ formatCurrency(payment.amount) }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ formatDate(payment.paymentDate) }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ payment.billingMonth }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span
                :class="getStatusClass(payment.status)"
                class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full"
              >
                {{ getStatusText(payment.status) }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
              <button
                v-if="payment.status !== 'COMPLETED'"
                @click="completePayment(payment.id)"
                class="text-green-600 hover:text-green-900 mr-4"
              >
                완료
              </button>
              <button
                @click="issueReceipt(payment.id)"
                class="text-blue-600 hover:text-blue-900 mr-4"
              >
                영수증
              </button>
              <button
                @click="deletePayment(payment.id)"
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
import { paymentApi } from '../services/api'
import { useAcademyStore } from '../stores/academy'

const academyStore = useAcademyStore()
const payments = ref([])
const loading = ref(false)
const showCreateModal = ref(false)

const filter = ref({
  status: '',
})

const overdueCount = computed(() => {
  return payments.value.filter((p) => p.status === 'OVERDUE').length
})

const getStatusClass = (status) => {
  const classes = {
    PENDING: 'bg-yellow-100 text-yellow-800',
    COMPLETED: 'bg-green-100 text-green-800',
    OVERDUE: 'bg-red-100 text-red-800',
  }
  return classes[status] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status) => {
  const texts = {
    PENDING: '대기',
    COMPLETED: '완료',
    OVERDUE: '연체',
  }
  return texts[status] || status
}

const formatDate = (date) => {
  return date ? new Date(date).toLocaleDateString('ko-KR') : '-'
}

const formatCurrency = (amount) => {
  return new Intl.NumberFormat('ko-KR', {
    style: 'currency',
    currency: 'KRW',
  }).format(amount)
}

const loadPayments = async () => {
  loading.value = true
  try {
    const academyId = academyStore.academyId || 1
    if (filter.value.status) {
      payments.value = await paymentApi.getByStatus(academyId, filter.value.status)
    } else {
      // TODO: 전체 결제 조회 API 필요
      payments.value = []
    }
  } catch (error) {
    console.error('Failed to load payments:', error)
  } finally {
    loading.value = false
  }
}

const completePayment = async (id) => {
  try {
    await paymentApi.updateStatus(id, 'COMPLETED')
    await loadPayments()
  } catch (error) {
    console.error('Failed to complete payment:', error)
    alert('결제 완료 처리에 실패했습니다.')
  }
}

const issueReceipt = async (paymentId) => {
  try {
    const receipt = await paymentApi.createReceipt({ paymentId })
    alert(`영수증이 발급되었습니다. 영수증 번호: ${receipt.receiptNumber}`)
  } catch (error) {
    console.error('Failed to issue receipt:', error)
    alert('영수증 발급에 실패했습니다.')
  }
}

const deletePayment = async (id) => {
  if (!confirm('정말로 이 결제를 삭제하시겠습니까?')) return

  try {
    await paymentApi.delete(id)
    await loadPayments()
  } catch (error) {
    console.error('Failed to delete payment:', error)
    alert('결제 삭제에 실패했습니다.')
  }
}

onMounted(() => {
  loadPayments()
})
</script>
