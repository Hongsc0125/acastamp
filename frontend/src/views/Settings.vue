<template>
  <div class="px-4 sm:px-0">
    <h2 class="text-2xl font-bold text-gray-900 mb-6">설정</h2>

    <div class="space-y-6">
      <!-- 학원 기본 정보 -->
      <div class="bg-white shadow rounded-lg p-6">
        <h3 class="text-lg font-medium text-gray-900 mb-4">학원 기본 정보</h3>

        <div v-if="loadingAcademy" class="text-center py-4">
          <p class="text-gray-500">로딩 중...</p>
        </div>

        <form v-else-if="academy" @submit.prevent="saveAcademy" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700">학원명</label>
            <input
              v-model="academy.name"
              type="text"
              required
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">사업자 번호</label>
            <input
              v-model="academy.businessNumber"
              type="text"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            />
          </div>

          <div class="grid grid-cols-1 gap-4 sm:grid-cols-2">
            <div>
              <label class="block text-sm font-medium text-gray-700">은행명</label>
              <input
                v-model="academy.bankName"
                type="text"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700">계좌번호</label>
              <input
                v-model="academy.accountNumber"
                type="text"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">주소</label>
            <input
              v-model="academy.address"
              type="text"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">전화번호</label>
            <input
              v-model="academy.phoneNumber"
              type="tel"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            />
          </div>

          <div class="flex justify-end">
            <button
              type="submit"
              class="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700"
            >
              저장
            </button>
          </div>
        </form>
      </div>

      <!-- 시스템 설정 -->
      <div class="bg-white shadow rounded-lg p-6">
        <h3 class="text-lg font-medium text-gray-900 mb-4">시스템 설정</h3>

        <div v-if="loadingSettings" class="text-center py-4">
          <p class="text-gray-500">로딩 중...</p>
        </div>

        <form v-else-if="settings" @submit.prevent="saveSettings" class="space-y-4">
          <div>
            <label class="flex items-center">
              <input
                v-model="settings.autoPaymentReminder"
                type="checkbox"
                class="rounded border-gray-300 text-blue-600 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
              <span class="ml-2 text-sm text-gray-700">자동 결제 알림</span>
            </label>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">결제 알림일 (매월)</label>
            <input
              v-model.number="settings.paymentReminderDay"
              type="number"
              min="1"
              max="31"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            />
            <p class="mt-1 text-sm text-gray-500">매월 며칠에 결제 알림을 보낼지 설정합니다.</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">출석 체크 시간 (분)</label>
            <input
              v-model.number="settings.attendanceCheckTime"
              type="number"
              min="1"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            />
            <p class="mt-1 text-sm text-gray-500">출석 체크를 받을 수 있는 시간 범위를 설정합니다.</p>
          </div>

          <div>
            <label class="flex items-center">
              <input
                v-model="settings.allowMakeupLesson"
                type="checkbox"
                class="rounded border-gray-300 text-blue-600 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
              <span class="ml-2 text-sm text-gray-700">보강 수업 허용</span>
            </label>
          </div>

          <div class="flex justify-end">
            <button
              type="submit"
              class="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700"
            >
              저장
            </button>
          </div>
        </form>
      </div>

      <!-- 수업 유형 관리 -->
      <div class="bg-white shadow rounded-lg p-6">
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-lg font-medium text-gray-900">수업 유형 관리</h3>
          <button
            @click="showLessonTypeModal = true"
            class="inline-flex items-center px-3 py-1.5 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700"
          >
            추가
          </button>
        </div>

        <div v-if="lessonTypes.length === 0" class="text-center py-4">
          <p class="text-gray-500">수업 유형이 없습니다.</p>
        </div>
        <ul v-else class="divide-y divide-gray-200">
          <li v-for="lessonType in lessonTypes" :key="lessonType.id" class="py-3 flex justify-between items-center">
            <div>
              <p class="text-sm font-medium text-gray-900">{{ lessonType.name }}</p>
              <p class="text-sm text-gray-500">{{ formatCurrency(lessonType.price) }}</p>
            </div>
            <button
              @click="deleteLessonType(lessonType.id)"
              class="text-red-600 hover:text-red-900 text-sm"
            >
              삭제
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { academyApi, lessonApi } from '../services/api'
import { useAcademyStore } from '../stores/academy'

const academyStore = useAcademyStore()
const academy = ref(null)
const settings = ref(null)
const lessonTypes = ref([])
const loadingAcademy = ref(false)
const loadingSettings = ref(false)
const showLessonTypeModal = ref(false)

const formatCurrency = (amount) => {
  return new Intl.NumberFormat('ko-KR', {
    style: 'currency',
    currency: 'KRW',
  }).format(amount)
}

const loadAcademy = async () => {
  loadingAcademy.value = true
  try {
    const academyId = academyStore.academyId || 1
    academy.value = await academyApi.getById(academyId)
  } catch (error) {
    console.error('Failed to load academy:', error)
  } finally {
    loadingAcademy.value = false
  }
}

const loadSettings = async () => {
  loadingSettings.value = true
  try {
    const academyId = academyStore.academyId || 1
    settings.value = await academyApi.getSettings(academyId)
  } catch (error) {
    console.error('Failed to load settings:', error)
  } finally {
    loadingSettings.value = false
  }
}

const loadLessonTypes = async () => {
  try {
    const academyId = academyStore.academyId || 1
    lessonTypes.value = await lessonApi.getLessonTypesByAcademy(academyId)
  } catch (error) {
    console.error('Failed to load lesson types:', error)
  }
}

const saveAcademy = async () => {
  try {
    await academyApi.update(academy.value.id, academy.value)
    academyStore.setAcademy(academy.value)
    alert('학원 정보가 저장되었습니다.')
  } catch (error) {
    console.error('Failed to save academy:', error)
    alert('학원 정보 저장에 실패했습니다.')
  }
}

const saveSettings = async () => {
  try {
    const academyId = academyStore.academyId || 1
    await academyApi.updateSettings(academyId, settings.value)
    alert('설정이 저장되었습니다.')
  } catch (error) {
    console.error('Failed to save settings:', error)
    alert('설정 저장에 실패했습니다.')
  }
}

const deleteLessonType = async (id) => {
  if (!confirm('정말로 이 수업 유형을 삭제하시겠습니까?')) return

  try {
    await lessonApi.deleteLessonType(id)
    await loadLessonTypes()
  } catch (error) {
    console.error('Failed to delete lesson type:', error)
    alert('수업 유형 삭제에 실패했습니다.')
  }
}

onMounted(() => {
  loadAcademy()
  loadSettings()
  loadLessonTypes()
})
</script>
