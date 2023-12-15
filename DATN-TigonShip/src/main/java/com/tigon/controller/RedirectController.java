package com.tigon.controller;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class RedirectController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String key2 = "Iyz2habzyr7AG8SgvoBCbKwKi3UzlLi3";
    private Mac HmacSHA256;

    public RedirectController() throws Exception  {
        HmacSHA256 = Mac.getInstance("HmacSHA256");
        HmacSHA256.init(new SecretKeySpec(key2.getBytes(), "HmacSHA256"));
    }

    @GetMapping("/redirect-from-zalopay")
    public ResponseEntity redirect(@RequestParam Map<String, String> data) {

        String checksumData = data.get("appid") +"|"+ data.get("apptransid") +"|"+ data.get("pmcid") +"|"+ data.get("bankcode") +"|"+
                data.get("amount") +"|"+ data.get("discountamount") +"|"+ data.get("status");
        byte[] checksumBytes = HmacSHA256.doFinal(checksumData.getBytes());
        String checksum = DatatypeConverter.printHexBinary(checksumBytes).toLowerCase();

        JSONObject result = new JSONObject();
        if (!checksum.equals(data.get("checksum"))) {
            return ResponseEntity.badRequest().body("Bad Request");
        } else {
            // kiểm tra xem đã nhận được callback hay chưa, nếu chưa thì tiến hành gọi API truy vấn trạng thái thanh toán của đơn hàng để lấy kết quả cuối cùng
            return ResponseEntity.ok("OK");
        }
    }
}
