const app = angular.module('hoadon-app', []);
app.controller('hoadon-ctrl', function($scope, $http) {

	$scope.initialize = function() {
		$http.get("/rest/hoadon").then(response => {
			$scope.items = response.data;
			$scope.items.hoadon.forEach(item => {// trước khi bỏ vào chuyển đổi kiểu ngày tháng trong sql thành chuỗi
				item.ngaynhap = new Date(item.ngaynhap)
			})
			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			initDataTable($scope.items);
		});
	}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.hoadon, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'mahd' },
				{ data: 'datve.taikhoan.hovaten' },
				{ data: 'ngaylap' },
				{ data: 'tongtien' },
				{ data: 'trangthai' },
				{ data: 'loaithanhtoan' },
				// Cột mới chứa nút bấm
				{ data: null,
				  defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Chi tiết</button>'}
			],
			columnDefs: [{ "targets": -1, "orderable": false, "searchable": false }],
			"pageLength": 5
		});
		// Thêm sự kiện click vào nút "Cập nhật"
		$('#table2 tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();
			$scope.$apply(function() {
				$scope.edit(data);
			});
		});
	}
	$scope.initialize()
	$scope.edit = function(id) {
		$scope.selectedItem = id;
	}
})