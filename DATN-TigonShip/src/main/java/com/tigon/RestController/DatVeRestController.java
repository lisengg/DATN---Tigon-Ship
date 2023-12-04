package com.tigon.RestController;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tigon.dao.DatVeDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.Tuyen;

@CrossOrigin("*")
@RestController
public class DatVeRestController {
    @Autowired 
    DatVeDAO datVeDAO;
    @Autowired
    TuyenDAO tuyenDAO;


    /* @GetMapping("/rest/datve")
	public Map<String, Object> getAll() {
		Map<String, Object> map = new HashMap<>();
		map.put("tuyen", tuyenDAO.findAll());
		return map;
	} */

    @GetMapping("/rest/datve")
	public List<Tuyen> getAllTuyen() {
		List<Tuyen> list = tuyenDAO.findAll();
        return list;
	}


//    @GetMapping("/rest/datve/theongay/{id}/{date}")
//    public List<Object> thongtin(@PathVariable Integer id, @PathVariable Date date) {
//        return datVeDAO.thongTinDatVe(id,date);
//    }

}