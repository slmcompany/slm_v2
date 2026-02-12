package com.devmam.slmapiv2.services.impl.utils;

import com.devmam.slmapiv2.services.CalcService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CalcServiceImpl implements CalcService {

    @Override
    public String getRandomActiveCode(Long lenghtOfString) {
        String characters = "0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0;
             i < lenghtOfString; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }

    @Override
    public String genTenKhongDau(String tenString) {
        // Bỏ dấu tiếng Việt (bao gồm cả đ)
        String khongDau = StringUtils.stripAccents(tenString);

        // Chuyển về chữ thường
        khongDau = khongDau.toLowerCase();

        // Thay thế khoảng trắng bằng dấu gạch dưới
        String ma = khongDau.replaceAll("\\s+", "_");

        // Giữ lại chỉ các ký tự a-z, số và gạch dưới
        ma = ma.replaceAll("[^a-z0-9_]", "");

        return ma;
    }
}
