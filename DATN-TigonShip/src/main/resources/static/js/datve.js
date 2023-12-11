const app = angular.module('datve-app', []);
app.controller('datve-ctrl', function ($scope, $http) {
    $scope.datvetheongay
    $scope.datvetheongayHK
    $scope.datghe ="";
    $scope.nguoidicung="";
    $scope.madatve = ""; 
    $scope.initialize = function() {
        $http.get("/rest/tuyen").then(response => {
            $scope.items = response.data;
            if ($scope.items.tuyen.length > 0 && $scope.items.tuyen[0].idtuyen !== null) {
                $scope.selectedTuyen = $scope.items.tuyen[0].idtuyen;
            }
        });
    }
    $scope.initialize()
    $scope.searchByDate = function () {
        var index = $scope.items.tuyen.findIndex(a => a.idtuyen === $scope.selectedTuyen);
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
         if (!$scope.selectedDate) {
            $scope.errorMsg = "Vui lòng chọn ngày";
            $('#errorMessageModal').modal('show');
            return;
        }
        // Kiểm tra xem ngày được chọn có vượt quá ngày hiện tại không
       /*  if (selectedDate > currentDate) {
            $scope.errorMsg = "Ngày được chọn không được vượt quá ngày hiện tại.";
            // Hiển thị modal
            $('#errorMessageModal').modal('show');
            return; // Ngừng thực hiện hàm nếu có lỗi
        } */
        var formattedDate = $scope.selectedDate;
        if (index !== -1) {
            var url1 = `/rest/datve/theongay/${index+ 1}/${formattedDate}`;/* TÀI KHOẢN */
            $http.get(url1).then(function (response) {
                $scope.datvetheongay = response.data;
            }).catch(function (err) {
                console.log("Error", err);
            });

            var url2 = `/rest/datve/theongayhk/${index+ 1}/${formattedDate}`;/* HÀNH KHÁCH */
            $http.get(url2).then(function (response) {
                $scope.datvetheongayHK = response.data;
                console.log("DatvetheongayHK:", $scope.datvetheongayHK);
            }).catch(function (err) {
                console.log("Error", err);
            });
        } else {
            console.log("Chỉ số không hợp lệ.");
        }
    };
    $scope.searchByID = function (iddatve) {
        $scope.madatve = iddatve;
       /*  console.log("mã đặt vé ở đặt ghế"+ $scope.madatve); */
        var url1 = `/rest/datve/datghe/${iddatve}`;
        $http.get(url1).then(function (response) {
            $scope.datghe = response.data;
            console.log("$scope.datghe:", $scope.datghe);
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
    }
    $scope.deleteByIDDatVe = function(){
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
    
        // Kiểm tra xem ngày được chọn có hợp lệ không (lớn hơn ngày hiện tại ít nhất 3 ngày)
        var minValidDate = new Date();
        minValidDate.setDate(currentDate.getDate() + 3);
    
        if (selectedDate < minValidDate) {
            $scope.errorMsg = "Ngày được chọn phải từ ngày " +  + minValidDate.getDate() + "-" +(minValidDate.getMonth() + 1) + "-" + minValidDate.getFullYear() + " trở đi.";
            // Hiển thị modal
            $('#errorMessageModal').modal('show');
            return; // Ngừng thực hiện hàm nếu có lỗi
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
    $scope.deleteDatGhe = function(id) {
        $http.delete(`/rest/datve/datghe/${id}`).then(resp => {
            alert("Xóa dữ liệu đặt ghế thành công!");
            var index = $scope.datghe.findIndex(innerArray => innerArray[0] == id);
            $scope.datghe.splice(index, 1);
        }).catch(error => {
            alert("Lỗi xóa dữ liệu!");
            console.log("Error", error);
        });
    };
    $scope.deleteNguoiDiCung = function(id){
        $http.delete(`/rest/datve/nguoidicung/${id}`).then(resp => {
            alert("Xóa dữ liệu người đi cùng thành công!");
            var index = $scope.nguoidicung.findIndex(innerArray => innerArray[0] == id);
            $scope.nguoidicung.splice(index, 1);
        })
        .catch(error => {
            alert("Lỗi xóa dữ liệu!");
            console.log("Error", error);
        });
    };
    $scope.check = function() { // kiểm tra số ghế vs người đặt ghế
        if ($scope.nguoidicung.length + 1 === $scope.datghe.length) {
            console.log("đúng")
            return true;
        } 
        else if  ($scope.nguoidicung.length + 1 >= $scope.datghe.length) {
          /*   $scope.errorMsg = "Lỗi:Số hành khách nhiều hơn số ghế đặt";
            $('#errorMessageModal').modal('show'); */
            alert("Lỗi: Số lượng hành khách vượt quá số lượng người đi cùng!");
            return false ;
        }
        else if  ($scope.nguoidicung.length + 1 <= $scope.datghe.length) {
            alert("Lỗi: Số lượng ghế ngồi vượt quá số lượng hành khách!");
            return false ;
        }
    };
    $scope.checkAndClose = function() {
        if ($scope.check()) {
            $('#modal').modal('hide');
        } else {
            $('#modal').modal('show');
        }
    };

    
    function initDataTable(data) {
        var table = $('#table2').DataTable({
            data: data, // Sử dụng dữ liệu từ server
            columns: [
                { data: 'MAHD' },
                { data: 'NGAYDAT' },
                { data: 'NGAYDI' },
                { data: 'NGAYVE' },
                { data: 'TRANGTHAI' },
                { data: 'HOVATEN' },
                { data: 'sdt' },
                { data: 'SOGHE' },
                { data: 'TENGHE' },
                { data: 'GIOXUATPHAT' },
                { data: 'TENTAU' },
                { data: 'TENTUYEN' },
                {
                    data: null,
                    defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'
                }
            ],
            columnDefs: [{ "targets": -1, "orderable": false, "searchable": false }],
            "pageLength": 5
        });
    
        // Thêm sự kiện click vào nút "Cập nhật"
        $('#table2 tbody').on('click', 'button', function () {
            var data = table.row($(this).parents('tr')).data();
            $scope.$apply(function () {
                $scope.edit(data);
            });
        });
    }
    
    
    
    
    
   
    
    

  
})