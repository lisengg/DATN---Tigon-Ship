const app = angular.module('taikhoan-app', []);
app.controller('taikhoan-ctrl', function ($scope, $http) {
	$scope.allHK = [];
	$scope.form = {};
	$scope.initialize = function() {
			$http.get("/rest/taikhoan").then(response => {
				$scope.items = response.data;
				// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
				initDataTable($scope.items);
			});
	}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.taikhoan, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'idtaikhoan' },
				{ data: 'hovaten' },
				{ data: 'diachi' },
				{ data: 'email' },
				{ data: 'sdt'},
				// Cột mới chứa nút bấm
				{ data: null,
				  defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Chi tiết</button>'}
			],
			columnDefs: [{"targets": -1, "orderable": false, "searchable": false}],
			"pageLength": 5
		});
		// Thêm sự kiện click vào nút "Cập nhật"
		$('#table2 tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();
			$scope.$apply(function() {
				$scope.showDatVe(data.idtaikhoan);
			});
		});
	}
    $scope.initialize()
 	//hiển thị lên modal
     $scope.showDatVe = function(id) {
        console.log(id); 
        var url = `/rest/taikhoan/${id}`;
        $http.get(url).then(response => {
            $scope.items.datve = response.data;
        }).catch(err => {
            console.log("Error", err)
        })
    } 
    
       //lấy ra tất cả user
	$scope.allTK = function() {
		var url = `/rest/taikhoan/all`;
		$http.get(url).then(response => {
			$scope.allTK = response.data;
		}).catch(err => {
			console.log("Error", err)
		})
	}
	console.log($scope.allTK);
})