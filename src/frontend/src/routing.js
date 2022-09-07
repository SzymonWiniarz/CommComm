import { createRouter, createWebHashHistory } from 'vue-router'

import Home from './components/Home.vue'
import HelloWorld from './components/HelloWorld.vue'
import Bye from './components/Bye.vue'

const routes = [
    {
        path: '/',
        component: Home
    },
    {
        path: '/hello',
        component: HelloWorld
    },
    {
        path: '/bye',
        component: Bye
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export {
    router
}