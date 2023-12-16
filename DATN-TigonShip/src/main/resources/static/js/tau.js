const app = angular.module('tau-app', ['ngSanitize']);
app.controller('tau-ctrl', function($scope, $http, $sce) {
	$scope.form = { ngaynhap: new Date(), },
		$scope.initialize = function() {
			$http.get("/rest/tau").then(response => {
				$scope.items = response.data;
				$scope.items.tau.forEach(item => {
					item.ngaynhap = new Date(item.ngaynhap);
				});
				$scope.post = true;
				$scope.put = false;
				// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
				initDataTable($scope.items);
			});
		}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.tau, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'tentau' },
				{ data: 'hangtau.tenhangtau' },
				{ data: 'soghe' },
				{
					data: 'ngaynhap',
					render: function(data, type, full, meta) {
						if (type === 'display') {
							// Định dạng ngày theo dd/MM/yyyy
							return moment(data).format('DD/MM/YYYY'); // Sử dụng thư viện Moment.js hoặc thư viện khác tương tự nếu cần
						}
						return data; // Trả về dữ liệu gốc cho các loại khác
					}
				},
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
		return $scope.items.findIndex(a => a.idtau == id);
	}
	$scope.reset = function() {
		$scope.form = {
			ngaynhap: new Date(),// Gán ngày mặc định (hoặc giá trị khác) vào biến ngaynhap
			trangthai: 'Đang hoạt động',
		}
		$scope.post = true
		$scope.put = false
	}
	//Hiển thị lên form
	$scope.edit = function(id) {
		$scope.originalData = angular.copy(id); // Lưu trữ dữ liệu ban đầu
		$scope.form = angular.copy(id);
		console.log($scope.form)
		$scope.post = false;
		$scope.put = true;
	}
	//THÊM TÀU
