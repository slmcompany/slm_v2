<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Cập nhật trọn gói"
    :width="1200"
    @ok="handleSubmit"
    :confirmLoading="loading"
  >
  <Spin :spinning="loadingInit" tip="Đang tải dữ liệu...">
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
        label="Nhóm trọn gói"
        name="nhomTronGoiId"
        :rules="[{ required: true, message: 'Vui lòng chọn nhóm trọn gói' }]"
      >
        <Select
          v-model:value="formState.nhomTronGoiId"
          placeholder="Chọn nhóm trọn gói"
          show-search
          :filter-option="filterOption"
          :options="nhomTronGoiOptions"
          @change="(value) => handleNhomTronGoiChange(value)"
        ></Select>
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
          @change="handleSoPhaChange()"
        ></Select>
      </FormItem>

      <FormItem label="Mô tả" name="moTa">
        <Textarea v-model:value="formState.moTa" placeholder="Nhập mô tả" :rows="3"></Textarea>
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
        <div class="vat-tu-group">
          <Divider orientation="left" style="margin: 16px 0">Tấm pin</Divider>
          <div v-for="(item, itemIndex) in tamPinList" :key="itemIndex" class="vat-tu-item">
            <Card size="small">
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Tấm pin" :required="true">
                    <Select
                      v-model:value="item.vatTuId"
                      placeholder="Chọn vật tư"
                      show-search
                      :filter-option="filterOption"
                      :options="tamPinOptions"
                      @change="(value) => handleTamPinChange(value, itemIndex)"
                    ></Select>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng" :required="true">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                      @change="
                        (value) =>
                          handleChangeSoLuongTamPin(tamPinList[0].vatTuId, itemIndex, value)
                      "
                    />
                  </FormItem>
                </Col>
                <Col :span="24">
                  <FormItem label="GM (%)" :required="true">
                    <InputNumber
                      v-model:value="item.gm"
                      placeholder="Nhập GM"
                      :min="0"
                      :max="100"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian"
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
                <Col :span="24">
                  <FormItem label="Mô tả">
                    <Textarea
                      v-model:value="item.moTa"
                      placeholder="Nhập mô tả"
                      :rows="2"
                    ></Textarea>
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
              </Row>
            </Card>
          </div>
        </div>

        <!-- BIẾN TẦN -->
        <div class="vat-tu-group">
          <Divider orientation="left" style="margin: 16px 0">Biến tần</Divider>
          <Button
            type="dashed"
            block
            @click="handleAddBienTan"
            style="margin-bottom: 16px"
            size="small"
          >
            <template #icon><PlusOutlined /></template>
            Thêm Biến tần
          </Button>
          <div v-for="(item, itemIndex) in bienTanList" :key="itemIndex" class="vat-tu-item">
            <Card size="small">
              <template #extra>
                <Button type="link" danger size="small" @click="handleRemoveBienTan(itemIndex)"
                  ><DeleteOutlined
                /></Button>
              </template>
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Biến tần" :required="true">
                    <Select
                      v-model:value="item.vatTuId"
                      placeholder="Chọn vật tư"
                      show-search
                      :filter-option="filterOption"
                      :options="bienTanOptions"
                      @change="(value) => handleBienTanChange(value, itemIndex)"
                    ></Select>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng" :required="true">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="24">
                  <FormItem label="GM (%)" :required="true">
                    <InputNumber
                      v-model:value="item.gm"
                      placeholder="Nhập GM"
                      :min="0"
                      :max="100"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian"
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
                <Col :span="24">
                  <FormItem label="Mô tả">
                    <Textarea
                      v-model:value="item.moTa"
                      placeholder="Nhập mô tả"
                      :rows="2"
                    ></Textarea>
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
              </Row>
            </Card>
          </div>
        </div>

        <!-- PIN LƯU TRỮ -->
        <div class="vat-tu-group">
          <Divider orientation="left" style="margin: 16px 0">Pin lưu trữ</Divider>
          <Button
            type="dashed"
            block
            @click="handleAddPinLuuTru"
            style="margin-bottom: 16px"
            size="small"
          >
            <template #icon><PlusOutlined /></template>
            Thêm Pin lưu trữ
          </Button>
          <div v-for="(item, itemIndex) in pinLuuTruList" :key="itemIndex" class="vat-tu-item">
            <Card size="small">
              <template #extra>
                <Button type="link" danger size="small" @click="handleRemovePinLuuTru(itemIndex)"
                  ><DeleteOutlined
                /></Button>
              </template>
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Pin lưu trữ" :required="true">
                    <Select
                      v-model:value="item.vatTuId"
                      placeholder="Chọn vật tư"
                      show-search
                      :filter-option="filterOption"
                      :options="pinLuuTruOptions"
                      @change="(value) => handlePinLuuTruChange(value, itemIndex)"
                    ></Select>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng" :required="true">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="24">
                  <FormItem label="GM (%)" :required="true">
                    <InputNumber
                      v-model:value="item.gm"
                      placeholder="Nhập GM"
                      :min="0"
                      :max="100"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian"
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
                <Col :span="24">
                  <FormItem label="Mô tả">
                    <Textarea
                      v-model:value="item.moTa"
                      placeholder="Nhập mô tả"
                      :rows="2"
                    ></Textarea>
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
              </Row>
            </Card>
          </div>
        </div>

        <!-- HỆ KHUNG NHÔM -->
        <div class="vat-tu-group">
          <Divider orientation="left" style="margin: 16px 0">Hệ khung nhôm</Divider>
          <CheckboxGroup label="Loại mái" v-model:value="loaiMais" @change="handleLoaiMaiChange()">
            <Checkbox value="mái tôn">mái tôn</Checkbox>
            <Checkbox value="mái ngói">mái ngói</Checkbox>
            <Checkbox value="khung sắt">khung sắt</Checkbox>
          </CheckboxGroup>
          <Button
            type="dashed"
            block
            @click="handleAddHeKhungNhom"
            style="margin-bottom: 16px"
            size="small"
          >
            <template #icon><PlusOutlined /></template>
            Thêm Hệ khung nhôm
          </Button>
          <div v-for="(item, itemIndex) in heKhungNhomList" :key="itemIndex" class="vat-tu-item">
            <Card size="small">
              <template #extra>
                <Button type="link" danger size="small" @click="handleRemoveHeKhungNhom(itemIndex)"
                  ><DeleteOutlined
                /></Button>
              </template>
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Hệ khung nhôm" :required="true">
                    <Select
                      v-model:value="item.vatTuId"
                      placeholder="Chọn vật tư"
                      show-search
                      :filter-option="filterOption"
                      :options="heKhungNhomOptions"
                      @change="(value) => handleHeKhungNhomChange(value, itemIndex)"
                    ></Select>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng" :required="true">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="24">
                  <FormItem label="GM (%)" :required="true">
                    <InputNumber
                      v-model:value="item.gm"
                      placeholder="Nhập GM"
                      :min="0"
                      :max="100"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian"
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
                <Col :span="24">
                  <FormItem label="Mô tả">
                    <Textarea
                      v-model:value="item.moTa"
                      placeholder="Nhập mô tả"
                      :rows="2"
                    ></Textarea>
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
              </Row>
            </Card>
          </div>
        </div>

        <!-- HỆ DÂY ĐIỆN -->
        <div class="vat-tu-group">
          <Divider orientation="left" style="margin: 16px 0">Hệ dây điện</Divider>
          <Button
            type="dashed"
            block
            @click="handleAddHeDayDien"
            style="margin-bottom: 16px"
            size="small"
          >
            <template #icon><PlusOutlined /></template>
            Thêm Hệ dây điện
          </Button>
          <div v-for="(item, itemIndex) in heDayDienList" :key="itemIndex" class="vat-tu-item">
            <Card size="small">
              <template #extra>
                <Button type="link" danger size="small" @click="handleRemoveHeDayDien(itemIndex)"
                  ><DeleteOutlined
                /></Button>
              </template>
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Hệ dây điện" :required="true">
                    <Select
                      v-model:value="item.vatTuId"
                      placeholder="Chọn vật tư"
                      show-search
                      :filter-option="filterOption"
                      :options="heDayDienOptions"
                      @change="(value) => handleHeDayDienChange(value, itemIndex)"
                    ></Select>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng" :required="true">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="24">
                  <FormItem label="GM (%)" :required="true">
                    <InputNumber
                      v-model:value="item.gm"
                      placeholder="Nhập GM"
                      :min="0"
                      :max="100"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian"
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
                <Col :span="24">
                  <FormItem label="Mô tả">
                    <Textarea
                      v-model:value="item.moTa"
                      placeholder="Nhập mô tả"
                      :rows="2"
                    ></Textarea>
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
              </Row>
            </Card>
          </div>
        </div>

        <!-- TỦ ĐIỆN -->
        <div class="vat-tu-group">
          <Divider orientation="left" style="margin: 16px 0">Tủ điện</Divider>
          <Button
            type="dashed"
            block
            @click="handleAddTuDien"
            style="margin-bottom: 16px"
            size="small"
          >
            <template #icon><PlusOutlined /></template>
            Thêm Tủ điện
          </Button>
          <div v-for="(item, itemIndex) in tuDienList" :key="itemIndex" class="vat-tu-item">
            <Card size="small">
              <template #extra>
                <Button type="link" danger size="small" @click="handleRemoveTuDien(itemIndex)"
                  ><DeleteOutlined
                /></Button>
              </template>
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Tủ điện" :required="true">
                    <Select
                      v-model:value="item.vatTuId"
                      placeholder="Chọn vật tư"
                      show-search
                      :filter-option="filterOption"
                      :options="tuDienOptions"
                      @change="(value) => handleTuDienChange(value, itemIndex)"
                    ></Select>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng" :required="true">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="24">
                  <FormItem label="GM (%)" :required="true">
                    <InputNumber
                      v-model:value="item.gm"
                      placeholder="Nhập GM"
                      :min="0"
                      :max="100"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian"
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
                <Col :span="24">
                  <FormItem label="Mô tả">
                    <Textarea
                      v-model:value="item.moTa"
                      placeholder="Nhập mô tả"
                      :rows="2"
                    ></Textarea>
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
              </Row>
            </Card>
          </div>
        </div>

        <!-- HỆ TIẾP ĐỊA -->
        <div class="vat-tu-group">
          <Divider orientation="left" style="margin: 16px 0">Hệ tiếp địa</Divider>
          <Button
            type="dashed"
            block
            @click="handleAddHeTiepDia"
            style="margin-bottom: 16px"
            size="small"
          >
            <template #icon><PlusOutlined /></template>
            Thêm Hệ tiếp địa
          </Button>
          <div v-for="(item, itemIndex) in heTiepDiaList" :key="itemIndex" class="vat-tu-item">
            <Card size="small">
              <template #extra>
                <Button type="link" danger size="small" @click="handleRemoveHeTiepDia(itemIndex)"
                  ><DeleteOutlined
                /></Button>
              </template>
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Hệ tiếp địa" :required="true">
                    <Select
                      v-model:value="item.vatTuId"
                      placeholder="Chọn vật tư"
                      show-search
                      :filter-option="filterOption"
                      :options="heTiepDiaOptions"
                      @change="(value) => handleHeTiepDiaChange(value, itemIndex)"
                    ></Select>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng" :required="true">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="24">
                  <FormItem label="GM (%)" :required="true">
                    <InputNumber
                      v-model:value="item.gm"
                      placeholder="Nhập GM"
                      :min="0"
                      :max="100"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian"
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
                <Col :span="24">
                  <FormItem label="Mô tả">
                    <Textarea
                      v-model:value="item.moTa"
                      placeholder="Nhập mô tả"
                      :rows="2"
                    ></Textarea>
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
              </Row>
            </Card>
          </div>
        </div>

        <!-- TRỌN GÓI LẮP ĐẶT -->
        <div class="vat-tu-group">
          <Divider orientation="left" style="margin: 16px 0">Trọn gói lắp đặt</Divider>
          <Button
            type="dashed"
            block
            @click="handleAddTronGoiLapDat"
            style="margin-bottom: 16px"
            size="small"
          >
            <template #icon><PlusOutlined /></template>
            Thêm Trọn gói lắp đặt
          </Button>
          <div v-for="(item, itemIndex) in tronGoiLapDatList" :key="itemIndex" class="vat-tu-item">
            <Card size="small">
              <template #extra>
                <Button
                  type="link"
                  danger
                  size="small"
                  @click="handleRemoveTronGoiLapDat(itemIndex)"
                  ><DeleteOutlined
                /></Button>
              </template>
              <Row :gutter="16">
                <Col :span="12">
                  <FormItem label="Trọn gói lắp đặt" :required="true">
                    <Select
                      v-model:value="item.vatTuId"
                      placeholder="Chọn vật tư"
                      show-search
                      :filter-option="filterOption"
                      :options="tronGoiLapDatOptions"
                      @change="(value) => handleTronGoiLapDatChange(value, itemIndex)"
                    ></Select>
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Số lượng" :required="true">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="24">
                  <FormItem label="GM (%)" :required="true">
                    <InputNumber
                      v-model:value="item.gm"
                      placeholder="Nhập GM"
                      :min="0"
                      :max="100"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá nhập miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhapMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Bắc" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienBac"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Giá bán miền Nam" :required="true">
                    <InputNumber
                      v-model:value="item.giaBanMienNam"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="12">
                  <FormItem label="Thời gian BH (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian"
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
                <Col :span="24">
                  <FormItem label="Mô tả">
                    <Textarea
                      v-model:value="item.moTa"
                      placeholder="Nhập mô tả"
                      :rows="2"
                    ></Textarea>
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
            label="Tổng giá Miền Bắc (VND)"
            name="tongGiaMienBac"
            :rules="[{ required: true, message: 'Vui lòng nhập tổng giá Miền Bắc' }]"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.tongGiaMienBac"
              placeholder="Nhập tổng giá Miền Bắc"
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
            label="Tổng giá Miền Nam (VND)"
            name="tongGiaMienNam"
            :rules="[{ required: true, message: 'Vui lòng nhập tổng giá Miền Nam' }]"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.tongGiaMienNam"
              placeholder="Nhập tổng giá Miền Nam"
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

      <Row :gutter="16">
        <Col :span="12">
          <FormItem label="Tính tổng giá" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <Button type="primary" @click="tinhTongGia()">Tính tổng giá</Button>
          </FormItem>
        </Col>
      </Row>

      <Divider orientation="center">Hình ảnh</Divider>
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
  </Spin>
    
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, reactive, onUnmounted } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { PlusOutlined, DeleteOutlined } from '@ant-design/icons-vue';
  import {
    updateTronGoi,
    getAllNhomTronGoi,
    filterVatTu,
    type TronGoiDto,
    type NhomTronGoiDto,
    type VatTuDto,
    type VatTuTronGoiCreatingDto,
    type GiaInfo,
    type ResponseData,
    type PageResponse,
  } from './tronGoi';
  import {
    Button,
    Card,
    Checkbox,
    CheckboxGroup,
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
    Textarea,
    Upload,
    Spin,
  } from 'ant-design-vue';
  const loadingInit = ref(false);

  import type { UploadProps } from 'ant-design-vue';
  import { ceil } from 'lodash-es';

  defineOptions({ name: 'UpdateTronGoiModal' });
  const emit = defineEmits(['success', 'register']);

  const formRef = ref();
  const loading = ref(false);
  const currentRecord = ref<TronGoiDto | null>(null);
  const currentBlobUrl = ref<string | null>(null);
  const isImageDeleted = ref(false);

  // --- Nhóm trọn gói ---
  const nhomTronGoiList = ref<NhomTronGoiDto[]>([]);
  const nhomTronGoiOptions = ref<Array<{ label: string; value: number }>>([]);
  const thuongHieuIdsApplyByNhomTronGoi = ref<number[] | null>(null);
  const loaiMais = ref<string[]>(['mái tôn']);

  // --- Dữ liệu vật tư ---
  type VatTuItem = VatTuTronGoiCreatingDto;

  const tamPinData = ref<VatTuDto[]>([]);
  const bienTanData = ref<VatTuDto[]>([]);
  const pinLuuTruData = ref<VatTuDto[]>([]);
  const heKhungNhomData = ref<VatTuDto[]>([]);
  const heDayDienData = ref<VatTuDto[]>([]);
  const tuDienData = ref<VatTuDto[]>([]);
  const heTiepDiaData = ref<VatTuDto[]>([]);
  const tronGoiLapDatData = ref<VatTuDto[]>([]);

  const tamPinList = ref<VatTuItem[]>([]);
  const bienTanList = ref<VatTuItem[]>([]);
  const pinLuuTruList = ref<VatTuItem[]>([]);
  const heKhungNhomList = ref<VatTuItem[]>([]);
  const heDayDienList = ref<VatTuItem[]>([]);
  const tuDienList = ref<VatTuItem[]>([]);
  const heTiepDiaList = ref<VatTuItem[]>([]);
  const tronGoiLapDatList = ref<VatTuItem[]>([]);

  const tamPinOptions = ref<Array<{ label: string; value: number }>>([]);
  const bienTanOptions = ref<Array<{ label: string; value: number }>>([]);
  const pinLuuTruOptions = ref<Array<{ label: string; value: number }>>([]);
  const heKhungNhomOptions = ref<Array<{ label: string; value: number }>>([]);
  const heDayDienOptions = ref<Array<{ label: string; value: number }>>([]);
  const tuDienOptions = ref<Array<{ label: string; value: number }>>([]);
  const heTiepDiaOptions = ref<Array<{ label: string; value: number }>>([]);
  const tronGoiLapDatOptions = ref<Array<{ label: string; value: number }>>([]);

  const fileList = ref<any[]>([]);

  const formState = reactive({
    id: undefined as number | undefined,
    ten: '',
    nhomTronGoiId: undefined as number | undefined,
    loaiHeThong: undefined as string | undefined,
    loaiPha: '1 pha',
    moTa: '',
    congSuatHeThong: 0,
    tongGiaMienBac: 0,
    tongGiaMienNam: 0,
    gmTong: 0,
    banChay: false,
    trangThai: 1,
  });

  // Cập nhật useModalInner
  const [registerModal, { closeModal }] = useModalInner(async (data) => {
    resetForm();
    loadingInit.value = true; // 👈 bắt đầu loading
    try {
      await loadNhomTronGoiOptions();
      if (data?.record) {
        currentRecord.value = data.record;
        await loadRecordData(data.record);
      }
    } finally {
      loadingInit.value = false; // 👈 kết thúc loading
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
      nhomTronGoiId: undefined,
      loaiHeThong: undefined,
      loaiPha: '1 pha',
      moTa: '',
      congSuatHeThong: 0,
      tongGiaMienBac: 0,
      tongGiaMienNam: 0,
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
    isImageDeleted.value = false;
    currentRecord.value = null;
    thuongHieuIdsApplyByNhomTronGoi.value = null;
    loaiMais.value = ['mái tôn'];
  }

  async function loadNhomTronGoiOptions() {
    try {
      const res = await getAllNhomTronGoi();
      if (res?.data) {
        const list = Array.isArray(res.data) ? res.data : (res.data as any).content || [];
        nhomTronGoiList.value = list;
        nhomTronGoiOptions.value = list.map((item: NhomTronGoiDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
      }
    } catch (e) {
      console.error('Error loading nhom tron goi:', e);
    }
  }

  /**
   * Load toàn bộ options vật tư dựa trên thuongHieuIds và loaiPha/loaiMais hiện tại.
   * Không reset các list đang có — chỉ cập nhật options để người dùng có thể chọn lại.
   */
  async function loadVatTuOptions() {
    // TAM_PIN / BIEN_TAN / PIN_LUU_TRU: lọc theo thương hiệu từ nhóm trọn gói
    // Các nhóm khác: không lọc theo thương hiệu
    const FILTER_BY_THUONG_HIEU = ['TAM_PIN', 'BIEN_TAN', 'PIN_LUU_TRU'];
    const groups = [
      { code: 'TAM_PIN', dataRef: tamPinData, optionsRef: tamPinOptions },
      { code: 'BIEN_TAN', dataRef: bienTanData, optionsRef: bienTanOptions },
      { code: 'PIN_LUU_TRU', dataRef: pinLuuTruData, optionsRef: pinLuuTruOptions },
      { code: 'HE_KHUNG_NHOM', dataRef: heKhungNhomData, optionsRef: heKhungNhomOptions },
      { code: 'HE_DAY_DIEN', dataRef: heDayDienData, optionsRef: heDayDienOptions },
      { code: 'TU_DIEN', dataRef: tuDienData, optionsRef: tuDienOptions },
      { code: 'HE_TIEP_DIA', dataRef: heTiepDiaData, optionsRef: heTiepDiaOptions },
      { code: 'TRON_GOI_LAP_DAT', dataRef: tronGoiLapDatData, optionsRef: tronGoiLapDatOptions },
    ];

    for (const group of groups) {
      try {
        // Chỉ truyền thuongHieuIds khi nhóm cần lọc VÀ đã có giá trị
        const thuongHieuIds =
          FILTER_BY_THUONG_HIEU.includes(group.code) &&
          thuongHieuIdsApplyByNhomTronGoi.value?.length
            ? thuongHieuIdsApplyByNhomTronGoi.value
            : null;
        const response: ResponseData<PageResponse<VatTuDto>> = await filterVatTu(
          null,
          group.code,
          thuongHieuIds,
        );

        let content = response?.data?.content || [];

        if (group.code === 'HE_KHUNG_NHOM') {
          content = content.filter((item) => {
            const loaiMaiString = item.duLieuRieng?.loai_mai?.giaTri as string;
            if (!loaiMaiString) return false;
            for (const loaiMai of loaiMais.value) {
              if (!loaiMaiString.includes(loaiMai)) return false;
            }
            return true;
          });
        }

        if (group.code === 'TU_DIEN') {
          content = content.filter((item) => {
            const soPhaString = item.duLieuRieng?.so_pha?.giaTri as string;
            return soPhaString?.includes(formState.loaiPha);
          });
        }

        group.dataRef.value = content;
        group.optionsRef.value = content.map((item: VatTuDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
      } catch (e) {
        console.error(`Error loading options for ${group.code}:`, e);
      }
    }
  }

  /**
   * Map từ VatTuDto sang VatTuItem (VatTuTronGoiCreatingDto) với soLuong và moTa tùy chỉnh.
   */
  function mapVatTuToItem(
    vatTu: VatTuDto,
    soLuong = 1,
    moTa = '',
    duocBaoHanh = true,
    duocXem = true,
  ): VatTuItem {
    const dsGia = vatTu.thongTinGias?.[vatTu.thongTinGias.length - 1]?.dsGia || [];
    return {
      vatTuId: vatTu.id,
      moTa: moTa || vatTu.moTaBaoGia || '',
      soLuong,
      giaNhapMienBac: dsGia.find((g: GiaInfo) => g.maCoSo === 'HN')?.giaNhap || 0,
      giaNhapMienNam: dsGia.find((g: GiaInfo) => g.maCoSo === 'HCM')?.giaNhap || 0,
      giaBanMienBac: dsGia.find((g: GiaInfo) => g.maCoSo === 'HN')?.giaBan || 0,
      giaBanMienNam: dsGia.find((g: GiaInfo) => g.maCoSo === 'HCM')?.giaBan || 0,
      gm: vatTu.gm || 0,
      thoiGianBaoHanh: vatTu.thoiGianBaoHanh || 0,
      duocBaoHanh,
      duocXem,
      trangThai: 1,
    };
  }

  /**
   * Load dữ liệu record vào form.
   * Đồng thời load options vật tư theo nhóm trọn gói của record.
   */
  async function loadRecordData(record: TronGoiDto) {
    formState.id = record.id;
    formState.ten = record.ten || '';
    formState.nhomTronGoiId = record.nhomTronGoi?.id;
    formState.loaiHeThong = record.loaiHeThong;
    formState.loaiPha = record.loaiPha || '1 pha';
    formState.moTa = (record as any).moTa || '';
    formState.congSuatHeThong = record.congSuatHeThong || 0;
    // Tổng giá: dùng tongGia từ record (backend đã lưu theo coSo)
    formState.tongGiaMienBac = record.tongGia || 0;
    formState.tongGiaMienNam = record.tongGia || 0;
    formState.gmTong = record.gmTong || 0;
    formState.banChay = record.banChay || false;
    formState.trangThai = record.trangThai ?? 1;

    // Xác định thuongHieuIds theo nhóm trọn gói
    if (record.nhomTronGoi) {
      setThuongHieuIds(record.nhomTronGoi);
    }

    // Load options vật tư trước
    await loadVatTuOptions();

    // Sau khi có options, map vật tư từ record vào các list
    if (record.vatTuTronGois && record.vatTuTronGois.length > 0) {
      for (const item of record.vatTuTronGois) {
        const ma = item.vatTu?.nhomVatTu?.ma?.toUpperCase() || '';
        const vatTuItem: VatTuItem = {
          vatTuId: item.vatTu?.id,
          moTa: item.moTa || '',
          soLuong: item.soLuong || 1,
          // Lấy giá từ dữ liệu vật tư đầy đủ nếu có, ngược lại dùng gia từ record
          giaNhapMienBac: getGiaFromVatTuData(item.vatTu?.id, 'HN', 'nhap') ?? 0,
          giaNhapMienNam: getGiaFromVatTuData(item.vatTu?.id, 'HCM', 'nhap') ?? 0,
          giaBanMienBac: getGiaFromVatTuData(item.vatTu?.id, 'HN', 'ban') ?? item.gia ?? 0,
          giaBanMienNam: getGiaFromVatTuData(item.vatTu?.id, 'HCM', 'ban') ?? item.gia ?? 0,
          gm: item.gm || 0,
          thoiGianBaoHanh: item.thoiGianBaoHanh || 0,
          duocBaoHanh: item.duocBaoHanh ?? true,
          duocXem: item.duocXem ?? true,
          trangThai: item.trangThai ?? 1,
        };

        if (ma.includes('TAM_PIN')) tamPinList.value.push(vatTuItem);
        else if (ma.includes('BIEN_TAN')) bienTanList.value.push(vatTuItem);
        else if (ma.includes('PIN_LUU_TRU')) pinLuuTruList.value.push(vatTuItem);
        else if (ma.includes('HE_KHUNG_NHOM')) heKhungNhomList.value.push(vatTuItem);
        else if (ma.includes('HE_DAY_DIEN')) heDayDienList.value.push(vatTuItem);
        else if (ma.includes('TU_DIEN')) tuDienList.value.push(vatTuItem);
        else if (ma.includes('HE_TIEP_DIA')) heTiepDiaList.value.push(vatTuItem);
        else if (ma.includes('TRON_GOI_LAP_DAT')) tronGoiLapDatList.value.push(vatTuItem);
      }
    }

    // Load ảnh
    if (record.tepTin?.duongDan) {
      const imageUrl = record.tepTin.duongDan;
      currentBlobUrl.value && URL.revokeObjectURL(currentBlobUrl.value);
      currentBlobUrl.value = null;

      fetch(imageUrl)
        .then((res) => res.blob())
        .then((blob) => {
          const blobUrl = URL.createObjectURL(blob);
          currentBlobUrl.value = blobUrl;
          fileList.value = [
            {
              uid: '-1',
              name: record.tepTin.tenLuuTru || 'image.jpg',
              status: 'done',
              url: imageUrl,
              thumbUrl: blobUrl,
              type: blob.type,
            },
          ];
        })
        .catch(() => {
          fileList.value = [
            {
              uid: '-1',
              name: record.tepTin?.tenTepGoc || 'image.jpg',
              status: 'done',
              url: imageUrl,
            },
          ];
        });
    }
  }

  function getGiaFromVatTuData(
    vatTuId: number | undefined,
    maCoSo: string,
    loaiGia: 'nhap' | 'ban',
  ): number | null {
    if (!vatTuId) return null;
    const allData = [
      ...tamPinData.value,
      ...bienTanData.value,
      ...pinLuuTruData.value,
      ...heKhungNhomData.value,
      ...heDayDienData.value,
      ...tuDienData.value,
      ...heTiepDiaData.value,
      ...tronGoiLapDatData.value,
    ];
    const vatTu = allData.find((v) => v.id === vatTuId);
    if (!vatTu || !vatTu.thongTinGias?.length) return null;
    const dsGia = vatTu.thongTinGias[vatTu.thongTinGias.length - 1].dsGia;
    const gia = dsGia.find((g: GiaInfo) => g.maCoSo === maCoSo);
    return loaiGia === 'nhap' ? gia?.giaNhap ?? null : gia?.giaBan ?? null;
  }

  function setThuongHieuIds(nhomTronGoi: NhomTronGoiDto) {
    if (nhomTronGoi.thuongHieuPinLuuTru != null) {
      thuongHieuIdsApplyByNhomTronGoi.value = [
        nhomTronGoi.thuongHieuInverter.id,
        nhomTronGoi.thuongHieuPinLuuTru.id,
        nhomTronGoi.thuongHieuTamPin.id,
      ];
    } else {
      thuongHieuIdsApplyByNhomTronGoi.value = [
        nhomTronGoi.thuongHieuInverter.id,
        nhomTronGoi.thuongHieuTamPin.id,
      ];
    }
  }

  /**
   * Khi đổi nhóm trọn gói: cập nhật thuongHieuIds và reload options.
   * Các list vật tư bị reset về rỗng (giống create).
   */
  async function handleNhomTronGoiChange(value: number) {
    formState.nhomTronGoiId = value;
    const found = nhomTronGoiList.value.find((n) => n.id === value);
    if (found) setThuongHieuIds(found);

    // Reset tất cả list vật tư khi đổi nhóm
    tamPinList.value = [];
    bienTanList.value = [];
    pinLuuTruList.value = [];
    heKhungNhomList.value = [];
    heDayDienList.value = [];
    tuDienList.value = [];
    heTiepDiaList.value = [];
    tronGoiLapDatList.value = [];

    await loadVatTuOptions();

    // Tự động khởi tạo tấm pin nếu có data
    if (tamPinData.value.length > 0) {
      tamPinList.value = [mapVatTuToItem(tamPinData.value[0])];
    } else {
      tamPinList.value = [
        {
          vatTuId: undefined,
          moTa: '',
          soLuong: 1,
          giaNhapMienBac: 0,
          giaNhapMienNam: 0,
          giaBanMienBac: 0,
          giaBanMienNam: 0,
          gm: 0,
          thoiGianBaoHanh: 0,
          duocBaoHanh: true,
          duocXem: true,
          trangThai: 1,
        },
      ];
    }
  }

  async function handleSoPhaChange() {
    const response = await filterVatTu(null, 'TU_DIEN', null);
    if (response?.data?.content) {
      const filtered = response.data.content.filter((item) => {
        const soPhaString = item.duLieuRieng?.so_pha?.giaTri as string;
        return soPhaString?.includes(formState.loaiPha);
      });
      tuDienData.value = filtered;
      tuDienOptions.value = filtered.map((item: VatTuDto) => ({
        label: item.ten || String(item.id),
        value: item.id,
      }));
    }
  }

  async function handleLoaiMaiChange() {
    if (loaiMais.value.length === 0) loaiMais.value.push('mái tôn');
    const response = await filterVatTu(null, 'HE_KHUNG_NHOM', null);
    if (response?.data?.content) {
      const filtered = response.data.content.filter((item) => {
        const loaiMaiString = item.duLieuRieng?.loai_mai?.giaTri as string;
        if (!loaiMaiString) return false;
        for (const lm of loaiMais.value) {
          if (!loaiMaiString.includes(lm)) return false;
        }
        return true;
      });
      heKhungNhomData.value = filtered;
      heKhungNhomOptions.value = filtered.map((item: VatTuDto) => ({
        label: item.ten || String(item.id),
        value: item.id,
      }));
    }
  }

  function filterOption(input: string, option: any) {
    return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  }

  // ==================== HANDLER THÊM / XÓA ====================

  function handleAddBienTan() {
    if (!bienTanData.value.length) {
      message.error('Không có biến tần khả dụng');
      return;
    }
    bienTanList.value.push(mapVatTuToItem(bienTanData.value[0]));
  }
  function handleRemoveBienTan(index: number) {
    bienTanList.value.splice(index, 1);
  }

  function handleAddPinLuuTru() {
    if (!pinLuuTruData.value.length) {
      message.error('Không có pin lưu trữ khả dụng');
      return;
    }
    pinLuuTruList.value.push(mapVatTuToItem(pinLuuTruData.value[0]));
  }
  function handleRemovePinLuuTru(index: number) {
    pinLuuTruList.value.splice(index, 1);
  }

  function handleAddHeKhungNhom() {
    if (!heKhungNhomData.value.length) {
      message.error('Không tìm thấy hệ khung nhôm phù hợp');
      return;
    }
    const firstVatTu = heKhungNhomData.value[0];
    const soLuong = calcSoLuongHeKhungNhom(firstVatTu.ma);
    heKhungNhomList.value.push(mapVatTuToItem(firstVatTu, soLuong));
  }
  function handleRemoveHeKhungNhom(index: number) {
    heKhungNhomList.value.splice(index, 1);
  }

  function handleAddHeDayDien() {
    if (!heDayDienData.value.length) {
      message.error('Không có hệ dây điện khả dụng');
      return;
    }
    heDayDienList.value.push(mapVatTuToItem(heDayDienData.value[0]));
  }
  function handleRemoveHeDayDien(index: number) {
    heDayDienList.value.splice(index, 1);
  }

  function handleAddTuDien() {
    if (!tuDienData.value.length) {
      message.error('Không có tủ điện loại ' + formState.loaiPha);
      return;
    }
    tuDienList.value.push(mapVatTuToItem(tuDienData.value[0]));
  }
  function handleRemoveTuDien(index: number) {
    tuDienList.value.splice(index, 1);
  }

  function handleAddHeTiepDia() {
    if (!heTiepDiaData.value.length) {
      message.error('Không có hệ tiếp địa khả dụng');
      return;
    }
    heTiepDiaList.value.push(mapVatTuToItem(heTiepDiaData.value[0]));
  }
  function handleRemoveHeTiepDia(index: number) {
    heTiepDiaList.value.splice(index, 1);
  }

  function handleAddTronGoiLapDat() {
    if (!tronGoiLapDatData.value.length) {
      message.error('Không có trọn gói lắp đặt khả dụng');
      return;
    }
    tronGoiLapDatList.value.push(mapVatTuToItem(tronGoiLapDatData.value[0], 1, '', false));
  }
  function handleRemoveTronGoiLapDat(index: number) {
    tronGoiLapDatList.value.splice(index, 1);
  }

  // ==================== HANDLER ĐỔI VẬT TƯ ====================

  function updateItemFromVatTu(
    list: VatTuItem[],
    index: number,
    dataList: VatTuDto[],
    vatTuId: number,
  ) {
    const vatTu = dataList.find((v) => v.id === vatTuId);
    if (!vatTu) return;
    const dsGia = vatTu.thongTinGias?.[vatTu.thongTinGias.length - 1]?.dsGia || [];
    list[index].giaNhapMienBac = dsGia.find((g: GiaInfo) => g.maCoSo === 'HN')?.giaNhap || 0;
    list[index].giaNhapMienNam = dsGia.find((g: GiaInfo) => g.maCoSo === 'HCM')?.giaNhap || 0;
    list[index].giaBanMienBac = dsGia.find((g: GiaInfo) => g.maCoSo === 'HN')?.giaBan || 0;
    list[index].giaBanMienNam = dsGia.find((g: GiaInfo) => g.maCoSo === 'HCM')?.giaBan || 0;
    list[index].thoiGianBaoHanh = vatTu.thoiGianBaoHanh || 0;
    list[index].gm = vatTu.gm || 0;
  }

  function handleTamPinChange(value: number, index: number) {
    updateItemFromVatTu(tamPinList.value, index, tamPinData.value, value);
    const vatTu = tamPinData.value.find((v) => v.id === value);
    if (vatTu) {
      formState.congSuatHeThong =
        vatTu.duLieuRieng?.cong_suat?.giaTri * tamPinList.value[index].soLuong;
    }
  }

  function handleChangeSoLuongTamPin(idVatTu: number, index: number, value: number) {
    const vatTu = tamPinData.value.find((v) => v.id === idVatTu);
    if (vatTu) {
      formState.congSuatHeThong =
        vatTu.duLieuRieng?.cong_suat?.giaTri * tamPinList.value[index].soLuong;
    }
    // Cập nhật lại số lượng hệ khung nhôm
    heKhungNhomList.value.forEach((item, idx) => {
      const hkn = heKhungNhomData.value.find((v) => v.id === item.vatTuId);
      if (hkn) {
        heKhungNhomList.value[idx].soLuong = calcSoLuongHeKhungNhom(hkn.ma);
        updateItemFromVatTu(heKhungNhomList.value, idx, heKhungNhomData.value, hkn.id);
      }
    });
  }

  function handleBienTanChange(value: number, index: number) {
    updateItemFromVatTu(bienTanList.value, index, bienTanData.value, value);
  }
  function handlePinLuuTruChange(value: number, index: number) {
    updateItemFromVatTu(pinLuuTruList.value, index, pinLuuTruData.value, value);
  }

  function handleHeKhungNhomChange(value: number, index: number) {
    const vatTu = heKhungNhomData.value.find((v) => v.id === value);
    if (vatTu) {
      heKhungNhomList.value[index].soLuong = calcSoLuongHeKhungNhom(vatTu.ma);
      updateItemFromVatTu(heKhungNhomList.value, index, heKhungNhomData.value, value);
    }
  }

  function handleHeDayDienChange(value: number, index: number) {
    updateItemFromVatTu(heDayDienList.value, index, heDayDienData.value, value);
  }
  function handleTuDienChange(value: number, index: number) {
    updateItemFromVatTu(tuDienList.value, index, tuDienData.value, value);
  }
  function handleHeTiepDiaChange(value: number, index: number) {
    updateItemFromVatTu(heTiepDiaList.value, index, heTiepDiaData.value, value);
  }
  function handleTronGoiLapDatChange(value: number, index: number) {
    updateItemFromVatTu(tronGoiLapDatList.value, index, tronGoiLapDatData.value, value);
  }

  // ==================== TÍNH SỐ LƯỢNG HỆ KHUNG NHÔM ====================

  function calcSoLuongHeKhungNhom(ma: string): number {
    const soLuongTamPin = tamPinList.value[0]?.soLuong || 0;
    if (ma.includes('kep_bien')) return ceil(soLuongTamPin / 5) * 4 + 4;
    if (ma.includes('kep_giua')) return ceil(soLuongTamPin / 5) * 8 + 4;
    if (ma.includes('full') && ma.includes('rail')) return ceil((soLuongTamPin * 1.2 * 2) / 4);
    if (ma.includes('mini') && ma.includes('rail')) return ceil(soLuongTamPin / 5) * 12 + 8;
    if (ma.includes('thanh_noi')) return ceil((soLuongTamPin * 1.2 * 2) / 4) * 2 + 4;
    if (ma.includes('chan_l')) return ceil((soLuongTamPin * 1.2 * 2) / 4) * 6;
    if (ma.includes('kep_tiep_dia')) return ceil(soLuongTamPin / 5) + 1;
    if (ma.includes('la_tiep_dia')) return (ceil(soLuongTamPin / 5) * 8 + 4) / 2;
    if (ma.includes('kep_day_dien')) return soLuongTamPin * 4;
    return 0;
  }

  // ==================== TÍNH TỔNG GIÁ ====================

  function tinhTongGia() {
    let tongGiaMienBac = 0;
    let tongGiaMienNam = 0;
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
    for (const vatTu of allVatTu) {
      tongGiaMienBac += (vatTu.giaBanMienBac || 0) * (vatTu.soLuong || 0);
      tongGiaMienNam += (vatTu.giaBanMienNam || vatTu.giaBanMienBac || 0) * (vatTu.soLuong || 0);
    }
    formState.tongGiaMienBac = tongGiaMienBac;
    formState.tongGiaMienNam = tongGiaMienNam;
  }

  // ==================== UPLOAD ====================

  const beforeUpload: UploadProps['beforeUpload'] = (file) => {
    if (!file.type?.startsWith('image/')) {
      message.error('Chỉ chấp nhận file ảnh!');
      return false;
    }
    if (file.size / 1024 / 1024 >= 5) {
      message.error('Kích thước ảnh phải nhỏ hơn 5MB!');
      return false;
    }
    return false;
  };

  function handleRemoveFile() {
    fileList.value = [];
    isImageDeleted.value = true;
  }

  // ==================== SUBMIT ====================

  async function handleSubmit() {
    try {
      await formRef.value?.validate();
      loading.value = true;

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

      if (allVatTu.length === 0) {
        message.error('Vui lòng thêm ít nhất một vật tư');
        return;
      }
      for (let i = 0; i < allVatTu.length; i++) {
        if (!allVatTu[i].vatTuId) {
          message.error(`Vui lòng chọn vật tư cho mục ${i + 1}`);
          return;
        }
      }

      const submitData = {
        id: formState.id,
        ten: formState.ten,
        nhomTronGoiId: formState.nhomTronGoiId,
        loaiHeThong: formState.loaiHeThong,
        loaiPha: formState.loaiPha,
        moTa: formState.moTa,
        congSuatHeThong: formState.congSuatHeThong,
        tongGiaMienBac: formState.tongGiaMienBac,
        tongGiaMienNam: formState.tongGiaMienNam,
        gmTong: formState.gmTong,
        banChay: formState.banChay,
        trangThai: formState.trangThai,
        vatTuTronGois: allVatTu.map((v) => ({
          vatTuId: v.vatTuId,
          moTa: v.moTa || '',
          soLuong: v.soLuong,
          giaNhapMienBac: v.giaNhapMienBac,
          giaNhapMienNam: v.giaNhapMienNam,
          giaBanMienBac: v.giaBanMienBac,
          giaBanMienNam: v.giaBanMienNam,
          gm: v.gm,
          thoiGianBaoHanh: v.thoiGianBaoHanh || 0,
          duocBaoHanh: v.duocBaoHanh,
          duocXem: v.duocXem,
          trangThai: v.trangThai,
        })),
      };

      let fileToSend: File | null = null;
      if (fileList.value.length > 0) {
        fileToSend = fileList.value[0].originFileObj || null;
      } else if (!isImageDeleted.value) {
        fileToSend = undefined as any;
      }

      const result = await updateTronGoi(
        formState.id!,
        submitData as any,
        fileToSend === undefined ? null : fileToSend,
      );

      if (result.status === 200 || result.status === 201) {
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
    if (currentBlobUrl.value) URL.revokeObjectURL(currentBlobUrl.value);
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

  .upload-hint {
    margin-top: 8px;
    color: #999;
    font-size: 12px;
  }

  :deep(.ant-select) {
    width: 100%;
  }
</style>
