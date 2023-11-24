package com.tigon.service;

import java.util.List;

import com.tigon.model.GheNgoi;

public class GheNgoiService {
    private List<GheNgoi> danhSachGheNgoi;

    public List<GheNgoi> getDanhSachGheNgoi() {
        return danhSachGheNgoi;
    }
    public void setDanhSachGheNgoi(List<GheNgoi> danhSachGheNgoi) {
        this.danhSachGheNgoi = danhSachGheNgoi;
    }
}
