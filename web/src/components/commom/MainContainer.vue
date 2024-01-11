<template>
  <ToolBar />
  <v-container>
    <v-responsive>
      <router-view></router-view>
    </v-responsive>
  </v-container>
</template>

<script>
import ToolBar from "./ToolBar.vue";

export default {};
</script>

<style lang="scss" scoped></style>
