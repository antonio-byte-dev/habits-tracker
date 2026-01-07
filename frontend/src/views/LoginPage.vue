<template>
  <div class="login-page">
    <div class="login-card">
      <h2>Login</h2>
      <div class="left">
        <input v-model="email" type="email" placeholder="Email" />
        <input v-model="password" type="password" placeholder="Password" />
      </div>
      <div class="button-group">
        <button @click="login" class="login-btn">Login</button>
        <button @click="signup" class="signup-btn">Signup</button>
      </div>
    </div>
  </div>
</template>

<script>
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebase";

export default {
  data() {
    return {
      email: "",
      password: ""
    };
  },
  methods: {
    signup() {
      this.$router.push("/signup");
    },
    async login() {
      try {
        await signInWithEmailAndPassword(auth, this.email, this.password);
        alert("Logged in!");
        this.$router.push("/");
      } catch (err) {
        alert("Login failed: " + err.message);
      }
    }
  }
};
</script>

<style scoped>
/* Full-page background */
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: teal;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Login card */
.login-card {
  background-color: #ffffff;
  padding: 2rem 2.5rem;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0,0,0,0.15);
  width: 100%;
  max-width: 400px;
  text-align: center;
}

/* Heading */
.login-card h2 {
  margin-bottom: 1.5rem;
  color: #00796b;
}

/* Inputs */
.login-card input {
  width: 100%;
  padding: 0.75rem 0rem;
  margin-bottom: 1rem;
  border-radius: 8px;
  border: 1px solid #bdbdbd;
  font-size: 1rem;
  outline: none;
  transition: border-color 0.3s;
}

.login-card input:focus {
  border-color: #00796b;
}

/* Buttons container */
.button-group {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

/* Buttons */
.login-card button {
  flex: 1;
  padding: 0.75rem;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
  color: #fff;
  transition: background-color 0.3s, transform 0.2s;
}

.login-btn {
  background-color: #00796b;
}

.signup-btn {
  background-color: #004d40;
}

.login-card button:hover {
  transform: translateY(-2px);
}

.login-btn:hover {
  background-color: #004d40;
}

.signup-btn:hover {
  background-color: #00796b;
}

/* Responsive */
@media (max-width: 450px) {
  .button-group {
    flex-direction: column;
  }
}
</style>
