const app = angular.module('ghengoi-app', []);
app.controller('ghengoi-ctrl', function($scope, $http) {

	$scope.index= 1
	$scope.items1= [];
	$scope.items2= [];
	$scope.mong = [];
	$scope.capnhat= false
	$scope.trangthai = false;
	$scope.idghedachon= "";

	$scope.initialize = function() {
		$http.get(`/rest/ghengoi/tau`).then(response => {
			$scope.tau = response.data;
			if ($scope.tau.length > 0 && $scope.tau[0].idtau !== null) {
				$scope.selectedTau = $scope.tau[0].idtau;
			}
		});
		$http.get(`/rest/ghengoi1/1`).then(response => {
			$scope.items1 = response.data;
		
		});
		$http.get(`/rest/ghengoi2/1`).then(response => {
				$scope.items2 = response.data;
				
		});
	}
	$scope.initialize();

	$scope.onSelectChange = function(selectedTau) { // Lấy danh sách ghế ngồi thong qua id tàu + khoan 1/2
		$scope.index = selectedTau; // Vì bạn đã chọn item.idtau làm giá trị của selectedTau
		console.log($scope.index);
			console.log($scope.index);
			$http.get(`/rest/ghengoi1/${$scope.index} `).then(response => {
				$scope.items1 = response.data;
			});
			$http.get(`/rest/ghengoi2/${$scope.index} `).then(response => {
				$scope.items2 = response.data;
			});
	}
	$scope.searchGheByIDghe = function(idghe){
		$scope.idghedachon= idghe;
		$scope.trangthai= true
		$scope.capnhat=true
		$http.get(`/rest/ghengoi/ghengoi/${idghe}`).then(response => {
			$scope.mong = response.data;
		});
	};
	$scope.onChangeTrangThai = function() {
		console.log($scope.mong.trangthai);
	};
	$scope.update = function(){
		$http({
			method: 'PUT',
			url: `/rest/ghengoi/update/${$scope.idghedachon}`,
			data: $scope.mong.trangthai,  // Truyền giá trị trangthai trực tiếp
			headers: {'Content-Type': 'text/plain'}  // Đặt kiểu dữ liệu là text/plain
		
		}).then(
			function successCallback(response) {
				$scope.onSelectChange($scope.index);
				console.log($scope.index)
					//CẬP NHẬT THÀNH CÔNG GHI Ở ĐÂY.
			/* 	document.getElementById('check3').checked = true; */
				alert("Cập nhật ghế thành công")
			},
			function errorCallback(error) {
				console.log("Lỗi cập nhật: ", error);
			}
		);
		
	};
	

})