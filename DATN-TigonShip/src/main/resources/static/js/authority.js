const app = angular.module('authority1-app', []);
app.controller('authority1-ctrl', function($scope, $http) {
	$scope.form = {};
	$scope.initialize = function() {
		$http.get("/rest/authority1").then(response => {
			$scope.items = response.data;
			$scope.put = false;
			$scope.post = true;
			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			initDataTable($scope.items);
		})
	}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.taikhoan, // Sử dụng dữ liệu từ biến items
			columns: [
				{ data: 'idtaikhoan' },
				{ data: 'hovaten' },
				{ data: 'email' },
				{ data: 'vaitro' },
				{ data: 'cccd' },
				{ data: 'sdt' },
				{ data: 'diachi' },
				{ data: 'quoctich' },
				{
					data: 'ngaysinh',
					render: function(data, type, full, meta) {
						if (type === 'display') {
							// Định dạng ngày theo dd/MM/yyyy
							return moment(data).format('DD/MM/YYYY'); // Sử dụng thư viện Moment.js hoặc thư viện khác tương tự nếu cần
						}
						return data; // Trả về dữ liệu gốc cho các loại khác
					}
				},
				{ data: 'matkhau' },
				{ data: 'loaihk.loaihk' },
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
		});
	}

	$scope.initialize();
	$scope.index_of = function(id) {
		return $scope.items.findIndex(q => q.idtaikhoan == id);
	}
	//Hiển thị lên form
	$scope.edit = function(id) {
		$scope.originalData = angular.copy(id); // Lưu trữ dữ liệu ban đầu
		$scope.form = angular.copy(id);
		$scope.put = true;
		$scope.post = false;
	}
	//Xóa form
	$scope.reset = function() {
		$scope.form = {
			vaitro: 'STAFF',
			quoctich: 'Việt Nam',
			matkhau: '123',
			loaihk: $scope.items.loaihk.find(item => item.loaihk === 'Người lớn'),
		};
		$scope.post = true;
		$scope.put = false;
	}
	// Hàm kiểm tra ngày sinh
	function isDateOfBirthValid(dateString) {
		// Chuyển đổi chuỗi ngày tháng nhập vào thành đối tượng Date
		var inputDate = new Date(dateString);

		// Kiểm tra xem ngày sinh có hợp lệ hay không
		var currentDate = new Date();
		var minDate = new Date(currentDate.getFullYear() - 18, currentDate.getMonth(), currentDate.getDate());

		return inputDate <= minDate;
	}
	//Thêm nhân viên mới
	$scope.save = function() {
		// Cập nhật giá trị city, district, và ward từ các trường HTML
		$scope.form.city = $("#city option:selected").text();
		$scope.form.district = $("#district option:selected").text();
		$scope.form.ward = $("#ward option:selected").text();
		console.log($scope.form); // Kiểm tra giá trị của $scope.form

		var index = $scope.items.loaihk.findIndex(a => a.idloaihk === $scope.form.loaihk.idloaihk)
		var item = {
			"hovaten": $scope.form.hovaten,
			"sdt": $scope.form.sdt,
			"matkhau": $scope.form.matkhau,
			"ngaysinh": $scope.form.ngaysinh,
			"email": $scope.form.email,
			"cccd": $scope.form.cccd,
			"vaitro": $scope.form.vaitro,
			"quoctich": $scope.form.quoctich,
			"diachi": $scope.form.diachi,
			"city": $scope.form.city,
			"district": $scope.form.district,
			"ward": $scope.form.ward,
			"diaChi": $scope.form.diaChi,
			"loaihk": $scope.items.loaihk[index]
		}
		var url = `/rest/authority1/save`;
		// Thêm dữ liệu Tỉnh/Thành phố, Quận/Huyện, và Phường/Xã vào địa chỉ
		item.diachi = $scope.form.diaChi + ', ' + $scope.form.ward + ', ' + $scope.form.district + ', ' + $scope.form.city;
		// Kiểm tra tên không được để trống
		if (!item.hovaten) {
			document.getElementById('check5').checked = true;
			return;
		}
		// Kiểm tra ngày sinh không được để trống
		if (!item.ngaysinh) {
			document.getElementById('check6').checked = true;
			return;
		}
		if (!isDateOfBirthValid(item.ngaysinh)) {
			document.getElementById('check19').checked = true;
			return;
		}
		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.sdt) {
			document.getElementById('check7').checked = true;
			return;
		}
		// Kiểm tra số điện thoại có đúng định dạng hay không
		var phonePattern = /^(0[1-9][0-9]{8})$/; // Định dạng số điện thoại ở Việt Nam
		if (!phonePattern.test($scope.form.sdt)) {
			document.getElementById('check9').checked = true;
			$scope.form.sdt = '';
			return;
		}
		// Kiểm tra email không được để trống
		if (!$scope.form.email) {
			document.getElementById('check16').checked = true;
			return;
		}
		// Kiểm tra email có đúng định dạng hay không
		var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		if (!emailPattern.test($scope.form.email)) {
			document.getElementById('check11').checked = true;
			return;
		}
		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.cccd) {
			document.getElementById('check17').checked = true;
			return;
		}

		if (!$scope.form.city || !$scope.form.district || !$scope.form.ward || !$scope.form.diaChi) {
			document.getElementById('check8').checked = true;
			return;
		}
		var cccd = $scope.items.taikhoan.some(a => a.idtaikhoan !== item.idtaikhoan && a.cccd === item.cccd);
		diachi = $scope.items.taikhoan.some(a => a.idtaikhoan !== item.idtaikhoan && a.diachi.toLowerCase() === item.diachi.toLowerCase());
		var email = $scope.items.taikhoan.some(a => a.idtaikhoan !== item.idtaikhoan && a.email.toLowerCase() === item.email.toLowerCase());
		var sdt = $scope.items.taikhoan.some(a => a.idtaikhoan !== item.idtaikhoan && a.sdt === item.sdt);
		if (cccd) {
			document.getElementById('check18').checked = true;
			return; // Ngăn cập nhật nếu cccd trùng
		}
		if (diachi) {
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
			$scope.items.taikhoan.push(response.data);
			var table = $('#table2').DataTable();
			table.row.add(response.data).draw();
			document.getElementById('check3').checked = true; // Hiển thị form thành công
			$scope.reset();
		}).catch(error => {
			document.getElementById('check2').checked = true;
			console.log("Error", error)
		})
	}

	//Phân quyền
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/authority1/${item.idtaikhoan}`;
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		//Kiểm tra xem người dùng có đang cố thay đổi từ QUẢN TRỊ thành NGƯỜI DÙNG hoặc NHÂN VIÊN không
		/*if ($scope.items.taikhoan[$scope.index_of(item.idtaikhoan)].vaitro === 'ADMIN' &&
			(item.vaitro === 'KHACHHANG' || item.vaitro === 'STAFF')) {
			document.getElementById('check4').checked = true;
			return;
		}*/
		if ($scope.originalData.vaitro === 'ADMIN') {
			// Nếu là ADMIN, kiểm tra xem có thay đổi vai trò không
			if (item.vaitro !== 'ADMIN') {
				// Hiển thị thông báo hoặc thực hiện các hành động khác tùy thuộc vào yêu cầu
				document.getElementById('check4').checked = true;
				return;
			}
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.taikhoan.findIndex(q => q.idtaikhoan === item.idtaikhoan);
			$scope.items.taikhoan[index] = item;
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

/*$(document).ready(function() {
		// Khởi tạo DataTables
		var table = $('#table2').DataTable({
			data: $scope.items, // Sử dụng dữ liệu từ biến items
			columns: [
				{ data: 'idtaikhoan' },
				{ data: 'hovaten' },
				{ data: 'email' },
				{ data: 'vaitro' },
				{ data: 'cccd' },
				{ data: 'sdt' },
				{ data: 'diachi' },
				{ data: 'quoctich' },
				{
					data: 'ngaysinh',
					render: function(data, type, full, meta) {
						if (type === 'display') {
							// Định dạng ngày theo dd/MM/yyyy
							return moment(data).format('DD/MM/YYYY'); // Sử dụng thư viện Moment.js hoặc thư viện khác tương tự nếu cần
						}
						return data; // Trả về dữ liệu gốc cho các loại khác
					}
				},
				{ data: 'matkhau' },
				{ data: 'loaihk.loaihk' },
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
	}*/