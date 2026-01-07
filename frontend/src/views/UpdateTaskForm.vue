<template>
  <div style="height: 30px;"></div>
  <div class="task-form-container">
    <h1>Edit Task</h1>

    <form class="task-form" @submit.prevent="updateTask">
      <div class="input-group">
        <label>Task Name</label>
        <input v-model="name" placeholder="Ex: Drink Water" />
      </div>

      <div class="input-group">
        <label>Description</label>
        <input v-model="description" placeholder="Ex: 8 glasses/day" />
      </div>

      <div class="input-group">
        <label>Every x days</label>
        <input v-model.number="frequency" type="number" min="1" />
      </div>

      <button class="submit-btn" type="submit">Accept changes</button>
    </form>
  </div>
  <button class="submit-btn" role="button" @click="redirectToDashboard">
      Volver
  </button>
</template>

<script>
import api from "../services/api";
//var habit;
export default {
  data() {
    return {
      name: "",
      description: "",
      frequency: 1,
    };
  },
    async created() {
    this.id = this.$route.params.id;

    try {
      const res = await api.get("/" + this.id);
      const habit = res.data;

      this.name = habit.name;
      this.description = habit.description;
      this.frequency = habit.frequency;
    } catch (err) {
      console.error("Failed to load habit:", err);
    }
  },

  methods: {
    redirectToDashboard(){
      this.$router.push('/');
    },
    async updateTask() {
      try {
        await api.put("/" + this.id, {
          name: this.name,
          description: this.description,
          frequency: this.frequency,
          userName: "admin",
        });
        alert("Habit modified correctly");

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

