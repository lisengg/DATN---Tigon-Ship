const app = angular.module('hangtau-app', []);
app.controller('hangtau-ctrl', function($scope, $http) {
	$scope.form = {};
	$scope.initialize = function() {
		$http.get("/rest/hangtau").then(response => {
			$scope.items = response.data;
			$scope.post = true;
			$scope.put = false;
			$scope.delete = false;
			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			initDataTable($scope.items);
		});
	}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.hangtau, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'idhangtau' },
				{ data: 'tenhangtau' },
				{ data: 'sdt' },
				{ data: 'email' },
				{ data: 'diachi' },
				// Cột mới chứa nút bấm
				{
					data: null,
					defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'
				}
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
		$scope.originalData = angular.copy(id); // Lưu trữ dữ liệu ban đầu
		$scope.form = angular.copy(id);
		$scope.post = false;
		$scope.put = true;
		$scope.delete = true;
	}
	//THÊM HÃNG TÀU
	$scope.save = function() {
		console.log($scope.form); // Kiểm tra giá trị của $scope.form
		/*var item = angular.copy($scope.form);*/
		var item = {
			"tenhangtau": $scope.form.tenhangtau,
			"diachi": $scope.form.diachi,
			"sdt": $scope.form.sdt,
			"email": $scope.form.email
		}
		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.tenhangtau) {
			document.getElementById('check11').checked = true;
			return;
		}
		var url = `/rest/hangtau/save`;

		if (!$scope.form.diachi) {
			document.getElementById('check4').checked = true;
			return;
		}
		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.sdt) {
			document.getElementById('check5').checked = true;
			return;
		}
		// Kiểm tra số điện thoại có đúng định dạng hay không
		var phonePattern = /^(0[1-9][0-9]{8})$/; // Định dạng số điện thoại ở Việt Nam
		if (!phonePattern.test($scope.form.sdt)) {
			document.getElementById('check7').checked = true;
			$scope.form.sdt = '';
			return;
		}
		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.email) {
			document.getElementById('check6').checked = true;
			return;
		}
		// Kiểm tra email có đúng định dạng hay không
		var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		if (!emailPattern.test($scope.form.email)) {
			document.getElementById('check8').checked = true;
			return;
		}
		var tenHangTau = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.tenhangtau.toLowerCase() === item.tenhangtau.toLowerCase());
		var diaChi = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.diachi.toLowerCase() === item.diachi.toLowerCase());
		var email = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.email.toLowerCase() === item.email.toLowerCase());
		var sdt = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.sdt === item.sdt);
		if (tenHangTau) {
			document.getElementById('check9').checked = true;
			return; // Ngăn cập nhật nếu tên hãng tàu trùng
		}
		if (diaChi) {
			document.getElementById('check12').checked = true;
			return; // Ngăn cập nhật nếu địa chỉ trùng
		}
		if (email) {
			document.getElementById('check13').checked = true;
			return; // Ngăn cập nhật nếu email trùng
		}
		if (sdt) {
			document.getElementById('check14').checked = true;
			return; // Ngăn cập nhật nếu số điện thoại trùng
		}
		$http.post(url, item).then(response => {
			$scope.items.hangtau.push(response.data);
			var table = $('#table2').DataTable();
			table.row.add(response.data).draw();
			document.getElementById('check3').checked = true;
			$scope.reset();
		}).catch(error => {
			document.getElementById('check15').checked = true;
			console.log("Error", error)
		})
	}
	//CẬP NHẬT HÃNG TÀU
	$scope.updateSuccess = false;
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/hangtau/${item.idhangtau}`;

		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		if ($scope.updateSuccess) {
			document.getElementById('check10').checked = true;
			return;
		}
		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.tenhangtau) {
			document.getElementById('check11').checked = true;
			return;
		}
		// Kiểm tra địa chỉ không được để trống
		if (!$scope.form.diachi) {
			document.getElementById('check4').checked = true;
			return;
		}
		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.sdt) {
			document.getElementById('check5').checked = true;
			return;
		}
		// Kiểm tra số điện thoại có đúng định dạng hay không
		var phonePattern = /^(0[1-9][0-9]{8})$/; // Định dạng số điện thoại ở Việt Nam
		if (!phonePattern.test($scope.form.sdt)) {
			document.getElementById('check7').checked = true;
			$scope.form.sdt = '';
			return;
		}
		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.email) {
			document.getElementById('check6').checked = true;
			return;
		}
		// Kiểm tra email có đúng định dạng hay không
		var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		if (!emailPattern.test($scope.form.email)) {
			document.getElementById('check8').checked = true; return;
		}
		var tenHangTau = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.tenhangtau.toLowerCase() === item.tenhangtau.toLowerCase());
		var diaChi = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.diachi.toLowerCase() === item.diachi.toLowerCase());
		var email = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.email.toLowerCase() === item.email.toLowerCase());
		var sdt = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.sdt === item.sdt);
		if (tenHangTau) {
			document.getElementById('check9').checked = true;
			return; // Ngăn cập nhật nếu tên hãng tàu trùng
		}
		if (diaChi) {
			document.getElementById('check12').checked = true;
			return; // Ngăn cập nhật nếu địa chỉ trùng
		}
		if (email) {
			document.getElementById('check13').checked = true;
			return; // Ngăn cập nhật nếu email trùng
		}
		if (sdt) {
			document.getElementById('check14').checked = true;
			return; // Ngăn cập nhật nếu số điện thoại trùng
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.hangtau.findIndex(a => a.idhangtau === item.idhangtau);
			$scope.items.hangtau[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			document.getElementById('check3').checked = true;
			$scope.updateSuccess = true; // Đặt trạng thái cập nhật thành công
		}).catch(error => {
			console.log("Error", error)
			document.getElementById('check15').checked = true;
		});
	}
	1//XÓA HÃNG TÀU
	$scope.deleteItem = function(data) {
		var url = `/rest/hangtau/${data.idhangtau}`;
		// Kiểm tra xem hãng tàu đó có đang được sử dụng trong tàu hay không
		var isHangtauUsedInTau = $scope.items.tau.some(function(tau) {
			return tau.hangtau.idhangtau === data.idhangtau;
		});
		if (isHangtauUsedInTau) {
			document.getElementById('check2').checked = true;
			return;
		}
		$http.delete(url).then(response => {
			var index = $scope.items.findIndex(a => a.idhangtau === data.idhangtau);
			$scope.items.splice(index, 1);
			// Cập nhật DataTables
			var table = $('#table2').DataTable();
			table.row(index).remove().draw();
			document.getElementById('check1').checked = true; // Hiển thị form thành công
			document.getElementById('check').checked = false;
		}).catch(error => {
			document.getElementById('check2').checked = true;
			document.getElementById('check').checked = false;
			console.log("Error", error);
		});
	}
})