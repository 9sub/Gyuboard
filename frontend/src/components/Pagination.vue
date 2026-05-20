<script setup>
const props = defineProps({ pageDto: { type: Object, default: null } })
const emit = defineEmits(['change'])
function move(page) { emit('change', page) }
</script>
<template>
  <div v-if="pageDto" class="pagination">
    <button v-if="pageDto.prev" class="page-link" @click="move(pageDto.startPage - 1)">이전</button>
    <button
      v-for="num in pageDto.endPage - pageDto.startPage + 1"
      :key="pageDto.startPage + num - 1"
      class="page-link"
      :class="{ active: pageDto.page === pageDto.startPage + num - 1 }"
      @click="move(pageDto.startPage + num - 1)"
    >
      {{ pageDto.startPage + num - 1 }}
    </button>
    <button v-if="pageDto.next" class="page-link" @click="move(pageDto.endPage + 1)">다음</button>
  </div>
</template>
