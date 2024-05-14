'use strict';

angular.module('sistemaApp').factory('SistemaService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:9090/sistema/';

    var factory = {
        fetchAllSistemas: fetchAllSistema,
        createSistema: createSistema,
        updateSistema:updateSistema,
        deleteSistema:deleteSistema
    };

    return factory;

    function fetchAllSistema() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Sistemas'+ errResponse.toString());
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function createSistema(sistema) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, sistema)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating Sistema');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function updateSistema(sistema, id) {
        console.log('updateSistema ', id + ' ' +REST_SERVICE_URI+id);
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+id, sistema)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating Sistema' + errResponse.getOwnPropertyNames());
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deleteSistema(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting Sistema');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);
