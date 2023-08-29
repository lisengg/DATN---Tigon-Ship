const app = angular.module('tau-app', []);
app.controller('tau-ctrl', function ($scope, $http) {
    $scope.items = [];
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
        }).catch(error =>{
            alert("Xóa thành công");
            console.log("Error",error)
        })
    }

    
    $scope.edit = function (id) {
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