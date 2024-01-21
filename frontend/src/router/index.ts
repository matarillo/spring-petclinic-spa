import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import OwnersView from '@/views/OwnersView.vue'
import OwnerList from '@/components/owners/OwnerList.vue'
import OwnerDetail from '@/components/owners/OwnerDetail.vue'
import OwnerAdd from '@/components/owners/OwnerAdd.vue'
import OwnerEdit from '@/components/owners/OwnerEdit.vue'
import PetAdd from '@/components/pets/PetAdd.vue'
import PetEdit from '@/components/pets/PetEdit.vue'
import VisitAdd from '@/components/visits/VisitAdd.vue'
import VetsView from '@/views/VetsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/owners',
      name: 'owners',
      component: OwnersView,
      children: [
        {
          path: '',
          name: 'list',
          component: OwnerList
        },
        {
          path: 'new',
          name: 'add',
          component: OwnerAdd
        },

        {
          path: ':id',
          name: 'detail',
          component: OwnerDetail
        },
        {
          path: ':id/edit',
          name: 'edit',
          component: OwnerEdit
        },
        {
          path: ':id/pets/new',
          name: 'add pet',
          component: PetAdd
        },
        {
          path: ':id/pets/:petId/edit',
          name: 'edit pet',
          component: PetEdit
        },
        {
          path: ':id/pets/:petId/visits/new',
          name: 'add visit',
          component: VisitAdd
        }
      ]
    },
    {
      path: '/vets',
      name: 'vets',
      component: VetsView
    },
    {
      path: '/oups',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    }
  ]
})

export default router
