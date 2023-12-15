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
				{
                data: null,
                render: function (data, type, full, meta) {
                    // Kiểm tra xem 'datve.taikhoan.hovaten' có sẵn không
                    var userName = data.datve && data.datve.taikhoan && data.datve.taikhoan.hovaten
                        ? data.datve.taikhoan.hovaten
                        : data.datve.hanhkhach.hovaten; // Sử dụng 'tenhankhach' nếu 'datve.taikhoan.hovaten' không có sẵn

                    return userName;
                }
            },
				{
					data: 'ngaylap',
					render: function(data, type, full, meta) {
						if (type === 'display') {
							// Định dạng ngày theo dd/MM/yyyy
							return moment(data).format('DD/MM/YYYY'); // Sử dụng thư viện Moment.js hoặc thư viện khác tương tự nếu cần
						}
						return data; // Trả về dữ liệu gốc cho các loại khác
					}
				},
				{
					data: 'tongtien',
					render: function(data, type, row) {
						// Format the total amount as currency
						return formatCurrency(data);
					}
				},
				{ data: 'trangthai' },
				{ data: 'loaithanhtoan' },
				// Cột mới chứa nút bấm
				{
					data: null,
					defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="custom-button"><i class="fas fa-eye"></i></button>'
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

	function formatCurrency(amount) {
		 return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
	}

	$scope.initialize()
	$scope.edit = function(id) {
		$scope.selectedItem = id;
	}
})