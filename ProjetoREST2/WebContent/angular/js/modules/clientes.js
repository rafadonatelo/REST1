var app = angular.module("clientesApp", []);


app.controller("teste", function($scope){
	var teste = 0;
});

app.controller("clientesController", function($scope, $http) {
  $http.get('http://localhost:8080/ProjetoREST2/resources/clientes?page=1&sortFields=id&sortDirections=asc').
    success(function(data, status, headers, config) {
    	$scope.paginatorWrapper = data;
    }).
    error(function(data, status, headers, config) {
      // log error
    });
});