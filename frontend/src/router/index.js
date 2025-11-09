import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import StudentList from '../views/StudentList.vue'
import StudentDetail from '../views/StudentDetail.vue'
import AttendanceList from '../views/AttendanceList.vue'
import PaymentList from '../views/PaymentList.vue'
import Settings from '../views/Settings.vue'

const routes = [
  {
    path: '/',
    name: 'Dashboard',
    component: Dashboard,
  },
  {
    path: '/students',
    name: 'StudentList',
    component: StudentList,
  },
  {
    path: '/students/:id',
    name: 'StudentDetail',
    component: StudentDetail,
    props: true,
  },
  {
    path: '/attendance',
    name: 'AttendanceList',
    component: AttendanceList,
  },
  {
    path: '/payments',
    name: 'PaymentList',
    component: PaymentList,
  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