$scope.create = function() {

		// Kiểm tra tên tàu đã được chọn
		if (!$scope.form.tentau) {
			document.getElementById('check4').checked = true;
			return;
		}
		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.hangtau || !$scope.form.hangtau.idhangtau) {
			document.getElementById('check5').checked = true;
			return;
		}
		// Kiểm tra tên tàu đã được chọn
		if (!$scope.form.soghe) {
			document.getElementById('check6').checked = true;
			return;
		}
		var index = $scope.items.hangtau.findIndex(a => a.idhangtau === $scope.form.hangtau.idhangtau)
		var item = {
			"tentau": $scope.form.tentau,
			"soghe": $scope.form.soghe,
			"tinhtrang": $scope.form.tinhtrang,
			"ngaynhap": $scope.form.ngaynhap = new Date(),
			"hangtau": $scope.items.hangtau[index],
			"trangthai": $scope.form.trangthai
		}
		var url = `/rest/tau/save`;
		// Kiểm tra xem tên tàu đã được chọn có trùng với tàu khác không
		var Tau = $scope.items.tau.some(a => a.idtau !== item.idtau && a.tentau.toLowerCase() === item.tentau.toLowerCase());
		if (Tau) {
			document.getElementById('check8').checked = true;
			return;
		}
		$http.post(url, item).then(response => {
			$scope.items.tau.push(response.data);
			var table = $('#table2').DataTable();
			table.row.add(response.data).draw();
			document.getElementById('check3').checked = true;
			var itemlichsu = {
				"tau": response.data,
				"thaotac": "Đã thêm mới tàu có ID : " + response.data.idtau,
			}
			// thêm lịch sử tàu
			$http.post('/rest/tau/lichsu/save', itemlichsu)
				.then(function(response) {
					$scope.items.lichsu.push(response.data)
				})
				.catch(function(error) {
					console.log("Error creating LichsuTau", error);
				});
			var itemsghe = [];
			var soghe = $scope.form.soghe
			console.log("Số ghế ngồi:" + soghe)
			for (let i = 1; i <= soghe; i++) {
				let rowIndex = Math.ceil(i / 10); // Số thứ tự của dòng (từ 1 đến 16)
				let columnIndex = (i - 1) % 10 + 1; // Số thứ tự của cột (từ 1 đến 10)
				let letter = String.fromCharCode('A'.charCodeAt(0) + rowIndex - 1);
				let tt = "Hoạt động"
				let item = {
					khoang: i <= 100 ? 1 : 2,
					tenghe: `${letter}${columnIndex}`,
					tau: response.data,
					trangthai: tt
				};
				itemsghe.push(item);
			}
			// Thực hiện yêu cầu POST để lưu trữ 160 đối tượng Ghế Ngồi
			$http.post('/rest/tau/ghengoi/saveAll', itemsghe)
				.then(function(response) {
					console.log(response.data); // Xem response của yêu cầu POST
					$scope.items.ghengoi.push(response.data);
					console.log("Thành công");
				})
				.catch(function(error) {
					console.log("Lỗi khi thêm ghế ngồi", error);
				});
			$scope.reset();
		}).catch(error => {
			document.getElementById('check2').checked = true;
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
	//CẬP NHẬT TÀU
	$scope.update = function() {
		var itemold = $scope.originalData
		var item = angular.copy($scope.form);
		var url = `/rest/tau/${item.idtau}`;
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		if (!$scope.form.tentau) {
			document.getElementById('check4').checked = true;
			return;
		}
		// Kiểm tra xem tên tàu đã được chọn có trùng với tàu khác không
		var Tau = $scope.items.tau.some(a => a.idtau !== item.idtau && a.tentau.toLowerCase() === item.tentau.toLowerCase());
		if (Tau) {
			document.getElementById('check8').checked = true;
			return;
		}
		// Cập nhật thông tin của hangtau trong item
		var indexHangTau = $scope.items.hangtau.findIndex(a => a.idhangtau === $scope.form.hangtau.idhangtau);
		item.hangtau = $scope.items.hangtau[indexHangTau];

		var ttupdate = "Cập nhật tàu có ID: " + itemold.idtau;
		if (itemold.tentau.toLowerCase() !== item.tentau.toLowerCase()) {
			ttupdate += "# Tên tàu: " + itemold.tentau + " thành " + item.tentau;
		}

		if (itemold.hangtau.tenhangtau.toLowerCase() !== item.hangtau.tenhangtau.toLowerCase()) {
			ttupdate += "# Tên hãng tàu: " + itemold.hangtau.tenhangtau + " thành " + item.hangtau.tenhangtau;
		}

		if (itemold.soghe !== item.soghe) {
			ttupdate += "# số ghế: " + itemold.soghe + " thành " + item.soghe;
		}

		if (!angular.equals(itemold.ngaynhap, item.ngaynhap)) {
			var ngaynhap = moment(item.ngaynhap).format('DD/MM/YYYY');
			var ngaynhapmoi = moment(itemold.ngaynhap).format('DD/MM/YYYY');
			ttupdate += "#Ngày nhập: " + ngaynhapmoi + " thành " + ngaynhap;
		}

		if (itemold.trangthai.toLowerCase() !== item.trangthai.toLowerCase()) {
			ttupdate += "# trạng thái thành " + item.trangthai;
		}
		console.log(ttupdate);
		var itemlichsu = {
			"tau": item,
			"thaotac": ttupdate,
		}
		// Hiển thị ttupdate trong một phần tử HTML
		$http.put(url, item).then(response => {
			var index = $scope.items.tau.findIndex(a => a.idtau === item.idtau);
			$scope.items.tau[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			document.getElementById('check3').checked = true;
			$http.post('/rest/tau/lichsu/save', itemlichsu)
				.then(function(response) {
					$scope.items.lichsu.push(response.data)
				})
				.catch(function(error) {
					console.log("Error creating LichSuTau", error);
				});
		}).catch(err => {
			document.getElementById('check2').checked = true;
			console.log("Error", error);
		});
	}
})