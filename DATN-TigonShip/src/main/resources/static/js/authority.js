const app = angular.module('authority1-app', []);
app.controller('authority1-ctrl', function($scope, $http) {
	$scope.form = {};
	$(document).ready(function() {
		// Khởi tạo DataTables
		var table = $('#table2').DataTable({
			data: $scope.items, // Sử dụng dữ liệu từ biến items
			columns: [
				{ data: 'idhanhkhach' },
				{ data: 'hovaten' },
				{ data: 'email' },
				{ data: 'quyen' },
				// Cột mới chứa nút bấm
				{
					data: null,
					defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'
				}
			],
			columnDefs: [{ "targets": -1, "orderable": false, "searchable": false }],
			"pageLength": 5 // Đặt giá trị mặc định là 5 mục hiển thị trên mỗi trang
		});
		// Thêm sự kiện click vào nút "Cập nhật"
		$('#table2 tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();
			$scope.$apply(function() {
				console.log(data)
				$scope.edit(data);
			});
			// data chứa thông tin của hàng được chọn, bạn có thể sử dụng nó ở đây
			// Ví dụ: alert(data.idHANHKHACH);
			// Hoặc gọi hàm edit(data) để hiển thị dữ liệu trong modal
			// edit(data);
		});
	});
	$scope.initialize = function() {
		$http.get("/rest/authority1").then(response => {
			$scope.items = response.data;
			$scope.put = false
			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			var table = $('#table2').DataTable();
			table.clear().rows.add($scope.items).draw();
		})
	}
	//Khởi đầu
	$scope.initialize();
	$scope.index_of = function(id) {
		return $scope.items.findIndex(q => q.idhanhkhach == id);
	}
	//Hiển thị lên form
	$scope.edit = function(id) {
		$scope.originalData = angular.copy(id); // Lưu trữ dữ liệu ban đầu
		$scope.form = angular.copy(id);
		$scope.put = true;
	}
	//Xóa form
	$scope.reset = function() {
		$scope.form = null;
		$scope.searchKeyword = null;
		$scope.put = false;
	}
	//Phân quyền
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/authority1/${item.idhanhkhach}`;
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		//Kiểm tra xem người dùng có đang cố thay đổi từ QUẢN TRỊ thành NGƯỜI DÙNG hoặc NHÂN VIÊN không
		if ($scope.items[$scope.index_of(item.idhanhkhach)].quyen === 'ADMIN' &&
			(item.quyen === 'USER' || item.quyen === 'STAFF')) {
			document.getElementById('check4').checked = true;
			return;
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.findIndex(q => q.idhanhkhach === item.idhanhkhach);
			$scope.items[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			document.getElementById('check3').checked = true;
		}).catch(error => {
			console.log("Error", error)
			document.getElementById('check2').checked = true;
		})
	}

})

