(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('CepDialogController', CepDialogController);

    CepDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cep', 'Address'];

    function CepDialogController ($scope, $stateParams, $uibModalInstance, entity, Cep, Address) {
        var vm = this;
        vm.cep = entity;
        vm.addresss = Address.query();
        vm.load = function(id) {
            Cep.get({id : id}, function(result) {
                vm.cep = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:cepUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.cep.id !== null) {
                Cep.update(vm.cep, onSaveSuccess, onSaveError);
            } else {
                Cep.save(vm.cep, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
