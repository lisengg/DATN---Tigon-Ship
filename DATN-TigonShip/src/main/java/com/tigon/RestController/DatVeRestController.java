package com.tigon.RestController;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.DatGheDAO;
import com.tigon.dao.DatVeDAO;
import com.tigon.dao.HoaDonDAO;
import com.tigon.dao.NguoiDiCungDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.Tuyen;

@CrossOrigin("*")
@RestController
public class DatVeRestController {
    @Autowired
    DatVeDAO datVeDAO;
    @Autowired
    TuyenDAO tuyenDAO;
    @Autowired
    HoaDonDAO hoadonDAO;
    @Autowired
    DatGheDAO datgheDAO;
    @Autowired 
    NguoiDiCungDAO nguoidicungDAO;
    

    /*
     * @GetMapping("/rest/datve")
     * public Map<String, Object> getAll() {
     * Map<String, Object> map = new HashMap<>();
     * map.put("tuyen", tuyenDAO.findAll());
     * return map;}
     */

    @GetMapping("/rest/datve")
    public List<Tuyen> getAllTuyen() { //select tuyến
        List<Tuyen> list = tuyenDAO.findAll();
        return list;
    }

    @GetMapping("/rest/datve/theongay/{id}/{ngay}")//select đặt vé theo ngày Tài Khoản
    public List<Object> thongtin(@PathVariable Integer id, @PathVariable Date ngay) {
        return datVeDAO.thongTinDatVe(id, ngay);
    }
    @GetMapping("/rest/datve/theongayhk/{id}/{ngay}")//select đặt vé theo ngày Hành Khách
    public List<Object> thongtinHK(@PathVariable Integer id, @PathVariable Date ngay) {
        return datVeDAO.thongTinDatVeHK(id, ngay);
    }

    @GetMapping("/rest/datve/datghe/{iddatve}") // selcect đặt ghế
    public List<Object> getDatGheById(@PathVariable Integer iddatve) {
        return datVeDAO.thongTinGheDat(iddatve);
    }

    @GetMapping("/rest/datve/nguoidicung/{iddatve}") // select người đi cùng
    public List<Object> getNguoiDiCungById(@PathVariable Integer iddatve) {
        return datVeDAO.thongTinNguoiDiCung(iddatve);
    }

    @DeleteMapping("/rest/datve/theongay/{id}")// xóa hóa đơn + đặt vé
    public void deleteByIDDatVe(@PathVariable("id") Integer id) {
        hoadonDAO.deleteById(id);
        datVeDAO.deleteById(id);
    }
    @DeleteMapping("/rest/datve/datghe/{id}") // xóa đặt ghế
    public void deleteDatghe(@PathVariable("id") Integer id) {
        datgheDAO.deleteById(id);
    }

    @DeleteMapping("/rest/datve/nguoidicung/{id}") // xóa người đi cùng
    public void deleteNguoiDiCung(@PathVariable("id") Integer id) {
        nguoidicungDAO.deleteById(id);
    }



}