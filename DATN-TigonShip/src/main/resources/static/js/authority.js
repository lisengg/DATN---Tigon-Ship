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
				{ data: null,
				  defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'}
			],
			columnDefs: [{"targets": -1, "orderable": false, "searchable": false}],
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
		// Kiểm tra xem người dùng có đang cố gắng thay đổi vai trò tương tự không
		if (item.quyen === 'USER' && item.quyen === $scope.items[$scope.index_of(item.idhanhkhach)].quyen) {
			alert(`Vai trò của ${item.hovaten} đã là USER. không cần thay đổi thành USER.`);
			return;
		}
		if (item.quyen === 'STAFF' && item.quyen === $scope.items[$scope.index_of(item.idhanhkhach)].quyen) {
			alert(`Vai trò của ${item.hovaten} đã là STAFF. không cần thay đổi thành STAFF.`);
			return;
		}
		if (item.quyen === 'ADMIN' && item.quyen === $scope.items[$scope.index_of(item.idhanhkhach)].quyen) {
			alert(`Vai trò của bạn đã là ADMIN. không cần thay đổi thành ADMIN.`);
			return;
		}
		//Kiểm tra xem người dùng có đang cố thay đổi từ QUẢN TRỊ thành NGƯỜI DÙNG hoặc NHÂN VIÊN không
		if ($scope.items[$scope.index_of(item.idhanhkhach)].quyen === 'ADMIN' &&
			(item.quyen === 'USER' || item.quyen === 'STAFF')) {
			alert(`${item.hovaten} đang là ADMIN và không thể thay đổi vai trò.`);
			return; // Don't proceed with the update
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.findIndex(q => q.idhanhkhach === item.idhanhkhach);
			$scope.items[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			alert("Phân quyền thành công")
		}).catch(error => {
			console.log("Error", error)
			alert("Phân quyền thất bại")
		})
	}

})

