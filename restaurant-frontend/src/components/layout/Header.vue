<template>
  <div id="header">
    <div id="box-1">
      <img src="../../assets/PwC_Logo.jpg" alt="Restaurant Logo" height="80px" width="80px">
    </div>
    <div id="box-2">
      <h1>Welcome to <i>food.equals("lecker");</i></h1>
    </div>
  </div>

</template>

<script>
export default {
  name: "Header"
}
</script>

<style scoped>
  h1{
    font-size: 30pt;
    line-height: 100%;
  }

  #header {
    display: flex;
    justify-content: space-between;
    background: #FFF;
    color: #000;
    text-align: center;
    padding: 20px;
  }

  #header a {
    color: #fff;
    padding-right: 5px;
    text-decoration: none;
  }

  #box-1 {
    flex: 1;
  }

  #box-2 {
    flex: 9;
  }

</style>
