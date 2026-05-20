<script setup>
defineProps({ message: { type: String, default: '' } })
</script>
<template>
  <div v-if="message" class="alert">{{ message }}</div>
</template>
