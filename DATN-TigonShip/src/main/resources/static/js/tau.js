const app = angular.module('tau-app', []);
app.controller('tau-ctrl', function ($scope, $http) {
	$scope.items = [];
	$scope.form = {},
    $scope.setform = function () {
        $scope.form = {
          //  ngaynhap:new Date(2023, 7, 21),// Gán ngày mặc định (hoặc giá trị khác) vào biến ngaynhap
            tinhtrang: 'Hoạt động',
            
        }
        $scope.post = true
        $scope.put = false
        $scope.delete = false
    }

    $scope.initialize = function () {
        $http.get("/rest/tau").then(response => {
            $scope.items = response.data;
            $scope.items.forEach(item => {// trước khi bỏ vào chuyển đổi kiểu ngày tháng trong sql thành chuỗi
				item.ngaynhap = new Date(item.ngaynhap)
			})
            
        })
    }
    $scope.initialize()

    $scope.edit = function (id) {
        $scope.post = false;
        $scope.put = true;
        var url = `/rest/tau/${id}`;
        $http.get(url).then(response => {
            $scope.form = response.data;
            $scope.form.ngaynhap = new Date($scope.form.ngaynhap);
        }).catch(err => {
            console.log("Error", err)
        })
    }
    $scope.reset = function () {
        $scope.setform()
    }


    $scope.pager = {
        page: 0,
        size: 4,
        get items() {
            var start = this.page * this.size;
            return $scope.items.tau.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.tau.length / this.size);
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