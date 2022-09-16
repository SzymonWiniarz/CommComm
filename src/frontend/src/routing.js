import { createRouter, createWebHashHistory } from 'vue-router'

import Dashboard from './components/Dashboard.vue'
import Kids from './components/kids/Kids.vue'
import Cars from './components/Cars.vue'
import Commuting from './components/Commuting.vue'
import EditUser from './components/user/EditUser.vue'
import AddKid from './components/kids/AddKid.vue'

const routes = [
    {
        path: '/',
        component: Dashboard
    },
    {
        path: '/dzieci',
        component: Kids
    },
    {
        path: '/dzieci/dodaj',
        component: AddKid
    },
    {
        path: '/samochody',
        component: Cars
    },
    {
        path: '/przewozy',
        component: Commuting
    },
    {
        path: '/uzytkownicy/:id',
        component: EditUser
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export {
    router
}