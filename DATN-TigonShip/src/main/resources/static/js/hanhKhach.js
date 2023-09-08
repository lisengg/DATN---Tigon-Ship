const app = angular.module('hanhkhach-app', []);
app.controller('hanhkhach-ctrl', function ($scope, $http) {
    $scope.form = {}
    $scope.initialize = function () {
    $http.get("/rest/hanhkhach").then(response => {
        $scope.items = response.data;
        console.log($scope.items)
    })}
    $scope.initialize()
    $scope.pager = {
        page: 0,
        size: 10,
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