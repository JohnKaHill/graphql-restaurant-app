<template>
  <div id="app">
    <Header/>
    <Navbar/>
    <router-view/>
  </div>
</template>

<script>
  import Header from './components/layout/Header.vue'
  import Navbar from './components/layout/Navbar.vue'
  import store from './store'

  export default {
    name: 'app',
    store,
    components: {
      Header,
      Navbar
    }
  }
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>
