<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Tạo trọn gói"
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

      <Divider orientation="center">Thông tin cơ sở</Divider>
      <FormItem :wrapper-col="{ span: 24 }">
        <Button type="dashed" block @click="handleAddCoSo" style="margin-bottom: 16px">
          <template #icon><PlusOutlined /></template>
          Thêm cơ sở
        </Button>
        <div v-for="(item, index) in tronGoiCoSosList" :key="index" class="co-so-item">
          <Card size="small" :title="`Cơ sở ${index + 1}`">
            <template #extra>
              <Button type="link" danger size="small" @click="handleRemoveCoSo(index)"
                ><DeleteOutlined
              /></Button>
            </template>
            <Row :gutter="16">
              <Col :span="8">
                <FormItem label="Cơ sở" :required="true">
                  <Select
                    v-model:value="item.coSoId"
                    placeholder="Chọn cơ sở"
                    show-search
                    :filter-option="filterOption"
                    :options="coSoOptions"
                  ></Select>
                </FormItem>
              </Col>
              <Col :span="8">
                <FormItem label="Sản lượng TT (kW)" :required="true">
                  <InputNumber
                    v-model:value="item.sanLuongToiThieu"
                    placeholder="Sản lượng tối thiểu"
                    :min="0"
                    style="width: 100%"
                  />
                </FormItem>
              </Col>
              <Col :span="8">
                <FormItem label="Sản lượng TĐ (kW)" :required="true">
                  <InputNumber
                    v-model:value="item.sanLuongToiDa"
                    placeholder="Sản lượng tối đa"
                    :min="0"
                    style="width: 100%"
                  />
                </FormItem>
              </Col>
            </Row>
          </Card>
        </div>
      </FormItem>

      <Divider orientation="center">Vật tư trong gói</Divider>
      <FormItem :wrapper-col="{ span: 24 }">
        <!-- TẤM PIN  -->
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
                      disabled
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
                      disabled
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
                      disabled
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
                      disabled
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
                <Col :span="6">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="6">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
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
        <!-- BIẾN TẦN  -->
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
                      disabled
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
                      disabled
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
                      disabled
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
                      disabled
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
                <Col :span="6">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="6">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
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
        <!-- PIN LƯU TRỮ  -->
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
                      disabled
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
                      disabled
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
                      disabled
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
                      disabled
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
                <Col :span="6">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="6">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
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
        <!-- HỆ KHUNG NHÔM  -->
        <div class="vat-tu-group">
          <Divider orientation="left" style="margin: 16px 0">Hệ khung nhôm</Divider>

          <CheckboxGroup label="Loại mái" v-model:value="loaiMais" @change="handleLoaiMaiChange()">
            <Checkbox value="mái tôn">mái tôn</Checkbox>
            <Checkbox value="mái ngói">mái ngói</Checkbox>
            <Checkbox value="khung sắt">khung sắt</Checkbox>
            <Checkbox value="mái bằng">mái bằng</Checkbox>
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
                      disabled
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
                      disabled
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
                      disabled
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
                      disabled
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
                <Col :span="6">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="6">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
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
        <!-- HỆ DÂY ĐIỆN  -->
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
                      @change="handleHeDayDienChange(values, itemIndex)"
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
                      disabled
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
                      disabled
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
                      disabled
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
                      disabled
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
                <Col :span="6">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="6">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
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
        <!-- TỦ ĐIỆN  -->
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
                      @change="handleTuDienChange(values, itemIndex)"
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
                      disabled
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
                      disabled
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
                      disabled
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
                      disabled
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
                <Col :span="6">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="6">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
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
        <!-- HỆ TIẾP ĐỊA  -->
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
                      @change="handleHeTiepDiaChange(values, itemIndex)"
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
                      disabled
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
                      disabled
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
                      disabled
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
                      disabled
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
                <Col :span="6">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="6">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
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
        <!-- TRỌN GÓI LẮP ĐẶT  -->
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
                      @change="handleTronGoiLapDatChange(values, itemIndex)"
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
                      disabled
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
                      disabled
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
                      disabled
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
                      disabled
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
                <Col :span="6">
                  <FormItem label="Được bảo hành">
                    <RadioGroup v-model:value="item.duocBaoHanh" button-style="solid">
                      <RadioButton :value="true">Có</RadioButton>
                      <RadioButton :value="false">Không</RadioButton>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col :span="6">
                  <FormItem label="Được xem">
                    <RadioGroup v-model:value="item.duocXem" button-style="solid">
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
            label="Giá khung sắt (VND)"
            name="tongGia"
            :rules="[{ required: true, message: 'Vui lòng nhập giá khung sắt' }]"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.giaKhungSat"
              placeholder="Nhập giá khung sắt"
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
            label="Công suất hệ thống (kW)"
            name="congSuatHeThong"
            :rules="[{ required: true, message: 'Vui lòng nhập công suất hệ thống' }]"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.congSuatHeThong"
              placeholder="Nhập công suất hệ thống"
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
            name="tongGia"
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
            name="tongGia"
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
            :rules="[{ required: true, message: 'Vui lòng nhập GM tổng' }]"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <InputNumber
              v-model:value="formState.gmTong"
              placeholder="Nhập GM tổng"
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
            label="Tính tổng giá"
            name="banChay"
            :label-col="{ span: 12 }"
            :wrapper-col="{ span: 12 }"
          >
            <Button type="primary" :reqired="true" @click="tinhTongGia()">Tính tổng giá</Button>
          </FormItem>
        </Col>
      </Row>

      <Divider orientation="center">Hình ảnh</Divider>
      <FormItem label="Tải ảnh lên" 
      :wrapper-col="{ span: 18 }"
      :rules="[{ required: true, message: 'Vui lòng chọn 1 ảnh' }]"
      >
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
        <div class="upload-hint">Tối đa 1 ảnh, định dạng: JPG, PNG, GIF</div>
      </FormItem>
    </Form>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { PlusOutlined, DeleteOutlined } from '@ant-design/icons-vue';
  import {
    createTronGoi,
    getAllCoSo,
    getAllNhomTronGoi,
    filterVatTu,
    type ThongTinTronGoiCoSoCreatingDto,
    type VatTuTronGoiCreatingDto,
    type CoSoDto,
    type NhomTronGoiDto,
    type VatTuDto,
    GiaInfo,
    ResponseData,
    PageResponse,
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
    SelectOption,
    Textarea,
    Upload,
  } from 'ant-design-vue';
  import type { UploadProps } from 'ant-design-vue';
  import { data } from '../../excel/data';
  import { values } from 'xe-utils';
  import { ceil } from 'lodash-es';

  defineOptions({ name: 'CreateTronGoiModal' });
  const emit = defineEmits(['success', 'register']);
  const formRef = ref();
  const loading = ref(false);
  const formState = reactive({
    ten: '',
    nhomTronGoiId: undefined as number | undefined,
    loaiHeThong: undefined as string | undefined,
    loaiPha: '1 pha',
    giaKhungSat: 0,
    moTa: '',
    tongGiaMienBac: 0,
    tongGiaMienNam: 0,
    gmTong: 0,
    congSuatHeThong: 0,
    banChay: false,
    trangThai: 1,
  });
  const tronGoiCoSosList = ref<ThongTinTronGoiCoSoCreatingDto[]>([]);
  const fileList = ref<any[]>([]);
  const coSoOptions = ref<Array<{ label: string; value: number }>>([]);
  const nhomTronGoiList = ref<NhomTronGoiDto[]>([]);
  const nhomTronGoiOptions = ref<Array<{ label: string; value: number }>>([]);
  const thuongHieuIdsApllyByNhomTronGoi = ref<number[] | null>(null);
  type VatTuItem = VatTuTronGoiCreatingDto;
  const loaiMais = ref<string[]>(['mái tôn']);
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

  const [registerModal, { closeModal }] = useModalInner(async () => {
    resetForm();
    await loadOptions();
    await initializeVatTu();
  });

  function resetForm() {
    Object.assign(formState, {
      ten: '',
      nhomTronGoiId: undefined,
      loaiHeThong: undefined,
      loaiPha: '1 pha',
      giaKhungSat: 0,
      moTa: '',
      tongGia: 0,
      gmTong: 0,
      congSuatHeThong: 0,
      banChay: false,
      trangThai: 1,
    });
    tronGoiCoSosList.value = [];
    tamPinList.value = [];
    bienTanList.value = [];
    pinLuuTruList.value = [];
    heKhungNhomList.value = [];
    heDayDienList.value = [];
    tuDienList.value = [];
    heTiepDiaList.value = [];
    tronGoiLapDatList.value = [];
    fileList.value = [];
  }

  async function loadOptions() {
    try {
      const [coSoRes, nhomTronGoiRes] = await Promise.allSettled([
        getAllCoSo(),
        getAllNhomTronGoi(),
      ]);
      if (coSoRes.status === 'fulfilled' && coSoRes.value?.data) {
        const data = coSoRes.value.data;
        const list = Array.isArray(data) ? data : data.content || [];
        coSoOptions.value = list.map((item: CoSoDto) => ({
          label: item.ten || item.ma || String(item.id),
          value: item.id,
        }));
      }
      if (nhomTronGoiRes.status === 'fulfilled' && nhomTronGoiRes.value?.data) {
        const data = nhomTronGoiRes.value.data;
        nhomTronGoiList.value = Array.isArray(data) ? data : data.content || [];
        const list = Array.isArray(data) ? data : data.content || [];
        nhomTronGoiOptions.value = list.map((item: NhomTronGoiDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
      }
    } catch (error) {
      console.error('Error loading options:', error);
      message.error('Không thể tải dữ liệu danh mục');
    }
  }

  async function initializeVatTu() {
    try {
      const groups = [
        {
          code: 'TAM_PIN',
          dataRef: tamPinData,
          optionsRef: tamPinOptions,
          listRef: tamPinList,
          initialize: true,
        },
        {
          code: 'BIEN_TAN',
          dataRef: bienTanData,
          optionsRef: bienTanOptions,
          listRef: bienTanList,
          initialize: false,
        },
        {
          code: 'PIN_LUU_TRU',
          dataRef: pinLuuTruData,
          optionsRef: pinLuuTruOptions,
          listRef: pinLuuTruList,
          initialize: false,
        },
        {
          code: 'HE_KHUNG_NHOM',
          dataRef: heKhungNhomData,
          optionsRef: heKhungNhomOptions,
          listRef: heKhungNhomList,
          initialize: false,
        },
        {
          code: 'HE_DAY_DIEN',
          dataRef: heDayDienData,
          optionsRef: heDayDienOptions,
          listRef: heDayDienList,
          initialize: false,
        },
        {
          code: 'TU_DIEN',
          dataRef: tuDienData,
          optionsRef: tuDienOptions,
          listRef: tuDienList,
          initialize: false,
        },
        {
          code: 'HE_TIEP_DIA',
          dataRef: heTiepDiaData,
          optionsRef: heTiepDiaOptions,
          listRef: heTiepDiaList,
          initialize: false,
        },
        {
          code: 'TRON_GOI_LAP_DAT',
          dataRef: tronGoiLapDatData,
          optionsRef: tronGoiLapDatOptions,
          listRef: tronGoiLapDatList,
          initialize: false,
        },
      ];

      for (const group of groups) {
        let response: ResponseData<PageResponse<VatTuDto>>;
        if (group.code in ['TAM_PIN', 'BIEN_TAN', 'PIN_LUU_TRU']) {
          response = await filterVatTu(
            null,
            group.code,
            thuongHieuIdsApllyByNhomTronGoi.value || null,
          );
        } else {
          response = await filterVatTu(null, group.code, null);
        }

        if (group.code === 'HE_KHUNG_NHOM') {
          response.data.content = response.data.content.filter(function (filtering) {
            let loaiMaiString = filtering.duLieuRieng.loai_mai.giaTri as string;
            for (const loaiMaiGet of loaiMais.value) {
              if (!loaiMaiString.includes(loaiMaiGet)) {
                return false;
              }
            }
            return true;
          });
        }

        if (group.code === 'TU_DIEN') {
          response.data.content = response.data.content.filter(function (filtering) {
            let soPhaString = filtering.duLieuRieng.so_pha.giaTri as string;
            return soPhaString.includes(formState.loaiPha);
          });
        }

        if (response?.data?.content && response.data.content.length > 0) {
          group.dataRef.value = response.data.content;
          group.optionsRef.value = response.data.content.map((item: VatTuDto) => ({
            label: item.ten || String(item.id),
            value: item.id,
          }));
          if (group.initialize) {
            const firstVatTu = response.data.content[0];
            let dsGiaVatTu = firstVatTu.thongTinGias[firstVatTu.thongTinGias.length - 1].dsGia;
            let giaNhapMienBac =
              dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
            let giaNhapMienNam =
              dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
            let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
            let giaBanMienNam =
              dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
            group.listRef.value = [
              {
                vatTuId: firstVatTu.id,
                moTa: firstVatTu.moTaBaoGia || '',
                soLuong: 1,
                giaNhapMienBac: giaNhapMienBac,
                giaNhapMienNam: giaNhapMienNam,
                giaBanMienBac: giaBanMienBac,
                giaBanMienNam: giaBanMienNam,
                gm: firstVatTu.nhomVatTu.gm,
                thoiGianBaoHanh: 0,
                duocBaoHanh: true,
                duocXem: true,
                trangThai: 1,
              },
            ];
          } else {
            group.listRef.value = [];
          }
        } else {
          group.optionsRef.value = [];
          if (group.initialize) {
            group.listRef.value = [
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
          } else {
            group.listRef.value = [];
          }
        }
      }
    } catch (error) {
      console.error('Error initializing vat tu:', error);
      message.error('Không thể khởi tạo danh sách vật tư');
    }
  }

  function handleNhomTronGoiChange(value: number) {
    formState.nhomTronGoiId = value;
    for (const nhomTronGoi of nhomTronGoiList.value) {
      if (nhomTronGoi.id === value) {
        if (nhomTronGoi.thuongHieuPinLuuTru != null) {
          thuongHieuIdsApllyByNhomTronGoi.value = [
            nhomTronGoi.thuongHieuInverter.id,
            nhomTronGoi.thuongHieuPinLuuTru.id,
            nhomTronGoi.thuongHieuTamPin.id,
          ];
        } else {
          thuongHieuIdsApllyByNhomTronGoi.value = [
            nhomTronGoi.thuongHieuInverter.id,
            nhomTronGoi.thuongHieuTamPin.id,
          ];
        }
        break;
      }
    }
  }

  function filterOption(input: string, option: any) {
    return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  }

  function handleAddCoSo() {
    const coSoIdBegin = coSoOptions.value.length > 0 ? coSoOptions.value[0].value : undefined;
    tronGoiCoSosList.value.push({ coSoId: coSoIdBegin, sanLuongToiThieu: 0, sanLuongToiDa: 0 });
  }

  function handleRemoveCoSo(index: number) {
    tronGoiCoSosList.value.splice(index, 1);
  }

  async function handleLoaiMaiChange() {
    if (loaiMais.value.length == 0) {
      loaiMais.value.push('mái tôn');
    }
    const response = await filterVatTu(null, 'HE_KHUNG_NHOM', null);
    if (response?.data?.content && response.data.content.length > 0) {
      response.data.content = response.data.content.filter(function (filtering) {
        let loaiMaiString = filtering.duLieuRieng.loai_mai.giaTri as string;
        for (const loaiMaiGet of loaiMais.value) {
          if (!loaiMaiString.includes(loaiMaiGet)) {
            return false;
          }
        }
        return true;
      });

      if (response.data.content.length > 0) {
        heKhungNhomData.value = response.data.content;
        heKhungNhomOptions.value = response.data.content.map((item: VatTuDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
      } else {
        heKhungNhomData.value = [];
        heKhungNhomOptions.value = [];
      }
    } else {
      heKhungNhomData.value = [];
      heKhungNhomOptions.value = [];
    }
  }

  async function handleSoPhaChange() {
    const response = await filterVatTu(null, 'TU_DIEN', null);

    if (response?.data?.content && response.data.content.length > 0) {
      response.data.content = response.data.content.filter(function (filtering) {
        let soPhaString = filtering.duLieuRieng.so_pha.giaTri as string;
        return soPhaString.includes(formState.loaiPha);
      });
      if (response.data.content.length > 0) {
        tuDienData.value = response.data.content;
        tuDienOptions.value = response.data.content.map((item: VatTuDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
      } else {
        tuDienData.value = [];
        tuDienOptions.value = [];
      }
    } else {
      tuDienData.value = [];
      tuDienOptions.value = [];
    }
  }

  function handleAddBienTan() {
    const firstVatTu = bienTanData.value[0];
    let dsGiaVatTu = firstVatTu.thongTinGias[firstVatTu.thongTinGias.length - 1].dsGia;
    let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
    let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
    let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
    let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
    const firstId = bienTanOptions.value.length > 0 ? bienTanOptions.value[0].value : undefined;
    bienTanList.value.push({
      vatTuId: firstId,
      moTa: '',
      soLuong: 1,
      giaNhapMienBac: giaNhapMienBac,
      giaNhapMienNam: giaNhapMienNam,
      giaBanMienBac: giaBanMienBac,
      giaBanMienNam: giaBanMienNam,
      gm: firstVatTu.nhomVatTu.gm,
      thoiGianBaoHanh: 0,
      duocBaoHanh: true,
      duocXem: true,
      trangThai: 1,
    });
  }

  function handleRemoveBienTan(index: number) {
    bienTanList.value.splice(index, 1);
  }

  function handleAddPinLuuTru() {
    const firstVatTu = pinLuuTruData.value[0];
    let dsGiaVatTu = firstVatTu.thongTinGias[firstVatTu.thongTinGias.length - 1].dsGia;
    let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
    let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
    let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
    let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
    const firstId = pinLuuTruOptions.value.length > 0 ? pinLuuTruOptions.value[0].value : undefined;
    pinLuuTruList.value.push({
      vatTuId: firstId,
      moTa: '',
      soLuong: 1,
      giaNhapMienBac: giaNhapMienBac,
      giaNhapMienNam: giaNhapMienNam,
      giaBanMienBac: giaBanMienBac,
      giaBanMienNam: giaBanMienNam,
      gm: firstVatTu.nhomVatTu.gm,
      thoiGianBaoHanh: 0,
      duocBaoHanh: true,
      duocXem: true,
      trangThai: 1,
    });
  }

  function handleRemovePinLuuTru(index: number) {
    pinLuuTruList.value.splice(index, 1);
  }

  function handleAddHeKhungNhom() {
    if (heKhungNhomData.value.length == 0) {
      message.error('Không tìm thấy hệ khung nhôm phù hợp');
      return;
    }
    const firstHeKhungNhom = heKhungNhomData.value[0];
    let dsGiaVatTu = firstHeKhungNhom.thongTinGias[firstHeKhungNhom.thongTinGias.length - 1].dsGia;
    let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
    let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
    let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
    let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
    const firstId =
      heKhungNhomOptions.value.length > 0 ? heKhungNhomOptions.value[0].value : undefined;
    let soLuongCal = 0;
    if (firstHeKhungNhom.ma.includes('kep_bien')) {
      soLuongCal = ceil(tamPinList.value[0].soLuong / 5) * 4 + 4;
    }
    if (firstHeKhungNhom.ma.includes('kep_giua')) {
      soLuongCal = ceil(tamPinList.value[0].soLuong / 5) * 8 + 4;
    }
    if (firstHeKhungNhom.ma.includes('full_rail')) {
      soLuongCal = ceil((tamPinList.value[0].soLuong * 1.2 * 2) / 4);
    }
    if (firstHeKhungNhom.ma.includes('thanh_noi')) {
      soLuongCal = ceil((tamPinList.value[0].soLuong * 1.2 * 2) / 4) * 2 + 4;
    }
    if (firstHeKhungNhom.ma.includes('chan_l')) {
      soLuongCal = ceil((tamPinList.value[0].soLuong * 1.2 * 2) / 4) * 6;
    }
    if (firstHeKhungNhom.ma.includes('kep_tiep_dia')) {
      soLuongCal = ceil(tamPinList.value[0].soLuong / 5) + 1;
    }
    if (firstHeKhungNhom.ma.includes('la_tiep_dia')) {
      soLuongCal = (ceil(tamPinList.value[0].soLuong / 5) * 8 + 4) / 2;
    }
    if (firstHeKhungNhom.ma.includes('kep_day_dien')) {
      soLuongCal = tamPinList.value[0].soLuong * 4;
    }
    heKhungNhomList.value.push({
      vatTuId: firstId,
      moTa: '',
      soLuong: soLuongCal,
      giaNhapMienBac: giaNhapMienBac,
      giaNhapMienNam: giaNhapMienNam,
      giaBanMienBac: giaBanMienBac,
      giaBanMienNam: giaBanMienNam,
      gm: firstHeKhungNhom.nhomVatTu.gm,
      thoiGianBaoHanh: 0,
      duocBaoHanh: true,
      duocXem: true,
      trangThai: 1,
    });
  }

  function handleRemoveHeKhungNhom(index: number) {
    heKhungNhomList.value.splice(index, 1);
  }

  function handleAddHeDayDien() {
    const firstHeDayDien = heDayDienData.value[0];
    let dsGiaVatTu = firstHeDayDien.thongTinGias[firstHeDayDien.thongTinGias.length - 1].dsGia;
    let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
    let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
    let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
    let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
    const firstId = heDayDienOptions.value.length > 0 ? heDayDienOptions.value[0].value : undefined;
    heDayDienList.value.push({
      vatTuId: firstId,
      moTa: '',
      soLuong: 1,
      giaNhapMienBac: giaNhapMienBac,
      giaNhapMienNam: giaNhapMienNam,
      giaBanMienBac: giaBanMienBac,
      giaBanMienNam: giaBanMienNam,
      gm: firstHeDayDien.nhomVatTu.gm,
      thoiGianBaoHanh: 0,
      duocBaoHanh: true,
      duocXem: true,
      trangThai: 1,
    });
  }

  function handleRemoveHeDayDien(index: number) {
    heDayDienList.value.splice(index, 1);
  }

  function handleAddTuDien() {
    if (tuDienData.value.length === 0) {
      let m = 'Không có tủ điện loại ' + formState.loaiPha;
      message.error(m);
      return;
    }
    const firstTuDien = tuDienData.value[0];
    let dsGiaVatTu = firstTuDien.thongTinGias[firstTuDien.thongTinGias.length - 1].dsGia;
    let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
    let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
    let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
    let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
    const firstId = tuDienOptions.value.length > 0 ? tuDienOptions.value[0].value : undefined;
    tuDienList.value.push({
      vatTuId: firstId,
      moTa: '',
      soLuong: 1,
      giaNhapMienBac: giaNhapMienBac,
      giaNhapMienNam: giaNhapMienNam,
      giaBanMienBac: giaBanMienBac,
      giaBanMienNam: giaBanMienNam,
      gm: firstTuDien.nhomVatTu.gm,
      thoiGianBaoHanh: 0,
      duocBaoHanh: true,
      duocXem: true,
      trangThai: 1,
    });
  }

  function handleRemoveTuDien(index: number) {
    tuDienList.value.splice(index, 1);
  }

  function handleAddHeTiepDia() {
    const firstHeTiepDia = heTiepDiaData.value[0];
    let dsGiaVatTu = firstHeTiepDia.thongTinGias[firstHeTiepDia.thongTinGias.length - 1].dsGia;
    let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
    let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
    let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
    let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
    const firstId = heTiepDiaOptions.value.length > 0 ? heTiepDiaOptions.value[0].value : undefined;
    heTiepDiaList.value.push({
      vatTuId: firstId,
      moTa: '',
      soLuong: 1,
      giaNhapMienBac: giaNhapMienBac,
      giaNhapMienNam: giaNhapMienNam,
      giaBanMienBac: giaBanMienBac,
      giaBanMienNam: giaBanMienNam,
      gm: firstHeTiepDia.nhomVatTu.gm,
      thoiGianBaoHanh: 0,
      duocBaoHanh: true,
      duocXem: true,
      trangThai: 1,
    });
  }

  function handleRemoveHeTiepDia(index: number) {
    heTiepDiaList.value.splice(index, 1);
  }

  function handleAddTronGoiLapDat() {
    const firstVatTu = tronGoiLapDatData.value[0];
    let dsGiaVatTu = firstVatTu.thongTinGias[firstVatTu.thongTinGias.length - 1].dsGia;
    let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
    let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
    let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
    let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
    const firstId =
      tronGoiLapDatOptions.value.length > 0 ? tronGoiLapDatOptions.value[0].value : undefined;
    tronGoiLapDatList.value.push({
      vatTuId: firstId,
      moTa: '',
      soLuong: 1,
      giaNhapMienBac: giaNhapMienBac,
      giaNhapMienNam: giaNhapMienNam,
      giaBanMienBac: giaBanMienBac,
      giaBanMienNam: giaBanMienNam,
      gm: firstVatTu.nhomVatTu.gm,
      thoiGianBaoHanh: 0,
      duocBaoHanh: false,
      duocXem: true,
      trangThai: 1,
    });
  }

  function handleRemoveTronGoiLapDat(index: number) {
    tronGoiLapDatList.value.splice(index, 1);
  }

  function handleTamPinChange(value: number, index: number) {
    // Xử lý khi thay đổi Tam Pin (nếu cần)
    for (const vatTu of tamPinData.value) {
      if (vatTu.id === value) {
        let dsGiaVatTu = vatTu.thongTinGias[vatTu.thongTinGias.length - 1].dsGia;
        let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
        let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
        let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
        let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
        tamPinList.value[index].giaNhapMienBac = giaNhapMienBac;
        tamPinList.value[index].giaNhapMienNam = giaNhapMienNam;
        tamPinList.value[index].giaBanMienBac = giaBanMienBac;
        tamPinList.value[index].giaBanMienNam = giaBanMienNam;
        tamPinList.value[index].gm = vatTu.nhomVatTu.gm;
        formState.congSuatHeThong =
          vatTu.duLieuRieng.cong_suat.giaTri * tamPinList.value[index].soLuong;
        break;
      }
    }
  }

  function handleChangeSoLuongTamPin(idVatTu: number, index: number, value: number) {
    for (const vatTu of tamPinData.value) {
      if (vatTu.id === idVatTu) {
        formState.congSuatHeThong =
          vatTu.duLieuRieng.cong_suat.giaTri * tamPinList.value[index].soLuong;
      }
    }

    for (const vatTuTronGoi of heKhungNhomList.value) {
      let vatTuFinding = getVatTuByIdInList(vatTuTronGoi.vatTuId, heKhungNhomData.value);
      if (vatTuFinding) {
        let soLuongCal = 0;
        if (vatTuFinding.ma.includes('kep_bien')) {
          soLuongCal = ceil(tamPinList.value[0].soLuong / 5) * 4 + 4;
        }
        if (vatTuFinding.ma.includes('kep_giua')) {
          soLuongCal = ceil(tamPinList.value[0].soLuong / 5) * 8 + 4;
        }
        if (vatTuFinding.ma.includes('full_rail')) {
          soLuongCal = ceil((tamPinList.value[0].soLuong * 1.2 * 2) / 4);
        }
        if (vatTuFinding.ma.includes('thanh_noi')) {
          soLuongCal = ceil((tamPinList.value[0].soLuong * 1.2 * 2) / 4) * 2 + 4;
        }
        if (vatTuFinding.ma.includes('chan_l')) {
          soLuongCal = ceil((tamPinList.value[0].soLuong * 1.2 * 2) / 4) * 6;
        }
        if (vatTuFinding.ma.includes('kep_tiep_dia')) {
          soLuongCal = ceil(tamPinList.value[0].soLuong / 5) + 1;
        }
        if (vatTuFinding.ma.includes('la_tiep_dia')) {
          soLuongCal = (ceil(tamPinList.value[0].soLuong / 5) * 8 + 4) / 2;
        }
        if (vatTuFinding.ma.includes('kep_day_dien')) {
          soLuongCal = tamPinList.value[0].soLuong * 4;
        }
        let dsGiaVatTu = vatTuFinding.thongTinGias[vatTuFinding.thongTinGias.length - 1].dsGia;
        let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
        let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
        let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
        let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
        heKhungNhomList.value[index].giaNhapMienBac = giaNhapMienBac;
        heKhungNhomList.value[index].giaNhapMienNam = giaNhapMienNam;
        heKhungNhomList.value[index].giaBanMienBac = giaBanMienBac;
        heKhungNhomList.value[index].giaBanMienNam = giaBanMienNam;
        heKhungNhomList.value[index].gm = vatTuFinding.nhomVatTu.gm;
        heKhungNhomList.value[index].soLuong = soLuongCal;
      } else {
        continue;
      }
    }
  }

  function getVatTuByIdInList(
    id: number | null | undefined,
    data_list: VatTuDto[],
  ): VatTuDto | null {
    for (const vatTu of data_list) {
      if (vatTu.id === id) {
        return vatTu;
      }
    }
    return null;
  }

  function handleBienTanChange(value: number, index: number) {
    // Xử lý khi thay đổi Biến Tần (nếu cần)
    for (const vatTu of bienTanData.value) {
      if (vatTu.id === value) {
        let dsGiaVatTu = vatTu.thongTinGias[vatTu.thongTinGias.length - 1].dsGia;
        let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
        let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
        let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
        let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
        bienTanList.value[index].giaNhapMienBac = giaNhapMienBac;
        bienTanList.value[index].giaNhapMienNam = giaNhapMienNam;
        bienTanList.value[index].giaBanMienBac = giaBanMienBac;
        bienTanList.value[index].giaBanMienNam = giaBanMienNam;
        bienTanList.value[index].gm = vatTu.nhomVatTu.gm;
        break;
      }
    }
  }

  function handlePinLuuTruChange(value: number, index: number) {
    // Xử lý khi thay đổi Pin Lưu Trữ (nếu cần)
    for (const vatTu of pinLuuTruData.value) {
      if (vatTu.id === value) {
        let dsGiaVatTu = vatTu.thongTinGias[vatTu.thongTinGias.length - 1].dsGia;
        let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
        let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
        let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
        let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
        pinLuuTruList.value[index].giaNhapMienBac = giaNhapMienBac;
        pinLuuTruList.value[index].giaNhapMienNam = giaNhapMienNam;
        pinLuuTruList.value[index].giaBanMienBac = giaBanMienBac;
        pinLuuTruList.value[index].giaBanMienNam = giaBanMienNam;
        pinLuuTruList.value[index].gm = vatTu.nhomVatTu.gm;
        break;
      }
    }
  }

  function handleHeKhungNhomChange(value: number, index: number) {
    // Xử lý khi thay đổi Hệ Khung Nhôm (nếu cần)

    for (const vatTu of heKhungNhomData.value) {
      if (vatTu.id === value) {
        let soLuongCal = 0;
        if (vatTu.ma.includes('kep_bien')) {
          soLuongCal = ceil(tamPinList.value[0].soLuong / 5) * 4 + 4;
        }
        if (vatTu.ma.includes('kep_giua')) {
          soLuongCal = ceil(tamPinList.value[0].soLuong / 5) * 8 + 4;
        }
        if (vatTu.ma.includes('full_rail')) {
          soLuongCal = ceil((tamPinList.value[0].soLuong * 1.2 * 2) / 4);
        }
        if (vatTu.ma.includes('thanh_noi')) {
          soLuongCal = ceil((tamPinList.value[0].soLuong * 1.2 * 2) / 4) * 2 + 4;
        }
        if (vatTu.ma.includes('chan_l')) {
          soLuongCal = ceil((tamPinList.value[0].soLuong * 1.2 * 2) / 4) * 6;
        }
        if (vatTu.ma.includes('kep_tiep_dia')) {
          soLuongCal = ceil(tamPinList.value[0].soLuong / 5) + 1;
        }
        if (vatTu.ma.includes('la_tiep_dia')) {
          soLuongCal = (ceil(tamPinList.value[0].soLuong / 5) * 8 + 4) / 2;
        }
        if (vatTu.ma.includes('kep_day_dien')) {
          soLuongCal = tamPinList.value[0].soLuong * 4;
        }
        let dsGiaVatTu = vatTu.thongTinGias[vatTu.thongTinGias.length - 1].dsGia;
        let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
        let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
        let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
        let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
        heKhungNhomList.value[index].giaNhapMienBac = giaNhapMienBac;
        heKhungNhomList.value[index].giaNhapMienNam = giaNhapMienNam;
        heKhungNhomList.value[index].giaBanMienBac = giaBanMienBac;
        heKhungNhomList.value[index].giaBanMienNam = giaBanMienNam;
        heKhungNhomList.value[index].gm = vatTu.nhomVatTu.gm;
        heKhungNhomList.value[index].soLuong = soLuongCal;
        break;
      }
    }
  }

  function handleHeDayDienChange(value: number, index: number) {
    for (const vatTu of heDayDienData.value) {
      let dsGiaVatTu = vatTu.thongTinGias[vatTu.thongTinGias.length - 1].dsGia;
      let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
      let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
      let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
      let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
      heDayDienList.value[index].giaNhapMienBac = giaNhapMienBac;
      heDayDienList.value[index].giaNhapMienNam = giaNhapMienNam;
      heDayDienList.value[index].giaBanMienBac = giaBanMienBac;
      heDayDienList.value[index].giaBanMienNam = giaBanMienNam;
      heDayDienList.value[index].gm = vatTu.nhomVatTu.gm;
    }
  }

  function handleTuDienChange(value: number, index: number) {
    for (const vatTu of tuDienData.value) {
      let dsGiaVatTu = vatTu.thongTinGias[vatTu.thongTinGias.length - 1].dsGia;
      let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
      let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
      let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
      let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
      tuDienList.value[index].giaNhapMienBac = giaNhapMienBac;
      tuDienList.value[index].giaNhapMienNam = giaNhapMienNam;
      tuDienList.value[index].giaBanMienBac = giaBanMienBac;
      tuDienList.value[index].giaBanMienNam = giaBanMienNam;
      tuDienList.value[index].gm = vatTu.nhomVatTu.gm;
    }
  }

  function handleHeTiepDiaChange(value: number, index: number) {
    for (const vatTu of heTiepDiaData.value) {
      let dsGiaVatTu = vatTu.thongTinGias[vatTu.thongTinGias.length - 1].dsGia;
      let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
      let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
      let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
      let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
      heTiepDiaList.value[index].giaNhapMienBac = giaNhapMienBac;
      heTiepDiaList.value[index].giaNhapMienNam = giaNhapMienNam;
      heTiepDiaList.value[index].giaBanMienBac = giaBanMienBac;
      heTiepDiaList.value[index].giaBanMienNam = giaBanMienNam;
      heTiepDiaList.value[index].gm = vatTu.nhomVatTu.gm;
    }
  }

  function handleTronGoiLapDatChange(value: number, index: number) {
    for (const vatTu of tronGoiLapDatData.value) {
      let dsGiaVatTu = vatTu.thongTinGias[vatTu.thongTinGias.length - 1].dsGia;
      let giaNhapMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaNhap || 0;
      let giaNhapMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaNhap || 0;
      let giaBanMienBac = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HN')?.giaBan || 0;
      let giaBanMienNam = dsGiaVatTu.find((gia: GiaInfo) => gia.maCoSo === 'HCM')?.giaBan || 0;
      tronGoiLapDatList.value[index].giaNhapMienBac = giaNhapMienBac;
      tronGoiLapDatList.value[index].giaNhapMienNam = giaNhapMienNam;
      tronGoiLapDatList.value[index].giaBanMienBac = giaBanMienBac;
      tronGoiLapDatList.value[index].giaBanMienNam = giaBanMienNam;
      tronGoiLapDatList.value[index].gm = vatTu.nhomVatTu.gm;
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
  }

  function tinhTongGia() {
    let tongGiaMienBac = 0;
    let tongGiaMienNam = 0;
    const allVatTu: VatTuItem[] = [
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
      if(vatTu.giaBanMienBac){
        tongGiaMienBac += (vatTu.giaBanMienBac || 0) * (vatTu.soLuong || 0);
      }
      if(vatTu.giaBanMienNam){
        tongGiaMienNam += (vatTu.giaBanMienNam || 0) * (vatTu.soLuong || 0);
      } else {
        tongGiaMienNam += (vatTu.giaBanMienBac || 0) * (vatTu.soLuong || 0);
      }
    }
    formState.tongGiaMienBac = tongGiaMienBac;
    formState.tongGiaMienNam = tongGiaMienNam;
  }

  async function handleSubmit() {
    try {
      await formRef.value?.validate();
      loading.value = true;
      if (tronGoiCoSosList.value.length === 0) {
        message.error('Vui lòng thêm ít nhất một cơ sở');
        return;
      }
      for (let i = 0; i < tronGoiCoSosList.value.length; i++) {
        const coSo = tronGoiCoSosList.value[i];
        if (!coSo.coSoId) {
          message.error(`Vui lòng chọn cơ sở cho thông tin ${i + 1}`);
          return;
        }
      }
      const allVatTu: VatTuItem[] = [
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
        const vatTu = allVatTu[i];
        if (!vatTu.vatTuId) {
          message.error(`Vui lòng chọn vật tư cho thông tin ${i + 1}`);
          return;
        }
      }
      const submitData = {
        ...formState,
        tronGoiCoSos: tronGoiCoSosList.value,
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
      const file = fileList.value[0]?.originFileObj || null;
      const result = await createTronGoi(submitData, file);
      if (result.status === 200 || result.status === 201) {
        message.success('Tạo mới thành công');
        closeModal();
        emit('success');
      } else {
        message.error(result.message || 'Có lỗi xảy ra');
      }
    } catch (error: any) {
      console.error('Submit error:', error);
      if (error.errorFields) {
        message.error('Vui lòng kiểm tra lại thông tin');
      } else {
        message.error(error?.message || 'Có lỗi xảy ra khi lưu dữ liệu');
      }
    } finally {
      loading.value = false;
    }
  }
</script>

<style lang="less" scoped>
  .co-so-item,
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
