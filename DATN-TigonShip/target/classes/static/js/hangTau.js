const app = angular.module('hangtau-app', []);
app.controller('hangtau-ctrl', function($scope, $http) {
	$scope.form = {}
	$(document).ready(function() {
		// Khởi tạo DataTables
		var table = $('#table2').DataTable({
			data: $scope.items, // Sử dụng dữ liệu từ biến items
			columns: [
				{ data: 'idhangtau' },
				{ data: 'tenhangtau' },
				{ data: 'sdt' },
				{ data: 'email' },
				{ data: 'diachi' },
				// Cột mới chứa nút bấm
				{ data: null,
				  defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'}
			],
			columnDefs: [{"targets": -1, "orderable": false, "searchable": false}],
			"lengthMenu": [1, 2, 3, 4, 5, 6, 10], // Định nghĩa danh sách tùy chọn cho số lượng mục hiển thị
			"pageLength": 3 // Đặt giá trị mặc định là 3 mục hiển thị trên mỗi trang
		});
		// Thêm sự kiện click vào nút "Cập nhật"
		$('#table2 tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();
			$scope.$apply(function() {
				console.log(data)
				$scope.edit(data);
			});
			// data chứa thông tin của hàng được chọn, bạn có thể sử dụng nó ở đây
			// Ví dụ: alert(data.idhangtau);
			// Hoặc gọi hàm edit(data) để hiển thị dữ liệu trong modal
			// edit(data);
		});
		// Để thực hiện phân trang, bạn có thể sử dụng các tuỳ chọn khác của DataTables.
	});
	$scope.initialize = function() {
		$http.get("/rest/hangtau").then(response => {
			$scope.items = response.data;
			$scope.post = true
			$scope.put = false
			$scope.delete = false

			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			var table = $('#table2').DataTable();
			table.clear().rows.add($scope.items).draw();
		})
	}
	$scope.initialize()
	$scope.index_of = function(id) {
		return $scope.items.findIndex(a => a.idhangtau == id);
	}
	//Xóa form
	$scope.reset = function() {
		$scope.form = null;
		$scope.post = true;
		$scope.put = false;
		$scope.delete = false;
	}
	//hiển thị lên modal
	$scope.edit = function(id) {
		$scope.form = angular.copy(id);
		$scope.post = false;
		$scope.put = true;
		$scope.delete = true;
	}
	//THÊM HÃNG TÀU
	$scope.save = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/hangtau/save`;
		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.tenhangtau) {
			alert("Vui lòng nhập tên hãng tàu!");
			return;
		}
		// Kiểm tra địa chỉ không được để trống
		if (!$scope.form.diachi) {
			alert("Vui lòng nhập địa chỉ!");
			return;
		}
		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.sdt) {
			alert("Vui lòng nhập số điện thoại!");
			return;
		}
		// Kiểm tra số điện thoại có đúng định dạng hay không
		var phonePattern = /^(0[1-9][0-9]{8})$/; // Định dạng số điện thoại ở Việt Nam
		if (!phonePattern.test($scope.form.sdt)) {
			alert("Số điện thoại không hợp lệ!");
			$scope.form.sdt = '';
			return;
		}
		// Kiểm tra email có đúng định dạng hay không
		var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		if (!emailPattern.test($scope.form.email)) {
			alert("Vui lòng nhập đúng định dạng email!");
			return;
		}
		var tenHangTau = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.tenhangtau.toLowerCase() === item.tenhangtau.toLowerCase());
		var diaChi = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.diachi.toLowerCase() === item.diachi.toLowerCase());
		var email = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.email.toLowerCase() === item.email.toLowerCase());
		var sdt = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.sdt === item.sdt);
		if (tenHangTau) {
			alert("Tên hãng tàu đã tồn tại! Vui lòng nhập tên khác.");
			return; // Ngăn cập nhật nếu tên hãng tàu trùng
		}
		if (diaChi) {
			alert("Địa chỉ đã tồn tại! Vui lòng nhập khác.");
			return; // Ngăn cập nhật nếu địa chỉ trùng
		}
		if (email) {
			alert("Email đã tồn tại! Vui lòng nhập email khác.");
			return; // Ngăn cập nhật nếu email trùng
		}
		if (sdt) {
			alert("Số điện thoại đã tồn tại! Vui lòng nhập số điện thoại khác.");
			return; // Ngăn cập nhật nếu số điện thoại trùng
		}
		$http.post(url, item).then(response => {
			$scope.items.push(response.data);
			var table = $('#table2').DataTable();
			table.row.add(response.data).draw();
			alert("Thêm hãng tàu mới thành công")
			$scope.reset();
		}).catch(error => {
			alert("Thêm hãng tàu mới thất bại");
			console.log("Error", error)
		})
	}
	//CẬP NHẬT HÃNG TÀU
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/hangtau/${item.idhangtau}`;
		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.tenhangtau) {
			alert("Vui lòng nhập tên hãng tàu!");
			return;
		}
		// Kiểm tra địa chỉ không được để trống
		if (!$scope.form.diachi) {
			alert("Vui lòng nhập địa chỉ!");
			return;
		}
		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.sdt) {
			alert("Vui lòng nhập số điện thoại!");
			return;
		}
		// Kiểm tra số điện thoại có đúng định dạng hay không
		var phonePattern = /^(0[1-9][0-9]{8})$/; // Định dạng số điện thoại ở Việt Nam
		if (!phonePattern.test($scope.form.sdt)) {
			alert("Số điện thoại không hợp lệ!");
			$scope.form.sdt = '';
			return;
		}
		// Kiểm tra email có đúng định dạng hay không
		var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		if (!emailPattern.test($scope.form.email)) {
			alert("Vui lòng nhập đúng định dạng email!");
			return;
		}
		var tenHangTau = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.tenhangtau.toLowerCase() === item.tenhangtau.toLowerCase());
		var diaChi = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.diachi.toLowerCase() === item.diachi.toLowerCase());
		var email = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.email.toLowerCase() === item.email.toLowerCase());
		var sdt = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.sdt === item.sdt);
		if (tenHangTau) {
			alert("Tên hãng tàu đã tồn tại! Vui lòng nhập tên khác.");
			return; // Ngăn cập nhật nếu tên hãng tàu trùng
		}
		if (diaChi) {
			alert("Địa chỉ đã tồn tại! Vui lòng nhập khác.");
			return; // Ngăn cập nhật nếu địa chỉ trùng
		}
		if (email) {
			alert("Email đã tồn tại! Vui lòng nhập email khác.");
			return; // Ngăn cập nhật nếu email trùng
		}
		if (sdt) {
			alert("Số điện thoại đã tồn tại! Vui lòng nhập số điện thoại khác.");
			return; // Ngăn cập nhật nếu số điện thoại trùng
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.findIndex(a => a.idhangtau === item.idhangtau);
			$scope.items[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			$scope.reset();
			alert("Cập nhật hãng tàu thành công")
		}).catch(error => {
			console.log("Error", error)
			alert("Cập nhật hãng tàu thất bại")
		});
	}
	1//XÓA HÃNG TÀU
	$scope.deleteItem = function(data) {
		var confirmation = confirm("Bạn có chắc chắn muốn xóa hãng tàu này?");
		if (confirmation) {
			$http.delete(`/rest/hangtau/${data.idhangtau}`).then(response => {
				var index = $scope.items.findIndex(a => a.idhangtau === data.idhangtau);
				$scope.items.splice(index, 1);
				// Cập nhật DataTables
				var table = $('#table2').DataTable();
				table.row(index).remove().draw();
				$scope.reset();
				alert("Xóa thành công");
			}).catch(error => {
				alert("Hãng tàu đang được sử dụng. Vui lòng không xóa mà hãy chuyển sang trạng thái ẩn hoạt động");
				console.log("Error", error);
			});
		}
	}
})