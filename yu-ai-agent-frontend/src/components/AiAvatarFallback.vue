<template>
  <div class="ai-avatar-fallback" :class="type">
    <span v-if="type === 'love'">🧭</span>
    <span v-else>🗺️</span>
  </div>
</template>

<script setup>
defineProps({
  type: {
    type: String,
    default: 'default'
  }
})
</script>

<style scoped>
.ai-avatar-fallback {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  border-radius: 50%;
}

.love {
  background: linear-gradient(45deg, #0f766e, #14b8a6);
}

.default, .super {
  background: linear-gradient(45deg, #f59e0b, #f97316);
}
</style> 
