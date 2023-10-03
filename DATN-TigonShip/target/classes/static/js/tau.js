const app = angular.module('tau-app', []);
app.controller('tau-ctrl', function ($scope, $http) {
    $scope.items = [];
    $scope.ghe = {},
    $scope.searchKeyword = '';
    $scope.reset = function () {
        $scope.form = {
            ngaynhap:new Date(),// Gán ngày mặc định (hoặc giá trị khác) vào biến ngaynhap
            tinhtrang: 'Hoạt động',
        }
        $scope.post = true
        $scope.put = false
        $scope.dele = false
    }

    $scope.initialize = function () {
        $http.get("/rest/tau").then(response => {
            $scope.items = response.data;
            $scope.reset()
            console.log($scope.items.tau);
        })
    }
    
    $scope.initialize()
    $scope.save = function () {
        var index = $scope.items.hangtau.findIndex(a => a.idhangtau === $scope.form.hangtau.idhangtau)
        var item = {
            "tentau": $scope.form.tentau,
            "soghe": $scope.form.soghe,
            "tinhtrang": $scope.form.tinhtrang,
            "ngaynhap":  $scope.form.ngaynhap = new Date(),
            "hangtau": $scope.items.hangtau[index]
        }
        console.log(item)
        var url = `/rest/tau/save`;
        $http.post(url,item).then(response => {
            $scope.items.tau.push(response.data);
            alert("Thêm tàu mới thành công")
            $scope.reset();
        }).catch(error => {
            alert("Thêm tàu mới thất bại");
            console.log("Error",error)
        })
    }
    $scope.update = function () {
        var index = $scope.items.hangtau.findIndex(a => a.idhangtau === $scope.form.hangtau.idhangtau)
        var item = {
            "tentau": $scope.form.tentau,
            "soghe": $scope.form.soghe,
            "tinhtrang": $scope.form.tinhtrang,
            "ngaynhap":  $scope.form.ngaynhap = new Date(),
            "hangtau": $scope.items.hangtau[index]
        }

        console.log(item)
        var url = `/rest/tau/${$scope.form.idtau}`;
        $http.put(url, item).then(response => {
            var index = $scope.items.tau.findIndex(a => a.idtau === $scope.form.idtau);
           // console.log($scope.db[index])
            $scope.items.tau[index] = response.data;
            alert("Cập nhật tàu thành công")
            $scope.reset();
        }).catch(err => {
            alert("Lỗi cập nhật tàu");
            console.log("Error", error);
        });
    }
    $scope.delete = function (id){
        $http.delete(`/rest/tau/${id}`).then(response => {
            var index = $scope.items.tau.findIndex(a => a.idtau === $scope.form.idtau);
            $scope.items.tau.splice(index,1);
            alert("Xóa thành công");
            $scope.reset()
        }).catch(error =>{
            alert("Xóa thành công");
            console.log("Error",error)
        })
    }
 /*    $scope.edit = function (id) {
        $scope.post = false;
        $scope.put = true;
        $scope.dele = true;
        var url = `/rest/tau/${id}`;
        $http.get(url).then(response => {
            $scope.form = response.data;
            $scope.form.ngaynhap = new Date($scope.form.ngaynhap);
            console.log($scope.form.ngaynhap)

        }).catch(err => {
            console.log("Error", err)
        })
    } */
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
            $scope.post = false;
            $scope.put = true;
            $scope.dele = true;
    }
    
  //Close search form
	$scope.reset1 = function() {
		$scope.searchKeyword = '';
	}
	
	// Hàm cập nhật dữ liệu đã phân trang khi tìm kiếm thay đổi
	$scope.$watch('searchKeyword', function(newVal, oldVal) {
		$scope.searchPager.first();

		// Cập nhật dữ liệu đã phân trang
		$scope.updatePagedData();
	});

	// Hàm cập nhật dữ liệu đã phân trang
	$scope.updatePagedData = function() {
		// Tính toán lại dữ liệu đã phân trang dựa trên trang hiện tại và số mục trên mỗi trang
		var startIndex = ($scope.pager.page - 1) * 5;
		var endIndex = startIndex + 5;
		$scope.pager.items.tau = $scope.pager.items.tau.slice(startIndex, endIndex);
	};
    //Phân trang
	$scope.searchPager = {
		page: 0, // Bắt đầu từ trang đầu tiên
		size: 4, // Số mục trên mỗi trang
		get items() {
			// Tính toán phạm vi chỉ mục dựa trên trang và kích thước hiện tại
			var startIndex = (this.page - 1) * this.size;
			var endIndex = startIndex + this.size;

			// Lọc các mục dựa trên từ khóa tìm kiếm
			var filteredItems = $scope.items.tau.filter(function(item) {
				return item.tentau.toLowerCase().includes($scope.searchKeyword.toLowerCase()) || 
                item.hangtau.tenhangtau.toLowerCase().includes($scope.searchKeyword.toLowerCase());
			});
			return filteredItems.slice(startIndex, endIndex);
		},
		get count() {
			// Tính tổng số trang dựa trên các mục được lọc
			var filteredItems = $scope.items.tau.filter(function(item) {
				return item.tentau.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
                item.hangtau.tenhangtau.toLowerCase().includes($scope.searchKeyword.toLowerCase());
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