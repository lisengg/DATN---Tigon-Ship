package com.tigon.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.GheNgoiDAO;
import com.tigon.dao.TauDAO;
import com.tigon.model.GheNgoi;
import com.tigon.model.Tau;

@CrossOrigin("*")
@RestController
public class gheNgoiRestController {
    @Autowired
    GheNgoiDAO gheNgoiDAO;
    @Autowired
    TauDAO tauDAO;


    @GetMapping("/rest/ghengoi/tau") // lấy ra tất cả tàu
    public List<Tau> getTauAll() { 
        List<Tau> list = tauDAO.findAll();
        return list;
    } 
    @GetMapping("/rest/ghengoi1/{idtau}") // lấy danh sách ghế ngồi theo idtau và khoan 1
    public List<GheNgoi> thongTinGheNgoi1ByIdtau(@PathVariable("idtau") Integer idtau) {
        return gheNgoiDAO.gheNgoi1(idtau);
    }

    @GetMapping("/rest/ghengoi2/{idtau}") // lấy danh sách ghế ngồi theo idtau và khoan 2
    public List<GheNgoi> thongTinGheNgoi2ByIdtau(@PathVariable("idtau") Integer idtau) {
        return gheNgoiDAO.gheNgoi2(idtau);
    }

    @GetMapping("/rest/ghengoi/ghengoi/{idghe}")
    public GheNgoi getGheNgoiChiTiet(@PathVariable Integer idghe){
        return gheNgoiDAO.gheNgoiChiTiet(idghe);
    }

    @PutMapping("/rest/ghengoi/update/{idghe}") // cập nhật trạng thái ghê ngồi
    public void capNhatTrangThai(@PathVariable Integer idghe, @RequestBody String trangthai) {
        gheNgoiDAO.updateGheNgoi(trangthai, idghe);
    }  
    /* @GetMapping("/rest/ghengoi")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("tau", tauDAO.findAll());
        map.put("ghengoi", gheNgoiDAO.findAll());
        return map;
    } */
   /*  @GetMapping("/rest/ghengoi/{id}") // lấy danh sách ghế ngồi theo id tàu
    public List<GheNgoi> getAllGheNgoi(@PathVariable("id") Integer id) {
        return gheNgoiDAO.gheNgoiByIdtau(id);
    }

    @PostMapping("/rest/ghengoi/save")
    public GheNgoi save(@RequestBody GheNgoi gheNgoi) {
        return gheNgoiDAO.save(gheNgoi);
    }
    @PutMapping("/rest/ghengoi/{id}")
    public GheNgoi update(@PathVariable("id") Integer id, @RequestBody GheNgoi gheNgoi) {
        return gheNgoiDAO.save(gheNgoi);
    }
    
    @DeleteMapping("/rest/ghengoi/{id}")
    public void delete(@PathVariable("id") Integer id) {
        gheNgoiDAO.deleteById(id);
    } */
}
