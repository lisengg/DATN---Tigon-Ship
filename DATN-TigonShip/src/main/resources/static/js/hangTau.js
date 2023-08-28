const app = angular.module('hangtau-app', []);
app.controller('hangtau-ctrl', function ($scope, $http) {
    $scope.form = {}
    $scope.initialize = function () {
    $http.get("/rest/hangtau").then(response => {
        $scope.items = response.data;
        $scope.post = true
        $scope.put = false
        $scope.delete = false
    })}
    $scope.initialize()

    $scope.index_of = function (id) {
        return $scope.items.findIndex(a => a.idhangtau == id);
    }

    $scope.reset = function () {
        $scope.form = null;
        $scope.post = true;
        $scope.put = false;
        $scope.delete = false;
    }

    $scope.edit = function (id) {
        var url = `/rest/hangtau/${id}`;
        $http.get(url).then(response => {
            $scope.form = response.data;
            $scope.post = false;
            $scope.put = true;
            $scope.delete = true;
        }).catch(err => {
            console.log("Error", err)
        })
    }
    $scope.save = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/hangtau/save`;
        $http.post(url,item).then(response => {
            $scope.items.push(response.data);
            alert("Thêm loại hãng tàu mới thành công")
            $scope.reset();
        }).catch(error => {
            alert("Thêm loại hãng tàu mới thất bại");
            console.log("Error",error)
        })
    }
    $scope.update = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/hangtau/${item.idhangtau}`;
        $http.put(url, item).then(response => {
            var index = $scope.items.findIndex(a => a.idhangtau === item.idhangtau);
            $scope.items[index] = item;
            $scope.reset();                                         
            alert("Cập nhật hãng tàu thành công")
        }).catch(error=>{
            console.log("Error",error)
            alert("Cập nhật hãng tàu thất bại")
        })
    }


    $scope.pager = {
        page: 0,
        size: 5,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.length / this.size);
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