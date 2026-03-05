<template>
  <div style="height: 30px;"></div>
  <div class="task-form-container">
    <h1>Add a New Task</h1>

    <form class="task-form" @submit.prevent="addTask">
      <div class="input-group">
        <label>Task Name</label>
        <input v-model="name" placeholder="Ex: Drink Water" />
      </div>

      <div class="input-group">
        <label>Description</label>
        <input v-model="description" placeholder="Ex: 8 glasses/day" />
      </div>

      <button class="submit-btn" type="submit">Add Task</button>
    </form>
  </div>
  <button class="submit-btn" role="button" @click="redirectToDashboard">
      Volver
  </button>
</template>

<script>
import api from "../services/api";
import { auth } from "../firebase";
export default {
  data() {
    return {
      name: "",
      description: "",
      frequency: 1,
    };
  },
  methods: {
    redirectToDashboard(){
      this.$router.push('/');
    },
    async addTask() {
      try {
        let headers={};
        const user = auth.currentUser;
        const token = await user.getIdToken(); // get Firebase ID token
        headers["Authorization"] = `Bearer ${token}`;
        await api.post("/", {
          name: this.name,
          description: this.description,
          frequency: 1,
        },
      {
      headers: {
      Authorization: "Bearer " + token,
      "Content-Type": "application/json"
      }
    });
        alert("Task added!");

        this.name = "";
        this.description = "";
        this.frequency = 1;

        this.$router.push('/');
      } catch (err) {
        console.error(err);
      }
    },
  },
};
</script>

<style scoped>
.task-form-container {
  max-width: 450px;
  margin: 2rem auto;
  padding: 2rem 1.5rem;
  background: #ffffffd9;
  border-radius: 14px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.12);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

h1 {
  text-align: center;
  color: #2c3e50;
  font-size: 1.7rem;
  margin-bottom: 1.5rem;
}

.task-form {
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
}

.input-group {
  display: flex;
  flex-direction: column;
}

label {
  font-weight: 600;
  margin-bottom: 0.3rem;
  color: #2c3e50;
}

input {
  padding: 0.7rem;
  border-radius: 8px;
  border: 1px solid #ccc;
  transition: border-color 0.25s, box-shadow 0.25s;
}

input:focus {
  border-color: #00a6a6;
  box-shadow: 0 0 5px rgba(0, 166, 166, 0.4);
  outline: none;
}

.submit-btn {
  background: #00a6a6;
  color: white;
  padding: 0.8rem;
  border-radius: 8px;
  border: none;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.2s, transform 0.2s;
}

.submit-btn:hover {
  background: #008b8b;
  transform: translateY(-2px);
}

</style>

