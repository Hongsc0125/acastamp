<script setup>
import { computed, onMounted } from 'vue'
import { useAcademyStore } from './stores/academy'

const academyStore = useAcademyStore()
const academyName = computed(() => academyStore.academyName)

onMounted(() => {
  // TODO: 실제 학원 ID를 로그인 정보에서 가져와야 함
  // 임시로 하드코딩
  const academyId = 1
  academyStore.fetchAcademy(academyId).catch(() => {
    // 학원 정보 로드 실패 시 처리
  })
})
</script>

<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white shadow-sm border-b">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16">
          <div class="flex">
            <div class="flex-shrink-0 flex items-center">
              <h1 class="text-xl font-bold text-gray-900">{{ academyName || '학원 관리 시스템' }}</h1>
            </div>
            <div class="hidden sm:ml-8 sm:flex sm:space-x-8">
              <router-link
                to="/"
                class="border-transparent text-gray-600 hover:border-gray-300 hover:text-gray-900 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium"
                active-class="border-blue-500 text-gray-900"
              >
                대시보드
              </router-link>
              <router-link
                to="/students"
                class="border-transparent text-gray-600 hover:border-gray-300 hover:text-gray-900 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium"
                active-class="border-blue-500 text-gray-900"
              >
                학생 관리
              </router-link>
              <router-link
                to="/attendance"
                class="border-transparent text-gray-600 hover:border-gray-300 hover:text-gray-900 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium"
                active-class="border-blue-500 text-gray-900"
              >
                출석 관리
              </router-link>
              <router-link
                to="/payments"
                class="border-transparent text-gray-600 hover:border-gray-300 hover:text-gray-900 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium"
                active-class="border-blue-500 text-gray-900"
              >
                결제 관리
              </router-link>
              <router-link
                to="/settings"
                class="border-transparent text-gray-600 hover:border-gray-300 hover:text-gray-900 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium"
                active-class="border-blue-500 text-gray-900"
              >
                설정
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <router-view />
    </main>
  </div>
</template>
