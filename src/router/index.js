import { createRouter, createWebHistory } from 'vue-router'
import SignInView from '../views/SignInView.vue'
import SignUpView from '../views/SignUpView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/signin',
      name: 'signin',
      component: SignInView
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignUpView
    }
  ]
})

export default router