<script>

import axios from 'axios';

export default {
  el: '#app',
  props: {
    msg: {
    type: String,
    required: true
  }
  },
  data () {
    return {
      info: null
    }
  },
  mounted () {
    axios
      .get('http://localhost:8080/api/file/helloworld')
      .then(response => {
        this.info = response.data;
        console.log(response.data);
      })
  }
}


</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>
      You’ve successfully created a project with
      <a href="https://vitejs.dev/" target="_blank" rel="noopener">Vite</a> +
      <a href="https://vuejs.org/" target="_blank" rel="noopener">Vue 3</a>.
    </h3>
  </div>

  <div id="app">
    {{ info }}
  </div>

  <form action="http://localhost:8080/api/file/upload" method="post" enctype="multipart/form-data">
    <input name="file" type="file" multiple>
    <button class="button button2" type="submit">Upload</button>
  </form>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}

.button, .input, input::file-selector-button {
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}

.button2, .input2, input::file-selector-button { background-color: #008CBA; }



</style>
