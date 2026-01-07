import { createRouter, createWebHistory } from "vue-router";
import Dashboard from "../views/DashboardPage.vue";
import AddTaskForm from "../views/AddTaskForm.vue";
import UpdateTaskForm from "@/views/UpdateTaskForm.vue";
import { auth } from "../firebase";
import { onAuthStateChanged } from "firebase/auth";
import LoginPage from "@/views/LoginPage.vue";
import SignupPage from "@/views/SignupPage.vue";

function isAuthenticated() {
  return new Promise(resolve =>
    onAuthStateChanged(auth, (user) => resolve(!!user))
  );
}
const routes = [
  { path: '/', name: 'dashboard' ,component: Dashboard, meta: { requiresAuth: true } },
  { path: '/add',name:'addtask', component: AddTaskForm , meta: { requiresAuth: true }},
  {path: '/edit/:id',name:'edithabit',component: UpdateTaskForm, meta: { requiresAuth: true }},
    {path: '/login',name:'login',component: LoginPage, meta: { requiresAuth: false }},
      {path: '/signup',name:'register',component: SignupPage, meta: { requiresAuth: false }},
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  if (to.meta.requiresAuth && !(await isAuthenticated())) {
    next("/login");
  } else {
    next();
  }
});

export default router;
