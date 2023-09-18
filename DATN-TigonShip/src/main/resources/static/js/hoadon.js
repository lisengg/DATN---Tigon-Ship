const app = angular.module('hoadon-app', []);
app.controller('hoadon-ctrl', function($scope, $http) {
	$scope.items = [];
	$scope.searchKeyword = '';
	
	$scope.initialize = function() {
		$http.get("/rest/hoadon").then(response => {
			$scope.items = response.data;
			$scope.items.forEach(item => {// trước khi bỏ vào chuyển đổi kiểu ngày tháng trong sql thành chuỗi
				item.ngaynhap = new Date(item.ngaynhap)

			})

		})
	}
	$scope.initialize()

	$scope.edit = function(item) {
		$scope.selectedItem = item;
	}

	//Close search form
	$scope.reset1 = function() {
		$scope.searchKeyword = '';
	}

	// Hàm cập nhật dữ liệu đã phân trang khi tìm kiếm thay đổi
	$scope.$watch('searchKeyword', function(newVal, oldVal) {
		// Reset the searchPager to the first page when the searchKeyword changes
		$scope.searchPager.first();

		// Cập nhật dữ liệu đã phân trang
		$scope.updatePagedData();
	});

	// Hàm cập nhật dữ liệu đã phân trang
	$scope.updatePagedData = function() {
		// Tính toán lại dữ liệu đã phân trang dựa trên trang hiện tại và số mục trên mỗi trang
		var startIndex = ($scope.pager.page - 1) * 5;
		var endIndex = startIndex + 5;
		$scope.pager.items.hoadon = $scope.pager.items.hoadon.slice(startIndex, endIndex);
	};
	//Phân trang
	$scope.searchPager = {
		page: 0, // Bắt đầu từ trang đầu tiên
		size: 5, // Số mục trên mỗi trang
		get items() {
			// Tính toán phạm vi chỉ mục dựa trên trang và kích thước hiện tại
			var startIndex = (this.page - 1) * this.size;
			var endIndex = startIndex + this.size;

			// Lọc các mục dựa trên từ khóa tìm kiếm
			var filteredItems = $scope.items.hoadon.filter(function(item) {
				return item.datve.hanhkhach.hovaten.toLowerCase().includes($scope.searchKeyword.toLowerCase());
			});
			return filteredItems.slice(startIndex, endIndex);
		},
		get count() {
			// Tính tổng số trang dựa trên các mục được lọc
			var filteredItems = $scope.items.hoadon.filter(function(item) {
				return item.datve.hanhkhach.hovaten.toLowerCase().includes($scope.searchKeyword.toLowerCase());
			});
			return Math.ceil(filteredItems.length / this.size);
		},
		first() {
			this.page = 1;
		},
		prev() {
			this.page--;
			if (this.page < 1) {
				this.page = 1;
			}
		},
		next() {
			this.page++;


			if (this.page > this.count) {
				this.page = this.count;
			}
		},
		last() {
			this.page = this.count;
		}
	}
})