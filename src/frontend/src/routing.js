import { createRouter, createWebHashHistory } from 'vue-router'

import Dashboard from './components/Dashboard.vue'
import Kids from './components/kids/Kids.vue'
import Cars from './components/cars/Cars.vue'
import EditCar from './components/cars/EditCar.vue'
import AddCar from './components/cars/AddCar.vue'
import Commuting from './components/commuting/Commuting.vue'
import DeclareTransportToSchool from './components/commuting/DeclareTransportToSchool.vue'
import EditUser from './components/user/EditUser.vue'
import AddKid from './components/kids/AddKid.vue'
import EditKid from './components/kids/EditKid.vue'
import EditSchedule from './components/schedules/EditSchedule.vue'
import Absences from './components/absences/Absences.vue'

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
        path: '/dzieci/:id',
        component: EditKid,
        props: true
    },
    {
        path: '/dzieci/dodaj',
        component: AddKid
    },
    {
        path: '/dzieci/:id/plan',
        component: EditSchedule,
        props: true
    },
    {
        path: '/dzieci/:id/nieobecnosci',
        component: Absences,
        props: true
    },
    {
        path: '/samochody',
        component: Cars
    },
    {
        path: '/samochody/:id',
        component: EditCar,
        props: true
    },
    {
        path: '/samochody/dodaj',
        component: AddCar
    },
    {
        path: '/przewozy',
        component: Commuting
    },
    {
        path: '/dowozy/dodaj',
        component: DeclareTransportToSchool
    },
    {
        path: '/uzytkownicy/:id',
        component: EditUser,
        props: true
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export {
    router
}