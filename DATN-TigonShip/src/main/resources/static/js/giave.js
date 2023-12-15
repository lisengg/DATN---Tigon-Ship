const app = angular.module('giave-app', []);
app.controller('giave-ctrl', function($scope, $http, $sce) {
	$scope.form = {},
		$scope.initialize = function() {
			$http.get("/rest/giave").then(response => {
				$scope.items = response.data;
/*				$scope.items.giave.forEach(item => {
					item.ngaybatdau = new Date(item.ngaybatdau);
					item.ngayketthuc = new Date(item.ngayketthuc);
				});*/
				$scope.post = true;
				$scope.put = false;
				// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
				initDataTable($scope.items);
			});
		}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.giave, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'loaive.loaive' },
				{ data: 'tuyen.tentuyen' },
				{ data: 'gia',
					render: function(data, type, row) {
						return formatCurrency(data);
					}
				 },
				{ data: 'loaihk.loaihk' },
/*				{
					data: 'ngaybatdau',
					render: function(data, type, full, meta) {
						if (type === 'display') {
							// Định dạng ngày theo dd/MM/yyyy
							return moment(data).format('DD/MM/YYYY'); // Sử dụng thư viện Moment.js hoặc thư viện khác tương tự nếu cần
						}
						return data; // Trả về dữ liệu gốc cho các loại khác
					}
				},
				{
					data: 'ngayketthuc',
					render: function(data, type, full, meta) {
						if (type === 'display') {
							// Định dạng ngày theo dd/MM/yyyy
							return moment(data).format('DD/MM/YYYY'); // Sử dụng thư viện Moment.js hoặc thư viện khác tương tự nếu cần
						}
						return data; // Trả về dữ liệu gốc cho các loại khác
					}
				},*/
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
			var rowData = table.row($(this).parents('tr')).data();
			$scope.$apply(function() {
				$scope.edit(rowData);
			});
		});
	}
	
	function formatCurrency(amount) {
		 return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
	}
	
	$scope.initialize()
	$scope.index_of = function(id) {
		return $scope.items.findIndex(p => p.idgiave == id);
	}
	//Hiển thị lên form
	$scope.edit = function(id) {
		$scope.originalData = angular.copy(id); // Lưu trữ dữ liệu ban đầu
		$scope.form = angular.copy(id);
		$scope.post = false;
		$scope.put = true;
	}
	//Xóa form
	$scope.reset = function() {
		$scope.form = {
			/*ngaybatdau: null,
			ngayketthuc: null,*/
			trangthai: 'Đang hoạt động',
		};
		$scope.post = true;
		$scope.put = false;
	}
	//Thêm giá vé mới
	$scope.create = function() {
		// Kiểm tra xem tên loại vé đã được chọn
		if (!$scope.form.loaive || !$scope.form.loaive.idloaive) {
			document.getElementById('check5').checked = true;
			return;
		}
		// Kiểm tra xem tuyen đã được chọn
		if (!$scope.form.tuyen || !$scope.form.tuyen.idtuyen) {
			document.getElementById('check6').checked = true;
			return;
		}
		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.loaihk || !$scope.form.loaihk.idloaihk) {
			document.getElementById('check11').checked = true;
			return;
		}
		var index = $scope.items.loaive.findIndex(a => a.idloaive == $scope.form.loaive.idloaive)
		var index1 = $scope.items.tuyen.findIndex(a => a.idtuyen == $scope.form.tuyen.idtuyen)
		var index2 = $scope.items.loaihk.findIndex(a => a.idloaihk == $scope.form.loaihk.idloaihk)
		var item = {
			"loaive": $scope.items.loaive[index],
			"tuyen": $scope.items.tuyen[index1],
			"loaihk": $scope.items.loaihk[index2],
			"gia": $scope.form.gia,
			/*"ngaybatdau": $scope.form.ngaybatdau = new Date(),
			"ngayketthuc": $scope.form.ngayketthuc = new Date(),*/
			"trangthai": $scope.form.trangthai
		}
		var url = `/rest/giave/save`;
		// Kiểm tra giá vé không được để trống
		if (!item.gia) {
			document.getElementById('check4').checked = true;
			return;
		}
		// Kiểm tra giá vé là số hợp lệ
		if (item.gia <= 100000 || item.gia >= 700000) {
			document.getElementById('check7').checked = true;
			return;
		}
		// Kiểm tra ngày bắt đầu và ngày kết thúc
		/*if (item.ngaybatdau > item.ngayketthuc) {
			document.getElementById('check9').checked = true;
			return;
		}*/
		// Kiểm tra xem tên loại vé và tên tuyến đã được chọn có trùng với giá vé khác không
		var isDuplicate = $scope.items.giave.some(function(giave) {
			return giave.idgiave !== item.idgiave &&
				giave.loaive.idloaive === item.loaive.idloaive &&
				giave.tuyen.idtuyen === item.tuyen.idtuyen &&
				giave.loaihk.idloaihk === item.loaihk.idloaihk; 
		});
		if (isDuplicate) {
			document.getElementById('check8').checked = true;
		} else {
			$http.post(url, item).then(response => {
				$scope.items.giave.push(response.data);
				var table = $('#table2').DataTable();
				table.row.add(response.data).draw();
				document.getElementById('check3').checked = true; // Hiển thị form thành công
				var itemlichsu = {
					"giave": response.data,
					"thaotac": "Đã thêm mới giá vé có ID : " + response.data.idgiave,
				}
				$http.post('/rest/giave/lichsu/save', itemlichsu)
					.then(function(response) {
						$scope.items.lichsu.push(response.data)
					})
					.catch(function(error) {
						console.log("Error creating LichSuGiaVe", error);
					});
				$scope.reset();
			}).catch(error => {
				document.getElementById('check2').checked = true;
				console.log("Error", error)
			})
		}
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
	//Cập nhật giá vé
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/giave/${item.idgiave}`;
		var itemold = $scope.originalData;
		console.log(itemold)
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		// Kiểm tra giá vé không được để trống
		if (!item.gia) {
			document.getElementById('check4').checked = true;
			return;
		}
		// Kiểm tra giá vé là số hợp lệ
		if (item.gia <= 99999 || item.gia >= 700001) {
			document.getElementById('check7').checked = true;
			return;
		}
		// Kiểm tra ngày bắt đầu và ngày kết thúc
		/*if (item.ngaybatdau > item.ngayketthuc) {
			document.getElementById('check9').checked = true;
			return;
		}*/
		// Kiểm tra xem tên loại vé và tên tuyến đã được chọn có trùng với giá vé khác không
		var isDuplicate = $scope.items.giave.some(function(giave) {
			return giave.idgiave !== item.idgiave &&
				giave.loaive.idloaive === item.loaive.idloaive &&
				giave.tuyen.idtuyen === item.tuyen.idtuyen &&
				giave.loaihk.idloaihk === item.loaihk.idloaihk;;
		});

		if (isDuplicate) {
			document.getElementById('check8').checked = true;
		} else {
			var ttupdate = "Cập nhật giá vé có ID: " + itemold.idgiave;
			if (itemold.trangthai !== item.trangthai) {
				ttupdate += "#Trạng thái thành " + item.trangthai;
			}
			if (itemold.giave !== item.giave) {
				ttupdate += "#Giá vé: " + itemold.giave + " thành " + item.trangthai;
			}
			/*if (!angular.equals(itemold.ngaybatdau, item.ngaybatdau)) {
				var batdau = moment(item.ngaybatdau).format('DD/MM/YYYY');
				var batdaumoi = moment(itemold.ngaybatdau).format('DD/MM/YYYY');
				ttupdate += "#Ngày bắt đầu: " + batdaumoi + " thành " + batdau;
			}
			if (!angular.equals(itemold.ngayketthuc, item.ngayketthuc)) {
				var ketthuc = moment(item.ngayketthuc).format('DD/MM/YYYY');
				var ketthucmoi = moment(itemold.ngayketthuc).format('DD/MM/YYYY');
				ttupdate += "#Ngày kết thúc: " + ketthucmoi + " thành " + ketthuc;
			}*/
			console.log(ttupdate);
			var itemlichsu = {
				"giave": item,
				"thaotac": ttupdate,
			}
			$http.put(url, item).then(response => {
				var index = $scope.items.giave.findIndex(p => p.idgiave == item.idgiave);
				$scope.items.giave[index] = item;
				var table = $('#table2').DataTable();
				var row = table.row(index);
				row.data(item).draw();
				document.getElementById('check3').checked = true; // Hiển thị form thành công
				$http.post('/rest/giave/lichsu/save', itemlichsu)
					.then(function(response) {
						$scope.items.lichsu.push(response.data)
					})
					.catch(function(error) {
						console.log("Error creating LichSuGiaVe", error);
					});
			}).catch(error => {
				document.getElementById('check2').checked = true;
				console.log("Error", error)

			});
		}
	}
})
