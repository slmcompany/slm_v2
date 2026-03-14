<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Cập nhật trọn gói"
    :width="1200"
    @ok="handleSubmit"
    :confirmLoading="loading"
  >
    <Form ref="formRef" :model="formState" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <Divider orientation="center">Thông tin cơ bản</Divider>
      <FormItem
        label="Tên trọn gói"
        name="ten"
        :rules="[{ required: true, message: 'Vui lòng nhập tên trọn gói' }]"
      >
        <Input v-model:value="formState.ten" placeholder="Nhập tên trọn gói" :maxlength="400" />
      </FormItem>

      <FormItem
        label="Loại hệ thống"
        name="loaiHeThong"
        :rules="[{ required: true, message: 'Vui lòng chọn loại hệ thống' }]"
      >
        <Select
          v-model:value="formState.loaiHeThong"
          placeholder="Chọn loại hệ thống"
          :options="[
            { label: 'On-Grid', value: 'On-Grid' },
            { label: 'Hy-Brid', value: 'Hy-Brid' },
          ]"
        ></Select>
      </FormItem>

      <FormItem
        label="Loại pha"
        name="loaiPha"
        :rules="[{ required: true, message: 'Vui lòng chọn loại pha' }]"
      >
        <Select
          v-model:value="formState.loaiPha"
          placeholder="Chọn loại pha"
          :options="[
            { label: '1 pha', value: '1 pha' },
            { label: '3 pha', value: '3 pha' },
          ]"
        ></Select>
      </FormItem>

      <FormItem label="Bán chạy" name="banChay">
        <RadioGroup v-model:value="formState.banChay" button-style="solid">
          <RadioButton :value="true">Có</RadioButton>
          <RadioButton :value="false">Không</RadioButton>
        </RadioGroup>
      </FormItem>

      <FormItem
        label="Trạng thái"
        name="trangThai"
        :rules="[{ required: true, message: 'Vui lòng chọn trạng thái' }]"
      >
        <RadioGroup v-model:value="formState.trangThai" button-style="solid">
          <RadioButton :value="1">Kích hoạt</RadioButton>
          <RadioButton :value="0">Vô hiệu</RadioButton>
        </RadioGroup>
      </FormItem>

      <Divider orientation="center">Vật tư trong gói</Divider>
      <FormItem :wrapper-col="{ span: 24 }">
        <!-- TẤM PIN -->
        <div class="vat-tu-group" v-if="tamPinList.length > 0">
          <Divider orientation="left" style="margin: 16px 0">Tấm pin</Divider>
          <div v-for="(item, index) in tamPinList" :key="index" class="vat-tu-item">
            <Card size="small">
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Vật tư">
                    <Input :value="item.vatTuTen" disabled />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian bảo hành"
                      :min="0"
                      style="width: 100%"
                      :disabled="!item.duocBaoHanh"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Trạng thái">
                    <RadioGroup v-model:value="item.trangThai" button-style="solid">
                      <RadioButton :value="1">Kích hoạt</RadioButton>
                      <RadioButton :value="0">Vô hiệu</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </div>
        </div>

        <!-- BIẾN TẦN -->
        <div class="vat-tu-group" v-if="bienTanList.length > 0">
          <Divider orientation="left" style="margin: 16px 0">Biến tần</Divider>
          <div v-for="(item, index) in bienTanList" :key="index" class="vat-tu-item">
            <Card size="small">
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Vật tư">
                    <Input :value="item.vatTuTen" disabled />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian bảo hành"
                      :min="0"
                      style="width: 100%"
                      :disabled="!item.duocBaoHanh"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Trạng thái">
                    <RadioGroup v-model:value="item.trangThai" button-style="solid">
                      <RadioButton :value="1">Kích hoạt</RadioButton>
                      <RadioButton :value="0">Vô hiệu</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </div>
        </div>

        <!-- PIN LƯU TRỮ -->
        <div class="vat-tu-group" v-if="pinLuuTruList.length > 0">
          <Divider orientation="left" style="margin: 16px 0">Pin lưu trữ</Divider>
          <div v-for="(item, index) in pinLuuTruList" :key="index" class="vat-tu-item">
            <Card size="small">
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Vật tư">
                    <Input :value="item.vatTuTen" disabled />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>   
                <Col :span="12">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian bảo hành"
                      :min="0"
                      style="width: 100%"
                      :disabled="!item.duocBaoHanh"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Trạng thái">
                    <RadioGroup v-model:value="item.trangThai" button-style="solid">
                      <RadioButton :value="1">Kích hoạt</RadioButton>
                      <RadioButton :value="0">Vô hiệu</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </div>
        </div>

        <!-- HỆ KHUNG NHÔM -->
        <div class="vat-tu-group" v-if="heKhungNhomList.length > 0">
          <Divider orientation="left" style="margin: 16px 0">Hệ khung nhôm</Divider>
          <div v-for="(item, index) in heKhungNhomList" :key="index" class="vat-tu-item">
            <Card size="small">
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Vật tư">
                    <Input :value="item.vatTuTen" disabled />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian bảo hành"
                      :min="0"
                      style="width: 100%"
                      :disabled="!item.duocBaoHanh"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Trạng thái">
                    <RadioGroup v-model:value="item.trangThai" button-style="solid">
                      <RadioButton :value="1">Kích hoạt</RadioButton>
                      <RadioButton :value="0">Vô hiệu</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </div>
        </div>

        <!-- HỆ DÂY ĐIỆN -->
        <div class="vat-tu-group" v-if="heDayDienList.length > 0">
          <Divider orientation="left" style="margin: 16px 0">Hệ dây điện</Divider>
          <div v-for="(item, index) in heDayDienList" :key="index" class="vat-tu-item">
            <Card size="small">
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Vật tư">
                    <Input :value="item.vatTuTen" disabled />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian bảo hành"
                      :min="0"
                      style="width: 100%"
                      :disabled="!item.duocBaoHanh"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Trạng thái">
                    <RadioGroup v-model:value="item.trangThai" button-style="solid">
                      <RadioButton :value="1">Kích hoạt</RadioButton>
                      <RadioButton :value="0">Vô hiệu</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </div>
        </div>

        <!-- TỦ ĐIỆN -->
        <div class="vat-tu-group" v-if="tuDienList.length > 0">
          <Divider orientation="left" style="margin: 16px 0">Tủ điện</Divider>
          <div v-for="(item, index) in tuDienList" :key="index" class="vat-tu-item">
            <Card size="small">
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Vật tư">
                    <Input :value="item.vatTuTen" disabled />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian bảo hành"
                      :min="0"
                      style="width: 100%"
                      :disabled="!item.duocBaoHanh"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Trạng thái">
                    <RadioGroup v-model:value="item.trangThai" button-style="solid">
                      <RadioButton :value="1">Kích hoạt</RadioButton>
                      <RadioButton :value="0">Vô hiệu</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </div>
        </div>

        <!-- HỆ TIẾP ĐỊA -->
        <div class="vat-tu-group" v-if="heTiepDiaList.length > 0">
          <Divider orientation="left" style="margin: 16px 0">Hệ tiếp địa</Divider>
          <div v-for="(item, index) in heTiepDiaList" :key="index" class="vat-tu-item">
            <Card size="small">
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Vật tư">
                    <Input :value="item.vatTuTen" disabled />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian bảo hành"
                      :min="0"
                      style="width: 100%"
                      :disabled="!item.duocBaoHanh"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Trạng thái">
                    <RadioGroup v-model:value="item.trangThai" button-style="solid">
                      <RadioButton :value="1">Kích hoạt</RadioButton>
                      <RadioButton :value="0">Vô hiệu</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </div>
        </div>

        <!-- TRỌN GÓI LẮP ĐẶT -->
        <div class="vat-tu-group" v-if="tronGoiLapDatList.length > 0">
          <Divider orientation="left" style="margin: 16px 0">Trọn gói lắp đặt</Divider>
          <div v-for="(item, index) in tronGoiLapDatList" :key="index" class="vat-tu-item">
            <Card size="small">
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Vật tư">
                    <Input :value="item.vatTuTen" disabled />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian bảo hành"
                      :min="0"
                      style="width: 100%"
                      :disabled="!item.duocBaoHanh"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Trạng thái">
                    <RadioGroup v-model:value="item.trangThai" button-style="solid">
                      <RadioButton :value="1">Kích hoạt</RadioButton>
                      <RadioButton :value="0">Vô hiệu</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </div>
        </div>
      </FormItem>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem
            label="Công suất hệ thống (kW)"
            name="congSuatHeThong"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.congSuatHeThong"
              placeholder="Công suất hệ thống"
              :min="0"
              :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
              :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
              style="width: 100%"
            ></InputNumber>
          </FormItem>
        </Col>
      </Row>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem
            label="Sản lượng TT (kW)"
            name="sanLuongToiThieu"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.sanLuongToiThieu"
              placeholder="Sản lượng tối thiểu"
              :min="0"
              style="width: 100%"
            ></InputNumber>
          </FormItem>
        </Col>
      </Row>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem
            label="Sản lượng TĐ (kW)"
            name="sanLuongToiDa"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.sanLuongToiDa"
              placeholder="Sản lượng tối đa"
              :min="0"
              style="width: 100%"
            ></InputNumber>
          </FormItem>
        </Col>
      </Row>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem
            label="Tổng giá (VND)"
            name="tongGia"
            :rules="[{ required: true, message: 'Vui lòng nhập tổng giá' }]"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.tongGia"
              placeholder="Nhập tổng giá"
              :min="0"
              :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
              :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
              style="width: 100%"
            ></InputNumber>
          </FormItem>
        </Col>
      </Row>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem
            label="GM Tổng (%)"
            name="gmTong"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.gmTong"
              placeholder="Nhập GM tổng"
              :min="0"
              :max="100"
              style="width: 100%"
            ></InputNumber>
          </FormItem>
        </Col>
      </Row>

      <Divider orientation="center">Hình ảnh</Divider>

      <!-- Upload ảnh mới -->
      <FormItem label="Hình ảnh" :wrapper-col="{ span: 18 }">
        <Upload
          v-model:file-list="fileList"
          list-type="picture-card"
          :before-upload="beforeUpload"
          @remove="handleRemoveFile"
          accept="image/*"
          :max-count="1"
        >
          <div v-if="fileList.length < 1">
            <PlusOutlined />
            <div style="margin-top: 8px">Tải ảnh lên</div>
          </div>
        </Upload>
        <div class="upload-hint">
          Tối đa 1 ảnh, định dạng: JPG, PNG, GIF. Xóa ảnh cũ và không chọn ảnh mới sẽ xóa ảnh.
        </div>
      </FormItem>
    </Form>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, reactive, onUnmounted } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { PlusOutlined, DeleteOutlined } from '@ant-design/icons-vue';
  import { updateTronGoi, type TronGoiDto } from './tronGoi';
  import {
    Button,
    Card,
    Col,
    Divider,
    Form,
    FormItem,
    Input,
    InputNumber,
    message,
    RadioButton,
    RadioGroup,
    Row,
    Select,
    Upload,
  } from 'ant-design-vue';
  import type { UploadProps } from 'ant-design-vue';

  defineOptions({ name: 'UpdateTronGoiModal' });
  const emit = defineEmits(['success', 'register']);

  const formRef = ref();
  const loading = ref(false);
  const currentRecord = ref<TronGoiDto | null>(null);
  const currentImageUrl = ref<string | null>(null);
  const isImageDeleted = ref(false); // Đánh dấu nếu user xóa ảnh cũ
  const currentBlobUrl = ref<string | null>(null);

  const formState = reactive({
    id: undefined as number | undefined,
    ten: '',
    loaiHeThong: undefined as string | undefined,
    loaiPha: '1 pha',
    congSuatHeThong: 0,
    sanLuongToiThieu: 0,
    sanLuongToiDa: 0,
    tongGia: 0,
    gmTong: 0,
    banChay: false,
    trangThai: 1,
  });

  type VatTuUpdateItem = {
    id: number;
    soLuong: number; // ← THÊM DÒNG NÀY
    vatTuTen: string;
    nhomVatTuMa: string;
    duocBaoHanh: boolean;
    thoiGianBaoHanh: number; // ← THÊM DÒNG NÀY
    duocXem: boolean;
    trangThai: number;
  };

  const tamPinList = ref<VatTuUpdateItem[]>([]);
  const bienTanList = ref<VatTuUpdateItem[]>([]);
  const pinLuuTruList = ref<VatTuUpdateItem[]>([]);
  const heKhungNhomList = ref<VatTuUpdateItem[]>([]);
  const heDayDienList = ref<VatTuUpdateItem[]>([]);
  const tuDienList = ref<VatTuUpdateItem[]>([]);
  const heTiepDiaList = ref<VatTuUpdateItem[]>([]);
  const tronGoiLapDatList = ref<VatTuUpdateItem[]>([]);
  const fileList = ref<any[]>([]);

  const [registerModal, { closeModal }] = useModalInner(async (data) => {
    resetForm();
    if (data?.record) {
      currentRecord.value = data.record;
      loadRecordData(data.record);
    }
  });

  function resetForm() {
    if (currentBlobUrl.value) {
      URL.revokeObjectURL(currentBlobUrl.value);
      currentBlobUrl.value = null;
    }
    Object.assign(formState, {
      id: undefined,
      ten: '',
      loaiHeThong: undefined,
      loaiPha: '1 pha',
      congSuatHeThong: 0,
      sanLuongToiThieu: 0,
      sanLuongToiDa: 0,
      tongGia: 0,
      gmTong: 0,
      banChay: false,
      trangThai: 1,
    });
    tamPinList.value = [];
    bienTanList.value = [];
    pinLuuTruList.value = [];
    heKhungNhomList.value = [];
    heDayDienList.value = [];
    tuDienList.value = [];
    heTiepDiaList.value = [];
    tronGoiLapDatList.value = [];
    fileList.value = [];
    currentImageUrl.value = null;
    isImageDeleted.value = false;
    currentRecord.value = null;
  }

  function loadRecordData(record: TronGoiDto) {
    // THÊM ĐOẠN DEBUG NÀY:
    console.log('=== DEBUG loadRecordData ===');
    console.log('Full record:', record);
    console.log('tepTin object:', record.tepTin);
    console.log('tepTin.duongDan:', record.tepTin?.duongDan);
    formState.id = record.id;
    formState.ten = record.ten || '';
    formState.loaiHeThong = record.loaiHeThong;
    formState.loaiPha = record.loaiPha || '1 pha';
    formState.congSuatHeThong = record.congSuatHeThong || 0;
    formState.sanLuongToiThieu = record.sanLuongToiThieu || 0;
    formState.sanLuongToiDa = record.sanLuongToiDa || 0;
    formState.tongGia = record.tongGia || 0;
    formState.gmTong = record.gmTong || 0;
    formState.banChay = record.banChay || false;
    formState.trangThai = record.trangThai ?? 1;

    // Load ảnh hiện tại từ tepTin.duongDan
    // Load ảnh cũ vào fileList
    // TÌM VÀ THAY THẾ đoạn load ảnh:
    // Load ảnh cũ vào fileList
    if (record.tepTin?.duongDan) {
      const imageUrl = record.tepTin.duongDan;

      // Revoke blob cũ nếu có
      if (currentBlobUrl.value) {
        URL.revokeObjectURL(currentBlobUrl.value);
        currentBlobUrl.value = null;
      }

      currentImageUrl.value = imageUrl;
      isImageDeleted.value = false;

      // Fetch ảnh và tạo blob để Upload component hiển thị preview
      fetch(imageUrl)
        .then((res) => res.blob())
        .then((blob) => {
          const blobUrl = URL.createObjectURL(blob);
          currentBlobUrl.value = blobUrl; // Lưu lại để revoke sau
          const fileName = record.tepTin.tenLuuTru || 'image.jpg';

          fileList.value = [
            {
              uid: '-1',
              name: fileName,
              status: 'done',
              url: imageUrl,
              thumbUrl: blobUrl,
              type: blob.type,
            },
          ];
        })
        .catch((err) => {
          console.error('Failed to load image:', err);
          fileList.value = [
            {
              uid: '-1',
              name: record.tepTin.tenTepGoc || 'image.jpg',
              status: 'done',
              url: imageUrl,
            },
          ];
        });
    }

    // Load và phân nhóm vật tư
    if (record.vatTuTronGois && record.vatTuTronGois.length > 0) {
      record.vatTuTronGois.forEach((item) => {
        // Tìm dòng này trong hàm loadRecordData:
        const vatTuItem: VatTuUpdateItem = {
          id: item.id,
          soLuong: item.soLuong || 0, // ← THÊM DÒNG NÀY
          vatTuTen: item.vatTu?.ten || 'N/A',
          nhomVatTuMa: item.vatTu?.nhomVatTu?.ma || '',
          duocBaoHanh: item.duocBaoHanh ?? true,
          thoiGianBaoHanh: item.thoiGianBaoHanh || 0, // ← THÊM DÒNG NÀY
          duocXem: item.duocXem ?? true,
          trangThai: item.trangThai ?? 1,
        };

        // Phân nhóm theo mã nhóm vật tư
        const ma = vatTuItem.nhomVatTuMa.toUpperCase();
        if (ma.includes('TAM_PIN')) {
          tamPinList.value.push(vatTuItem);
        } else if (ma.includes('BIEN_TAN')) {
          bienTanList.value.push(vatTuItem);
        } else if (ma.includes('PIN_LUU_TRU')) {
          pinLuuTruList.value.push(vatTuItem);
        } else if (ma.includes('HE_KHUNG_NHOM')) {
          heKhungNhomList.value.push(vatTuItem);
        } else if (ma.includes('HE_DAY_DIEN')) {
          heDayDienList.value.push(vatTuItem);
        } else if (ma.includes('TU_DIEN')) {
          tuDienList.value.push(vatTuItem);
        } else if (ma.includes('HE_TIEP_DIA')) {
          heTiepDiaList.value.push(vatTuItem);
        } else if (ma.includes('TRON_GOI_LAP_DAT')) {
          tronGoiLapDatList.value.push(vatTuItem);
        }
      });
    }
  }

  const beforeUpload: UploadProps['beforeUpload'] = (file) => {
    const isImage = file.type && file.type.startsWith('image/');
    if (!isImage) {
      message.error('Chỉ chấp nhận file ảnh!');
      return false;
    }
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      message.error('Kích thước ảnh phải nhỏ hơn 5MB!');
      return false;
    }
    return false;
  };

  function handleRemoveFile() {
    fileList.value = [];
    isImageDeleted.value = true; // Đánh dấu là đã xóa ảnh
  }

  async function handleSubmit() {
    try {
      await formRef.value?.validate();
      loading.value = true;

      // Tập hợp tất cả vật tư từ các nhóm
      const allVatTu = [
        ...tamPinList.value,
        ...bienTanList.value,
        ...pinLuuTruList.value,
        ...heKhungNhomList.value,
        ...heDayDienList.value,
        ...tuDienList.value,
        ...heTiepDiaList.value,
        ...tronGoiLapDatList.value,
      ];

      const submitData = {
        id: formState.id,
        ten: formState.ten,
        loaiHeThong: formState.loaiHeThong,
        loaiPha: formState.loaiPha,
        congSuatHeThong: formState.congSuatHeThong,
        sanLuongToiThieu: formState.sanLuongToiThieu,
        sanLuongToiDa: formState.sanLuongToiDa,
        tongGia: formState.tongGia,
        gmTong: formState.gmTong,
        banChay: formState.banChay,
        trangThai: formState.trangThai,
        vatTuTronGois: allVatTu.map((v) => ({
          id: v.id,
          soLuong: v.soLuong,
          duocBaoHanh: v.duocBaoHanh,
          thoiGianBaoHanh: v.thoiGianBaoHanh,
          duocXem: v.duocXem,
          trangThai: v.trangThai,
        })),
      };

      // Xác định file gửi đi theo logic mới
      let fileToSend: File | null = null;

      if (fileList.value.length > 0) {
        // Trường hợp 1: User đã upload ảnh mới
        fileToSend = fileList.value[0].originFileObj || null;
      } else if (isImageDeleted.value) {
        // Trường hợp 2: User xóa ảnh cũ và không upload ảnh mới
        // Gửi null để backend xóa ảnh
        fileToSend = null;
      } else {
        // Trường hợp 3: Giữ nguyên ảnh cũ
        // Không gửi file, backend sẽ tự giữ ảnh cũ
        fileToSend = undefined as any; // Không append vào formData
      }

      const result = await updateTronGoi(
        formState.id!,
        submitData,
        fileToSend === undefined ? null : fileToSend,
      );

      if (result.status === 200 || result.status === 201) {
        // Revoke blob URL ngay sau khi update thành công
        if (currentBlobUrl.value) {
          URL.revokeObjectURL(currentBlobUrl.value);
          currentBlobUrl.value = null;
        }

        message.success('Cập nhật thành công');
        closeModal();
        emit('success', result.data);
      } else {
        message.error(result.message || 'Có lỗi xảy ra');
      }
    } catch (error: any) {
      console.error('Submit error:', error);
      if (error.errorFields) {
        message.error('Vui lòng kiểm tra lại thông tin');
      } else {
        message.error(error?.message || 'Có lỗi xảy ra khi cập nhật dữ liệu');
      }
    } finally {
      loading.value = false;
    }
  }

  onUnmounted(() => {
    if (currentBlobUrl.value) {
      URL.revokeObjectURL(currentBlobUrl.value);
    }
  });
</script>

<style lang="less" scoped>
  .vat-tu-item {
    margin-bottom: 16px;

    &:last-child {
      margin-bottom: 0;
    }
  }

  .vat-tu-group {
    margin-bottom: 24px;
  }

  :deep(.ant-card-body) {
    padding: 12px;
  }

  :deep(.ant-form-item) {
    margin-bottom: 16px;
  }

  .upload-hint {
    margin-top: 8px;
    color: #999;
    font-size: 12px;
  }

  :deep(.ant-select) {
    width: 100%;
  }
</style>
