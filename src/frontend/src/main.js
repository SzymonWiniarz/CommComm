import { createApp } from 'vue'

import App from './App.vue'

import './assets/main.css'
import { router } from './routing'

createApp(App)
    .use(router)
    .mount('#app')
