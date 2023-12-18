const app = angular.module('hangtau-app', ['ngSanitize']);
app.controller('hangtau-ctrl', function($scope, $http, $sce) {
	$scope.form = {};
	$scope.initialize = function() {
		$http.get("/rest/hangtau").then(response => {
			$scope.items = response.data;
			$scope.post = true;
			$scope.put = false;
			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			initDataTable($scope.items);
		});
	}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.hangtau, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'tenhangtau' },
				{ data: 'sdt' },
				{ data: 'email' },
				{ data: 'diachi' },
				{ data: 'trangthai' },
				// Cột mới chứa nút bấm
				{
					data: null,
					defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="custom-button"><i class="fas fa-pen"></i></button>'
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
		//hiển thị lên modal
	$scope.edit = function(id) {
		$scope.originalData = angular.copy(id); // Lưu trữ dữ liệu ban đầu
		$scope.form = angular.copy(id);
		$scope.post = false;
		$scope.put = true;
	}
	//Xóa form
	$scope.reset = function() {
		$scope.form = {
			trangthai: 'Đang hoạt động',
		};
		$scope.post = true;
		$scope.put = false;
	}

	//THÊM HÃNG TÀU
	$scope.save = function() {
		// Cập nhật giá trị city, district, và ward từ các trường HTML
		$scope.form.city = $("#city option:selected").text();
		$scope.form.district = $("#district option:selected").text();
		$scope.form.ward = $("#ward option:selected").text();
		console.log($scope.form); // Kiểm tra giá trị của $scope.form
		var item = {
			"tenhangtau": $scope.form.tenhangtau,
			"diachi": $scope.form.diachi,
			"sdt": $scope.form.sdt,
			"email": $scope.form.email,
			"city": $scope.form.city,
			"district": $scope.form.district,
			"ward": $scope.form.ward,
			"diaChi": $scope.form.diaChi,
			"trangthai": $scope.form.trangthai
		}
		// Thêm dữ liệu Tỉnh/Thành phố, Quận/Huyện, và Phường/Xã vào địa chỉ
		item.diachi = $scope.form.diaChi + ', ' + $scope.form.ward + ', ' + $scope.form.district + ', ' + $scope.form.city;
		var url = `/rest/hangtau/save`;
		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.tenhangtau) {
			document.getElementById('check11').checked = true;
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
		diachi = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.diachi.toLowerCase() === item.diachi.toLowerCase());
		var email = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.email.toLowerCase() === item.email.toLowerCase());
		var sdt = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.sdt === item.sdt);
		if (tenHangTau) {
			document.getElementById('check9').checked = true;
			return; // Ngăn cập nhật nếu tên hãng tàu trùng
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
		if (!$scope.form.city || !$scope.form.district || !$scope.form.ward || !$scope.form.diaChi) {
			document.getElementById('check4').checked = true;
			return;
		}
		$http.post(url, item).then(response => {
			$scope.items.hangtau.push(response.data);
			var table = $('#table2').DataTable();
			table.row.add(response.data).draw();
			document.getElementById('check3').checked = true;
			var itemlichsu = {
				"hangtau": response.data,
				"thaotac": "Đã thêm mới hãng tàu có ID : " + response.data.idhangtau,
			}
			$http.post('/rest/hangtau/lichsu/save', itemlichsu)
				.then(function(response) {
					$scope.items.lichsu.push(response.data)
				})
				.catch(function(error) {
					console.log("Error creating LichSuHangTau", error);
				});
			$scope.reset();
		}).catch(error => {
			document.getElementById('check15').checked = true;
			console.log("Error", error)
		})
	}
	$scope.formatThaoTac = function(thaoTac) {
		if (thaoTac.includes('#')) {
			// Nếu chuỗi thao tác chứa dấu phẩy, cắt chuỗi và thêm thẻ xuống dòng
			var separatedLines = thaoTac.split('#').map(line => line.trim());
			return $sce.trustAsHtml(separatedLines.join('<br>'));
		} else {
			// Ngược lại, trả về nguyên bản
			return thaoTac;
		}
	};
	//CẬP NHẬT HÃNG TÀU
	$scope.update = function() {

		// Cập nhật giá trị city, district, và ward từ các trường HTML
		$scope.form.city = $("#city option:selected").text();
		$scope.form.district = $("#district option:selected").text();
		$scope.form.ward = $("#ward option:selected").text();
		var item = angular.copy($scope.form);
		var url = `/rest/hangtau/${item.idhangtau}`;
		var itemold = $scope.originalData;
		console.log(itemold)
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		// Thêm dữ liệu Tỉnh/Thành phố, Quận/Huyện, và Phường/Xã vào địa chỉ
		item.diachi = $scope.form.diaChi + ', ' + $scope.form.ward + ', ' + $scope.form.district + ', ' + $scope.form.city;

		if (!$scope.form.diaChi && !$scope.form.ward && !$scope.form.district && !$scope.form.city) {
			item.diachi = $scope.form.diachi;
		}
		else if (!$scope.form.city || !$scope.form.diaChi) {
			document.getElementById('check4').checked = true;
			return;
		}
		else if ($scope.form.city && $scope.form.diaChi && !$scope.form.district) {
			document.getElementById('check4').checked = true;
			return;
		} else if ($scope.form.city && $scope.form.diaChi && $scope.form.district && !$scope.form.ward) {
			document.getElementById('check4').checked = true;
			return;
		}

		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.tenhangtau) {
			document.getElementById('check11').checked = true;
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
		var diachi = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.diachi.toLowerCase() === item.diachi.toLowerCase());
		var email = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.email.toLowerCase() === item.email.toLowerCase());
		var sdt = $scope.items.hangtau.some(a => a.idhangtau !== item.idhangtau && a.sdt === item.sdt);
		if (tenHangTau) {
			document.getElementById('check9').checked = true;
			return; // Ngăn cập nhật nếu tên hãng tàu trùng
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

		var ttupdate = "Cập nhật hãng tàu có ID: " + itemold.idhangtau;

		if (itemold.tenhangtau.toLowerCase() !== item.tenhangtau.toLowerCase()) {
			ttupdate += "# Tên: " + itemold.tenhangtau + " thành " + item.tenhangtau;
		}

		if (itemold.sdt !== item.sdt) {
			ttupdate += "# số ĐT: " + itemold.sdt + " thành " + item.sdt;
		}

		if (itemold.email.toLowerCase() !== item.email.toLowerCase()) {
			ttupdate += "# email: " + itemold.email + " thành " + item.email;
		}

		if (itemold.diachi.toLowerCase() !== item.diachi.toLowerCase()) {
			ttupdate += "# đc: " + itemold.diachi + " thành " + item.diachi;
		}

		if (itemold.trangthai.toLowerCase() !== item.trangthai.toLowerCase()) {
			ttupdate += "# trạng thái thành " + item.trangthai;
		}

		console.log(ttupdate); // In ra để kiểm tra giá trị của ttupdate
		var itemlichsu = {
			"hangtau": item,
			"thaotac": ttupdate,
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.hangtau.findIndex(a => a.idhangtau === item.idhangtau);
			$scope.items.hangtau[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			document.getElementById('check3').checked = true;
			$http.post('/rest/hangtau/lichsu/save', itemlichsu)
				.then(function(response) {
					$scope.items.lichsu.push(response.data)
				})
				.catch(function(error) {
					console.log("Error creating LichSuHangTau", error);
				});
		}).catch(error => {
			console.log("Error", error)
			document.getElementById('check15').checked = true;
		});
	}
})