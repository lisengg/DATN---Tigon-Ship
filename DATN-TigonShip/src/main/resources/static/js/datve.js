const app = angular.module('datve-app', []);
app.controller('datve-ctrl', function ($scope, $http) {
    $scope.datvetheongay
    $scope.initialize = function() {
        $http.get("/rest/tuyen").then(response => {
            $scope.items = response.data;
    
            if ($scope.items.tuyen.length > 0 && $scope.items.tuyen[0].idtuyen !== null) {
                $scope.selectedTuyen = $scope.items.tuyen[0].idtuyen;
            }
    
            console.log($scope.items.tuyen);
        });
    }
    
    $scope.initialize()
    $scope.searchByDate = function () {
        var index = $scope.items.tuyen.findIndex(a => a.idtuyen === $scope.selectedTuyen);
        console.log(index+1);
       
        var selectedDate = new Date($scope.selectedDate);
        var currentDate = new Date();
    
        // Kiểm tra xem ngày được chọn có vượt quá ngày hiện tại không
        if (selectedDate > currentDate) {
            $scope.errorMsg = "Ngày được chọn không được vượt quá ngày hiện tại.";
            // Hiển thị modal
            $('#errorMessageModal').modal('show');
            return; // Ngừng thực hiện hàm nếu có lỗi
        }
    
        var formattedDate = $scope.selectedDate;
        console.log("Formatted Date:", formattedDate);
        // Kiểm tra index trước khi sử dụng
        if (index !== -1) {
            var url = `/rest/datve/theongay/${index+ 1}/${formattedDate}`;
            $http.get(url).then(function (response) {
                $scope.datvetheongay = response.data;
                console.log("Datvetheongay:", $scope.datvetheongay);
            }).catch(function (err) {
                console.log("Error", err);
            });
        } else {
            console.log("Chỉ số không hợp lệ.");
        }
    };
    $scope.searchByMaHD = function (maHD) {
        // Lưu trữ dữ liệu ban đầu
        var originalData = angular.copy($scope.datvetheongay);
    
        // Sử dụng filter để lọc các phần tử có MaHD trùng khớp
        var soDauTienCuaMangCon = [];

        for (var i = 0; i < $scope.datvetheongay.length; i++) {
            var soDauTien = $scope.datvetheongay[i][0]; // Lấy số đầu tiên của mảng con
            var filteredItems = $scope.datvetheongay.filter(function (item) {
                return item[0] === maHD;
            });
            soDauTienCuaMangCon.push(soDauTien);
        }

        console.log(soDauTienCuaMangCon);
        var filteredItems = $scope.datvetheongay.filter(function (item) {
            return item[0] === maHD;
        });
    
        // Kiểm tra xem có kết quả nào không
        if (filteredItems.length > 0) {
            // Có kết quả, gán kết quả lọc cho $scope.datvetheongay để hiển thị trên table
            $scope.datvetheongay = [filteredItems[0]]; // Lưu ý: Gán vào một mảng để giữ nguyên cấu trúc
        } else {
            // Không có kết quả, khôi phục dữ liệu ban đầu và hiển thị thông báo
            $scope.datvetheongay = originalData;
            console.log("Không có thông tin nào có MaHD là", maHD);
            // Hiển thị thông báo cho người dùng, ví dụ:
            $scope.errorMsg = "Không có thông tin nào có MaHD là " + maHD;
            // Hiển thị modal hoặc thông báo tùy thuộc vào cách bạn triển khai
            $('#errorMessageModal').modal('show');
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