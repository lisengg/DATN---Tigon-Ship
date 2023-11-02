package com.tigon.RestController;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.HoaDonDAO;

@CrossOrigin("*")
@RestController
public class doanhThuRestController {
    @Autowired
    HoaDonDAO hoaDonDAO;

    @GetMapping("rest/doanhthu/doanhthu")
    public Integer getDoanhThu() {
        return hoaDonDAO.doanhThu();
    }

    @GetMapping("rest/doanhthu/chitiet")
    public List<Object> getDoanhThuChiTietList() {
        return hoaDonDAO.doanhThuChiTiet();
    }

    @GetMapping("rest/doanhthu/theongay/{date}")
    public List<Object> getDoanhThuTheoNgay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return hoaDonDAO.doanhThuTheoNgay();
       // System.out.println(hoaDonDAO.DoanhThuTheoNgay());
    }
}
