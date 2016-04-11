var appControllers = angular.module('AppControllers',[ ]);

appControllers.controller('IndexCtrl', ['$scope', '$route', '$location', '$http', 
	function($scope, $route, $location, $http) {
		var request = $http({
				method : 'GET',
				url : '/rest/',
				header : {'accpet' : 'application/json' }
			});

		request.success(function(data) {
		});

	}
]);

appControllers.controller('SigninCtrl', ['$rootScope', '$scope', '$route', '$http', '$cookieStore', '$location',
	function($rootScope, $scope, $route, $http, $cookieStore, $location) {

		$scope.error = $route.current.params.error;
		if ($scope.error) {
			$rootScope.alert(true, '로그인 하셔야 합니다.');
		}

		$scope.fnSignin = function(user) {
			if ($scope.form.$invalid) return;

			$http({
				method : 'POST',
				url : 'http://localhost:8080/login',
				//data : user,
//                data: [
//                        'email=' + encodeURIComponent(user.email),
//                        'password=' + encodeURIComponent(user.password)
//                ].join('&'),

                params : user,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(data, status, headers, config) {
				var id = data.id;
                if(id == "1") {
                    $location.url('/mypage');
                }
                else {
                    $location.url('/index');
                }

			}).error(function (data, status, headers, config) {
                /* 서버와의 연결이 정상적이지 않을 때 처리 */
                console.log(status);
            });
		};
	}
]);

appControllers.controller('MypageCtrl', ['$scope', '$route', '$location', '$http',
	function($scope, $route, $location, $http) {

		$scope.user = { };		

		var request = $http({
			method : 'GET',
			url : '/rest/mypage',
			header : {'accpet' : 'application/json' }
		});

		request.success(function(data) {
			$scope.user = data;
		});

		request.error(function() {
			$location.url('/signin?error=true');
		});

	}
]);
