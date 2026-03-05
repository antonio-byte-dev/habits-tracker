<template>
  <div class="navbar">
      <span class="user-email">{{ email }}</span>
      <button class="logout-btn" @click="logout">Logout</button>
    </div>
  <div class="dashboard">
    <h1>Your Tasks</h1>
    <ul class="task-list">
      <li v-for="task in tasks" :key="getTaskKey(task)" class="task-card">
        <!-- Header row: clickable -->
        <div class="task-header" >
          <div class="left">
            <span class="task-name">{{ task.name }}</span>
          </div>
          
          <div class="right">
            <button  @click="editTask(task)">
            Edit
            </button>
            <button  @click="deleteTask(task)">
            Done!
            </button>
            <span class="arrow"  @click="toggleTask(getTaskKey(task))" :class="{ open: expandedTask === getTaskKey(task) }">▼</span>
          </div>
        </div>

        <!-- Dropdown content (full width under header) -->
        <div class="dropdown" :class="{ open: expandedTask === getTaskKey(task) }">
          <p class="task-description">{{ task.description || "No description." }}</p>
        </div>
        
      </li>
      <div>
      <button class="button-27" role="button" @click="redirectToAddTask">
      Add Task
      </button>
    </div>
    </ul>
  </div>
</template>

<script>
import api from "../services/api";
import { auth } from "../firebase";
import {  signOut } from "firebase/auth";

export default {
  data() {
    return {
      tasks: [],
      expandedTask: null,
      email: null,
    };
  },
  methods: {
    editTask(habit){
      this.$router.push({ name: "edithabit", params: { id: habit.id } });
    },
    async deleteTask(habit){
      //const ok = window.confirm(`Are you sure you want to delete "${habit.name}"?`);

      //if (!ok) return; // User clicked "Cancel"

      try{
        await api.delete('/' + habit.id);
        location.reload();
      }catch(err){
        console.error(err);
      }
      
    },
    redirectToAddTask(){
      this.$router.push('/add');
    },
    // toggle expanded task by key
    toggleTask(key) {
      this.expandedTask = this.expandedTask === key ? null : key;
    },
    // support tasks that have either .id or Mongo _id.$oid
    getTaskKey(task) {
      if (!task) return null;
      if (task.id !== undefined && task.id !== null) return String(task.id);
      if (task._id && (task._id.$oid || task._id)) return String(task._id.$oid || task._id);
      // fallback to name+frequency
      return `${task.name}-${task.frequency}`;
    },
    async markAsDone(){
      
    },
    async logout(){
      try {
        await signOut(auth);
        this.$router.push("/login"); // redirect to login
      } catch (err) {
        console.error("Logout error:", err);
      }
    },
  },
  async created() {
    try {
      let headers={};
      const user = auth.currentUser;
      const token = await user.getIdToken(); // get Firebase ID token
      headers["Authorization"] = `Bearer ${token}`;
      const res = await api.get("/",{headers}); // backend /api/tasks
      this.tasks = res.data || [];
      
      if (user) {
        this.email = user.email;
      }
    } catch (err) {
      console.error(err);
    }
  },
};
</script>

<style scoped>
.right {
  display: flex;
  justify-content: flex-end; /* pushes content to the right */
  margin-top: 0.5rem;
}

.center-down {
  display: flex;
  justify-content: flex-start; /* centers horizontally */

}

.dashboard {
  max-width: 700px;
  margin: 2rem auto;
  padding: 1rem;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #ffffff;
}

.dashboard h1 {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 1.25rem;
  color: #ffffff;
}

.task-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.task-card {
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.06);
  overflow: hidden;
}

/* Header row */
.task-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.25rem;
  cursor: pointer;
  user-select: none;
  transition: background-color 0.15s;
}
.task-header:hover {
  background-color: #f1f6fb;
}

.task-header .left {
  display: flex;
  flex-direction: column;
}
.task-name {
  font-weight: 600;
  font-size: 1.05rem;
  color: #222;
}
.task-meta {
  font-size: 0.85rem;
  color: #666;
  margin-top: 4px;
}

/* right side */
.task-header .right {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}
.task-frequency {
  background-color: #e8f4ff;
  color: #006bb3;
  padding: 0.28rem 0.6rem;
  border-radius: 6px;
  font-size: 0.9rem;
  font-weight: 500;
}

/* Arrow */
.arrow {
  display: inline-block;
  transition: transform 0.25s ease;
  color: #555;
  margin-left: 6px;
}
.arrow.open {
  transform: rotate(180deg);
}

/* Dropdown area */
.dropdown {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.35s ease, padding 0.25s ease;
  padding: 0 1.25rem; /* left/right padding matches header */
  box-sizing: border-box;
}

.dropdown.open {
  /* large enough for description text; adjust if needed */
  max-height: 300px;
  padding: 0.75rem 1.25rem 1rem 1.25rem;
  background: linear-gradient(180deg, rgba(245,248,255,0.9), rgba(255,255,255,0.9));
}

.task-description {
  margin: 0;
  color: #444;
  font-size: 0.95rem;
  line-height: 1.4;
}





/* CSS */
.button-27 {
  appearance: none;
  background-color: #000000;
  border: 2px solid #1A1A1A;
  border-radius: 15px;
  box-sizing: border-box;
  color: #FFFFFF;
  cursor: pointer;
  display: inline-block;
  font-family: Roobert,-apple-system,BlinkMacSystemFont,"Segoe UI",Helvetica,Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol";
  font-size: 16px;
  font-weight: 600;
  line-height: normal;
  margin: 0;
  min-height: 60px;
  min-width: 0;
  outline: none;
  padding: 16px 24px;
  text-align: center;
  text-decoration: none;
  transition: all 300ms cubic-bezier(.23, 1, 0.32, 1);
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  width: 100%;
  will-change: transform;
}

.button-27:disabled {
  pointer-events: none;
}

.button-27:hover {
  box-shadow: rgba(0, 0, 0, 0.25) 0 8px 15px;
  transform: translateY(-2px);
}

.button-27:active {
  box-shadow: none;
  transform: translateY(0);
}


/* Responsive */
@media (max-width: 520px) {
  .task-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.6rem;
  }
  .task-header .right {
    align-self: stretch;
    justify-content: space-between;
    width: 100%;
  }
}

button {
  padding: 0.4rem 0.8rem;
  border-radius: 6px;
  border: none;
  background: #00a6a6;
  color: white;
  cursor: pointer;
  transition: 0.2s;
}

button:hover {
  background: #008b8b;
}
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.75rem 2rem;
  background-color: #ffffff; /* teal shade */
  color: black;
  box-shadow: 0 2px 6px rgba(0,0,0,0.15);
}

.navbar h2 {
  margin: 0;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 1rem;
}
.user-email {
  color:#000000;
  font-weight: bold;
}

.logout-btn {
  background-color: #004d40;
  color: white;
  border: none;
  padding: 0.4rem 0.8rem;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.logout-btn:hover {
  background-color: #00332e;
}
</style>
