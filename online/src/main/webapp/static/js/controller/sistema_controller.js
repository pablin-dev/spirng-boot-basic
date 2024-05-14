'use strict';

angular.module('sistemaApp').controller('SistemaController', ['$scope', 'SistemaService', function($scope, SistemaService) {
    var self = this;
    self.sistema={id:null,name:'',description:''};
    self.sistemas=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;


    fetchAllSistemas();

    function fetchAllSistemas(){
        SistemaService.fetchAllSistemas()
            .then(
            function(d) {
                self.sistemas = d;
            },
            function(errResponse){
                console.error('Error while fetching Sistemas' + errResponse.toString());
            }
        );
    }

    function createSistema(sistema){
        SistemaService.createSistema(sistema)
            .then(
            fetchAllSistemas,
            function(errResponse){
                console.error('Error while creating Sistema'+ errResponse.toString());
            }
        );
    }

    function updateSistema(sistema, id){
        SistemaService.updateSistema(sistema, id)
            .then(
            fetchAllSistemas,
            function(errResponse){
                console.error('Error while updating Sistema'+ errResponse.toString());
            }
        );
    }

    function deleteSistema(id){
        SistemaService.deleteSistema(id)
            .then(
            fetchAllSistemas,
            function(errResponse){
                console.error('Error while deleting Sistema'+ errResponse.toString());
            }
        );
    }

    function submit() {
        if(self.sistema.id===null){
            console.log('Saving New Sistema', self.sistema);
            createSistema(self.sistema);
        }else{
            updateSistema(self.sistema, self.sistema.id);
            console.log('Sistema updated with id ', self.sistema.id);
        }
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.sistemas.length; i++){
            if(self.sistemas[i].id === id) {
                self.sistema = angular.copy(self.sistemas[i]);
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.sistema.id === id) {
            reset(); //Limpiamos el form
        }
        deleteSistema(id);
    }

    function reset(){
        self.sistema={id:null,name:'',description:''};
        $scope.sistemaForm.$setPristine(); //reset Form
    }

}]);
