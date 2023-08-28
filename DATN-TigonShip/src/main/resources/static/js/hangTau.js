const app = angular.module('hangtau-app', []);
app.controller('hangtau-ctrl', function ($scope, $http) {
    
    $scope.initialize = function () {
    $http.get("/rest/hangtau").then(response => {
        $scope.items = response.data;
        console.log($scope.items);
    })}
    $scope.initialize()

})