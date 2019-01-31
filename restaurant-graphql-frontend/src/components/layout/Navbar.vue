<template>
  <div id="navbar">
    <router-link to="/">Overview</router-link>
    <router-link to="/menu">Menu</router-link>
    <router-link to="/statistics">Statistics</router-link>
    <router-link to="/about">About</router-link>
  </div>

</template>

<script>
export default {
  name: "Navbar"
}
</script>

<style scoped>

  * {
    margin: 0;
    padding: 0;
  }

  #navbar {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    justify-content: space-between;
    padding: 30px;
    background-color: #D04A02;
    border-top: 2px #000 solid;
    border-bottom: 2px #000 solid;
  }

  #navbar a {
    font-weight: bold;
    font-size: 20pt;
    color: #000;
  }

  #navbar a.router-link-exact-active {
    color: #FFF;
  }

</style>
