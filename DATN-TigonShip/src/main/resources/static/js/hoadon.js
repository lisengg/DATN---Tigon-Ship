const app = angular.module('hoadon-app', []);
app.controller('hoadon-ctrl', function ($scope, $http) {
	$scope.items = [];
$scope.selectedItem = {};
    $scope.initialize = function () {
        $http.get("/rest/hoadon").then(response => {
            $scope.items = response.data;
            $scope.items.forEach(item => {// trước khi bỏ vào chuyển đổi kiểu ngày tháng trong sql thành chuỗi
				item.ngaynhap = new Date(item.ngaynhap)
			})
            
        })
    }
    $scope.initialize()

    $scope.edit = function (item) {
		 console.log(item);
		$scope.hdct = true
		 $scope.selectedItem = item;
    }
    
    $scope.reset = function () {
        $scope.hdct = false
         
    }


    $scope.pager = {
        page: 0,
        size: 4,
        get items() {
            var start = this.page * this.size;
            return $scope.items.hoadon.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.hoadon.length / this.size);
        },
        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        last() {
            this.page = this.count - 1;
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        }
    }
})