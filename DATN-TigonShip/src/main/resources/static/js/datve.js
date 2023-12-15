const app = angular.module('datve-app', []);
app.controller('datve-ctrl', function ($scope, $http) {
    $scope.datvetheongay
    $scope.datvetheongayHK
    $scope.datghe ="";
    $scope.nguoidicung="";
    $scope.madatve = ""; 
    $scope.originalData="";
    $scope.search = false
    $scope.maHD = false
    $scope.tongTien="";
    $scope.loaiVe="";
    $scope.tuyen="";
    $scope.xoaNguoi=true;
    $scope.xoaGhe=true;
    $scope.maHdUpdate="";
    $scope.ngaydi ="";
    $scope.tuyen ="";
    $scope.tiencon
    $scope.tienHK= "";

    $scope.initialize = function() {
        $http.get("/rest/tuyen").then(response => {
            $scope.items = response.data;
            if ($scope.items.tuyen.length > 0 && $scope.items.tuyen[0].idtuyen !== null) {
                $scope.selectedTuyen = $scope.items.tuyen[0].idtuyen;
            }
        });

        $http.get("/rest/datve/hanhkhach").then(response => { /*Tất cả thông tin đặt vé của hành khách */
            $scope.datvetheongayHK = response.data;
           
        });
        $http.get("/rest/datve/taikhoan").then(response => { /*Tất cả thông tin đặt vé của tài khoản */
            $scope.datvetheongay = response.data;
            console.log($scope.datvetheongay)
        });
    }
    $scope.initialize()
    $scope.reset = function () {
        $scope.search = false
        $scope.maHD = false
        $scope.selectedDate = null;
        $scope.datvetheongay = null
        $scope.initialize();
      
    }

    $scope.searchByDate = function () { // tìm kiếm theo tuyến + ngày khởi hành
        var index = $scope.items.tuyen.findIndex(a => a.idtuyen === $scope.selectedTuyen);
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
         if (!$scope.selectedDate) {
            $scope.errorMsg = "Vui lòng chọn ngày";
            $('#errorMessageModal').modal('show');
            return;
        }
        var formattedDate = $scope.selectedDate;
        if (index !== -1) {
            $scope.searchMaHD = null
            $scope.search = true;
            $scope.maHD = true;
            var url1 = `/rest/datve/theongay/${index+ 1}/${formattedDate}`;/* TÀI KHOẢN */
            $http.get(url1).then(function (response) {
                $scope.datvetheongay = response.data;
                $scope.originalDataTK = angular.copy($scope.datvetheongay);
                console.log($scope.datvetheongay)
            }).catch(function (err) {
                
                console.log("Error", err);
            });

            var url2 = `/rest/datve/theongayhk/${index+ 1}/${formattedDate}`;/* HÀNH KHÁCH */
            $http.get(url2).then(function (response) {
                $scope.datvetheongayHK = response.data;
                $scope.originalDataHK = angular.copy($scope.datvetheongayHK);
            }).catch(function (err) {
                console.log("Error", err);
            });
        } else {
            console.log("Chỉ số không hợp lệ.");
        }
    };
    $scope.searchByMaHD = function(searchMaHD) { // tìm kiếm thông tin TK - HK theo mã hóa đơn
        $scope.datvetheongay = angular.copy($scope.originalDataTK)
        $scope.datvetheongayHK = angular.copy($scope.originalDataHK)
        
        if ($scope.datvetheongay.length > 0) { // kiểm tra xem mảng tài khoản có dữ liệu không ?
            // Lọc kết quả tìm kiếm theo mã hóa đơn
            var result1 = $scope.datvetheongay.filter(function(item) {
                return item[0] === parseInt(searchMaHD); // Giả sử số đầu tiên trong mảng con là mã hóa đơn
            });
            
            $scope.datvetheongay = result1;
        } 
        else if ($scope.datvetheongayHK.length > 0) { // kiểm tra mảng hành khách có dữ liệu không ?
            var result2 = $scope.datvetheongayHK.filter(function(item) {
                return item[0] === parseInt(searchMaHD); // Giả sử số đầu tiên trong mảng con là mã hóa đơn
            });
            console.log(result2);
            $scope.datvetheongay = result2;
        } else {
            alert("Không có kết quả đặt vé của hành hành khách theo mã hóa đơn");
        }
    };
    $scope.searchByID = function (iddatve) { // tìm kiếm người đi cùng + ghế đặt thông qua mã đặt vé
        $scope.madatve = iddatve;
        var url1 = `/rest/datve/datghe/${iddatve}`;
        $http.get(url1).then(function (response) {
            $scope.datghe = response.data;
        }).catch(function (err) {
            console.log("Error", err);
        });
        var url2 = `/rest/datve/nguoidicung/${iddatve}`;
        $http.get(url2).then(function (response) {
            $scope.nguoidicung = response.data;
            console.log("$scope.nguoidicung:", $scope.datghe);
        }).catch(function (err) {
            console.log("Error", err);
        });
        $scope.filterDataByIdDatVe(iddatve);
    }
    $scope.deleteByIDDatVe = function(){ // xóa hết vé đặt qua id đặt vé
        $scope.filterDataByIdDatVe($scope.madatve)// hàm lọc theo mã đặt vé
        console.log($scope.ngaydi)
        var ngaydi = new Date($scope.ngaydi)
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
        // Kiểm tra xem ngày được chọn có hợp lệ không (lớn hơn ngày hiện tại ít nhất 3 ngày)
        var minValidDate = new Date();
        minValidDate.setDate(currentDate.getDate() + 3);
        if (selectedDate < minValidDate || ngaydi < minValidDate) {
            $scope.errorMsg = "Vé có được quyền cập nhật phải có thời gian khởi hành từ ngày: "+ minValidDate.getDate() + "-" +(minValidDate.getMonth() + 1) + "-" + minValidDate.getFullYear() + " trở đi.";74
          alert( $scope.errorMsg );
            return;
        }

        $http.delete(`/rest/datve/theongay/hoadon/${$scope.maHdUpdate}`).then(resp => {             
        })
        var id = $scope.madatve;
        $http.delete(`/rest/datve/theongay/${id}`).then(resp => {
            alert("Xóa dữ liệu đặt vé thành công!");
            var index = $scope.datvetheongay.findIndex(innerArray => innerArray[0] == $scope.maHdUpdate);
            $scope.datvetheongay.splice(index, 1);
            $('#modal').attr('aria-hidden', 'true');
            // Đặt thuộc tính 'style' của modal thành 'display: none' để ẩn modal
            $('#modal').css('display', 'none');
            location.reload();
        })
        .catch(error => {
            alert("Lỗi xóa dữ liệu!");
            console.log("Error", error);
        });
      // Đặt thuộc tính 'aria-hidden' của modal thành 'true' để ẩn modal
       
      
    };
    $scope.deleteDatGhe = function(id) { // xóa ghế đặt qua id đặt ghế
        $scope.filterDataByIdDatVe($scope.madatve)// hàm lọc theo mã đặt vé
        console.log($scope.ngaydi)
        var ngaydi = new Date($scope.ngaydi)
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
        // Kiểm tra xem ngày được chọn có hợp lệ không (lớn hơn ngày hiện tại ít nhất 3 ngày)
        var minValidDate = new Date();
        minValidDate.setDate(currentDate.getDate() + 3);
        if (selectedDate < minValidDate || ngaydi < minValidDate) {
            $scope.errorMsg = "Vé có được quyền cập nhật phải có thời gian khởi hành từ ngày: "+ minValidDate.getDate() + "-" +(minValidDate.getMonth() + 1) + "-" + minValidDate.getFullYear() + " trở đi.";74
          alert( $scope.errorMsg );
            return;
        }
        $http.delete(`/rest/datve/datghe/${id}`).then(resp => {
            alert("Xóa dữ liệu đặt ghế thành công!");
            var index = $scope.datghe.findIndex(innerArray => innerArray[0] == id);
            $scope.datghe.splice(index, 1);
        }).catch(error => {
            alert("Lỗi xóa dữ liệu!");
            console.log("Error", error);
        });
    };
    $scope.deleteNguoiDiCung = function(id){ // xóa người đi cùng qua id người đi cùng
        console.log("ngày đi :" + $scope.ngaydi)
        var ngaydi = new Date($scope.ngaydi)
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
        // Kiểm tra xem ngày được chọn có hợp lệ không (lớn hơn ngày hiện tại ít nhất 3 ngày)
        var minValidDate = new Date();
        minValidDate.setDate(currentDate.getDate() + 3);
        if (selectedDate < minValidDate || ngaydi < minValidDate) {
            $scope.errorMsg = "Vé có được quyền cập nhật phải có thời gian khởi hành từ ngày: "+ minValidDate.getDate() + "-" +(minValidDate.getMonth() + 1) + "-" + minValidDate.getFullYear() + " trở đi.";74
          alert( $scope.errorMsg );
            return;
        }
        console.log($scope.loaiVe)
        var loaive = parseInt( $scope.loaiVe)
        console.log(id)
        var index = $scope.items.tuyen.findIndex(a => a.idtuyen === $scope.selectedTuyen);
        $http.get(`/rest/datve/nguoidicung/${id}/${index + 1}/${loaive}`).then(resp => {
            $scope.tienHK = resp.data;
            console.log("Tổng tiền hành khách đó  " + $scope.tongTien);
            if (!isNaN($scope.tongTien)) {
                $scope.tiencon = $scope.tongTien - $scope.tienHK;
                console.log("Tổng tiền còn lại là :" + $scope.tiencon);
                // Trừ tiền hành khách từ tổng tiền và cập nhật
                $scope.tongTien -= $scope.tienHK;
                updateHoaDon( $scope.tiencon);
            } else {
                console.error("Lỗi chuyển đổi giá trị tổng tiền thành số");
            }
        }).catch(error => {
            console.error("Lỗi khi lấy thông tin tiền hành khách!");
        });
        
        $http.delete(`/rest/datve/nguoidicung/${id}`).then(resp => {
            alert("Xóa dữ liệu người đi cùng thành công!");
            var index = $scope.nguoidicung.findIndex(innerArray => innerArray[0] == id);
            $scope.nguoidicung.splice(index, 1);
            $scope.checkAndClose();
        })
        .catch(error => {
            console.log("Lỗi tính tiền: ", error);
        });    
    };
    function updateHoaDon(newTongTien) {
        $http({
            method: 'PUT',
            url: `/rest/datve/hoadon/update/${$scope.maHdUpdate}`,
            data: newTongTien,
            headers: {'Content-Type': 'application/json'}
        }).then(
            function successCallback(response) {
                console.log("Thành công:" + $scope.tiencon);
            },
            function errorCallback(error) {
                console.log("Lỗi cập nhật: ", error);
            }
        ).catch(error => {
            console.error("Lỗi Cập Nhật!");
        });
    }
    $scope.check = function() { // kiểm tra số ghế vs người đặt ghế
        if ($scope.nguoidicung.length + 1 === $scope.datghe.length) {
            return true;
        } 
        else if  ($scope.nguoidicung.length + 1 >= $scope.datghe.length) {
            alert("Lỗi: Số lượng hành khách vượt qua số lượng ghế ngồi!");
            return false ;
        }
        else if  ($scope.nguoidicung.length + 1 <= $scope.datghe.length) {
            alert("Lỗi: Số lượng ghế ngồi vượt quá số lượng hành khách!");
            return false ;
        }
    };
    $scope.checkAndClose = function() { // hàm check đúng thì đóng modal và ngược lại
        if ($scope.check()) {
            $('#modal').modal('hide');
        } else {
            $('#modal').modal('show');
        }
    };
    $scope.filterDataByIdDatVe = function (iddatve) { // hàm tìm tổng tiền - idloaive - ngày đi - mã hóa đơn thông qua iddatve
        if ($scope.datvetheongay.length > 0) {
            var result = $scope.datvetheongay.filter(function(item) {
                return item[3] === iddatve; // Giả sử số thứ tư trong mảng con là iddatve
            });  
            if (result.length > 0) {
                $scope.tongTien = result[0][10];
                $scope.loaiVe = result[0][11];
                $scope.maHdUpdate = result[0][0];
                $scope.ngaydi = result[0][2];
                $scope.tentuyen = result[0][13];
                $scope.ngayDat = result[0][12]
                
             
               
            } else {
                $scope.tongTien = null;
                $scope.loaiVe = null;
                $scope.loaiVe = null;
                $scope.ngaydi = null;
            }
        }
    };
    
})