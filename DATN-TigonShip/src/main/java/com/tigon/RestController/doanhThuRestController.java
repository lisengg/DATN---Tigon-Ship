package com.tigon.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<Object> getDoanhThuChiTietList(){
        return hoaDonDAO.doanhThuChiTiet();

    }
}
