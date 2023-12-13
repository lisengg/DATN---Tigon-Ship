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
    
       /*  // In kết quả
        console.log("$scope.tongTien:", $scope.tongTien);
        console.log("$scope.loaiVe:", $scope.loaiVe); */

    }
    $scope.deleteByIDDatVe = function(){ // xóa hết vé đặt qua id đặt vé
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
        // Kiểm tra xem ngày được chọn có hợp lệ không (lớn hơn ngày hiện tại ít nhất 3 ngày)
        var minValidDate = new Date();
        minValidDate.setDate(currentDate.getDate() + 3);
        if (selectedDate < minValidDate) {
            $scope.errorMsg = "Vé có được quyền cập nhật phải có thời gian khởi hành từ ngày: "+ minValidDate.getDate() + "-" +(minValidDate.getMonth() + 1) + "-" + minValidDate.getFullYear() + " trở đi.";
          /*   $('#errorMessageModal').modal('show'); */
          alert( $scope.errorMsg );
            return;
        }
        var id = $scope.madatve;
        $http.delete(`/rest/datve/theongay/${id}`).then(resp => {
            alert("Xóa dữ liệu đặt vé thành công!");
        })
        .catch(error => {
            alert("Lỗi xóa dữ liệu!");
            console.log("Error", error);
        });
    };
    $scope.deleteDatGhe = function(id) { // xóa ghế đặt qua id đặt ghế
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
        // Kiểm tra xem ngày được chọn có hợp lệ không (lớn hơn ngày hiện tại ít nhất 3 ngày)
        var minValidDate = new Date();
        minValidDate.setDate(currentDate.getDate() + 3);
        if (selectedDate < minValidDate) {
            $scope.errorMsg = "Vé có được quyền cập nhật phải có thời gian khởi hành từ ngày: "+ minValidDate.getDate() + "-" +(minValidDate.getMonth() + 1) + "-" + minValidDate.getFullYear() + " trở đi.";
          /*   $('#errorMessageModal').modal('show'); */
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
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
        // Kiểm tra xem ngày được chọn có hợp lệ không (lớn hơn ngày hiện tại ít nhất 3 ngày)
        var minValidDate = new Date();
        minValidDate.setDate(currentDate.getDate() + 3);
        if (selectedDate < minValidDate) {
            $scope.errorMsg = "Vé có được quyền cập nhật phải có thời gian khởi hành từ ngày: "+ minValidDate.getDate() + "-" +(minValidDate.getMonth() + 1) + "-" + minValidDate.getFullYear() + " trở đi.";
            alert( $scope.errorMsg );
            return;
        } 
        console.log($scope.loaiVe)
        var loaive = parseInt( $scope.loaiVe)
        var index = $scope.items.tuyen.findIndex(a => a.idtuyen === $scope.selectedTuyen);
        $http.get(`/rest/datve/nguoidicung/${id}/${index + 1}/${loaive}`).then(resp => {
                $scope.tienHK = resp.data;
               /*  $scope.tongTien = parseFloat($scope.tongTien) */;
                if (!isNaN($scope.tongTien)) {
                    $scope.tongTien = $scope.tongTien - $scope.tienHK;
                    console.log("Tiền hành khách đó  " + $scope.tienHK);
                } else {
                    console.error("Lỗi chuyển đổi giá trị tổng tiền thành số");
                }
        }).catch(error => {
            console.error("Lỗi khi lấy thông tin tiền hành khách!");
        });
        $http({
            method: 'PUT',
            url: `/rest/datve/hoadon/update/${$scope.maHdUpdate}`,
            data: { tongtien: $scope.tongTien },
            headers: {'Content-Type': 'application/json'}
        }).then(
            function successCallback(response) {
                console.log("Thành công:" + $scope.tongTien);
            },
            function errorCallback(error) {
                console.log("Lỗi cập nhật: ", error);
            }
        );
        $http.delete(`/rest/datve/nguoidicung/${id}`).then(resp => {
            alert("Xóa dữ liệu người đi cùng thành công!");
            var index = $scope.nguoidicung.findIndex(innerArray => innerArray[0] == id);
            $scope.nguoidicung.splice(index, 1);
        })
        .catch(error => {
            console.log("Lỗi tính tiền: ", error);
        });
       /*  var tongtien = $scope.tongTien;
        console.log("tongtien Cập nhật:" + tongtien) */
       
        
        
    };
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
    $scope.filterDataByIdDatVe = function (iddatve) { // hàm tìm tổng tiền - idloaive
        if ($scope.datvetheongay.length > 0) {
            // Lọc kết quả theo iddatve
            var result = $scope.datvetheongay.filter(function(item) {
                return item[3] === iddatve; // Giả sử số thứ tư trong mảng con là iddatve
            });
    
            // Kiểm tra xem có kết quả nào không
            if (result.length > 0) {
                // Gán giá trị số 10 và 11 từ kết quả đầu tiên
                $scope.tongTien = result[0][10];
                $scope.loaiVe = result[0][11];
                $scope.maHdUpdate = result[0][0];
            } else {
                // Xử lý nếu không có kết quả nào thỏa mãn
                // Ví dụ: Gán giá trị mặc định hoặc làm gì đó khác theo yêu cầu của bạn
                $scope.tongTien = null;
                $scope.loaiVe = null;
                $scope.loaiVe = null;
            }
        }
    };
    
    
    // Kiểm tra xem ngày được chọn có vượt quá ngày hiện tại không
       /*  if (selectedDate > currentDate) {
            $scope.errorMsg = "Ngày được chọn không được vượt quá ngày hiện tại.";
            // Hiển thị modal
            $('#errorMessageModal').modal('show');
            return; // Ngừng thực hiện hàm nếu có lỗi
        } */
    
        /* $http.put(url, item).then(response => {
			var index = $scion(response) {
					$scope.items.lichsu.push(response.data)
				})
				.catch(function(error) {
					consoleope.items.hangtau.findIndex(a => a.idhangtau === item.idhangtau);
			$scope.items.hangtau[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			document.getElementById('check3').checked = true;
			$http.post('/rest/hangtau/lichsu/save', itemlichsu)
				.then(funct.log("Error creating LichSuHangTau", error);
				});
		}).catch(error => {
			console.log("Error", error)
			document.getElementById('check15').checked = true;
		}); */
})