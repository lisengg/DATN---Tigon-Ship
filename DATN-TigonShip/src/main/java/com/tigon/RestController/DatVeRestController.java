package com.tigon.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.DatVeDAO;
import com.tigon.model.DatVe;

@CrossOrigin("*")
@RestController
public class DatVeRestController {
//    @Autowired
//    HanhKhachDAO hanhKhachDAO;
    @Autowired 
    DatVeDAO datVeDAO;
//    @Autowired
//    LoaiHanhKhachDAO loaiHanhKhachDAO;
//    @Autowired 
//    LoaiVeDAO loaiVeDAO;
//    @Autowired 
//    NguoiDiCungDAO nguoiDiCungDAO;

    @GetMapping("/rest/datve")
    public List<DatVe> getAllDatVe() {
        List<DatVe> list = datVeDAO.findAll();
        
        return list;
    }
//    @GetMapping("/rest/datve/{id}") // lấy danh sách ghế ngồi theo id tàu
//    public List<NguoiDiCung> getAll(@PathVariable("id") Integer id) {
//        return nguoiDiCungDAO.ListNguoiDiCungByiddatve(id);
//    }

}