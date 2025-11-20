<template>
  <Card size="small">
    <template #extra>
      <Button v-if="showDelete" type="link" danger size="small" @click="$emit('remove')">
        <DeleteOutlined />
      </Button>
    </template>
    <Row :gutter="16">
      <Col :span="12">
        <FormItem label="Vật tư" :required="true">
          <Select
            :value="modelValue.vatTuId"
            @change="value => handleVatTuChange(value)"
            placeholder="Chọn vật tư"
            show-search
            :filter-option="filterOption"
            :options="options"
          />
        </FormItem>
      </Col>
      <Col :span="12">
        <FormItem label="Số lượng" :required="true">
          <InputNumber
            :value="modelValue.soLuong"
            @update:value="updateField('soLuong', $event)"
            placeholder="Nhập số lượng"
            :min="0"
            style="width: 100%"
          />
        </FormItem>
      </Col>
      <Col :span="12">
        <FormItem label="GM (%)" :required="true">
          <InputNumber
            :value="modelValue.gm"
            @update:value="updateField('gm', $event)"
            placeholder="Nhập GM"
            :min="0"
            :max="100"
            style="width: 100%"
          />
        </FormItem>
      </Col>
      <Col :span="12">
        <FormItem :label="`Giá bán ${region}`" :required="true">
          <InputNumber
            :value="modelValue.giaBan"
            @update:value="updateField('giaBan', $event)"
            :min="0"
            :formatter="formatNumber"
            :parser="parseNumber"
            placeholder="Nhập giá bán"
            style="width: 100%"
          />
        </FormItem>
      </Col>
      <Col :span="12">
        <FormItem label="Thời gian BH (tháng)">
          <InputNumber
            :value="modelValue.thoiGianBaoHanh"
            @update:value="updateField('thoiGianBaoHanh', $event)"
            placeholder="Nhập thời gian"
            :min="0"
            style="width: 100%"
          />
        </FormItem>
      </Col>
      <Col :span="12">
        <FormItem label="Được bảo hành">
          <RadioGroup 
            :value="modelValue.duocBaoHanh"
            @update:value="updateField('duocBaoHanh', $event)"
            button-style="solid"
          >
            <RadioButton :value="true">Có</RadioButton>
            <RadioButton :value="false">Không</RadioButton>
          </RadioGroup>
        </FormItem>
      </Col>
      <Col :span="24">
        <FormItem label="Mô tả">
          <Textarea
            :value="modelValue.moTa"
            @update:value="updateField('moTa', $event)"
            placeholder="Nhập mô tả"
            :rows="2"
          />
        </FormItem>
      </Col>
    </Row>
  </Card>
</template>

<script lang="ts" setup>
  import { computed } from 'vue';
  import { DeleteOutlined } from '@ant-design/icons-vue';
  import {
    Button, Card, Col, FormItem, InputNumber, 
    RadioButton, RadioGroup, Row, Select, Textarea
  } from 'ant-design-vue';
  import type { VatTuHopDongCreatingDto, VatTuDto, GiaInfo } from './hopDong';

  interface Props {
    modelValue: VatTuHopDongCreatingDto;
    options: Array<{ label: string; value: number }>;
    vatTuData: VatTuDto[];
    showDelete?: boolean;
    region?: string;
    coSoMa?: string;
  }

  const props = withDefaults(defineProps<Props>(), {
    showDelete: true,
    region: 'Miền Bắc',
    coSoMa: 'HN'
  });

  const emit = defineEmits<{
    (e: 'update:modelValue', value: VatTuHopDongCreatingDto): void;
    (e: 'remove'): void;
  }>();

  const filterOption = (input: string, option: any) => {
    return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  const formatNumber = (value: any) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  const parseNumber = (value: any) => value.replace(/\$\s?|(,*)/g, '');

  const updateField = (field: keyof VatTuHopDongCreatingDto, value: any) => {
    emit('update:modelValue', { ...props.modelValue, [field]: value });
  };

  const handleVatTuChange = (vatTuId: number) => {
    const vatTu = props.vatTuData.find(v => v.id === vatTuId);
    if (!vatTu) return;

    const latestGia = vatTu.thongTinGias[vatTu.thongTinGias.length - 1];
    const giaInfo = latestGia?.dsGia.find((g: GiaInfo) => g.maCoSo === props.coSoMa);
    
    emit('update:modelValue', {
      ...props.modelValue,
      vatTuId,
      giaBan: giaInfo?.giaBan || 0,
      gm: vatTu.nhomVatTu.gm,
      moTa: vatTu.moTaBaoGia || '',
    });
  };
</script>

<style lang="less" scoped>
  :deep(.ant-card-body) {
    padding: 12px;
  }
</style>