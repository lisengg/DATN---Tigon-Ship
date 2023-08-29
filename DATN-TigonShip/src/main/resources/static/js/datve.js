const app = angular.module('datve-app', []);
app.controller('datve-ctrl', function ($scope, $http) {
    
    $scope.initialize = function () {
    $http.get("/rest/datve").then(response => {
        $scope.items = response.data;
        console.log($scope.items);
    })}
    $scope.initialize()

})