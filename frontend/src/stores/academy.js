import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { academyApi } from '../services/api'

export const useAcademyStore = defineStore('academy', () => {
  const currentAcademy = ref(null)
  const academySettings = ref(null)
  const loading = ref(false)
  const error = ref(null)

  const academyId = computed(() => currentAcademy.value?.id || null)
  const academyName = computed(() => currentAcademy.value?.name || '')

  async function fetchAcademy(id) {
    loading.value = true
    error.value = null
    try {
      currentAcademy.value = await academyApi.getById(id)
    } catch (err) {
      error.value = err.message
      throw err
    } finally {
      loading.value = false
    }
  }

  async function fetchSettings(id) {
    loading.value = true
    error.value = null
    try {
      academySettings.value = await academyApi.getSettings(id)
    } catch (err) {
      error.value = err.message
      throw err
    } finally {
      loading.value = false
    }
  }

  async function updateSettings(id, settings) {
    loading.value = true
    error.value = null
    try {
      academySettings.value = await academyApi.updateSettings(id, settings)
    } catch (err) {
      error.value = err.message
      throw err
    } finally {
      loading.value = false
    }
  }

  function setAcademy(academy) {
    currentAcademy.value = academy
  }

  function $reset() {
    currentAcademy.value = null
    academySettings.value = null
    loading.value = false
    error.value = null
  }

  return {
    currentAcademy,
    academySettings,
    loading,
    error,
    academyId,
    academyName,
    fetchAcademy,
    fetchSettings,
    updateSettings,
    setAcademy,
    $reset,
  }
})
