package com.tigon.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.DatVeDAO;
import com.tigon.dao.TaiKhoanDAO;
import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.dao.LoaiVeDAO;
import com.tigon.model.DatVe;
@CrossOrigin("*")
@RestController
public class taiKhoanRestController {
    @Autowired
    TaiKhoanDAO taiKhoanDAO;
    @Autowired 
    DatVeDAO datVeDAO;
    @Autowired
    LoaiHanhKhachDAO loaiHanhKhachDAO;
    @Autowired 
    LoaiVeDAO loaiVeDAO;
//    @Autowired 
//    NguoiDiCungDAO nguoiDiCungDAO;

    @GetMapping("/rest/taikhoan")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("datve", datVeDAO.findAll());
        map.put("taikhoan", taiKhoanDAO.findAll());
        map.put("loaive",loaiVeDAO.findAll());
        return map;
    }
    @GetMapping("/rest/taikhoan/{id}") // lấy danh sách ghế ngồi theo id tàu
    public List<DatVe> getAll(@PathVariable("id") Integer id) {
        return datVeDAO.ListDatVeByIdTaiKhoan(id);
    }


}