<template>
  <div class="vat-tu-group">
    <Divider orientation="left" style="margin: 16px 0">{{ title }}</Divider>

    <Button
      v-if="showAddButton"
      type="dashed"
      block
      @click="$emit('add')"
      style="margin-bottom: 16px"
      size="small"
    >
      <template #icon><PlusOutlined /></template>
      Thêm {{ title }}
    </Button>

    <div v-for="(item, index) in items" :key="index" class="vat-tu-item">
      <VatTuFormItem
        :model-value="item"
        :options="options"
        :vat-tu-data="vatTuData"
        :co-so-ma="coSoMa"
        :region="region"
        @update:model-value="(value) => $emit('update', index, value)"
        @remove="$emit('remove', index)"
      />
    </div>
  </div>
</template>

<script lang="ts" setup>
  import { PlusOutlined } from '@ant-design/icons-vue';
  import { Button, Divider } from 'ant-design-vue';
  import VatTuFormItem from './VatTuFormItem.vue';
  import type { VatTuHopDongCreatingDto, VatTuDto } from './hopDong.ts';

  interface Props {
    title: string;
    items: VatTuHopDongCreatingDto[];
    options: Array<{ label: string; value: number }>;
    vatTuData: VatTuDto[];
    coSoMa?: string;
    region?: string; // Thêm prop này
    showAddButton?: boolean;
  }

  const props = withDefaults(defineProps<Props>(), {
    coSoMa: 'HN',
    region: 'Miền Bắc', // Default value
    showAddButton: true,
  });

  defineEmits<{
    (e: 'add'): void;
    (e: 'remove', index: number): void;
    (e: 'update', index: number, value: VatTuHopDongCreatingDto): void;
  }>();
</script>

<style lang="less" scoped>
  .vat-tu-group {
    margin-bottom: 24px;
  }

  .vat-tu-item {
    margin-bottom: 16px;

    &:last-child {
      margin-bottom: 0;
    }
  }

  :deep(.ant-card-head) {
    min-height: 40px;
    padding: 0 12px;

    .ant-card-head-title {
      padding: 8px 0;
      font-size: 14px;
    }
  }

  :deep(.ant-card-body) {
    padding: 12px;
  }

  :deep(.ant-form-item) {
    margin-bottom: 16px;
  }
</style>
